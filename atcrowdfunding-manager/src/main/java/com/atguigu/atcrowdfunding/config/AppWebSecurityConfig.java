package com.atguigu.atcrowdfunding.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@EnableGlobalMethodSecurity(prePostEnabled = true)    //支持方法级别的注解
@Configuration     // 标注描述项目中的所有组件
@EnableWebSecurity // 启用安全框架中的注解 (对springsecurity中的注解支持)
public class AppWebSecurityConfig extends WebSecurityConfigurerAdapter {
     @Autowired
     private UserDetailsService userDetailsService;

    //认证(登录)
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {


        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    //授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //试验1
     //  super.configure(http);//父类中的该方法默认拦截所有的请求
        http.authorizeRequests().antMatchers("/welcome.jsp","/static/**").permitAll()//放行

                .anyRequest().authenticated();//其他的资源必须登录才能够访问到(认证)
      //试验2  访问受限跳转到登录页面
        http.formLogin().loginPage("/welcome.jsp");
     //试验3   loginProcessingUrl
         http.formLogin().loginProcessingUrl("/login")
                 .usernameParameter("loginacct")// getParameter("username")
                 .passwordParameter("userpswd")
                 // 登录成功之后访问的控制器地址
                 .defaultSuccessUrl("/atcrowdfunding/main");
            //将csrf禁用(暂时用不到)
        http.csrf().disable();
        //试验4   logoutUrl 处理退出的控制器    logoutSuccessUrl 退出之后跳转到登录页面
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/welcome.jsp");
        //试验7
        //第二种方式  访问受限的异常处理器
        http.exceptionHandling().accessDeniedHandler(new AccessDeniedHandler() {
            @Override
            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

//                异步请求处理
                if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){
                    String msg = "403";
                    response.getWriter().write(msg);
                }else{//同步请求处理

                    //1.将错误的信息保存并携带到页面中
                    request.setAttribute("err",accessDeniedException.getMessage());
//                2.跳转到显示的页面
                    request.getRequestDispatcher("/unauth.html").forward(request,response);

                }


            }
        });
        //试验8
        http.rememberMe();
    }
}
