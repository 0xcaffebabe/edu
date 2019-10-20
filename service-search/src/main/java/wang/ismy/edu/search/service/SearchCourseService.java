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
    public QueryResponseResult findList(Integer page, Integer size, CourseSearchParam param) {
        String json = "{\n" +
                "    \"query\": {\n" +
                "      \"bool\": {\n" +
                "        \"must\": [\n" +
                "          {\n" +
                "            \"multi_match\": {\n" +
                "              \"query\": \""+param.getKeyword()+"\",\n" +
                "              \"fields\": [\"name\",\"description\",\"teachplan\"]\n" +
                "            }\n" +
                "          }\n" +
                "        ],\n" +
                "        \"filter\": [\n" +
                "        {\n" +
                "        \"term\": {\n" +
                "        \"mt\": \""+param.getMt()+"\"\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"term\": {\n" +
                "        \"st\": \""+param.getSt()+"\"\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"term\": {\n" +
                "        \"grade\": \""+param.getGrade()+"\"\n" +
                "        }\n" +
                "      }\n" +
                "        ]\n" +
                "      }\n" +
                "    }\n" +
                "}";

        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type","application/json; charset=UTF-8");
        HttpEntity<String> request = new HttpEntity<>(json,headers);
        ResponseEntity<String> result = restTemplate.postForEntity("http://my-pc:9200/edu_course/_search", request, String.class);
        String body = result.getBody();
        JSONObject jsonObject = JSON.parseObject(body);
        JSONArray hits = jsonObject.getJSONObject("hits").getJSONArray("hits");
        List<CoursePub> pubList = new ArrayList<>();
        for (int i = 0; i < hits.size(); i++) {
            JSONObject source = hits.getJSONObject(i).getJSONObject("_source");
            CoursePub pub = JSON.parseObject(source.toJSONString(), CoursePub.class);
            pubList.add(pub);
        }

        return new QueryResponseResult<>(CommonCode.SUCCESS,new QueryResult<>(pubList,pubList.size()));
    }

    public QueryResponseResult findList1(Integer page, Integer size, CourseSearchParam param) {

        SearchRequest searchRequest = new SearchRequest(EDU_COURSE);
        searchRequest.types(TYPE);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchRequest.source(SearchSourceBuilder.searchSource());
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // 根据关键字搜索
        if (!StringUtils.isEmpty(param.getKeyword())) {

            MultiMatchQueryBuilder multiMatchQueryBuilder =
                    QueryBuilders.multiMatchQuery(param.getKeyword(), "name", "description", "teachplan")
                            .minimumShouldMatch("70%")
                            .field("name", 10);

            boolQueryBuilder.must(multiMatchQueryBuilder);
        }

        searchSourceBuilder.query(boolQueryBuilder);


        SearchResponse response = null;
        try {
            response = restHighLevelClient.search(searchRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SearchHits hits = response.getHits();
        QueryResult<CoursePub> result = new QueryResult<>();
        result.setTotal(hits.totalHits);
        List<CoursePub> coursePubList = new ArrayList<>();
        for (SearchHit hit : hits.getHits()) {
            CoursePub pub = new CoursePub();
            Map<String, Object> map = hit.getSourceAsMap();
            pub.setName(map.get("name").toString());
            pub.setPic(map.get("pic").toString());
            if (map.get("price") != null) {
                pub.setPrice(Float.parseFloat(map.get("price").toString()));
            }

            if (map.get("price_old") != null) {
                pub.setPrice_old(Float.parseFloat(map.get("price_old").toString()));
            }

            coursePubList.add(pub);
        }
        result.setList(coursePubList);
        return new QueryResponseResult<>(CommonCode.SUCCESS, result);

    }
}
