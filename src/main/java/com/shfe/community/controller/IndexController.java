package com.shfe.community.controller;

import com.shfe.community.mapper.UserMapper;
import com.shfe.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request){
//        读取请求带来的cookie信息，取出其中的token, 到后台数据库中查询token, 验证用户是否已登录
        Cookie[] cookies = request.getCookies();
        if (cookies != null ) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
//                如果通过token 查找到用户，则设置session 的user属性未user对象，这样前端页面可以获得session对象来获取user对象，判断是否要展示登录还是用户名
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        return ("index");

    }
}
