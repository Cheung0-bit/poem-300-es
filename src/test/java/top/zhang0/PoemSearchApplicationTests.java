package top.zhang0;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;

@SpringBootTest
class PoemSearchApplicationTests {

    @Resource
    private ElasticsearchClient client;

    @Test
    void contextLoads() {
    }

    @Test
    void queryDocument() throws IOException {
        System.out.println(client.search(
                req -> {
                    req.index("poem").query(
                            q -> q.match(
                                    m -> m.field("title").query("李白")
                            )
                    );
                    return req;
                },
                Object.class
        ).hits());
    }

}
