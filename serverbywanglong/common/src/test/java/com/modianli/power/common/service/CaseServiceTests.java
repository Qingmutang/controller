package com.modianli.power.common.service;

import com.google.common.collect.Lists;

import com.modianli.power.domain.jpa.Cases;
import com.modianli.power.domain.jpa.Enterprise;
import com.modianli.power.model.CaseForm;
import com.modianli.power.persistence.repository.jpa.CasePicturesRepository;
import com.modianli.power.persistence.repository.jpa.CasesRepository;
import com.modianli.power.persistence.repository.jpa.EnterpriseRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import lombok.extern.slf4j.Slf4j;

import static org.mockito.Mockito.when;

/**
 * Created by gao on 17-7-5.
 */
@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class CaseServiceTests {

  @Mock
  private EnterpriseRepository enterpriseRepository;

  @Mock
  private CasesRepository casesRepository;

  @Mock
  private CasePicturesRepository casePicturesRepository;

  @Mock
  private RedisService redisService;

  @InjectMocks
  private CaseService caseService;

  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void saveCase() throws Exception {
	Cases cases = Cases
		.builder()
		.projectName("test")
		.build();

	CaseForm caseForm = CaseForm
		.builder()
		.projectName("test")
		.urls(Lists.newArrayList("http://www.baidu.com"))
		.build();

	Enterprise enterprise = new Enterprise();
	enterprise.setId(1L);

	when(casesRepository.save(Matchers.any(Cases.class))).thenReturn(cases);
	when(enterpriseRepository.findOne(1L)).thenReturn(enterprise);

	caseService.saveCase(caseForm, 1L);

  }

  @Test
  public void deleteCase() throws Exception {
  }

  @Test
  public void restoreCase() throws Exception {
  }

  @Test
  public void updateCase() throws Exception {
  }

  @Test
  public void searchCase() throws Exception {
  }

  @Test
  public void findOne() throws Exception {
  }

  @Test
  public void searchPubCase() throws Exception {
  }

}