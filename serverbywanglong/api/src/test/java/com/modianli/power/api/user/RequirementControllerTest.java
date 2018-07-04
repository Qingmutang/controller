package com.modianli.power.api.user;

import com.google.common.collect.Lists;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modianli.power.MoDianPowerApplication;
import com.modianli.power.common.captcha.CaptchaService;
import com.modianli.power.model.ApiConstants;
import com.modianli.power.model.AuthenticationRequest;
import com.modianli.power.model.ImgCaptchaRequest;
import com.modianli.power.model.RequirementBiddingForm;
import com.modianli.power.model.RequirementForm;
import com.modianli.power.model.RequirementQueryForm;
import com.modianli.power.model.SigninForm;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Created by haijun on 2017/6/6.
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MoDianPowerApplication.class)
public class RequirementControllerTest {

  @Value("${modian.localhost}")
  private String host;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private WebApplicationContext context;

  @MockBean
  private CaptchaService captchaService;

  private TestRestTemplate restTemplate = new TestRestTemplate();

  private MockMvc mockMvc;

  private String profileToken;

  private String backendToken;

  private String enterpriseToken;

  @Before
  public void setup() throws Exception {
    log.debug("setup mockMvc.");

    //验证码返回：true
    given(this.captchaService.verifyImgCaptcha(any(ImgCaptchaRequest.class))).willReturn(true);

	mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

	//管理员登录后的token
	MvcResult result = mockMvc.perform(
		post( "/api/authenticate" )
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.content(objectMapper.writeValueAsString(new AuthenticationRequest("admin", "test123")))
	).andDo(print()).andReturn();
	String responseBody = result.getResponse().getContentAsString();
	backendToken = responseBody.substring(responseBody.indexOf("\"token\":") + 8, responseBody.length() - 1);
	log.info(">>> admin login result:\n {}", backendToken);

	//TODO 企业用户登录后的token, PS:更改登录用户
	SigninForm signinForm = new SigninForm();
	signinForm.setPassword("123456");
	signinForm.setUsername("15821963290");

	ImgCaptchaRequest request = new ImgCaptchaRequest();
	request.setKey("key");
	request.setResponseValue("value");
	signinForm.setCaptchaResponse(request);

	result = mockMvc.perform(
		post( "/api/signin" )
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.content(objectMapper.writeValueAsString(signinForm))
	).andDo(print()).andReturn();
	responseBody = result.getResponse().getContentAsString();
	profileToken = responseBody.substring(responseBody.indexOf("\"token\":") + 8, responseBody.length() - 1);
	log.info(">>> profile login token: {}", profileToken);

	//TODO 供应商登录后的token, PS:更改登录用户
	signinForm = new SigninForm();
	signinForm.setPassword("123456");
	signinForm.setUsername("md_test");
	signinForm.setCaptchaResponse(request);

	result = mockMvc.perform(
		post( "/api/enterprise/signin" )
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.content(objectMapper.writeValueAsString(signinForm))
	).andDo(print()).andReturn();
	responseBody = result.getResponse().getContentAsString();
	enterpriseToken = responseBody.substring(responseBody.indexOf("\"token\":") + 8, responseBody.length() - 1);
	log.info(">>> enterprise login token: {}", enterpriseToken);
  }

  /**
   * profile start
   */
  @Test
  public void getRequirementTest(){
    log.debug("根据UUID获得需求详情(未登录状态)");
    log.info(">>> result:\n {}", restTemplate.getForEntity(host + ApiConstants.URI_API_PUBLIC + ApiConstants.URI_REQUIREMENT + "/{uuid}", String.class, "2017060514570184549465"));
  }

  /**
   * case:
   * 1 登录用户为当前发标用户
   * 2 登录用户为非当前发标用户
   */
  @Test
  public void getRequirementLoginTest() throws Exception {
	log.debug("根据UUID获得需求详情(登录状态)");
	MvcResult result = mockMvc.perform(
		get( ApiConstants.URI_API_PUBLIC + ApiConstants.URI_REQUIREMENT + "/{uuid}", "2017060514570184549465" )
			.accept(MediaType.APPLICATION_JSON_UTF8)
			.header("x-auth-token", backendToken) //TODO 切换登录token
	).andDo(print()).andReturn();

	log.info(">>> result:\n {}", result.getResponse().getContentAsString());
  }

