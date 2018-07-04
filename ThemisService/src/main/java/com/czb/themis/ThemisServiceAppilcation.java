package com.czb.themis;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.czb.themis.util.SpringContextHolder;

/**
 * 讲师排班系统 主程序
 *
 * @author ljmatlight
 * @date 2018/6/14
 */
@SpringBootApplication
public class ThemisServiceAppilcation {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ThemisServiceAppilcation.class);
        //关闭banner
        application.setBannerMode(Mode.OFF);
        SpringContextHolder.setApplicationContext(application.run(args));
    }

}
