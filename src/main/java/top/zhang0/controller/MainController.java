package top.zhang0.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.zhang0.entity.Poem;
import top.zhang0.service.PoemService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * <>
 *
 * @Author cheung0
 * @createTime 2022/10/3 15:07
 */
@Controller
public class MainController {

    @Resource
    private PoemService poemService;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/search")
    public String search(HttpServletRequest request, @RequestParam(required = false) String keyword) throws IOException {
        if (keyword != null&& !keyword.isEmpty()) {
            List<Poem> poems = poemService.fuzzySearch(keyword);
            request.setAttribute("poems",poems);
        }
        return "search/search";
    }

}
