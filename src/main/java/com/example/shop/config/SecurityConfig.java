package com.example.shop.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;



@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final HandlerMappingIntrospector introspector;

    //認可の設定
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        RequestMatcher mvcLoginMatcher = new MvcRequestMatcher(introspector, "/login");
        RequestMatcher registerMatcher = new MvcRequestMatcher(introspector, "/user/**");
        RequestMatcher errorMatcher = new MvcRequestMatcher(introspector, "/error");
        RequestMatcher bootstrapMatcher = new MvcRequestMatcher(introspector, "/bootstrap/**");
        RequestMatcher cssMatcher = new MvcRequestMatcher(introspector, "/css/**");
        RequestMatcher h2ConsoleMatcher = new AntPathRequestMatcher("/h2-console/**");

        System.out.println("SecurityFilterChain initialized");
         http
                 .authorizeHttpRequests(authz -> authz
                         .requestMatchers(mvcLoginMatcher).permitAll()
                         .requestMatchers(registerMatcher).permitAll()
                         .requestMatchers(errorMatcher).permitAll()
                         .requestMatchers(bootstrapMatcher).permitAll()
                         .requestMatchers(cssMatcher).permitAll()
                        .requestMatchers(h2ConsoleMatcher).permitAll()  // H2コンソールも認証不要
                                 .anyRequest().authenticated()                 //↑以外は認証必要
                 )
                 //h2-consoleの設定----------------------------------------------------------------------
                 .csrf(csrf -> csrf
                         .ignoringRequestMatchers(mvcLoginMatcher)  // /login に対してCSRFを無効化
                         .ignoringRequestMatchers(h2ConsoleMatcher)  // H2 Console に対してCSRFを無効化
                 )
                 .headers(headers -> headers
                         .frameOptions(frame -> frame
                                 .sameOrigin()  // H2 Console がiframe内に表示されることを許可
                         )
                 )
                 //------------------------------------------------------------------------------------
                 .formLogin(form -> form
                         .loginPage("/login")    // 自作のログインページ（/login.html）
                         .usernameParameter("email")    //認証キーをusernameからemailへ変更
                         .loginProcessingUrl("/login") // 認証処理URL
                         .defaultSuccessUrl("/") // ログイン成功後のリダイレクト先
                         .failureUrl("/login?error")// ログイン失敗時のリダイレクト先
                         .permitAll()
                 );

        return http.build();
    }
}


