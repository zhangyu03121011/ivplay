package com.mm.dev.dao.jpa.user;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mm.dev.entity.user.UserFiles;

/**
 * @Description: UserFilesDao
 * @author Jacky
 * @date 2017年8月6日 下午9:45:39
 */
@Repository
public interface UserFilesDao extends JpaRepository<UserFiles, String> {

    /**
	 * @Description: 根据opoenId,文件分类查询列表
	 * @Datatime 2017年8月6日 下午9:42:44 
	 * @return List<UserFiles>    返回类型
	 */
	public List<UserFiles> findByOpenIdAndFileCategory(String openId,String fileCategory) throws Exception;
	
	/**
	 * @Description: 根据opoenId分页查询列表
	 * @Datatime 2017年8月6日 下午10:57:27 
	 * @return List<UserFiles>    返回类型
	 */
	public Page<UserFiles> findAllByOpenIdAndDelFlag(String openId,String delFlag,Pageable pageable) throws Exception;
	
}
