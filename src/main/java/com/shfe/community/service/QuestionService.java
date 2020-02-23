package com.shfe.community.service;

import com.shfe.community.dto.PaginationDTO;
import com.shfe.community.dto.QuestionDTO;
import com.shfe.community.mapper.QuestionMapper;
import com.shfe.community.mapper.UserMapper;
import com.shfe.community.model.Question;
import com.shfe.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

/*
* service层将多个数据层对象组装成一个，比如将user,question 对象组装为QuestionDTO 对象，QuestionDto对象包含一个问题以及这个问题创建者的信息
* */
    public PaginationDTO list(Integer page, Integer size) {

        List<QuestionDTO> questionDTOList= new ArrayList<>();
        PaginationDTO paginationDTO= new PaginationDTO();  //包含questionDTOlist 和其他属性

//        限制page不能超过最大page
        Integer totalcount=questionMapper.count();
        paginationDTO.setPagination(totalcount,page,size);
        if(page<1){
            page=1;
        }
        if (page>paginationDTO.getTotalPage()){
            page=paginationDTO.getTotalPage();
        }

        Integer offset = size * (page-1);
//        取出question中特定页面的记录
        List<Question> questions = questionMapper.list(offset,size);
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
//            BeanUtils.copyProperties 将一个对象的属性全部拷贝到另外一个对象中
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
//        设置PaginationDTO对象的属性
        paginationDTO.setQuestions(questionDTOList);


        return paginationDTO;
    }
}
