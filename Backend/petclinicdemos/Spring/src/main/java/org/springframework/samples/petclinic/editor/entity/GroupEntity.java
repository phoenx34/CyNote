package org.springframework.samples.petclinic.editor.entity;

import org.springframework.samples.petclinic.editor.utils.ReflectToStringUtil;

public class GroupEntity {
	
	private Long id;
	
	private String author;
	private String group;
	private String member;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getGroup() {
		return this.group;
	}
	
	public void setGroup(String group) {
		this.group = group;
	}
	
	public String getMember() {
		return this.member;
	}
	
	public void setMember(String member) {
		this.member = member;
	}
	
	public String toString() {
		return ReflectToStringUtil.toStringUtil(this, true);
	}
}
