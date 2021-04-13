package com.jwzt.datagener.model;

import java.util.Map;

import org.apache.log4j.Logger;

public class BaiDuType {
	private static final Logger logger = Logger.getLogger(	BaiDuType.class);
	private String id;
	private String nodeName;
	private String value;
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public BaiDuType() {
		// TODO Auto-generated constructor stub
	}

	public static String findVulueByNodeID(int nodeId,String category){
		int findNodeid = 0;
    	String type = category;
    	
    	try {
    		Node currentNode = NodeTree.getNodeById(nodeId+"");
    		if(null == currentNode){
    			logger.info("nodeTree get nodebyid is null");
    		}else{
    			logger.info("node info rootid:"+ currentNode.getRootid());
            	if(currentNode.getRootid() == 177415){//如果是优酷专区，就是当前栏目的父栏目
            		findNodeid = currentNode.getParentid();
            	}else{//其它栏目就是当前栏目
            		findNodeid = nodeId;
            	}
            	logger.info("findNodeid:"+findNodeid);
            	Map<String ,BaiDuType> baiduTypes = AppConextConfig.getBaiduTypes();
        		
        		BaiDuType findType = baiduTypes.get(findNodeid+"");
            	if(null != findType){
            		logger.info("是需要调整的栏目");
            		type = findType.getValue();
            	}
    		}	
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("获得资产的type出现异常，"+ e.toString());
			e.printStackTrace();
		}
    	return type;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
