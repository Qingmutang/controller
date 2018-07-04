package com.modianli.power.api.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.modianli.power.common.service.UserService;
import com.modianli.power.model.SignupForm;
import com.modianli.power.model.SmsCaptchaRequest;
import com.modianli.power.model.UserAccountDetails;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by gao on 17-4-11.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Slf4j
public class SignupControllerTests {

  @Autowired
  private MockMvc mvc;

  @Inject
  private ObjectMapper objectMapper;

  @MockBean(name = "userService")
  private UserService userService;

  @Test
  public void signup() throws Exception {
	UserAccountDetails userAccountDetails = new UserAccountDetails();
	userAccountDetails.setId(1L);
	userAccountDetails.setUsername("test123");
	userAccountDetails.setName("test");

	given(this.userService.registerUser(any(SignupForm.class))).willReturn(userAccountDetails);

	MvcResult result = mvc.perform(
		post("/api/public/signup")
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.content(newEntityAsJson())
	)
						  .andDo(print())
						  .andExpect(status().isCreated())
						  .andReturn();

	String content = result.getResponse().getContentAsString();
	log.info("content {} ", content);
  }

  private SignupForm newEntity() {

	SmsCaptchaRequest smsCaptchaRequest = new SmsCaptchaRequest();
	smsCaptchaRequest.setMobileNumber("18516571718");
	smsCaptchaRequest.setCaptchaValue("123456");

	SignupForm form = SignupForm.builder()
								.captchaResponse(smsCaptchaRequest)
								.username("test123")
								.password("123456")
								.build();

	return form;
  }

  private String newEntityAsJson() throws JsonProcessingException {
	return objectMapper.writeValueAsString(newEntity());
  }

}
