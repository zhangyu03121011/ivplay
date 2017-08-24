package com.mm.dev.controller.user;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
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

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.mm.dev.config.ConfigProperties;
import com.mm.dev.constants.WechatConstant;
import com.mm.dev.entity.user.UserFiles;
import com.mm.dev.entity.wechat.ReturnMsg;
import com.mm.dev.enums.ExceptionEnum;
import com.mm.dev.service.user.IUserFilesService;
import com.mm.dev.wechatUtils.ReturnMsgUtil;
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
	
	@Autowired
	private ConfigProperties configProperties;
	
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
	
	/**
	 * @Description: 根据id查询上传的信息
	 * @Datatime 2017年8月12日 下午7:48:17 
	 * @return ReturnMsg<Object>    返回类型
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ReturnMsg<Object> findById(@PathVariable("id") String id,HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/query/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ReturnMsg<Object> deleteById(@PathVariable("id") String id,HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, String> blurInfoMap = null;
		try {
//			String openId = (String)UserSession.getSession(WechatConstant.OPEN_ID);
			String openId = "o5z7ywOP7qycrtAAxIqDfgMbfcFY";
			if(StringUtils.isNotEmpty(openId)) {
				blurInfoMap = userFileService.queryBlurInfoById(id);
			}
		} catch (Exception e) {
			logger.error("根据opoenId,文件分类查询列表异常",e);
			return ReturnMsgUtil.error(ExceptionEnum.system_error);
		}
		return ReturnMsgUtil.success(blurInfoMap);
	}
	
	/**
	 * @Description: 根据ID获取打赏二维码
	 * @DateTime:2017年8月10日 上午11:29:22
	 * @return ReturnMsg<Object>
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getPayShareQrImage")
	@ResponseBody
	public ReturnMsg<Object> getPayShareQrImage(String id,HttpServletRequest request, HttpServletResponse response){
		String qrcodeImagePath = null;
		if(StringUtils.isNotEmpty(id)) {
			Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
			// 指定纠错等级
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
			// 指定编码格式
			hints.put(EncodeHintType.CHARACTER_SET, "GBK");
			//设值白框间距
			hints.put(EncodeHintType.MARGIN, 1);
			
			try {
				BitMatrix bitMatrix = new MultiFormatWriter().encode("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+configProperties.getAPPID()+"&redirect_uri=http%3a%2f%2fjacky.tunnel.qydev.com%2fwechat%2fcallback?id="+id+"&response_type=code&scope=snsapi_base&state=3&connect_redirect=1#wechat_redirect",
						BarcodeFormat.QR_CODE, configProperties.getWidth(), configProperties.getHeight(), hints);
				response.setContentType("image/jpeg");  
				// 将图像输出到Servlet输出流中。  
				ServletOutputStream sos = response.getOutputStream();  
				MatrixToImageWriter.writeToStream(bitMatrix, "jpeg", sos);
				sos.close();  
			} catch (Exception e) {
				logger.error("根据ID获取打赏二维码异常",e);
			} 
		}
		return ReturnMsgUtil.success(qrcodeImagePath);
	}
	
}

