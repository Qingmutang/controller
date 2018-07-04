package com.modianli.power.common.service;

import com.google.common.collect.Lists;

import com.modianli.power.domain.jpa.Enterprise;
import com.modianli.power.domain.jpa.Product;
import com.modianli.power.domain.jpa.Requirements;
import com.modianli.power.model.Sitemap;
import com.modianli.power.persistence.repository.jpa.EnterpriseRepository;
import com.modianli.power.persistence.repository.jpa.ProductRepository;
import com.modianli.power.persistence.repository.jpa.RequirementRepository;
import com.redfin.sitemapgenerator.ChangeFreq;
import com.redfin.sitemapgenerator.WebSitemapGenerator;
import com.redfin.sitemapgenerator.WebSitemapUrl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by dell on 2017/4/20.
 */
@Service
@Slf4j
public class SiteMapService {

  @Inject
  private ProductRepository productRepository;

  @Inject
  private EnterpriseRepository enterpriseRepository;

  @Inject
  private RequirementRepository requirementRepository;

  @Value("${modian.url}")
  private String url;

  @SneakyThrows
  public void siteMap() {
	String tmp = System.getProperty("java.io.tmpdir");
	File file = new File(tmp + "/sitemap");
	if (!file.exists()) {
	  file.mkdirs();
	}

	WebSitemapGenerator wsg = WebSitemapGenerator
		.builder(url, file)
		.build();

	List<Sitemap> sitemapList = Lists.newArrayList();

	int page = 0;
	int size = 300;
	boolean flag = true;
	//产品siteMap

	while (flag) {
	  Page<Product> productPage = productRepository.findByActiveOrderByCreatedDateDesc(true, new PageRequest(page, size));
	  if (productPage.getTotalPages() == 0 || productPage.getTotalPages() == page + 1) {
		flag = false;
	  }

	  List<Product> products = productPage.getContent();

	  List<Sitemap> productSitemap = products.stream().parallel().map(p -> {
		String tmpUrl = url + "quotesMall/detail/" + p.getUuid();
		return new Sitemap(tmpUrl, p.getLastModifiedDate());
	  }).collect(Collectors.toList());

	  sitemapList.addAll(productSitemap);

	  page++;
	}

	page = 0;
	flag = true;
	//供应商siteMap

	while (flag) {
	  Page<Enterprise>
		  enterprisePage =
		  enterpriseRepository.findByActiveOrderByCreatedDateDesc(true, new PageRequest(page, size));
	  if (enterprisePage.getTotalPages() == 0 || enterprisePage.getTotalPages() == page + 1) {
		flag = false;
	  }

	  List<Enterprise> enterprises = enterprisePage.getContent();

	  List<Sitemap> enterpriseSitemap = enterprises.stream().parallel().map(p -> {
		String tmpUrl = url + "enterprise/detail/" + p.getUuid() + "/index";
		return new Sitemap(tmpUrl, p.getLastModifiedDate());
	  }).collect(Collectors.toList());

	  sitemapList.addAll(enterpriseSitemap);

	  page++;
	}

	page = 0;
	flag = true;
	//需求siteMap

	while (flag) {
	  Page<Requirements>
		  requirementsPage =
		  requirementRepository.findByActiveOrderByCreatedDateDesc(true, new PageRequest(page, size));
	  if (requirementsPage.getTotalPages() == 0 || requirementsPage.getTotalPages() == page + 1) {
		flag = false;
	  }

	  List<Requirements> requirements = requirementsPage.getContent();

	  List<Sitemap> enterpriseSitemap = requirements.stream().parallel().map(p -> {
		String tmpUrl = url + "requirement/detail/" + p.getRequirementUUID();
		return new Sitemap(tmpUrl, p.getLastModifiedDate());
	  }).collect(Collectors.toList());

	  sitemapList.addAll(enterpriseSitemap);

	  page++;
	}

	log.info("sitemap size {} ", sitemapList.size());

	sitemapList.stream()
			   .parallel()
			   .sorted(Comparator.comparing(Sitemap::getLastModified).reversed())
			   .forEachOrdered(s -> {
				 try {
				   WebSitemapUrl webSitemapUrl = new WebSitemapUrl.Options(s.getUrl())
					   .lastMod(Date.from(s.getLastModified().atZone(ZoneId.systemDefault()).toInstant()))
					   .priority(1D)
					   .changeFreq(ChangeFreq.WEEKLY)
					   .build();
				   wsg.addUrl(webSitemapUrl);
				 } catch (Exception e) {
				   log.error("error {} ", e.getMessage());
				 }
			   });

	wsg.write();

	if (sitemapList.size() > 50000) {
	  wsg.writeSitemapsWithIndex();
	}

  }


}
