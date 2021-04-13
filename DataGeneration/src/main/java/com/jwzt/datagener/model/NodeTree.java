/**
 * 
 */
package com.jwzt.datagener.model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;



public class NodeTree {
	private static Logger logger = Logger.getLogger(NodeTree.class);
	public static String site_id;
	public static String version;
	public static Map<String, Node> nodeTree = new TreeMap<String, Node>();

	/***
	 * 缓存里面取数据
	 */
	public static HashMap<Integer, List<Node>> nodeMap = new HashMap<Integer, List<Node>>();
	/**
	 * @return the site_id
	 */
	public static String getSite_id() {
		return site_id;
	}

	/**
	 * @param site_id the site_id to set
	 */
	public static void setSite_id(String site_id) {
		NodeTree.site_id = site_id;
	}

	/**
	 * @return the version
	 */
	public static String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public static void setVersion(String version) {
		NodeTree.version = version;
	}

	/**
	 * @return the nodeTree
	 */
	public static Map<String, Node> getNodeTree() {
		return nodeTree;
	}

	/**
	 * @param nodeTree the nodeTree to set
	 */
	public static void setNodeTree(Map<String, Node> nodeTree) {
		NodeTree.nodeTree = nodeTree;
	}

	public static boolean createNodeTree(String xmlpath) throws Exception {
		File file = new File(xmlpath);
		if (!file.exists()) {
			throw new Exception("nodexml 文件不存在"+xmlpath);
		}
		SAXReader reader = new SAXReader();
		Document doc = null;
		try {
			doc = reader.read(file);

		} catch (DocumentException e) {
			e.printStackTrace();
			logger.info("解析nodexml出现异常",e);
		}
		Element root = doc.getRootElement();
		// 解析head节点
		Element head = root.element("head");
		String newVersion = head.elementText("version");
		//版本相同则不需要更新
		if (newVersion.equals(version)) {
			logger.info("版本相同则不需要更新");
			return false;
		}
		//版本不同则清空节点树，重新构造
		nodeTree.clear();
		nodeMap.clear();
		
		version = head.elementText("version");
		site_id = head.elementText("channel_id");
		
		// 解析nodes,构造节点树
		Element nodes = root.element("nodes");
		int index = 0;
		for (Iterator<?> it = nodes.elementIterator("node"); it.hasNext();) {
			index ++;
			try{
				
				Element ele = (Element) it.next();
				if(ele.elementTextTrim("id") == null){
					continue;
				}
				Node node = new Node();
				String id = ele.elementText("id");
				//logger.info("id:"+id);
				node.setId(Integer.parseInt(id));
				
				node.setName(ele.elementTextTrim("name"));
				//logger.info("name:"+ele.elementTextTrim("name"));
				node.setRootid(Integer.parseInt(ele.elementText("root")));
				node.setParentid(Integer.parseInt(ele.elementText("parent")));
				node.setRecord_num(Integer.parseInt(ele.elementTextTrim("record_num")));
				node.setType(Integer.parseInt(ele.elementTextTrim("type")));
				node.setPic1(ele.elementTextTrim("pic_url"));
				node.setPic2(ele.elementTextTrim("pic_url2"));
				if(ele.element("xml_url")!=null){
					List<String> urls = new ArrayList<String>();
					for (Iterator<?> it2 = ele.elementIterator("xml_url"); it2.hasNext();) {
						Element url = (Element) it2.next();
						urls.add(url.getTextTrim());
					}
					node.setXmlurl(urls);
				}
				node.setLink_url(ele.elementTextTrim("link_url"));
				node.setIndex(index);
				String subjectLocalPath = AppConextConfig.getSearchConfig().getNewsXml() + "/" + node.getLink_url();
				if(new File(subjectLocalPath).exists()){
					node.setExist(true);
				}else{
					node.setExist(false);
				}
				nodeTree.put(id, node);
			}catch (Exception e) {
				logger.error("com.jwzt.search.manager.NodeTree.java构造树时出错,取的当前node对象是有问题",e);
			}
		}
		return true;
	}