  @Test
  public void getRequirementBiddingTest(){
    log.debug("分页获得需求配单信息");
	log.info(">>> result:\n {}", restTemplate.getForEntity(host + ApiConstants.URI_API_PUBLIC + ApiConstants.URI_REQUIREMENT + "/bid/{uuid}", String.class, "2017060514570184549465"));
  }

  /**
   * case:
   * 1 登录用户为当前发标用户 可以看到供应商报价相关内容
   * 2 登录用户为非当前发标用户 无法看到供应商报价相关内容
   */
  @Test
  public void getRequirementBiddingLoginTest() throws Exception {
	log.debug("分页获得需求配单信息");

	MvcResult result = mockMvc.perform(
		get( ApiConstants.URI_API_PUBLIC + ApiConstants.URI_REQUIREMENT + "/bid/{uuid}", "2017060514570184549465" )
			.accept(MediaType.APPLICATION_JSON_UTF8)
			.header("x-auth-token", backendToken)//TODO 切换登录token
	).andDo(print()).andReturn();

	log.info(">>> result:\n {}", result.getResponse().getContentAsString());
  }

  /**
   * case
   * 通过设置query form进行筛选
   * 如：分类， 企业认证， 省市
   */
  @Test
  public void getRequirementsTest() throws Exception {
    log.debug("分页获得需求");

	RequirementQueryForm form = new RequirementQueryForm();
	//TODO setup form to filter result
	MvcResult result = mockMvc.perform(
		post( ApiConstants.URI_API_PUBLIC + ApiConstants.URI_REQUIREMENT + "s")
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.content(objectMapper.writeValueAsString( form ))
	).andDo(print()).andReturn();

	log.info(">>> result:\n {}", result.getResponse().getContentAsString());
  }

  @Test
  public void getRequirementCategoriesTest(){
	log.debug("获得需求分类");
	log.info(">>> result:\n {}", restTemplate.getForEntity(host + ApiConstants.URI_API_PUBLIC + ApiConstants.URI_REQUIREMENT + "s/categories", String.class));
  }

  @Test
  public void topNTest(){
	log.debug("获得最新发布的10条记录");
	log.info(">>> result:\n {}", restTemplate.getForEntity(host + ApiConstants.URI_API_PUBLIC + ApiConstants.URI_REQUIREMENT + "s/top/{size}", String.class, 10));
  }

  /**
   * form不需price
   */
  @Test
  public void createRequirementTest() throws Exception {
    log.debug("企业方:发布需求");
	RequirementForm form = new RequirementForm();
	form.setName("222哈哈哈");
	form.setDescription("hahahaha");
	form.setMoney("self");
	form.setProvince("上海市");
	form.setProvinceCode("310000");
	form.setCity("市辖区");
	form.setCityCode("310100");
	form.setArea("徐汇区");
	form.setAreaCode("310104");
	form.setBiddingEndDate(LocalDate.of(2018, 10, 10));

	List<Long> categoryIds = Lists.newArrayList();
	categoryIds.add(2L);
	categoryIds.add(3L);
	form.setCategoryIds(categoryIds);

	MvcResult result = mockMvc.perform(
		post(ApiConstants.URI_API_MGT + ApiConstants.URI_REQUIREMENT + ApiConstants.URI_PROFILE)
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.content(objectMapper.writeValueAsString( form ))
			.header("x-auth-token", profileToken)
	).andDo(print()).andReturn();

	log.info(">>> result:\n {}", result.getResponse().getContentAsString());
  }

  @Test
  public void authTest() throws Exception {
    log.debug("企业方认证状态");
	MvcResult result = mockMvc.perform(
		get(ApiConstants.URI_API_MGT + ApiConstants.URI_REQUIREMENT + ApiConstants.URI_PROFILE + "/certificate")
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.header("x-auth-token", profileToken)
	).andDo(print()).andReturn();

	log.info(">>> result:\n {}", result.getResponse().getContentAsString());
  }

