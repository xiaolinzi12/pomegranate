package top.ok.fine.pomegranate.system.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ok.fine.pomegranate.system.common.JsonResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program
 * @description:
 * @author: zhengLin
 * @date 2019/08/26
 */

@RestController
public class LoginController {


    @PostMapping(value="/logout")
    public JsonResponse logoutPage (HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonResponse result = new JsonResponse();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        result.setCode(200);
        result.setMsg("退出成功");
        return result;
    }

}
