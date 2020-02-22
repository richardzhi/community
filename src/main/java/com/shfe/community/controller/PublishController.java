package com.shfe.community.controller;

import com.shfe.community.mapper.QuestionMapper;
import com.shfe.community.mapper.UserMapper;
import com.shfe.community.model.Question;
import com.shfe.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/*
* get 方法展示 提交问题页面
* post 方法提问题内容
* */
@Controller
public class PublishController {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    /*
    * post 请求提交了3个参数title、description、tag，和页面中input中的id保持一致
    * */
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title",required = false) String title,
            @RequestParam(value = "description",required = false) String description,
            @RequestParam(value = "tag", required = false) String tag,
            HttpServletRequest request,
            Model model){
        User user=null;
//        在model中加入的属性可以直接返回到前端页面中
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        if(title==null || title==""){
            model.addAttribute("error","标题不能为空");
            return ("publish");
        }

        if(description==null || description==""){
            model.addAttribute("error","内容不能为空");
            return ("publish");
        }

        if(tag==null || tag==""){
            model.addAttribute("error","标签不能为空");
            return ("publish");
        }
//        检查用户是否登录
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length!=0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);
//                如果通过token 查找到用户，则设置session 的user属性未user对象，这样前端页面可以获得session对象来获取user对象，判断是否要展示登录还是用户名
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
//        如果user对象不存在，则返回model 错误属性信息, 并返回publish.html页面
        if( user == null){
            model.addAttribute("error","用户未登录");
            return ("publish");
        }
        Question question = new Question();
        question.setDescription(description);
        question.setTitle(title);
        question.setTag(tag);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        question.setCreator(user.getId());
        questionMapper.create(question);
        return "redirect:/";


    }
}
