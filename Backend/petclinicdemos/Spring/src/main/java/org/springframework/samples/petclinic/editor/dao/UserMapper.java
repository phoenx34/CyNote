package org.springframework.samples.petclinic.editor.dao;

import org.springframework.samples.petclinic.editor.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import java.util.List;

public interface UserMapper {
	
	@Select("select * from t_user")
	List<UserEntity> findAll();
	
	@Select("select * from t_user where name=#{name} and password=#{password}")
	List<UserEntity> findOne(String name, String password);
	
	@Select("select * from t_user where name=#{name} and password=#{password}")
	List<UserEntity> findOneUserEntity(UserEntity user);
}
