package top.zhang0.api;

import org.springframework.web.bind.annotation.*;
import top.zhang0.entity.Poem;
import top.zhang0.service.PoemService;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * <>
 *
 * @Author cheung0
 * @createTime 2022/10/2 21:38
 */
@RestController
@RequestMapping("/poem")
public class PoemApi {

    @Resource
    private PoemService poemService;

    @PostMapping("/importAll")
    public String importAll() throws IOException {
        poemService.importAll();
        return "SUCCESS";
    }

    @PostMapping("/search")
    public List<Poem> search(@RequestParam String keyword) throws IOException {
        return poemService.fuzzySearch(keyword);
    }


}
