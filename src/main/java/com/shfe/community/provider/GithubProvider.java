package com.shfe.community.provider;

import com.alibaba.fastjson.JSON;
import com.shfe.community.dto.AccessTokenDTO;
import com.shfe.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {

    public String getAccessToken(AccessTokenDTO accessTokenDTO){

        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        /*
        * 调用post 请求，请求如下：POST https://github.com/login/oauth/access_token
        * body 需要设置：client_id, client_secret, code, redirect_uri, state
        * */
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String resp = response.body().string();
                /*
                * 返回格式：access_token=7201cb19feda133cc97a64335cd2f8766be1af3e&scope=user&token_type=bearer
                * 用&分割取出第一个access_token字符串，再将其按=分割
                * */
                return resp.split("&")[0].split("=")[1];

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
    }

    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url("https://api.github.com/user?access_token=90a132abf93179c39e687a7232fede6d03056a02")
//                .build();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            /*
            * 由于网络异常原因，resp很有可能会为null，因此需要try catch捕获
            * 另外JSON.parseObject 是调用fastjson 库将字符串转换为GithubUser 的对象
            * */
            String resp = response.body().string();
            GithubUser githubUser = JSON.parseObject(resp, GithubUser.class);
            return githubUser;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }
}
