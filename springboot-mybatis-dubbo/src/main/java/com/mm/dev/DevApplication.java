package com.mm.dev;
import javax.servlet.MultipartConfigElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
* @ClassName: DevApplication 
* @Description: TODO 应用启动类
* @author Jacky
* @date 2017年7月29日 下午12:16:43
 */
@SpringBootApplication
@EnableJpaRepositories
public class DevApplication {
	private static final Logger logger = LoggerFactory.getLogger(DevApplication.class);
	
    public static void main(String[] args) {
    	long currentTimeMillis = System.currentTimeMillis();
        SpringApplication.run(DevApplication.class, args);
        long currentTimeMillis2 = System.currentTimeMillis();
        logger.info("应用启动成功耗时:{}秒",currentTimeMillis2-currentTimeMillis);
    }
    
    /**  
     * 文件上传配置  
     * @return  
     */  
    @Bean  
    public MultipartConfigElement multipartConfigElement() {  
        MultipartConfigFactory factory = new MultipartConfigFactory();  
        //文件最大  
        factory.setMaxFileSize("10240KB"); //KB,MB  
        //设置总上传数据总大小  
        factory.setMaxRequestSize("102400KB");  
        return factory.createMultipartConfig();  
    }  

}