	public static List<Node> getAllNodes(String nodeid) {
		List<Node> nodes = new ArrayList<Node>();
		Set<String> nodeids = nodeTree.keySet();
		for (String id : nodeids) {
			nodes.add(nodeTree.get(id));
		}
		return nodes;
	}

	// 通过id,获得node
	public static Node getNodeById(String id) {
		return nodeTree.get(id);
	}

	// 通过id获得根节点
	public static int getRootidByNodeid(String id) {
		try {
			Node node = nodeTree.get(id);
			return node.getRootid();
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("获得栏目【"+id+"】的根节点出现异常");
		}
		return 0;
	}
	
	//拿到CMS后台一级栏目的编号
	public static int getRootid(String id){
		int currentNodeRootid = getRootidByNodeid(id);
		Node nodeParent = nodeTree.get(String.valueOf(currentNodeRootid));
		if(nodeParent.getParentid() ==0){
			return nodeParent.getId(); 
		}
		int parentid = nodeParent.getParentid();
		Node parentNode = nodeTree.get(String.valueOf(parentid));
		if(parentNode.getParentid() ==0){
			return parentNode.getId();
		}
		return parentNode.getParentid();
	}


	/***
	 * 获取子栏目列表,最好要排序？
	 * @param id
	 * @return
	 */
	public static List<Node> getChildrenNode(int id){
		
		if(nodeMap.get(id) == null){
		
			List<Node> nodeList = new ArrayList<Node>();
			for(Iterator<String> it = nodeTree.keySet().iterator();it.hasNext();){
				
				Node node = nodeTree.get(it.next());
				if(node != null && node.getParentid() == id){
					nodeList.add(node);
				}
			}
			nodeMap.put(id, nodeList);
		}
		return nodeMap.get(id);
	}
	

	/***
	 * 获取该栏目下第几级的栏目列表,最好要排序？
	 * @param id
	 * @param level 
	 * @return
	 */
	public static List<Node> getChildrenNode(int id,int level){
		

		if(level <=1){
			return getChildrenNode(id);
		}
		level --;
		
		List<Node> tempNodeList = getChildrenNode(id);
		List<Node> levelNodeList = new ArrayList<Node>();
		for(int i = 0; i < tempNodeList.size(); i++){
			
			levelNodeList.addAll(getChildrenNode(tempNodeList.get(i).getId(),level));
		}
		
		return levelNodeList;
	}
	/***
	 * 根据栏目id获取栏目path，如：电影#警匪动作 
	 * @param nodeId
	 * @return
	 */
	public static String getNodePath(int nodeId){
		
		Node node = getNodeById(String.valueOf(nodeId));
		if(node == null){
			
			return null;
		}
		if(node.getNode_path() == null){
		
			StringBuffer sb = new StringBuffer(node.getName());
			Node parentNode = null;
			int parentId = node.getParentid();
			while((parentNode = getNodeById(String.valueOf(parentId))) != null){
				
				parentId = parentNode.getParentid();
				sb.insert(0, parentNode.getName() + "#");
				if(parentNode.getParentid() == 0){
					node.setNode_path(sb.toString());
					break;
				}
			}
		}
		return node.getNode_path();
	}
	
	/***
	 * 
	 * @param tmpList
	 * @param nodeId
	 */
	private static void getAllChildrenNode(List<Node> tmpList, int nodeId){
		
		List<Node> tp = getChildrenNode(nodeId);
		if(tp != null && tp.size() > 0){
			tmpList.addAll(tp);
			for(Node nd : tp){
				getAllChildrenNode(tmpList,nd.getId());
			}
		}
	}
	/****
	 * 获取子孙栏目
	 * @param nodeId
	 * @return
	 */
	public static List<Node> getAllChildrenNode(int nodeId){
		
		List<Node> finalList = new ArrayList<Node>();
		
		getAllChildrenNode(finalList,nodeId);

		return finalList;
	}

}
