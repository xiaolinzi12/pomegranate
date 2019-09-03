package top.ok.fine.pomegranate.system.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import top.ok.fine.pomegranate.system.exception.AuthException;
import top.ok.fine.pomegranate.system.token.JwtToken;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @program
 * @description:
 * @author: zhengLin
 * @date 2019/09/02
 */
@Slf4j
@Component
@WebFilter(urlPatterns = "/**")
public class TokenFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!JwtToken.open) {
            chain.doFilter(req, response);
        } else {
            final HttpServletRequest request = (HttpServletRequest) req;

            String uri = request.getRequestURI();
            for (String ur : JwtToken.urls) {
                if (ur.contains(uri)) {
                    chain.doFilter(req, response);
                    return;
                }
            }
            //客户端将token封装在请求头中，格式为（Bearer后加空格）：Authorization：Bearer +token

            /**
             * 打印headers信息
             */
           /* Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String element = headerNames.nextElement();
                String value = request.getHeader(element);
                log.info("{} = {}", element, value);
            }*/

            final String authHeader = request.getHeader(JwtToken.header).replaceAll("\"", "");
            if (authHeader == null || !authHeader.startsWith("Bearer")) {
                log.warn("Token is not exist");
                throw new AuthException("Token is not exist");
            }
            final String token = authHeader.substring(7);
            try {
                JwtToken jwtToken = new JwtToken();
                jwtToken.parseToken(token);
                Boolean expired = jwtToken.isTokenExpired(token);
                if (expired) {
                    log.warn("Token is timeout，token:{}", token);
                    throw new AuthException("Token is timeout");
                }
            } catch (final Exception ex) {
                log.info("token 无效，token:{},错误原因：{}", token, ex.getMessage());
                throw new AuthException("token 无效");
            }
        }
        chain.doFilter(req, response);
    }
}
