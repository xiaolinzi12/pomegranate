package top.ok.fine.pomegranate.system.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program
 * @description:
 * @author: zhengLin
 * @date 2019/08/23
 */
@RestController
public class TestController {

    @GetMapping(value = "/api/getNewsList")
    public String test() {
        return "hello customer!";
    }



}
