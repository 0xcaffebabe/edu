package wang.ismy.edu.search.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;

import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import wang.ismy.edu.common.model.response.CommonCode;
import wang.ismy.edu.common.model.response.QueryResponseResult;
import wang.ismy.edu.common.model.response.QueryResult;
import wang.ismy.edu.domain.course.CoursePub;
import wang.ismy.edu.domain.search.CourseSearchParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author MY
 * @date 2019/10/20 14:39
 */
@Service
@AllArgsConstructor
public class SearchCourseService {

    private static final String EDU_COURSE = "edu_course";
    private static final String TYPE = "doc";

    private RestHighLevelClient restHighLevelClient;
    private RestTemplate restTemplate;

    public QueryResponseResult<CoursePub> findList(int page, int size, CourseSearchParam courseSearchParam) {
        if(courseSearchParam == null){
            courseSearchParam = new CourseSearchParam();
        }
        //创建搜索请求对象
        SearchRequest searchRequest = new SearchRequest("edu_course");
        //设置搜索类型
        searchRequest.types("doc");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //过虑源字段
//        String[] source_field_array = source_field.split(",");
//        searchSourceBuilder.fetchSource(source_field_array,new String[]{});
        //创建布尔查询对象
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //搜索条件
        //根据关键字搜索
        if(StringUtils.isNotEmpty(courseSearchParam.getKeyword())){
            MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(courseSearchParam.getKeyword(), "name", "description", "teachplan")
                    .minimumShouldMatch("70%")
                    .field("name", 10);
            boolQueryBuilder.must(multiMatchQueryBuilder);
        }
        if(StringUtils.isNotEmpty(courseSearchParam.getMt())){
            //根据一级分类
            boolQueryBuilder.filter(QueryBuilders.termQuery("mt",courseSearchParam.getMt()));
        }
        if(StringUtils.isNotEmpty(courseSearchParam.getSt())){
            //根据二级分类
            boolQueryBuilder.filter(QueryBuilders.termQuery("st",courseSearchParam.getSt()));
        }
        if(StringUtils.isNotEmpty(courseSearchParam.getGrade())){
            //根据难度等级
            boolQueryBuilder.filter(QueryBuilders.termQuery("grade",courseSearchParam.getGrade()));
        }

        //设置boolQueryBuilder到searchSourceBuilder
        searchSourceBuilder.query(boolQueryBuilder);
        searchRequest.source(searchSourceBuilder);

        QueryResult<CoursePub> queryResult = new QueryResult();
        List<CoursePub> list = new ArrayList<>();
        try {
            //执行搜索
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest);
            //获取响应结果
            SearchHits hits = searchResponse.getHits();
            //匹配的总记录数
            long totalHits = hits.totalHits;
            queryResult.setTotal(totalHits);
            SearchHit[] searchHits = hits.getHits();
            for(SearchHit hit:searchHits){
                CoursePub coursePub = new CoursePub();
                //源文档
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                //取出name
                String name = (String) sourceAsMap.get("name");
                coursePub.setName(name);
                //图片
                String pic = (String) sourceAsMap.get("pic");
                coursePub.setPic(pic);
                //价格
                Double price = null;
                try {
                    if(sourceAsMap.get("price")!=null ){
                        price = (Double) sourceAsMap.get("price");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                coursePub.setPrice(price);
                //旧价格
                Double price_old = null;
                try {
                    if(sourceAsMap.get("price_old")!=null ){
                        price_old = (Double) sourceAsMap.get("price_old");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                coursePub.setPrice_old(price_old);
                //将coursePub对象放入list
                list.add(coursePub);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        queryResult.setList(list);
        QueryResponseResult<CoursePub> queryResponseResult = new QueryResponseResult<CoursePub>(CommonCode.SUCCESS,queryResult);

        return queryResponseResult;
    }
}
