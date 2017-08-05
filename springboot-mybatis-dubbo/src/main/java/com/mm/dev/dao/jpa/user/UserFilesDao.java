package com.mm.dev.dao.jpa.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mm.dev.entity.user.UserFiles;

/**
 * Created by Lipengfei on 2015/6/27.
 */
@Repository
public interface UserFilesDao extends JpaRepository<UserFiles, String> {

//    Page<User> findAll(Pageable pageable);

}
