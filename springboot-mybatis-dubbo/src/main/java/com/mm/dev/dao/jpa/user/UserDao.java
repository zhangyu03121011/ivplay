package com.mm.dev.dao.jpa.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mm.dev.entity.user.User;

/**
 * @Description: UserDao
 * @author Jacky
 * @date 2017年8月6日 下午9:46:02
 */
@Repository
public interface UserDao extends JpaRepository<User, String> {

//    Page<User> findAll(Pageable pageable);

}
