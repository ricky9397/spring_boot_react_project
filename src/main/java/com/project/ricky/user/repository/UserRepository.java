package com.project.ricky.user.repository;

import com.project.ricky.user.vo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserEmail(String username);

    @Modifying // update , delete Query시 @Modifying 어노테이션, nativeQuery = true 추가
    @Query(value = "UPDATE TB_USER SET refreshToken = :refreshToken where userId = :userId", nativeQuery = true)
    void update(@Param(value = "refreshToken") String refreshToken, @Param(value = "userId") Long userId);

}
