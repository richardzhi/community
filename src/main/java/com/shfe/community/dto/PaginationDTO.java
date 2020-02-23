package com.shfe.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private boolean showPreviouse;  //受否显示向前箭头
    private boolean showFirstpage;  //受否显示首页箭头
    private boolean showNext;       //受否显示向后箭头
    private boolean showEndpage;   //受否显示尾页箭头
    private Integer page;          //当前页
    private List<Integer> pages = new ArrayList<>();   //要显示的页号list
    private Integer totalPage;

    //    根据总记录数，当前页号，每页记录数，对DTO 其他参数进行赋值
    public void setPagination(Integer totalcount, Integer page, Integer size) {

        if (totalcount % size == 0) {
            totalPage = totalcount / size;
        } else {
            totalPage = totalcount / size + 1;
        }
        if(page<1){
            page=1;
        }
        if (page>totalPage){
            page=totalPage;
        }
        this.page=page;
/*        检查page号页面前是否有3个页面，如果有则从头部插入；
        检查page号页后面是否有3个页面，如果有则从尾部插入*/
        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                pages.add(0, page - i);
            }
            if (page + i <= totalPage) {
                pages.add(page + i);
            }
        }
//        判断是否有上一页箭头
        if (page == 1) {
            showPreviouse = false;
        } else {
            showPreviouse = true;
        }
//        判断是否有下一页箭头
        if (page == totalPage) {
            showNext = false;
        } else {
            showNext = true;
        }
//        判断是否有首页或尾页箭头的时候，只要判断当前页列表中不包含头页或尾页即设置头尾页箭头
        if (pages.contains(1)) {
            showFirstpage = false;
        } else {
            showFirstpage = true;
        }
        if (pages.contains(totalPage)) {
            showEndpage = false;
        } else {
            showEndpage = true;
        }
    }
}
