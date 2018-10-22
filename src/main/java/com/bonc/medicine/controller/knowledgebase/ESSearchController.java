package com.bonc.medicine.controller.knowledgebase;


import com.bonc.medicine.entity.Result;
import com.bonc.medicine.enums.HomeTypeEnum;
import com.bonc.medicine.service.knowledgebase.VarietyEncyclopediaService;
import com.bonc.medicine.utils.ResultUtil;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.ScoreSortBuilder;
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
    public Result<Object> searchByType(@RequestParam(required = false) String searchText, @RequestParam(required = false) String searchType, @RequestParam(required = false) String platform) throws Exception {
        return searchAll(searchText,searchType,platform);
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
        return ResultUtil.success(getEveryTypeInfo(userCare));
    }

    private Result<Object> searchAll(String searchText, String searchType,String platform) throws Exception {
        //获取连接client
        Client client = elasticsearchTemplate.getClient();
        //根据索引搜索
        SearchRequestBuilder srb = client.prepareSearch("knowledge_new");

        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        qb = validateCodeSearch(searchText,searchType,qb,null,platform);

        SortBuilder sortBuilder = SortBuilders.fieldSort("@timestamp").order(SortOrder.DESC).unmappedType("boolean"); // 定义排序方式
        SearchResponse sr = srb.setQuery(qb)./*addSort(new ScoreSortBuilder()).*/addSort(sortBuilder).setSize(50).execute().actionGet();
//        System.out.println( srb.setQuery(qb).addSort(sortBuilder).setSize(50));

        SearchHits hits = sr.getHits();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (SearchHit hit : hits) {
            Map<String, Object> source = hit.getSource();
            list.add(source);
//            System.out.println(hit.getSourceAsString());
        }
        return ResultUtil.success(list);

    }

    private List getEveryTypeInfo(String searchText){
        List list = new ArrayList();
        for (HomeTypeEnum typeName:HomeTypeEnum.values()) {
            list.addAll(searchEachTypeFor5(searchText,typeName.toString().toLowerCase()));
        }
        return list;
    }

    private List searchEachTypeFor5(String searchText,String searchType){
        //获取连接client
        Client client = elasticsearchTemplate.getClient();
        //根据索引搜索
        SearchRequestBuilder srb = client.prepareSearch("knowledge_new");
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        qb = validateCodeSearch(searchText,searchType,qb,null,null);

        SortBuilder sortBuilder = SortBuilders.fieldSort("@timestamp").order(SortOrder.DESC).unmappedType("boolean"); // 定义排序方式
        SearchResponse sr = srb.setQuery(qb).addSort(sortBuilder).setSize(5).execute().actionGet();
        SearchHits hits = sr.getHits();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (SearchHit hit : hits) {
            Map<String, Object> source = hit.getSource();
            list.add(source);
//            System.out.println(hit.getSourceAsString());
        }
        return list;
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
        SearchRequestBuilder srb = client.prepareSearch("knowledge_new");

        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        qb.must(QueryBuilders.termQuery("type", type));
        qb.must(QueryBuilders.idsQuery().addIds(type+"_"+id));

        SearchResponse sr = srb.setQuery(qb).execute().actionGet();
        SearchHits hits = sr.getHits();
        if (0 == hits.getTotalHits()) {
            return ResultUtil.success(0);
        }

//        相关资讯、文章、视频提取匹配类别与资讯标签
        Map map = hits.getAt(0).getSource();

//        查出 arcitle_type
        String article_type = (String) map.get("artice_type");

//        根据查出的文章类型，推荐相同类型的文章，按分值排序，取前10篇
        BoolQueryBuilder qbd = QueryBuilders.boolQuery();
//        qbd.must(QueryBuilders.termQuery("type", type));
        qbd.must(QueryBuilders.matchQuery("artice_type", article_type));
        qbd.mustNot(QueryBuilders.idsQuery().addIds(type+"_"+id));

        qbd = validateCodeSearch("",type,qbd,map,null);

        SearchResponse sresult = srb.setQuery(qbd).execute().actionGet();
        SearchHits hitsResult = sresult.getHits();
        if (0 == hitsResult.getTotalHits()) {
            return ResultUtil.success(0);
        }
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Double article_score = 0.0;
        for (SearchHit hit : hitsResult) {
            Map<String, Object> source = hit.getSource();
            Map sopTypes = (Map) source.get("sop_type_scores");
            article_score =  "0".equals(sopTypes.get(article_type).toString())?0.0:(Double) sopTypes.get(article_type);
//            article_score = (Double) sopTypes.get(article_type);
            source.put("article_score",article_score);
//            source.put("id",id);
            list.add(source);
//            System.out.println(hit.getSourceAsString());
        }

        Collections.sort(list, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Double score1 = Double.valueOf(o1.get("article_score").toString()) ;//score1是从你list里面拿出来的一个
                Double score2 = Double.valueOf(o2.get("article_score").toString()) ; //score2是从你list里面拿出来的第二个name
                return score2.compareTo(score1);
            }
        });
        return ResultUtil.success(list);
    }

    /**
     * 每种类别的查询条件
     * @param searchType
     * @param searchText
     * @return
     */
    private BoolQueryBuilder validateCodeSearch(String searchText,String searchType,BoolQueryBuilder qb,Map map,String platform){
        /*
        * 类别过滤
        * */
        if(null!=searchType && !"".equals(searchType)){
            switch (searchType){
                case "km_variety_encyclopedia":
                    if(null !=platform){
                        qb.must(QueryBuilders.termQuery("type", searchType));
                    }else {
                        qb.must(QueryBuilders.termsQuery("type",searchType,"km_pharmacopoeia_information"));
                    }
                    break;
                default:
                    qb.must(QueryBuilders.termQuery("type", searchType));
                    break;
            }

            /**
             * 每个类别状态码和搜索词过滤
             */
            switch (searchType){
                case "spec_info":
                    qb.must(QueryBuilders.wildcardQuery("professional_direction.keyword", "*"+searchText+"*"));
                    break;
                case "comm_dyanimic":
                    qb.must(QueryBuilders.termQuery("effect_flag", "0"));
                    qb.must(QueryBuilders.wildcardQuery("desciption.keyword", "*"+searchText+"*"));
                    break;
                case "common_price":
                    qb.must(QueryBuilders.termQuery("state", "1"));
                    qb.must(QueryBuilders.termQuery("status", "1"));
//                    qb.must(QueryBuilders.wildcardQuery("cat_name.keyword", "*"+searchText+"*"));
                    if(null != searchText && ""!=searchText){
                        qb.must(QueryBuilders.matchQuery("cat_name", searchText));
                    }
                    break;
                case "train_video_course":
                    qb.must(QueryBuilders.termQuery("operation_status", "3"));
                    qb.must(QueryBuilders.termQuery("status", "1"));
                    if(null != searchText && ""!=searchText){
                        qb.must(QueryBuilders.multiMatchQuery(searchText, "keywords","abstract","title"));
                    }
//                    qb.must(QueryBuilders.wildcardQuery("keywords.keyword", "*"+searchText+"*"));

                    if(null != map){
                        qb.must(QueryBuilders.termQuery("video_type", map.get("video_type")));
                    }

                    break;
                case "info_basic":
                    qb.must(QueryBuilders.termQuery("is_display", "1"));
                    qb.must(QueryBuilders.termQuery("status", "3"));
                    if(null != searchText && ""!=searchText){
                        qb.must(QueryBuilders.multiMatchQuery(searchText, "keywords","abstract","title"));
                    }
//                qb.must(QueryBuilders.wildcardQuery("keywords.keyword", "*"+searchText+"*"));

                    if(null != map){
                        qb.must(QueryBuilders.termQuery("cat_code", map.get("cat_code")));
                        BoolQueryBuilder qbtmp = QueryBuilders.boolQuery();
                        qbtmp.should(QueryBuilders.termQuery("is_market_top", map.get("is_market_top")));
                        qbtmp.should(QueryBuilders.termQuery("is_top_line", map.get("is_top_line")));
                        qbtmp.should(QueryBuilders.termQuery("is_alarm", map.get("is_alarm")));
                        qbtmp.should(QueryBuilders.termQuery("is_real", map.get("is_real")));
                        qbtmp.should(QueryBuilders.termQuery("is_pest", map.get("is_pest")));
                        qb.must(qbtmp);
                    }

                    break;
                case "spec_article":
                    qb.must(QueryBuilders.termQuery("is_audit", "1"));
                    qb.must(QueryBuilders.termQuery("status", "0"));
                    qb.must(QueryBuilders.wildcardQuery("keywords.keyword", "*"+searchText+"*"));

                    if(null != map){
                        qb.must(QueryBuilders.termQuery("article_type", map.get("article_type")));
                    }

                    break;
                case "km_variety_encyclopedia":
                    qb.must(QueryBuilders.termQuery("record_status", "3"));
                    if(null != searchText && ""!=searchText){
                        qb.must(QueryBuilders.multiMatchQuery(searchText, "keywords","abstract"));
                    }
//                qb.must(QueryBuilders.wildcardQuery("keywords.keyword", "*"+searchText+"*"));
                    break;
                case "km_pharmacopoeia_information":
                    qb.must(QueryBuilders.termQuery("record_status", "3"));
                    if(null != searchText && ""!=searchText){
                        qb.must(QueryBuilders.multiMatchQuery(searchText, "keywords","abstract"));
                    }
                    break;
                case "spec_case":
//                qb.should(QueryBuilders.wildcardQuery("title.keyword", "*"+searchText+"*"));
//                    qb.must(QueryBuilders.wildcardQuery("varieties.keyword", "*"+searchText+"*"));

                    if(null != searchText && ""!=searchText){
                        qb.must(QueryBuilders.multiMatchQuery(searchText, "keywords","abstract","title"));
                    }
                    break;
                default:
                    if(null != searchText && ""!=searchText){
                        qb.must(QueryBuilders.multiMatchQuery(searchText, "keywords","abstract"));
                    }
//                qb.must(QueryBuilders.wildcardQuery("keywords.keyword", "*"+searchText+"*"));
                    break;
            }

        }else {
            if(null != searchText && ""!=searchText){
                qb.must(QueryBuilders.multiMatchQuery(searchText, "keywords","abstract"));
            }
        }


        return qb;
    }

}