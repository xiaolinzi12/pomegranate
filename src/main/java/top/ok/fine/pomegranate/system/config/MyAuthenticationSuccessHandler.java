package top.ok.fine.pomegranate.system.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import top.ok.fine.pomegranate.system.common.JsonResponse;
import top.ok.fine.pomegranate.system.token.JwtToken;

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
@Slf4j
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication)
            throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        // 允许跨域
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        // 允许自定义请求头token(允许head跨域)
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "token, Accept, Origin, X-Requested-With, Content-Type, Last-Modified");

        log.info(authentication.getPrincipal().toString());
//        JsonResponse response = JsonResponse
//                .builder()
//                .code(200)
//                .msg("登录成功")
//                .data(JsonResponse.Token.builder().token(JwtToken.generateToken(authentication.getPrincipal().toString())).build())
//                .build();


        JsonResponse.Token token = new JsonResponse.Token();
        token.setToken(JwtToken.generateToken(authentication.getPrincipal().toString()));
        JsonResponse response = new JsonResponse();
        response.setCode(200);
        response.setMsg("登录成功");
        response.setData(token);

        httpServletResponse.getWriter().write(JSON.toJSONString(response));
    }
}
