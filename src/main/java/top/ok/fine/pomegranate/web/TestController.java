package top.ok.fine.pomegranate.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ok.fine.pomegranate.domain.User;
import top.ok.fine.pomegranate.repository.UserRepository;

import javax.annotation.Resource;

/**
 * @program
 * @description:
 * @author: zhengLin
 * @date 2019/08/23
 */
@RestController
public class TestController {

    @Resource
    private UserRepository userRepository;


    @GetMapping(value = "/test")
    public String test() {
        userRepository.save(new User("1", "AAA", "1234"));
        return "hello customer!";
    }

}
