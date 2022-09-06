package com.project.ricky.user.repository;

import com.project.ricky.user.vo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserEmail(String username);

//    User findByUserEmail(String username);

//    @Query("select count(*) from open_project.tb_user where userEmail = :userEamil and userPassword = :userPassword")
//    Long findByUserEmailAndUserPassword(@NotBlank String userEmail, @NotBlank String userPassword) throws Exception;

}
