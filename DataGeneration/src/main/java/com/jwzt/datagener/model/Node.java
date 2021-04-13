/**
 * 
 */
package com.jwzt.datagener.model;

import java.util.ArrayList;
import java.util.List;

public class Node {
	private int id;
	private String name;
	private int rootid;
	private int parentid;
	private int record_num;
	private String node_path; //栏目路径，相对于根栏目，如：电影#警匪动作 
	
	/**专题是否存在，根据link_url在本地是否存在做判断*/
	private boolean exist;
	
	public boolean isExist() {
		return exist;
	}
	public void setExist(boolean exist) {
		this.exist = exist;
	}
	/**栏目图片1*/
	private String pic1;
	
	/**栏目图片2*/
	private String pic2;
	
	/**0：非影视类节目、1：影视类电影、3：影视类电视剧*/
	private int type;
	
	private List<String> xmlurl=new ArrayList<String>();
	
	/**序号*/
	private int index; 
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	/**栏目链接地址*/
	private String link_url;
	
	public String getLink_url() {
		return link_url;
	}
	public void setLink_url(String link_url) {
		this.link_url = link_url;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return this.id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the rootid
	 */
	public int getRootid() {
		return this.rootid;
	}
	/**
	 * @param rootid the rootid to set
	 */
	public void setRootid(int rootid) {
		this.rootid = rootid;
	}
	/**
	 * @return the parentid
	 */
	public int getParentid() {
		return this.parentid;
	}
	/**
	 * @param parentid the parentid to set
	 */
	public void setParentid(int parentid) {
		this.parentid = parentid;
	}
	/**
	 * @return the record_num
	 */
	public int getRecord_num() {
		return this.record_num;
	}
	/**
	 * @param record_num the record_num to set
	 */
	public void setRecord_num(int record_num) {
		this.record_num = record_num;
	}
	/**
	 * @return the xmlurl
	 */
	public List<String> getXmlurl() {
		return this.xmlurl;
	}
	/**
	 * @param xmlurl the xmlurl to set
	 */
	public void setXmlurl(List<String> xmlurl) {
		this.xmlurl = xmlurl;
	}
	
	public boolean isSoon(int nodeid){
		if(this.id == (nodeid)){
			return true;
		}
		if(this.rootid == (nodeid)){
			return true;
		}
		if(this.parentid == (nodeid)){
			return true;
		}
		if(this.parentid == 0){
			return false;
		}
		if(hasParent(this.parentid,nodeid)){
			return true;
		}
		return false;
	}
	
	public boolean hasParent(int parentid,int soonid){
		Node node=NodeTree.nodeTree.get(parentid);
		if(node!=null){
			if(node.getParentid()==(soonid)){
				return true;
			}
			if(node.parentid != (node.getRootid())&&node.parentid != 0){
				hasParent(node.getParentid(),soonid);
			}
			
		}
		return false;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getPic1() {
		return pic1;
	}
	public void setPic1(String pic1) {
		this.pic1 = pic1;
	}
	public String getPic2() {
		return pic2;
	}
	public void setPic2(String pic2) {
		this.pic2 = pic2;
	}
	public String getNode_path() {
		return node_path;
	}
	public void setNode_path(String node_path) {
		this.node_path = node_path;
	}
	

}
