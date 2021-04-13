package com.jwzt.datagener.lucence;



import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

public class NewsSearch extends BaseSearch {

	/**稿件id*/
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	@Override
	protected Query getQuery() {
		// TODO Auto-generated method stub
		//必须在搜索结果中
		TermQuery query = new TermQuery(new Term("id",String.valueOf(this.getId())));
		return query;
	}
	

}
