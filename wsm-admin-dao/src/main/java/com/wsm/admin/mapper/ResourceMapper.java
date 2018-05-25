package com.wsm.admin.mapper;

import com.wsm.admin.model.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ResourceMapper extends tk.mybatis.mapper.common.Mapper<Resource>{
	
	@Select("select distinct f from Menu f,RoleMenu rf,AdminUserRole ru  where ru.role.id=rf.role.id and rf.menu.id=f.id and ru.adminUser.id=#{userId} ")
	List<Resource> getUserMenu(Long userId);

}
