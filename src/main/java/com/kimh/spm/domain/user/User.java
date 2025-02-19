package com.kimh.spm.domain.user;

import com.kimh.spm.domain.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true) //Lombok이 상속받은 필드도 포함하여 equals 및 hashCode를 생성하도록 설정
@Table(name = "USER")
public class User extends BaseEntity{
	@Id
	@Column(name="US_ID", nullable = false, unique = true)
	private String usId;

	@Column(name="US_NM",nullable=false)
	private String usNm;
	 
	@Column(name="US_PW",nullable=false)
	private String usPw;

	@Column(name="US_ROLE",nullable=false)
	private String usRole;
	
	@Column(name="US_EMAIL",nullable=false)
	private String usEmail;
	
}
