package com.mm.dev.service.user;

import com.mm.dev.entity.user.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Lipengfei on 2015/6/26.
 */
public interface IUserService {

    User getUser(Long id) throws Exception;

    Page<User> getAll(Pageable pageable) throws Exception;

    List<User> getUserList() throws Exception;

    Page<User> getUserAll(Pageable pageable) throws Exception;

    void save() throws Exception;

    void saveUser() throws Exception;

}
