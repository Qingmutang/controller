package com.czb.themis.controller;


import com.czb.themis.base.entity.TestMybatisUser;
import com.czb.themis.service.TestMybatisUserService;
import com.czb.themis.util.view.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ljmatlight
 * @since 2018-06-20
 */
@Slf4j
@RestController
@RequestMapping("/testMybatisUser")
public class TestMybatisUserController {

    @Autowired
    private TestMybatisUserService testMybatisUserService;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/user")
    public ApiResult saveUser(TestMybatisUser testMybatisUser) {

        boolean flag = testMybatisUserService.insert(testMybatisUser);

        log.info("flag ==== " + flag);
        return ApiResult.SUCCESS;
    }


    @GetMapping("/user")
    public ApiResult user() {
        log.info("flag ==== ");

        String result = restTemplate.getForObject("https://www.baidu.com/", String.class);

        log.info("result ==== {}", result);

        return ApiResult.SUCCESS;
    }


}

