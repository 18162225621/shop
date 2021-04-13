package com.jwzt.datagener.lucence;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

public class FullDataSearch extends BaseSearch{

	@Override
	protected Query getQuery() {
		// TODO Auto-generated method stub
		BooleanQuery assetIdQuery = new BooleanQuery();

		assetIdQuery.add(new TermQuery(new Term("inresultpage","1")),BooleanClause.Occur.MUST);
		return assetIdQuery;
	}

}
