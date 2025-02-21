package com.kimh.spm.domain.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.kimh.spm.domain.user.User;

import jakarta.transaction.Transactional;

public interface UserRepository extends CrudRepository<User, String> {
	Optional<User> findByUsNm(String usNm);
	Optional<User> findByUsId(String usId);
	
	@Modifying
    @Query(value = "UPDATE USER SET US_PW = :newPassword WHERE US_ID = :usId", nativeQuery = true)
    int updatePasswordByUserid(@Param("usId") String usId, @Param("newPassword") String newPassword);
}