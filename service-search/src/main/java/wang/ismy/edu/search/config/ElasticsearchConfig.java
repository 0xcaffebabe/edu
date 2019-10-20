package wang.ismy.edu.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author MY
 * @date 2019/10/20 14:24
 */
@Configuration
public class ElasticsearchConfig {

    private HttpHost host = new HttpHost("my-pc", 9200);

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        return new RestHighLevelClient(RestClient.builder(host).build());
    }

    @Bean
    public RestClient restClient(){
        return RestClient.builder(host).build();
    }


}