  @Test
  public void getRequirementsTest2() throws Exception {
    log.debug("企业方:分页获得需求");
	MvcResult result = mockMvc.perform(
		get(ApiConstants.URI_API_MGT + ApiConstants.URI_REQUIREMENT + ApiConstants.URI_PROFILE + "/page")
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.header("x-auth-token", profileToken)
	).andDo(print()).andReturn();

	log.info(">>> result:\n {}", result.getResponse().getContentAsString());
  }
  /**
   * end
   */

  /**
   * backend start
   */
  @Test
  public void getRequirementsViaConditionTest() throws Exception {
    log.debug("魔电后台:查看需求");

	MvcResult result = mockMvc.perform(
		get( ApiConstants.URI_API_MGT + ApiConstants.URI_REQUIREMENT + ApiConstants.URI_SELF)
			.param("requirementUUID", "")
			.param("status", "")
			.header("x-auth-token", backendToken)
			.accept(MediaType.APPLICATION_JSON_UTF8)
	).andDo(print()).andReturn();

	log.info(">>> result:\n {}", result.getResponse().getContentAsString());
  }

  @Test
  public void getRequirementInBkTest() throws Exception {
    log.debug("魔电后台:查看某一需求");

	/**
	 *  TODO 改变id的值查看不同的结果
	 *  id： xxx
	 */
	MvcResult result = mockMvc.perform(
		get( ApiConstants.URI_API_MGT + ApiConstants.URI_REQUIREMENT + ApiConstants.URI_SELF + "/{id}", 46)
			.header("x-auth-token", backendToken)
			.accept(MediaType.APPLICATION_JSON_UTF8)
	).andDo(print()).andReturn();

	log.info(">>> result:\n {}", result.getResponse().getContentAsString());
  }

  @Test
  public void getRequirementBiddingInBkTest() throws Exception {
    log.debug("魔电后台:查看某一需求的配单");

	/**
	 *  TODO 改变id的值查看不同的结果
	 *  id： xxx
	 */
	MvcResult result = mockMvc.perform(
		get( ApiConstants.URI_API_MGT + ApiConstants.URI_REQUIREMENT + ApiConstants.URI_SELF + "/bidding/{id}", 45)
			.header("x-auth-token", backendToken)
			.accept(MediaType.APPLICATION_JSON_UTF8)
	).andDo(print()).andReturn();

	log.info(">>> result:\n {}", result.getResponse().getContentAsString());
  }

  /**
   * case
   * form price 必须
   */
  @Test
  public void validateRequirementTest() throws Exception {
    log.debug("魔电后台:审核需求");

    RequirementForm form = new RequirementForm();
	form.setName("3333哈哈哈");
	form.setDescription("hahahaha222222222");
	form.setMoney("self");
	form.setProvince("上海市");
	form.setProvinceCode("310000");
	form.setCity("市辖区");
	form.setCityCode("310100");
	form.setArea("徐汇区");
	form.setAreaCode("310104");
	form.setBiddingEndDate(LocalDate.of(2018, 10, 11));
	form.setPrice(new BigDecimal("0.01"));//

	List<Long> categoryIds = Lists.newArrayList();
	categoryIds.add(2L);
	categoryIds.add(3L);
	form.setCategoryIds(categoryIds);

	/**
	 *  TODO 改变id的值查看不同的结果
	 *  id： xxx
	 */
	MvcResult result = mockMvc.perform(
		put(ApiConstants.URI_API_MGT + ApiConstants.URI_REQUIREMENT + ApiConstants.URI_SELF + "/{id}", 46)
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.content(objectMapper.writeValueAsString( form ))
			.header("x-auth-token", backendToken)
	).andDo(print()).andReturn();

	log.info(">>> result:\n {}", result.getResponse().getContentAsString());
  }

