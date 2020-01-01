package com.nsusoft.management.mapper;

import com.nsusoft.management.domain.Permission;
import com.nsusoft.management.domain.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface RoleMapper {
    @Select("select * from role where id in(select roleId from users_role where userId = #{userId})")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "roleName", column = "roleName"),
            @Result(property = "roleDesc", column = "roleDesc"),
            @Result(property = "permissions", column = "id", javaType = java.util.List.class,
                    many = @Many(select = "com.nsusoft.management.mapper.PermissionMapper.queryPermissionByRoleId"))
    })
    List<Role> queryRoleByUserId(String userId);

    @Select("select * from role")
    List<Role> queryAllRole();

    @Insert("insert into role values(replace(uuid(),'-',''),#{roleName},#{roleDesc})")
    void insertRole(Role role);

    @Select("select * from role where id = #{roleId}")
    Role queryRoleByRoleId(String roleId);

    @Select("select * from permission where id not in(select permissionId from role_permission where roleId = #{roleId})")
    List<Permission> queryOtherPermissions(String roleId);

    @Insert("insert into role_permission values(#{permissionId}, #{roleId})")
    void addPermissionToRole(@Param("roleId")String roleId, @Param("permissionId")String permissionId);
}
