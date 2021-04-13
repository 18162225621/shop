package com.jwzt.datagener.lucence;


import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;


public class NodeNewNameSearch extends BaseSearch {

	/**栏目id*/
	private String nodeId;

	private String newsName;

	public String getNodeId() {
		return nodeId;
	}


	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}


	public String getNewsName() {
		return newsName;
	}


	public void setNewsName(String newsName) {
		this.newsName = newsName;
	}


	@Override
	protected Query getQuery() {
		// TODO Auto-generated method stub
		BooleanQuery query = new BooleanQuery();
		query.add(new TermQuery(new Term("nodeid",this.getNodeId())),BooleanClause.Occur.MUST);
		query.add(new TermQuery(new Term("name",this.newsName)),BooleanClause.Occur.MUST);
		return query;
	}
	

}
