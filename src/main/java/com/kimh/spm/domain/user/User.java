package com.kimh.spm.domain.user;

import com.kimh.spm.domain.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true) //Lombok이 상속받은 필드도 포함하여 equals 및 hashCode를 생성하도록 설정
public class User extends BaseEntity{
	@Id
	@Column(name="us_id", nullable = false, unique = true)
	private String usId;

	@Column(name="us_nm",nullable=false)
	private String usNm;
	 
	@Column(name="us_pw",nullable=false)
	private String usPw;

	@Column(name="us_role",nullable=false)
	private String usRole;
	
	@Column(name="us_email",nullable=false)
	private String usEmail;
	
}
