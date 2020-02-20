package com.shfe.community.controller;

import com.shfe.community.dto.AccessTokenDTO;
import com.shfe.community.dto.GithubUser;
import com.shfe.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    /*
    * GithubProvider 类在定义的时候加入Conponent注解，表示自动实例化到spring 容器中，
    * @Autowired 表示从从spring 容器中直接取出对象，不需要再new 相关类的对象
    * */
    @Autowired
    private GithubProvider  githubProvider;

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
                           @RequestParam(name= "state") String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();

        accessTokenDTO.setClient_id("384c484dc1804a3e5ef8");
        accessTokenDTO.setClient_secret("5f98027048768f6186f1abdbadc3017637d4c5b1");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("http://localhost/callback");
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
//        注意user 对象有可能为null, 因此打印的时候会抛出异常
        GithubUser user = githubProvider.getUser(accessToken);
        
        System.out.println(user.toString());
//        System.out.println(user.getName());
        return "index";
    }
}
