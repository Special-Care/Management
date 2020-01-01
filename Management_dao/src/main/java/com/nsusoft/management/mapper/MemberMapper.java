package com.nsusoft.management.mapper;

import com.nsusoft.management.domain.Member;
import org.apache.ibatis.annotations.Select;

public interface MemberMapper {
    @Select("select * from member where id = #{memberId}")
    Member queryMemberByMemberId(String memberId);
}
