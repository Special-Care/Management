package com.nsusoft.management.mapper;

import com.nsusoft.management.domain.Role;
import com.nsusoft.management.domain.UserInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {
    @Select("select * from users where username = #{username}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "phoneNum", column = "phoneNum"),
            @Result(property = "status", column = "status"),
            @Result(property = "roles", column = "id", javaType = java.util.List.class,
                    many = @Many(select = "com.nsusoft.management.mapper.RoleMapper.queryRoleByUserId"))
    })
    UserInfo queryUserByUserName(String username);

    @Select("select * from users where id = #{userId}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "phoneNum", column = "phoneNum"),
            @Result(property = "status", column = "status"),
            @Result(property = "roles", column = "id", javaType = java.util.List.class,
                    many = @Many(select = "com.nsusoft.management.mapper.RoleMapper.queryRoleByUserId"))
    })
    UserInfo queryUserByUserId(String userId);

    @Select("select * from users")
    List<UserInfo> queryAllUsers();

    @Insert("insert into users values(replace(uuid(),'-',''),#{email},#{username},#{password},#{phoneNum},#{status})")
    void insertUser(UserInfo userInfo);

    @Select("select * from role where id not in(select roleId from users_role where userId = #{userId})")
    List<Role> queryOtherRoles(String userId);

    @Insert("insert into users_role values(#{userId}, #{roleId})")
    void addRoleToUser(@Param("userId") String userId, @Param("roleId") String roleId);
}
