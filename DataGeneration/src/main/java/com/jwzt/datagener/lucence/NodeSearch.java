package com.jwzt.datagener.lucence;


import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;


public class NodeSearch extends BaseSearch {

	/**栏目id*/
	private int nodeId;


	public int getNodeId() {
		return nodeId;
	}


	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}


	@Override
	protected Query getQuery() {
		// TODO Auto-generated method stub
		//必须在搜索结果中
		TermQuery query = new TermQuery(new Term("nodeid",String.valueOf(this.getNodeId())));
		return query;
	}
	

}
