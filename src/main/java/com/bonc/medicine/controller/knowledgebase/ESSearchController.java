package com.bonc.medicine.controller.knowledgebase;


import com.bonc.medicine.annotation.Authorization;
import com.bonc.medicine.annotation.CurrentUser;
import com.bonc.medicine.entity.Result;
import com.bonc.medicine.service.knowledgebase.VarietyEncyclopediaService;
import com.bonc.medicine.utils.ResultUtil;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/essearch")
public class ESSearchController {

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private VarietyEncyclopediaService varietyEncyclopediaService;


    /**
     * 知识库查询
     * @throws Exception
     */
    @GetMapping("/toSearch")
    public Result<Object> searchByType(@RequestParam(required = false) String searchText, @RequestParam(required = false) String searchType) throws Exception {
        return searchAll(searchText,searchType);
    }

    /**
     * 根据用户关注的品种查询
     * @param user_id
     * @return
     * @throws Exception
     */
//    @Authorization
//    @GetMapping("/searchByUser")
//    public Result<Object> searchByUser(@RequestParam/*@CurrentUser*/ String user_id) throws Exception {
//    @Authorization
    @GetMapping("/searchByUser")
    public Result<Object> searchByUser(@RequestParam(required = false) String user_id) throws Exception {
        String userCare = "";
        if (null!=user_id && "" != user_id){
            userCare = varietyEncyclopediaService.selectUserCare(user_id);
        }
        return searchAll(userCare,null);
    }

    private Result<Object> searchAll(String searchText, String searchType) throws Exception {
        //获取连接client
        Client client = elasticsearchTemplate.getClient();
        //根据索引搜索
        // SearchRequestBuilder srb = null == searchType || ""== searchType ?  client.prepareSearch("knowledge"): client.prepareSearch("knowledge").setTypes(searchType);
        SearchRequestBuilder srb = client.prepareSearch("knowledge");

        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        qb.must(QueryBuilders.matchAllQuery());
        SearchResponse sr;
        if (null != searchType && "" != searchType) {
            if("km_variety_encyclopedia".equals(searchType)){
                qb.must(QueryBuilders.termsQuery("type",searchType,"km_pharmacopoeia_information"));
            }else {
                qb.must(QueryBuilders.termQuery("type", searchType));
            }
        }
        if (null != searchText && "" != searchText) {
//            qb.must(QueryBuilders.matchQuery("abstract", searchText));
            qb.must(QueryBuilders.multiMatchQuery(searchText,"abstract","keywords"));
        }
        SortBuilder sortBuilder = SortBuilders.fieldSort("@timestamp").order(SortOrder.DESC).unmappedType("boolean"); // 定义排序方式
        sr = srb.setQuery(qb).addSort(sortBuilder).setSize(50).execute().actionGet();
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
//        int len = list.size();
//        len = len > 50 ? 50 : len;
        return ResultUtil.success(list);

    }


/*
* 1. 参数：id,type
*
* 2.查出 arcitle_type
*
*3.根据arcitle_type 查询，筛选最高的arcitle_type 得分
*
* */

    /**
     *
     * @param id 对应类型id
     * @param type 搜索类型 type: info_basic,spec_article,train_video_course
     * @return
     * @throws Exception
     */
    @GetMapping("/relativeSearch")
    public Result<Object> relativeSearch(@RequestParam String id,@RequestParam String type) throws Exception {
        //获取连接client
        Client client = elasticsearchTemplate.getClient();
        SearchRequestBuilder srb = client.prepareSearch("knowledge");

        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        qb.must(QueryBuilders.termQuery("type", type));
        qb.must(QueryBuilders.idsQuery().addIds(type+"_"+id));

        SearchResponse sr = srb.setQuery(qb).execute().actionGet();
        SearchHits hits = sr.getHits();

//        查出 arcitle_type
        String article_type = (String) hits.getAt(0).getSource().get("artice_type");

//        根据查出的文章类型，推荐相同类型的文章，按分值排序，取前10篇
        BoolQueryBuilder qbd = QueryBuilders.boolQuery();
        qbd.must(QueryBuilders.termQuery("type", type));
        qbd.must(QueryBuilders.matchQuery("artice_type", article_type));
        qbd.mustNot(QueryBuilders.idsQuery().addIds(type+"_"+id));

        SearchResponse sresult = srb.setQuery(qbd).execute().actionGet();
        SearchHits hitsResult = sresult.getHits();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Double article_score = 0.0;
        for (SearchHit hit : hitsResult) {
            Map<String, Object> source = hit.getSource();
            Map sopTypes = (Map) source.get("sop_type_scores");
            article_score = sopTypes.get(article_type).toString() == "0"?0.0:(Double) sopTypes.get(article_type);
//            article_score = (Double) sopTypes.get(article_type);
            source.put("article_score",article_score);
//            source.put("id",id);
            list.add(source);
//            System.out.println(hit.getSourceAsString());
        }
//        int len = list.size();
//        len = len>10?10:len;
        Collections.sort(list, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Double score1 = Double.valueOf(o1.get("article_score").toString()) ;//score1是从你list里面拿出来的一个
                Double score2 = Double.valueOf(o2.get("article_score").toString()) ; //score2是从你list里面拿出来的第二个name
                return score2.compareTo(score1);
            }
        });
        return ResultUtil.success(list);
    }

}