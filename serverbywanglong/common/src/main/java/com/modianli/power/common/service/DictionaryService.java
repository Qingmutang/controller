package com.modianli.power.common.service;

import com.modianli.power.DTOUtils;
import com.modianli.power.common.exception.ResourceNotFoundException;
import com.modianli.power.domain.jpa.DictionaryItem;
import com.modianli.power.domain.jpa.DictionaryType;
import com.modianli.power.model.DictionaryItemDetails;
import com.modianli.power.model.DictionaryItemForm;
import com.modianli.power.model.DictionaryTypeDetails;
import com.modianli.power.model.DictionaryTypeForm;
import com.modianli.power.persistence.repository.jpa.DictionaryItemRepository;
import com.modianli.power.persistence.repository.jpa.DictionaryTypeRepository;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class DictionaryService {

  @Inject
  private DictionaryItemRepository dictionaryItemRepository;

  @Inject
  private DictionaryTypeRepository dictionaryTypeRepository;

  @Transactional(readOnly = true)
  public List<DictionaryItemDetails> findDictionaryItemByCodeAndActive(String code) {
	Assert.hasText(code, "code cannot be empty");
	log.debug("findDictionaryItemByCodeAndActive code {}", code);
	List<DictionaryItem>
		dictionaryItemList =
		dictionaryItemRepository.findByCodeAndActive(code, new Sort(Sort.Direction.DESC, "sort"));
	return DTOUtils.mapList(dictionaryItemList, DictionaryItemDetails.class);
  }

  @Transactional(readOnly = true)
  public List<DictionaryItemDetails> findDictionaryItemByCode(String code) {
	Assert.hasText(code, "code cannot be empty");
	log.debug("findDictionaryItemByCode code {}", code);
	List<DictionaryItem> dictionaryItemList = dictionaryItemRepository.findByCode(code, new Sort(Sort.Direction.DESC, "sort"));
	return DTOUtils.mapList(dictionaryItemList, DictionaryItemDetails.class);
  }

  @Transactional(readOnly = true)
  public List<DictionaryTypeDetails> findDictionaryTypeByText(String text) {
	Assert.hasText(text, "text cannot be empty");
	log.debug("findDictionaryTypeByText text {}", text);
	List<DictionaryType> dictionaryTypeList = dictionaryTypeRepository.findByText(text);
	return DTOUtils.mapList(dictionaryTypeList, DictionaryTypeDetails.class);
  }

  @Transactional(readOnly = true)
  public List<DictionaryTypeDetails> findDictionaryType() {
	log.debug("findDictionaryType");
	List<DictionaryType> dictionaryTypeList = dictionaryTypeRepository.findAll();
	return DTOUtils.mapList(dictionaryTypeList, DictionaryTypeDetails.class);
  }

  public DictionaryTypeDetails saveDictionaryType(DictionaryTypeForm form) {
	Assert.notNull(form, "form cannot be null");
	log.debug("form {}", form);
	log.info("saveDictionaryType in => ");
	DictionaryType dictionaryType = DTOUtils.strictMap(form, DictionaryType.class);

	DictionaryType saved = dictionaryTypeRepository.save(dictionaryType);

	DictionaryTypeDetails details = DTOUtils.map(saved, DictionaryTypeDetails.class);
	log.info("saveDictionaryType out => ");
	return details;
  }

  @Transactional(readOnly = true)
  public DictionaryTypeDetails findDictionaryType(Long id) {
	Assert.notNull(id, "id cannot be null");
	log.info("findDictionaryType in =>");
	log.debug("id {}", id);
	DictionaryType dictionaryType = dictionaryTypeRepository.findOne(id);
	if (null == dictionaryType) {
	  throw new ResourceNotFoundException(" dictionaryType can't find by id :" + id);
	}
	DictionaryTypeDetails details = DTOUtils.map(dictionaryType, DictionaryTypeDetails.class);
	log.info("findDictionaryType out =>");
	return details;
  }

  public DictionaryTypeDetails updateDictionaryType(Long id, DictionaryTypeForm form) {
	Assert.notNull(id, "id cannot be null");
	Assert.notNull(form, "form cannot be null");
	log.debug("form {} id {} ", form, id);
	log.info("updateDictionaryType in => ");

	DictionaryType dictionaryType = dictionaryTypeRepository.findOne(id);

	if (null == dictionaryType) {
	  throw new ResourceNotFoundException(" dictionaryType can't find by id :" + id);
	}

	DTOUtils.strictMapTo(form, dictionaryType);

	DictionaryType saved = dictionaryTypeRepository.save(dictionaryType);

	DictionaryTypeDetails details = DTOUtils.map(saved, DictionaryTypeDetails.class);
	log.info("updateDictionaryType out => ");
	return details;
  }

  public void deactivateDictionaryType(Long id) {
	Assert.notNull(id, "DictionaryType id can not be null");
	dictionaryTypeRepository.updateActiveStatus(id, false);
  }

  public void activateDictionaryType(Long id) {
	Assert.notNull(id, "DictionaryType id can not be null");
	dictionaryTypeRepository.updateActiveStatus(id, true);
  }

  public DictionaryItemDetails saveDictionaryItem(DictionaryItemForm form) {
	Assert.notNull(form, "form cannot be null");
	Assert.notNull(form.getDictionaryTypeFK(), "dictionaryTypeFK cannot be null");
	Assert.notNull(form.getDictionaryTypeFK().getId(), "dictionaryTypeFK.getId() cannot be null");
	log.debug("form {}", form);
	log.info("saveDictionaryItem in => ");

	DictionaryType dictionaryType = dictionaryTypeRepository.findOne(form.getDictionaryTypeFK().getId());
	if (null == dictionaryType) {
	  throw new ResourceNotFoundException(" dictionaryType can't find by id :" + form.getDictionaryTypeFK().getId());
	}
	Long sort = dictionaryItemRepository.findMaxSortByDictionaryType(dictionaryType);

	if (sort == null) {
	  sort = 0L;
	}

	DictionaryItem dictionaryItem = DTOUtils.strictMap(form, DictionaryItem.class);
	dictionaryItem.setSort(sort.intValue() + 1);
	dictionaryItem.setDictionaryType(dictionaryType);

	DictionaryItem saved = dictionaryItemRepository.save(dictionaryItem);

	DictionaryItemDetails details = DTOUtils.map(saved, DictionaryItemDetails.class);
	log.info("saveDictionaryItem out => ");
	return details;
  }

  @Transactional(readOnly = true)
  public DictionaryItemDetails findDictionaryItem(Long id) {
	Assert.notNull(id, "id cannot be null");
	log.info("findDictionaryItem in =>");
	log.debug("id {}", id);
	DictionaryItem dictionaryItem = dictionaryItemRepository.findOne(id);
	if (null == dictionaryItem) {
	  throw new ResourceNotFoundException(" dictionaryType can't find by id :" + id);
	}
	DictionaryItemDetails details = DTOUtils.map(dictionaryItem, DictionaryItemDetails.class);
	log.info("findDictionaryItem out =>");
	return details;
  }

  public DictionaryItemDetails updateDictionaryItem(Long id, DictionaryItemForm form) {
	Assert.notNull(id, "id cannot be null");
	Assert.notNull(form, "form cannot be null");
	Assert.notNull(form.getDictionaryTypeFK(), "dictionaryTypeFK cannot be null");
	Assert.notNull(form.getDictionaryTypeFK().getId(), "dictionaryTypeFK.getId() cannot be null");
	log.debug("form {} id {} ", form, id);
	log.info("updateDictionaryItem in => ");

	DictionaryItem dictionaryItem = dictionaryItemRepository.findOne(id);

	if (null == dictionaryItem) {
	  throw new ResourceNotFoundException(" dictionaryItem can't find by id :" + id);
	}

	DTOUtils.strictMapTo(form, dictionaryItem);

	DictionaryType dictionaryType = dictionaryTypeRepository.findOne(form.getDictionaryTypeFK().getId());
	if (null == dictionaryType) {
	  throw new ResourceNotFoundException(" dictionaryType can't find by id :" + form.getDictionaryTypeFK().getId());
	}
	dictionaryItem.setDictionaryType(dictionaryType);
	DictionaryItem saved = dictionaryItemRepository.save(dictionaryItem);

	DictionaryItemDetails details = DTOUtils.map(saved, DictionaryItemDetails.class);
	log.info("updateDictionaryItem out => ");
	return details;
  }

  public void deactivateDictionaryItem(Long id) {
	Assert.notNull(id, "DictionaryItem id can not be null");
	dictionaryItemRepository.updateActiveStatus(id, false);
  }

  public void activateDictionaryItem(Long id) {
	Assert.notNull(id, "DictionaryItem id can not be null");
	dictionaryItemRepository.updateActiveStatus(id, true);
  }


}
