package top.zhang0.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <ES配置>
 *
 * @Author cheung0
 * @createTime 2022/10/2 14:51
 * Step1 Create the low-level client
 * Step2 Create the transport with a Jackson mapper
 * Step3 And create the API client
 */
@Configuration
public class EsClientConfig {

    @Value("${elasticsearch.host}")
    private String host;

    @Value("${elasticsearch.port}")
    private int port;

    @Bean
    public ElasticsearchClient elasticsearchClient() {
        // Step 1
        RestClient restClient = RestClient.builder(
                new HttpHost(host, port)).build();

        // Step 2
        ElasticsearchTransport transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper());

        // Step 3
        return new ElasticsearchClient(transport);
    }

}
