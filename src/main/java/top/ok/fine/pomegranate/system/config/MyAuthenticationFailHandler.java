package top.ok.fine.pomegranate.system.config;

import com.alibaba.fastjson.JSON;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import top.ok.fine.pomegranate.system.common.JsonResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program
 * @description:
 * @author: zhengLin
 * @date 2019/08/27
 */
@Component
public class MyAuthenticationFailHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e)
            throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        // 允许跨域
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
//        // 允许自定义请求头token(允许head跨域)
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "token, Accept, Origin, X-Requested-With, Content-Type, Last-Modified");


        JsonResponse response = new JsonResponse();
        response.setCode(400);
        response.setMsg("登录失败");
        response.setData(e);


//        JsonResponse response = JsonResponse
//                .code(400)
//                .msg("登录失败")
//                .data(e.getMessage())
//                .build();
        httpServletResponse.getWriter().write(JSON.toJSONString(response));

    }
}
