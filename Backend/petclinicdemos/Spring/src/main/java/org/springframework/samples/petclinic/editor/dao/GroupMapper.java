package org.springframework.samples.petclinic.editor.dao;

import org.springframework.samples.petclinic.editor.entity.GroupEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List; 

@Mapper
@Repository
public interface GroupMapper {
	
    @Select("select * from t_group")
    List<GroupEntity> findAll();
    
    @Select("select * from t_group where id= #{id} ")
    public GroupEntity get(int id);
    
    @Select("select * from t_group where authorName= #{authorName} ")
    public List<GroupEntity> getByAuthorName(String authorName);
    
    @Select("select * from t_group where memberName= #{memberName} ")
    public List<GroupEntity> getByMemberName(String memberName);
    
    @Select("select * from t_group where groupName= #{groupName} ")
    public List<GroupEntity> getByGroupName(String groupName);
    
    @Insert(" insert into t_group ( authorName,groupName,memberName ) values (#{authorName},#{groupName},#{memberName}) ")
    public int save(GroupEntity group);

}