  /**
   * case
   * 1 上架后无法再次上架
   * 2 结标后无法上架
   * 3 下架后无法再次下架
   * 4 上架后进行自动配单
   *   配单会根据企业类型, 企业认证情况, 市区进行匹配
   */
  @Test
  public void shelfRequirementTest() throws Exception {
    log.debug("魔电后台:上(下)架");

	/**
	 *  TODO 改变id的值查看不同的结果
	 *  id： xxx
	 */
	MvcResult result = mockMvc.perform(
		put(ApiConstants.URI_API_MGT + ApiConstants.URI_REQUIREMENT + ApiConstants.URI_SELF + "/shelf/{id}/{isShelf}", 46, 1)
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.header("x-auth-token", backendToken)
	).andDo(print()).andReturn();

	log.info(">>> result:\n {}", result.getResponse().getContentAsString());
  }
  /**
   * end
   */

  /**
   * enterprise start
   */
  @Test
  public void getMatchRequirementsTest() throws Exception {
    log.debug("供应商:查看配单");

	MvcResult result = mockMvc.perform(
		get(ApiConstants.URI_API_MGT + ApiConstants.URI_REQUIREMENT + ApiConstants.URI_ENTERPRISE)
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.header("x-auth-token", enterpriseToken)
	).andDo(print()).andReturn();

	log.info(">>> result:\n {}", result.getResponse().getContentAsString());
  }

  @Test
  public void price4RequirementTest() throws Exception {
    log.debug("供应商:投标");

	/**
	 * TODO 修改需求和配单的UUID
	 * requirementUUID  requirementBiddingUUID
	 */
	String requirementUUID = "20170607104516948b0b00",
		requirementBiddingUUID = "823d83b653bf418b86c3e4a7cb031f3b";
	if(StringUtils.isBlank(requirementUUID) || StringUtils.isBlank(requirementBiddingUUID)){
	  log.debug("uuid不能为空");
	  return ;
	}

	RequirementBiddingForm  form = new RequirementBiddingForm();
	form.setMark("hahahaaha");
	form.setPrice(new BigDecimal(100000));
	MvcResult result = mockMvc.perform(
		put(ApiConstants.URI_API_MGT + ApiConstants.URI_REQUIREMENT + ApiConstants.URI_ENTERPRISE + "/price/{requirementUUID}/{requirementBiddingUUID}", requirementUUID , requirementBiddingUUID)
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.content(objectMapper.writeValueAsString(form))
			.header("x-auth-token", enterpriseToken)
	).andDo(print()).andReturn();

	log.info(">>> result:\n {}", result.getResponse().getContentAsString());
  }

  @Test
  public void view4RequirementBiddingTest() throws Exception {
    log.debug("供应商:查看投标信息");

	/**
	 * TODO 修改配单的UUID
	 * requirementBiddingUUID
	 */
	String requirementBiddingUUID = "823d83b653bf418b86c3e4a7cb031f3b";
	if(StringUtils.isBlank(requirementBiddingUUID)){
	  log.debug("uuid不能为空");
	  return ;
	}

	MvcResult result = mockMvc.perform(
		get(ApiConstants.URI_API_MGT + ApiConstants.URI_REQUIREMENT + ApiConstants.URI_ENTERPRISE + "/{requirementBiddingUUID}", requirementBiddingUUID)
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.header("x-auth-token", enterpriseToken)
	).andDo(print()).andReturn();

	log.info(">>> result:\n {}", result.getResponse().getContentAsString());
  }

  @Test
  public void view4RequirementTest() throws Exception {
	log.debug("供应商:查看标书信息");

	String requirementBiddingUUID = "823d83b653bf418b86c3e4a7cb031f3b";
	if(StringUtils.isBlank(requirementBiddingUUID)){
	  log.debug("uuid不能为空");
	  return ;
	}

	MvcResult result = mockMvc.perform(
		get(ApiConstants.URI_API_MGT + ApiConstants.URI_REQUIREMENT + ApiConstants.URI_ENTERPRISE + "/requirement/{requirementBiddingUUID}", requirementBiddingUUID)
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.header("x-auth-token", enterpriseToken)
	).andDo(print()).andReturn();

	log.info(">>> result:\n {}", result.getResponse().getContentAsString());
  }
  /**
   * end
   */
}
