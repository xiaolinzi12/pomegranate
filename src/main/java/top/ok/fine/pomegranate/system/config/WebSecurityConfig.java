package top.ok.fine.pomegranate.system.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import top.ok.fine.pomegranate.system.filter.TokenFilter;

import javax.annotation.Resource;

/**
 * @program
 * @description:
 * @author: zhengLin
 * @date 2019/08/26
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Resource
    private WebUserDetailService userDetailsService;

    @Resource
    private MyAuthenticationSuccessHandler successHandler;

    @Resource
    private MyAuthenticationFailHandler failHandler;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Resource
    private SelfAuthenticationProvider provider;


    @Resource
    private TokenFilter tokenFilter;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(provider); // 添加自定义的认证逻辑
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 设置拦截忽略文件夹，可以对静态资源放行
        web.ignoring().antMatchers("/css/**", "/js/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and()
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/", "/login").permitAll()
                .anyRequest()
                .authenticated()
                //这里必须要写formLogin()，不然原有的UsernamePasswordAuthenticationFilter不会出现，也就无法配置我们重新的UsernamePasswordAuthenticationFilter
                .and()
                .formLogin()
                .loginPage("/")
                .and()
                .logout()
                .logoutUrl("logout")
                // 关闭CSRF跨域
                .and()
                .csrf()
                .disable()
        ;
        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);


        //用重写的Filter替换掉原有的UsernamePasswordAuthenticationFilter
        http.addFilterAt(customAuthenticationFilter(),
                UsernamePasswordAuthenticationFilter.class);

    }

    /**
     * 创建认证提供者Bean
     * 技巧01：DaoAuthenticationProvider是SpringSecurity提供的AuthenticationProvider实现类
     *
     * @return
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider
                = new DaoAuthenticationProvider(); // 创建DaoAuthenticationProvider实例
        authProvider.setUserDetailsService(userDetailsService); // 将自定义的认证逻辑添加到DaoAuthenticationProvider
        authProvider.setPasswordEncoder(passwordEncoder); // 设置自定义的密码加密
        return authProvider;
    }

    /**
     * 創建PsswordEncoder對應的Bean
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return s.equals(charSequence.toString());
            }
        };
    }

    //注册自定义的UsernamePasswordAuthenticationFilter
    @Bean
    CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(successHandler);
        filter.setAuthenticationFailureHandler(failHandler);
        filter.setFilterProcessesUrl("/login");

        //这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }
}
