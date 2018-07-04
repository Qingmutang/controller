package com.modianli.power.common.service;

import com.modianli.power.domain.jpa.Recruit;
import com.modianli.power.domain.jpa.UserAccount;
import com.modianli.power.model.RecruitDetails;
import com.modianli.power.model.RecruitForm;
import com.modianli.power.persistence.repository.jpa.EnterpriseRepository;
import com.modianli.power.persistence.repository.jpa.RecruitRepository;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import lombok.extern.slf4j.Slf4j;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by gao on 17-7-4.
 */
@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class RecruitServiceTests {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Mock
  private RecruitRepository recruitRepository;

  @Mock
  private EnterpriseRepository enterpriseRepository;

  @InjectMocks
  private RecruitService recruitService;

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void saveRecruit() throws Exception {
	Recruit recruit = Recruit
		// @formatter:off
		.builder()
		.positionName("test123")
       // @formatter:on
		.build();

	RecruitForm recruitForm = RecruitForm
		// @formatter:off
		.builder()
		.positionName("test123")
       // @formatter:on
		.build();
	UserAccount userAccount = new UserAccount();
	userAccount.setId(1L);

	when(recruitRepository.save(any(Recruit.class))).thenReturn(recruit);

	RecruitDetails saveRecruit = recruitService.saveRecruit(recruitForm, userAccount);

	assertEquals("test123", saveRecruit.getPositionName());

  }

  @Test
  public void findRecruits() throws Exception {
  }

  @Test
  public void findOneRecruit() throws Exception {
  }

  @Test
  public void findOneRecruit1() throws Exception {
  }

  @Test
  public void updateRecruit() throws Exception {
  }

  @Test
  public void deactivateRecruit() throws Exception {
  }

  @Test
  public void activateRecruit() throws Exception {
  }

}