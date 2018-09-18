package com.bonc.medicine.controller.knowledgebase;


import com.bonc.medicine.entity.Result;
import com.bonc.medicine.utils.ResultUtil;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/essearch")
public class ESSearchController {

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;


    /**
     * 知识库查询
     * @throws Exception
     */
    @GetMapping("/toSearch")
    public Result<Object> searchByType(@RequestParam(required = false) String searchText, @RequestParam(required = false) String searchType) throws Exception {
        return searchAll(searchText,searchType);
    }

    private Result<Object> searchAll(String searchText, String searchType) throws Exception{
        //获取连接client
        Client client = elasticsearchTemplate.getClient();
        //根据索引搜索
       // SearchRequestBuilder srb = null == searchType || ""== searchType ?  client.prepareSearch("knowledge"): client.prepareSearch("knowledge").setTypes(searchType);

        SearchRequestBuilder srb = client.prepareSearch("knowledge");

        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        qb.must(QueryBuilders.matchAllQuery());
        SearchResponse sr;
        if(null != searchType && ""!= searchType) {
            qb.must(QueryBuilders.termQuery("type", searchType));
        }
        if(null != searchText && ""!= searchText){
            qb.must(QueryBuilders.matchQuery("abstract",searchText));
        }

        sr = srb.setQuery(qb).execute().actionGet();
        /*if(null == searchText || ""== searchText){
            sr = srb.setQuery(QueryBuilders.matchAllQuery()).execute().actionGet(); // 查询所有
        }else{
            sr = srb.setQuery(QueryBuilders.multiMatchQuery(searchText,"abstract")).execute().actionGet();//条件查询
        }*/

        SearchHits hits = sr.getHits();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (SearchHit hit : hits) {
            Map<String, Object> source = hit.getSource();
            list.add(source);
            System.out.println(hit.getSourceAsString());
        }
        return ResultUtil.success(list);
    }

}



