package top.zhang0.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.HighlightField;
import co.elastic.clients.elasticsearch.core.search.Hit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.zhang0.dao.PoemDao;
import top.zhang0.entity.Poem;
import top.zhang0.service.PoemService;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * <>
 *
 * @Author cheung0
 * @createTime 2022/10/2 21:32
 */
@Service
@Slf4j
public class PoemServiceImpl implements PoemService {

    @Resource
    private PoemDao poemDao;

    @Resource
    private ElasticsearchClient client;

    private static final String INDEX = "poem";

    private static final List<String> SEARCH_COLLECTION = new ArrayList<>(
            Arrays.asList("contents", "author", "title", "type")
    );

    @Override
    public void importAll() throws IOException {
        List<Poem> poems = poemDao.queryAll();
        if (poems == null) {
            throw new RuntimeException("查询为空");
        }
        boolean flag = client.indices().exists(i -> i.index(INDEX)).value();
        if (flag) {
            log.info("index has existed!");
        } else {
            System.out.println(client.indices().create(i -> i.index(INDEX)).acknowledged());
        }
        client.bulk(
                req -> {
                    poems.forEach(
                            u -> {
                                req.operations(
                                        b -> b.create(
                                                d -> d
                                                        .index(INDEX)
                                                        .id(u.getId())
                                                        .document(u)
                                        )
                                );
                            }
                    );
                    return req;
                }
        );
    }

    @Override
    public List<Poem> fuzzySearch(String keyword) throws IOException {
        HighlightField fields = new HighlightField.Builder()
                .preTags("<font color='red'>").postTags("</font>").build();
        Map<String, HighlightField> fieldMap = new HashMap<>(4);
        fieldMap.put("author", fields);
        fieldMap.put("contents", fields);
        fieldMap.put("type", fields);
        fieldMap.put("title", fields);
        SearchResponse<Poem> response = client.search(
                req -> {
                    req.index(INDEX)
                            .query(
                                    q -> q.multiMatch(
                                            m -> m.fields(SEARCH_COLLECTION).query(keyword)
                                    )
                            );
                    req.highlight(
                            h -> h.fields(fieldMap)
                    );
                    req.size(40);
                    return req;
                },
                Poem.class
        );
        List<Hit<Poem>> hits = response.hits().hits();
        List<Poem> poems = new ArrayList<>(hits.size());
        hits.forEach(i -> {
            Poem poem = i.source();
            Set<String> keySet = i.highlight().keySet();
            if (keySet.contains("author")) {
                poem.setAuthor(i.highlight().get("author").toString());
            } else if (keySet.contains("contents")) {
                poem.setContents(i.highlight().get("contents").toString());
            } else if (keySet.contains("title")) {
                poem.setTitle(i.highlight().get("title").toString());
            } else {
                poem.setType(i.highlight().get("type").toString());
            }
            poems.add(poem);
        });
        return poems;
    }
}
