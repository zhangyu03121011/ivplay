package com.mm.dev.controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mm.dev.constants.WechatConstant;
import com.mm.dev.entity.user.UserFiles;
import com.mm.dev.entity.wechat.ReturnMsg;
import com.mm.dev.enums.ExceptionEnum;
import com.mm.dev.service.user.IUserFilesService;
import com.mm.dev.util.ReturnMsgUtil;
/**
 * @ClassName: wechartController 
 * @Description: 微信业务接口
 * @author zhangxy
 * @date 2017年7月31日 下午3:03:05
 */
@Controller
@RequestMapping("/userFiles")
public class UserFilesController{
	private Logger logger = LoggerFactory.getLogger(UserFilesController.class);
	
	@Autowired
	private IUserFilesService userFileService;
	
	/**
	 * @Description: 根据opoenId,文件分类查询列表
	 * @Datatime 2017年8月6日 下午9:42:44 
	 * @return List<UserFiles>    返回类型
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/{start}/{count}/list", method = RequestMethod.GET)
	@ResponseBody
	public ReturnMsg<Object> findListByOpenIdAndFileCategory(@PathVariable("start") int start,@PathVariable("count") int count,HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Page<UserFiles> userFileList = null;
		try {
//			String openId = (String)UserSession.getSession(WechatConstant.OPEN_ID);
			String openId = "o5z7ywOP7qycrtAAxIqDfgMbfcFY";
			if(StringUtils.isNotEmpty(openId)) {
				Sort sort = new Sort(Direction.DESC, "createTime");
				Pageable pageable = new PageRequest(start,count, sort);
				userFileList = userFileService.getAll(openId,WechatConstant.delete_flag_1,pageable);
			}
		} catch (Exception e) {
			logger.error("根据opoenId,文件分类查询列表异常",e);
			return ReturnMsgUtil.error(ExceptionEnum.system_error);
		}
		return ReturnMsgUtil.success(userFileList);
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ReturnMsg<Object> deleteById(@PathVariable("id") String id,HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
//			String openId = (String)UserSession.getSession(WechatConstant.OPEN_ID);
			String openId = "o5z7ywOP7qycrtAAxIqDfgMbfcFY";
			if(StringUtils.isNotEmpty(openId)) {
				userFileService.deleteById(id);
			}
		} catch (Exception e) {
			logger.error("根据opoenId,文件分类查询列表异常",e);
			return ReturnMsgUtil.error(ExceptionEnum.system_error);
		}
		return ReturnMsgUtil.success();
	}
}

