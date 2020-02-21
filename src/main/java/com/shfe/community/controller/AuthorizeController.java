package com.shfe.community.controller;

import com.shfe.community.dto.AccessTokenDTO;
import com.shfe.community.dto.GithubUser;
import com.shfe.community.mapper.UserMapper;
import com.shfe.community.model.User;
import com.shfe.community.provider.GithubProvider;
import org.h2.mvstore.FreeSpaceBitSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.PrivateKey;
import java.util.UUID;

@Controller
public class AuthorizeController {
    /*
    * GithubProvider 类在定义的时候加入Conponent注解，表示自动实例化到spring 容器中，
    * @Autowired 表示从从spring 容器中直接取出对象，不需要再new 相关类的对象
    * */
    @Autowired
    private GithubProvider  githubProvider;

    @Value("${github.client.id}")
    private String client_id;

    @Value("${github.client.secret}")
    private String client_secret;

    @Value("${github.redirecturl}")
    private String redirecturl;

    @Autowired
    private UserMapper userMapper;

    /*
    * 整个流程参考github Oauth 流程：https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/
    * 先需要申请Oauth app ，获取github 提供client id 和 Client Secret
    * 点登录按钮，系统调用github API ，格式为：GET https://github.com/login/oauth/authorize ，
    * github回调 http://localhost/callback 地址，调用请求格式：http://localhost/callback?code=f4bb489862bccf81ad19&state=1
    * callback函数从请求中提取code和state 的参数值，用于生成AccessTokenDTO对象
    * 然后调用getAccessToken 函数传入AccessTokenDTO对象， 开始调用github API获取access token, 调用格式：POST https://github.com/login/oauth/access_token
    * 最后调用getUser 函数使用access token 调用github 的接口，类似调用https://api.github.com/user?access_token=90a132abf93179c39e687a7232fede6d03056a02
    * 最后返回User 对象
    * */
    @GetMapping("/callback")
    public String callback(@RequestParam (name = "code") String code,
                           @RequestParam(name= "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response){

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(client_id);
        accessTokenDTO.setClient_secret(client_secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirecturl);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
//        注意user 对象有可能为null, 因此打印的时候会抛出异常
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser != null) {
//            记录用户信息到h2 数据库中的user 表
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
//            转换int为string
            user.setAccount_id(String.valueOf(githubUser.getId()));
            user.setGmt_create(System.currentTimeMillis());
            user.setGmt_modified(user.getGmt_create());

            userMapper.insert(user);

//            执行数据库插入后，可以token写入cookie信息
            response.addCookie(new Cookie("token",token));

//            在HttpServerletRequest对象中设置session 属性user存储GithubUser 对象，同时系统会自动将jsessionid写入cookie
//            request.getSession().setAttribute("githubUser",githubUser);
//            自动返回首页index.html
            return "redirect:/";
        } else {
            return "redirect:/";

        }

//        System.out.println(githubUser.toString());
//        System.out.println(githubUser.getName());
//        return "index";
    }
}
