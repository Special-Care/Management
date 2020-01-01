package com.nsusoft.management.mapper;

import com.nsusoft.management.domain.Permission;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PermissionMapper {
    @Select("select * from permission where id in(select permissionId from role_permission where roleId = #{roleId})")
    List<Permission> queryPermissionByRoleId(String roleId);

    @Select("select * from permission")
    List<Permission> queryAllPermission();

    @Insert("insert into permission values(replace(uuid(),'-',''),#{permissionName},#{url})")
    void insertPermission(Permission permission);
}
