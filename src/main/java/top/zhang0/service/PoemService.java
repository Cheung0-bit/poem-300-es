package top.zhang0.service;

import top.zhang0.entity.Poem;

import java.io.IOException;
import java.util.List;

/**
 * <>
 *
 * @Author cheung0
 * @createTime 2022/10/2 21:32
 */
public interface PoemService {

    void importAll() throws IOException;

    List<Poem> fuzzySearch(String keyword) throws IOException;

}
