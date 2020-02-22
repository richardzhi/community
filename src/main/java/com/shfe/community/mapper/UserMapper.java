package com.shfe.community.mapper;

import com.shfe.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("insert into user(name, account_id,token,gmt_create,gmt_modified,bio,avatar_url ) values(#{name}, #{accountId}, #{token}, #{gmtCreate}, #{gmtModified},#{bio}, #{avatarUrl})")
    void insert(User user);

//    将参数传入sql时，如果是一个类，可以自动通过get方法获取参数；如果是一个普通参数，则加入@Param("token") 指明后面参数会传递
    @Select("select * from user where token='${token}'")
    User findByToken(@Param("token") String token);

    @Select("select * from user where id=${id}")
    User findById(@Param("id") Integer id);
}
