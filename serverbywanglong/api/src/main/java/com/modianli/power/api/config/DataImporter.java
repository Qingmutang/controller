package com.modianli.power.api.config;

import com.google.common.collect.Lists;

import com.modianli.power.domain.jpa.Address;
import com.modianli.power.domain.jpa.Enterprise;
import com.modianli.power.domain.jpa.GrantedPermission;
import com.modianli.power.domain.jpa.IndustryCategory;
import com.modianli.power.domain.jpa.Permission;
import com.modianli.power.domain.jpa.Role;
import com.modianli.power.domain.jpa.StaffProfile;
import com.modianli.power.domain.jpa.UserAccount;
import com.modianli.power.domain.jpa.UserProfile;
import com.modianli.power.persistence.repository.jpa.EnterpriseRepository;
import com.modianli.power.persistence.repository.jpa.GrantedPermissionRepository;
import com.modianli.power.persistence.repository.jpa.IndustryCategoryRepository;
import com.modianli.power.persistence.repository.jpa.PermissionRepository;
import com.modianli.power.persistence.repository.jpa.RoleRepository;
import com.modianli.power.persistence.repository.jpa.StaffProfileRepository;
import com.modianli.power.persistence.repository.jpa.UserProfileRepository;
import com.modianli.power.persistence.repository.jpa.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

@Named
@Transactional
public class DataImporter {

  private static final Logger log = LoggerFactory.getLogger(DataImporter.class);

  @Inject
  private Environment env;

  @Inject
  private UserRepository userRepository;

  @Inject
  private PasswordEncoder passwordEncoder;

  @Inject
  private RoleRepository roleRepository;

  @Inject
  private PermissionRepository permissionRepository;

  @Inject
  private GrantedPermissionRepository grantedPermissionRepository;

  @Inject
  private StaffProfileRepository staffProfileRepository;

  @Inject
  private UserProfileRepository userProfileRepository;

  @Inject
  private ResourceLoader resourceLoader;

  @Inject
  private IndustryCategoryRepository industryCategoryRepository;

  @Inject
  private DataSource dataSource;

  @Inject
  private EnterpriseRepository enterpriseRepository;

  @PostConstruct
  public void onPostConstruct() {
	if (log.isInfoEnabled()) {
	  log.info("importing data into database...");
	}

//	initialzePostDatas();

	loadPermissions();
	loadReservedUserData();

	if (!"prod".equals(env.getActiveProfiles()[0])) {
	  loadTestData();
	}

  }

  private void loadPermissions() {
	if (log.isInfoEnabled()) {
	  log.info("loading permissions...");
	}

	createPermissionsIfAbsent(accountPermissions());
//	createPermissionsIfAbsent(acctPermissions());
	createPermissionsIfAbsent(aliasesPermissions());
	createPermissionsIfAbsent(redisPermissions());
//	createPermissionsIfAbsent(confPermissions());
	createPermissionsIfAbsent(equipmentPermissions());
	createPermissionsIfAbsent(qiniuPermissions());
	createPermissionsIfAbsent(userPermissions());
	createPermissionsIfAbsent(enterprisePermissions());
	createPermissionsIfAbsent(productPermissions());
	createPermissionsIfAbsent(requirementPermissions());
	createPermissionsIfAbsent(recruitPermission());
	createPermissionsIfAbsent(dictionaryPermissions());
  }

  private Permission[] accountPermissions() {

	int i = 1;

	Permission[] permissions
		= new Permission[]{
		new Permission(Permission.Category.ACCOUNT, "PERM_VIEW_ACCOUNT_COLLECTION",
					   "^/api/mgt/accounts(/?)(\\?.*)?$", "GET", i++),
		new Permission(Permission.Category.ACCOUNT, "PERM_ADD_ACCOUNT", "^/api/mgt/accounts(/?)$", "POST", i++),
		new Permission(Permission.Category.ACCOUNT, "PERM_VIEW_ACCOUNT", "^/api/mgt/accounts/(\\d+)(/?)$", "GET",
					   i++),
		new Permission(Permission.Category.ACCOUNT, "PERM_EDIT_ACCOUNT", "^/api/mgt/accounts/(\\d+)(/?)$", "PUT",
					   i++),
		new Permission(Permission.Category.ACCOUNT, "PERM_LOCK_ACCOUNT", "^/api/mgt/accounts/(\\d+)(/?)$",
					   "DELETE", i++),
		new Permission(Permission.Category.ACCOUNT, "PERM_UNLOCK_ACCOUNT",
					   "^/api/mgt/accounts/(\\d+)(/?)\\?action=UNLOCK$", "PUT", i++),
		new Permission(Permission.Category.ACCOUNT, "PERM_VIEW_ROLE", "^/api/mgt/roles(/?)(\\?.*)?$", "GET", i++),
		new Permission(Permission.Category.ACCOUNT, "PERM_ADD_ROLE", "^/api/mgt/roles(/?)$", "POST", i++),
		new Permission(Permission.Category.ACCOUNT, "PERM_EDIT_ROLE", "^/api/mgt/roles/(\\d+)(/?)$", "PUT", i++),
		new Permission(Permission.Category.ACCOUNT, "PERM_LOCK_ROLE", "^/api/mgt/roles/(\\d+)(/?)$", "DELETE", i++),
		new Permission(Permission.Category.ACCOUNT, "PERM_UNLOCK_ROLE",
					   "^/api/mgt/roles/(\\d+)(/?)\\?action=UNLOCK$", "PUT", i++),
		new Permission(Permission.Category.ACCOUNT, "PERM_ASSIGN_PERMISSION",
					   "^/api/mgt/roles/[\\d]/permissions(/?)$", "PUT", i++),
		new Permission(Permission.Category.ACCOUNT, "PERM_VIEW_PERMISSION", "^/api/mgt/permissions/(\\d+)(/?)$",
					   "GET", i++),
		new Permission(Permission.Category.ACCOUNT, "PERM_EDIT_PERMISSION", "^/api/mgt/permissions/(\\d+)(/?)$",
					   "PUT", i++)

	};

	if (log.isDebugEnabled()) {
	  log.debug("account permissions @" + permissions);
	}

	return permissions;
  }

  private Permission[] acctPermissions() {

	int i = 1;

	Permission[] permissions = new Permission[]{
		new Permission(Permission.Category.ACCOUNTING, "PERM_VIEW_ACCOUNTING", "^/api/mgt/acct(/?)$", "POST", i++),};

	if (log.isDebugEnabled()) {
	  log.debug("accounting permissions @" + permissions);
	}

	return permissions;
  }

  private Permission[] aliasesPermissions() {

	int i = 1;

	Permission[] permissions = new Permission[]{
		new Permission(Permission.Category.ALIASES, "PERM_VIEW_ALIASES", "^/api/mgt/aliases/indexes(/?)$", "GET", i++),
		new Permission(Permission.Category.ALIASES, "PERM_UPDATE_ALIASES", "^/api/mgt/aliases(/?)$", "PUT", i++),
		new Permission(Permission.Category.ALIASES, "PERM_ADD_ALIASES", "^/api/mgt/aliases(/?)$",
					   "POST", i++)
	};

	if (log.isDebugEnabled()) {
	  log.debug("aliases permissions @" + permissions);
	}

	return permissions;
  }

  private Permission[] redisPermissions() {

	int i = 1;

	Permission[] permissions = new Permission[]{
		new Permission(Permission.Category.ALIASES, "PERM_DELETE_PRERENDERS", "^/api/mgt/redis/prerenders(/?)$", "DELETE", i++),
		new Permission(Permission.Category.ALIASES, "PERM_DELETE_LIST_PAGE", "^/api/mgt/redis/list_page(/?)$", "DELETE", i++)
	};

	if (log.isDebugEnabled()) {
	  log.debug("redis permissions @" + permissions);
	}

	return permissions;
  }

  private Permission[] confPermissions() {

	int i = 1;

	Permission[] permissions = new Permission[]{
		new Permission(Permission.Category.CONFIG, "PERM_VIEW_CONF", "^/api/mgt/conf(/?)$", "GET", i++),
		new Permission(Permission.Category.CONFIG, "PERM_UPDATE_CONF", "^/api/mgt/conf(/?)$", "PUT", i++),
		new Permission(Permission.Category.CONFIG, "PERM_VIEW_APPUPDATE_COLLECTION", "^/api/mgt/appupdates(/?)(\\?.*)?$", "GET",
					   i++),
		new Permission(Permission.Category.CONFIG, "PERM_ADD_APPUPDATE", "^/api/mgt/appupdates(/?)$", "POST", i++),
		new Permission(Permission.Category.CONFIG, "PERM_EDIT_APPUPDATE", "^/api/mgt/appupdates(/?)$", "PUT", i++)
	};

	if (log.isDebugEnabled()) {
	  log.debug("system config permissions @" + permissions);
	}

	return permissions;
  }

  private Permission[] userPermissions() {

	int i = 1;

	Permission[] permissions
		= new Permission[]{
		new Permission(Permission.Category.USER, "PERM_VIEW_USER_COLLECTION", "^/api/mgt/users(/?)(\\?.*)?$",
					   "GET", i++),
		new Permission(Permission.Category.USER, "PERM_ADD_USER", "^/api/mgt/users(/?)$", "POST", i++),
		new Permission(Permission.Category.USER, "PERM_VIEW_USER", "^/api/mgt/users/(\\d+)(/?)(\\?.*)?$", "GET",
					   i++),
		// new Permission(Permission.Category.USER, "PERM_VIEW_USER_PROFILE",
		// "^/api/mgt/users/[\\d]/profile(/?)$", "GET", i++),
		new Permission(Permission.Category.USER, "PERM_EDIT_USER", "^/api/mgt/users/(\\d+)(/?)$", "PUT", i++),
		new Permission(Permission.Category.USER, "PERM_DEACTIVATE_USER", "^/api/mgt/users/(\\d+)(/?)$", "DELETE",
					   i++),
		new Permission(Permission.Category.USER, "PERM_ACTIVATE_USER",
					   "^/api/mgt/users/(\\d+)(/?)\\?action=ACTIVATE$", "PUT", i++),
		new Permission(Permission.Category.USER, "PERM_LOCK_USER", "^/api/mgt/users/(\\d+)(/?)\\?action=LOCK$",
					   "PUT", i++),
		new Permission(Permission.Category.USER, "PERM_UNLOCK_USER", "^/api/mgt/users/(\\d+)(/?)\\?action=UNLOCK$",
					   "PUT", i++),
		new Permission(Permission.Category.USER, "PERM_COMPLETE_CERTIFICATE", "^/api/me/userCertificate(/?)$",
					   "POST", i++)
	};

	if (log.isDebugEnabled()) {
	  log.debug("account permissions @" + permissions);
	}

	return permissions;
  }

  private Permission[] enterprisePermissions() {
	int i = 1;
	Permission[] permissions
		= new Permission[]{
		new Permission(Permission.Category.ENTERPRISE, "PERM_VIEW_ENTERPRISE_COLLECTION",
					   "^/api/mgt/enterprises(/?)(\\?.*)?$", "GET", i++),
		new Permission(Permission.Category.ENTERPRISE, "PERM_ADD_ENTERPRISE", "^/api/mgt/enterprises(/?)$",
					   "POST", i++),
		new Permission(Permission.Category.ENTERPRISE, "PERM_IMPORT_CREDIT_TYPE", "^/api/mgt/enterprises/importCreditType(/?)$",
					   "GET", i++),
		new Permission(Permission.Category.ENTERPRISE, "PERM_IMPORT_AREA", "^/api/mgt/enterprises/importArea(/?)$",
					   "GET", i++),
		new Permission(Permission.Category.ENTERPRISE, "PERM_VIEW_ENTERPRISE", "^/api/mgt/enterprises/(\\d+)(/?)$",
					   "GET", i++),
		new Permission(Permission.Category.ENTERPRISE, "PERM_EDIT_ENTERPRISE", "^/api/mgt/enterprises/(\\d+)(/?)$",
					   "PUT", i++),
		new Permission(Permission.Category.ENTERPRISE, "PERM_ADD_ENTERPRISE_COMMENT", "^/api/mgt/enterprises/(\\d+)/comment(/?)$",
					   "POST", i++),
		new Permission(Permission.Category.ENTERPRISE, "PERM_VIEW_COMMENT_LIST", "^/api/mgt/comment(/?)(\\?.*)?$",
					   "GET", i++),
		new Permission(Permission.Category.ENTERPRISE, "PERM_DELETE_ENTERPRISE",
					   "^/api/mgt/enterprises/(\\d+)(/?)$", "DELETE", i++),

		new Permission(Permission.Category.ENTERPRISE, "PERM_VIEW_MESSAGE_LIST", "^/api/mgt/enterprises/messages(/?)(\\?.*)?$",
					   "POST", i++),

		new Permission(Permission.Category.ENTERPRISE, "PERM_RECOMMEND_ENTERPRISE", "^/api/mgt/enterprises/recommend(/?)$",
					   "PUT", i++),
		new Permission(Permission.Category.ENTERPRISE, "PERM_REGISTER_SUPPLIER",
					   "^/api/mgt/enterprises/registerSupplier(/?)(\\?.*)?$",
					   "POST", i++),
		new Permission(Permission.Category.ENTERPRISE, "PERM_RESET_PWD_ENTERPRISE", "^/api/mgt/enterprises/password/(\\w+)(/?)$",
					   "PUT", i++),

		new Permission(Permission.Category.ENTERPRISE, "PERM_ADD_CASE", "^/api/mgt/enterprises/(\\d+)/case(/?)$",
					   "POST", i++),
		new Permission(Permission.Category.ENTERPRISE, "PERM_SEARCH_CASE_LIST", "^/api/mgt/enterprises/case/search(/?)(\\?.*)?$",
					   "POST", i++)

	};

	if (log.isDebugEnabled()) {
	  log.debug("account permissions @" + permissions);
	}

	return permissions;
  }

  private Permission[] equipmentPermissions() {
	int i = 1;
	Permission[] permissions
		= new Permission[]{
		new Permission(Permission.Category.EQUIPMENT, "PERM_ADD_CATEGORY", "^/api/mgt/equipments/categorys(/?)$",
					   "POST", i++),
		new Permission(Permission.Category.EQUIPMENT, "PERM_VIEW_CATEGORY", "^/api/mgt/equipments/categorys/(\\d+)(/?)$",
					   "GET", i++),
		new Permission(Permission.Category.EQUIPMENT, "PERM_VIEW_CATEGORY_COLLECTION", "^/api/mgt/equipments/categorys(/?)$",
					   "GET", i++),
		new Permission(Permission.Category.EQUIPMENT, "PERM_VIEW_PROPERTY", "^/api/mgt/equipments/propertys/(\\d+)(/?)$",
					   "GET", i++),
		new Permission(Permission.Category.EQUIPMENT, "PERM_ADD_HOT_CATEGORY", "^/api/mgt/equipments/hotCategory(/?)$",
					   "PUT", i++),
		new Permission(Permission.Category.EQUIPMENT, "PERM_VIEW_ALL_CATEGORY", "^/api/mgt/equipments/allCategories(/?)$",
					   "GET", i++),
		new Permission(Permission.Category.EQUIPMENT, "PERM_VIEW_HOT_CATEGORY", "^/api/mgt/equipments/hotCategoriesEs(/?)$",
					   "GET", i++),
		new Permission(Permission.Category.EQUIPMENT, "PERM_VIEW_IMPORT_CATEGORY", "^/api/mgt/equipments/importCategories(/?)$",
					   "GET", i++),
		new Permission(Permission.Category.EQUIPMENT, "PERM_VIEW_IMPORT_CATEGORY_PROPERTIES",
					   "^/api/mgt/equipments/importProducts(/?)$",
					   "GET", i++),
		new Permission(Permission.Category.EQUIPMENT, "PERM_VIEW_IMPORT_CATEGORY_PROPERTIES_ES",
					   "^/api/mgt/equipments/categoryProperties(/?)$",
					   "GET", i++),
		new Permission(Permission.Category.EQUIPMENT, "PERM_VIEW_IMPORT_ALL_CATEGORY_ES", "^/api/mgt/equipments/allCategory(/?)$",
					   "GET", i++)

	};

	if (log.isDebugEnabled()) {
	  log.debug("equipment permissions @" + permissions);
	}

	return permissions;
  }

  private Permission[] qiniuPermissions() {
	int i = 1;
	Permission[] permissions
		= new Permission[]{
		new Permission(Permission.Category.QINIU, "PERM_VIEW_UPLOAD_TOKEN", "^/api/mgt/qiniu/upload/token(/?)$",
					   "GET", i++),
		new Permission(Permission.Category.QINIU, "PERM_DELETE_RESOURCE", "^/api/mgt/qiniu/resource\\?action=DEACTIVATED$",
					   "POST", i++)

	};

	if (log.isDebugEnabled()) {
	  log.debug("qiniu permissions @" + permissions);
	}

	return permissions;
  }

  private Permission[] productPermissions() {
	int i = 1;
	Permission[] permissions
		= new Permission[]{
		new Permission(Permission.Category.PRODUCT, "PERM_VIEW_PRODUCT_COLLECTION",
					   "^/api/mgt/products/search(/?)(\\?.*)?$", "POST", i++),

		new Permission(Permission.Category.PRODUCT, "PERM_ADD_PRODUCT", "^/api/mgt/products(/?)$", "POST", i++),

		new Permission(Permission.Category.PRODUCT, "PERM_VIEW_PRODUCT", "^/api/mgt/products/(\\d+)(/?)$", "GET",
					   i++),

		new Permission(Permission.Category.PRODUCT, "PERM_EDIT_PRODUCT", "^/api/mgt/products/(\\d+)(/?)$", "PUT",
					   i++),

		new Permission(Permission.Category.PRODUCT, "PERM_DELETE_PRODUCT", "^/api/mgt/products/(\\d+)(/?)$",
					   "DELETE", i++),
		new Permission(Permission.Category.PRODUCT, "PERM_VIEW_PRODUCT_PRICE",
					   "^/api/mgt/products/validEnterpriseProductPrice/(\\d+)(/?)$", "GET", i++),

		new Permission(Permission.Category.PRODUCT, "PERM_DELETE_PRODUCT_PRICE",
					   "^/api/mgt/products/enterpriseProductPrice/(\\d+)(/?)$", "DELETE", i++),

		new Permission(Permission.Category.PRODUCT, "PERM_ADD_PRODUCT_PRICE",
					   "^/api/mgt/products/enterpriseProductPrice$", "POST", i++),

		new Permission(Permission.Category.PRODUCT, "PERM_EDIT_PRODUCT_PRICE",
					   "^/api/mgt/products/updateProduct/(\\d+)(/?)$", "PUT", i++),

		new Permission(Permission.Category.PRODUCT, "PERM_NO_PRODUCT_PRICE_ENTERPRISE",
					   "^/api/mgt/products/noProductPriceEnterprise/(\\d+)(/?)$", "POST", i++),

		new Permission(Permission.Category.PRODUCT, "PERM_PRODUCT_ES",
					   "^/api/mgt/products/productEs(/?)$", "PUT", i++),

		new Permission(Permission.Category.PRODUCT, "PERM_PRODUCT_PRICE_ES",
					   "^/api/mgt/products/productPriceEs(/?)$", "PUT", i++),

		new Permission(Permission.Category.PRODUCT, "PERM_HOT_PRODUCT_TYPES",
					   "^/api/mgt/products/hotProductTypes(/?)$", "GET", i++),

		new Permission(Permission.Category.PRODUCT, "PERM_HOT_PRODUCT_TYPES_BY_ID",
					   "^/api/mgt/products/hotProductTypes/\\d+(/?)(\\?.*)?$", "GET", i++),

		new Permission(Permission.Category.PRODUCT, "PERM_RESTORE_HOT_PRODUCT_TYPES_BY_ID",
					   "^/api/mgt/products/hotProductTypes/\\d+(/?)(\\?.*)?$", "PUT", i++),

		new Permission(Permission.Category.PRODUCT, "PERM_ADD_HOT_PRODUCT",
					   "^/api/mgt/products/hotProduct(/?)$", "POST", i++),

		new Permission(Permission.Category.PRODUCT, "PERM_RESTORE_HOT_PRODUCT",
					   "^/api/mgt/products/hotProduct\\?action=RESTORE$", "PUT", i++),

		new Permission(Permission.Category.PRODUCT, "PERM_DELETE_HOT_PRODUCT",
					   "^/api/mgt/products/hotProduct\\?action=DEACTIVATED$", "DELETE", i++),

		new Permission(Permission.Category.PRODUCT, "PERM_VIEW_HOT_PRODUCT",
					   "^/api/mgt/products/hotProduct(/?)(\\?.*)?$", "GET", i++),

		new Permission(Permission.Category.PRODUCT, "PERM_SORT_HOT_PRODUCT",
					   "^/api/mgt/products/hotProductSort(/?)$", "PUT", i++)

	};

	if (log.isDebugEnabled()) {
	  log.debug("product permissions @" + permissions);
	}

	return permissions;
  }

  private Permission[] requirementPermissions() {
	int i = 0;
	Permission[] permissions
		= new Permission[]{
		//供应商
		//投标
		new Permission(Permission.Category.REQUIREMENT, "PERM_ENTERPRISE_PRICE_REQUIREMENT",
					   "^/api/mgt/requirement/enterprises/price/(\\w+)/(\\w+)(/?)(\\?.*)?$",
					   "PUT", i++),
		//接单-- 无此接口
		/*new Permission(Permission.Category.REQUIREMENT, "PERM_ENTERPRISE_RECEIVE_REQUIREMENT",
					   "^/api/mgt/requirement/enterprises/(\\w+)/(\\w+)(/?)$",
					   "PUT", i++),*/
		//查看列表
		new Permission(Permission.Category.REQUIREMENT, "PERM_ENTERPRISE_VIEW_REQUIREMENT",
					   "^/api/mgt/requirement/enterprises(/?)(\\?.*)?$",
					   "GET", i++),
		//查看配单信息
		new Permission(Permission.Category.REQUIREMENT, "PERM_ENTERPRISE_BID_INFO_REQUIREMENT",
					   "^/api/mgt/requirement/enterprises/(\\w+)(/?)$",
					   "GET", i++),
		//查看需求信息
		new Permission(Permission.Category.REQUIREMENT, "PERM_ENTERPRISE_VIEW_ONE_REQUIREMENT",
					   "^/api/mgt/requirement/enterprises/requirement/(\\w+)(/?)$",
					   "GET", i++),
		//订单
		new Permission(Permission.Category.REQUIREMENT, "PERM_ENTERPRISE_ORDER_REQUIREMENT",
					   "^/api/mgt/requirement/enterprises/order/(\\w+)(/?)$",
					   "POST", i++),

		//魔电后台
		//配单
		new Permission(Permission.Category.REQUIREMENT, "PERM_MODIANLI_MATCH_REQUIREMENT",
					   "^/api/mgt/requirement/modianli/match/(\\d+)(/?)(\\?.*)?$",
					   "GET", i++),
		//上(下)架
		new Permission(Permission.Category.REQUIREMENT, "PERM_MODIANLI_SHELF_REQUIREMENT",
					   "^/api/mgt/requirement/modianli/shelf/(\\d+)/(\\d+)(/?)$",
					   "PUT", i++),
		//审核
		new Permission(Permission.Category.REQUIREMENT, "PERM_MODIANLI_VALIDATE_REQUIREMENT",
					   "^/api/mgt/requirement/modianli/(\\w+)(/?)$",
					   "PUT", i++),
		//查看需求
		new Permission(Permission.Category.REQUIREMENT, "PERM_MODIANLI_VIEW_REQUIREMENT",
					   "^/api/mgt/requirement/modianli(/?)(\\?.*)?$",
					   "GET", i++),
		//查看某一个需求
		new Permission(Permission.Category.REQUIREMENT, "PERM_MODIANLI_VIEW_ONE_REQUIREMENT",
					   "^/api/mgt/requrement/modianli/(\\w+)(/?)$",
					   "GET", i++),
		//查看某一个需求的配单
		new Permission(Permission.Category.REQUIREMENT, "PERM_MODIANLI_VIEW_ONE_BIDDING_REQUIREMENT",
					   "^/api/mgt/requirement/modianli/bidding/(\\w+)(/?)$",
					   "GET", i++),

		//企业
		//新增需求
		new Permission(Permission.Category.REQUIREMENT, "PERM_PROFILE_ADD_REQUIREMENT", "^/api/mgt/requirement/profile(/?)$",
					   "POST", i++),
		//认证状态
		new Permission(Permission.Category.REQUIREMENT, "PERM_PROFILE_CERT_ADD_REQUIREMENT",
					   "^/api/mgt/requirement/certification(/?)$",
					   "GET", i++),
		//查看自己发布需求
		new Permission(Permission.Category.REQUIREMENT, "PERM_PROFILE_PAGE_VIEW_REQUIREMENT",
					   "^/api/mgt/requirement/page(/?)(\\?.*)?$",
					   "GET", i++),
		//选择供应商
		new Permission(Permission.Category.REQUIREMENT, "PERM_PROFILE_BID_REQUIREMENT",
					   "^/api/mgt/requirement/(\\d+)/(\\w+)(/?)$",
					   "PUT", i++),
		};

	log.debug("requirements permissions: {}", permissions);
	return permissions;
  }

  private Permission[] recruitPermission() {
	int i = 0;
	Permission[] permissions = new Permission[]{
		new Permission(Permission.Category.RECRUIT, "PERM_CREATE_RECRUIT",
					   "^/api/mgt/recruits(/?)", "POST", i++),

		new Permission(Permission.Category.RECRUIT, "PERM_VIEW_RECRUIT_COLLECTION",
					   "^/api/mgt/recruits/search(/?)(\\?.*)?$", "POST", i++),

		new Permission(Permission.Category.RECRUIT, "PERM_ACTIVATE_RECRUIT",
					   "^/api/mgt/recruits/[\\d](/?)\\?action=ACTIVATE$", "PUT", i++),

		new Permission(Permission.Category.RECRUIT, "PERM_DEACTIVATE_RECRUIT",
					   "^/api/mgt/recruits/[\\d](/?)\\?action=DEACTIVATE$", "PUT", i++),

		new Permission(Permission.Category.RECRUIT, "PERM_VIEW_RECRUIT",
					   "^/api/mgt/recruits/(/?)(\\?.*)?", "GET", i++),

		new Permission(Permission.Category.RECRUIT, "PERM_EDIT_RECRUIT", "^/api/mgt/recruits(/?)$", "PUT", i++),
		};
	return permissions;
  }

  private Permission[] dictionaryPermissions(){
	int i = 0;
	Permission[] permissions = new Permission[]{
		new Permission(Permission.Category.DICTIONARY, "PERM_VIEW_TYPES_IN_DICTIONARY",
					   "^/api/mgt/dictionaries/type(/?)", "GET", i++)
		};
	return permissions;
  }

  private void createPermissionsIfAbsent(Permission... permissions) {
	for (Permission perm : permissions) {
	  createPermissionIfAbsent(perm);
	}
  }

  private void createPermissionIfAbsent(Permission permission) {
	if (this.permissionRepository.findByName(permission.getName()) == null) {
	  this.permissionRepository.save(permission);
	}
  }

  private void loadReservedUserData() {
	if (log.isInfoEnabled()) {
	  log.info("loading reserved roles...");
	}

	Role[] roles = new Role[]{new Role(Role.ROLE_ADMIN), new Role(Role.ROLE_USER), new Role(Role.ROLE_ENTERPRISE)};

	for (Role r : roles) {
	  createRoleIfAbsent(r);
	}

	// role User Permission
	loadUserRoleData();

	//role ENTERPRISE Permission
	loadEnterpriseRoleData();

	UserAccount userAccount
		= new UserAccount("admin", passwordEncoder.encode("test123"), "Administrator", false,
						  UserAccount.Type.STAFF, "ADMIN");

	createUserAccountIfAbsent(userAccount);
  }

  private void loadUserRoleData() {
	GrantedPermission permViewUploadToken = new GrantedPermission("USER", "PERM_VIEW_UPLOAD_TOKEN");
	createGrantedPermissionIfAbsent(permViewUploadToken);

	GrantedPermission permViewUser = new GrantedPermission("USER", "PERM_VIEW_USER");
	createGrantedPermissionIfAbsent(permViewUser);

	GrantedPermission permCompleteCertificate = new GrantedPermission("USER", "PERM_COMPLETE_CERTIFICATE");
	createGrantedPermissionIfAbsent(permCompleteCertificate);

	GrantedPermission permProfileAddRequirement = new GrantedPermission("USER", "PERM_PROFILE_ADD_REQUIREMENT");
	createGrantedPermissionIfAbsent(permProfileAddRequirement);

	GrantedPermission permProfileCertAddRequirement = new GrantedPermission("USER", "PERM_PROFILE_CERT_ADD_REQUIREMENT");
	createGrantedPermissionIfAbsent(permProfileCertAddRequirement);

	GrantedPermission permProfilePageViewRequirement = new GrantedPermission("USER", "PERM_PROFILE_PAGE_VIEW_REQUIREMENT");
	createGrantedPermissionIfAbsent(permProfilePageViewRequirement);

	GrantedPermission permProfileBidRequirement = new GrantedPermission("USER", "PERM_PROFILE_BID_REQUIREMENT");
	createGrantedPermissionIfAbsent(permProfileBidRequirement);

	GrantedPermission permAddEnterpriseComment = new GrantedPermission("USER", "PERM_ADD_ENTERPRISE_COMMENT");
	createGrantedPermissionIfAbsent(permAddEnterpriseComment);

	GrantedPermission permDeletePrerenders = new GrantedPermission("USER", "PERM_DELETE_PRERENDERS");
	createGrantedPermissionIfAbsent(permDeletePrerenders);

	GrantedPermission permDeleteListPage = new GrantedPermission("USER", "PERM_DELETE_LIST_PAGE");
	createGrantedPermissionIfAbsent(permDeleteListPage);
  }

  private void loadEnterpriseRoleData() {
	GrantedPermission
		permEnterprisePriceRequirement =
		new GrantedPermission("ENTERPRISE", "PERM_ENTERPRISE_PRICE_REQUIREMENT");
	createGrantedPermissionIfAbsent(permEnterprisePriceRequirement);

	GrantedPermission
		permEnterpriseReceiveRequirement =
		new GrantedPermission("ENTERPRISE", "PERM_ENTERPRISE_RECEIVE_REQUIREMENT");
	createGrantedPermissionIfAbsent(permEnterpriseReceiveRequirement);

	GrantedPermission permEnterpriseViewRequirement = new GrantedPermission("ENTERPRISE", "PERM_ENTERPRISE_VIEW_REQUIREMENT");
	createGrantedPermissionIfAbsent(permEnterpriseViewRequirement);

	GrantedPermission permViewUploadToken = new GrantedPermission("ENTERPRISE", "PERM_VIEW_UPLOAD_TOKEN");
	createGrantedPermissionIfAbsent(permViewUploadToken);

	GrantedPermission
		permEnterpriseBidInfoRequirement =
		new GrantedPermission("ENTERPRISE", "PERM_ENTERPRISE_BID_INFO_REQUIREMENT");
	createGrantedPermissionIfAbsent(permEnterpriseBidInfoRequirement);

	List<GrantedPermission> recruitList = Lists.newArrayList(
		new GrantedPermission("ENTERPRISE", "PERM_CREATE_RECRUIT"),
		new GrantedPermission("ENTERPRISE", "PERM_VIEW_RECRUIT_COLLECTION"),
		new GrantedPermission("ENTERPRISE", "PERM_ACTIVATE_RECRUIT"),
		new GrantedPermission("ENTERPRISE", "PERM_DEACTIVATE_RECRUIT"),
		new GrantedPermission("ENTERPRISE", "PERM_VIEW_RECRUIT"),
		new GrantedPermission("ENTERPRISE", "PERM_EDIT_RECRUIT"));
	createGrantedPermissionIfAbsent(recruitList);


  }

  private void createRoleIfAbsent(Role role) {
	if (this.roleRepository.findByName(role.getName()) == null) {
	  this.roleRepository.save(role);
	}
  }

  private UserAccount createUserAccountIfAbsent(UserAccount user) {
	final UserAccount existedUserAccount = this.userRepository.findByUsername(user.getUsername());
	if (existedUserAccount == null) {
	  UserAccount userAccount = this.userRepository.save(user);
	  this.userRepository.flush();
	  return userAccount;
	}
	return existedUserAccount;
  }

  private IndustryCategory createIndustryCategory(IndustryCategory industryCategory) {
	final IndustryCategory industryCategory1 = this.industryCategoryRepository.findByName(industryCategory.getName());
	if (industryCategory1 == null) {
	  IndustryCategory industryCategory2 = this.industryCategoryRepository.save(industryCategory);
	  return industryCategory2;
	}
	return industryCategory1;
  }

  private void loadTestData() {
	if (log.isInfoEnabled()) {
	  log.info("load test user data...");
	}

	createRoleIfAbsent(new Role("TEST"));

	GrantedPermission grantedPermission = new GrantedPermission("TEST", "PERM_VIEW_BANK");
	GrantedPermission grantedPermission2 = new GrantedPermission("TEST", "PERM_ADD_BANK");

	createGrantedPermissionIfAbsent(grantedPermission);
	createGrantedPermissionIfAbsent(grantedPermission2);

	UserAccount userAccount1
		= new UserAccount("test1", passwordEncoder.encode("test123"), "Test User 1", false,
						  UserAccount.Type.STAFF, "USER");
	UserAccount userAccount2
		= new UserAccount("test2", passwordEncoder.encode("test123"), "Test User 2", false,
						  UserAccount.Type.STAFF, "TEST");

	UserAccount grandz
		= new UserAccount("grandzh", passwordEncoder.encode("grandzh@123"), "Grand Zhang", false,
						  UserAccount.Type.USER, "USER");

	UserAccount roy
		= new UserAccount("roy", passwordEncoder.encode("test123"), "Roy Yang", false,
						  UserAccount.Type.ENTERPRISE, "USER");

	UserAccount savedroy = createUserAccountIfAbsent(roy);

	UserAccount yang
		= new UserAccount("yang", passwordEncoder.encode("test123"), "Yang Kui", false,
						  UserAccount.Type.ENTERPRISE, "USER");

	UserAccount saveyang = createUserAccountIfAbsent(yang);

	UserAccount savedTestUser1 = createUserAccountIfAbsent(userAccount1);
	UserAccount saved2 = createUserAccountIfAbsent(userAccount2);

	UserAccount savedZhang = createUserAccountIfAbsent(grandz);

	UserAccount appTestAccount
		= new UserAccount("appTest", passwordEncoder.encode("123456"), "appTest", false,
						  UserAccount.Type.USER, "USER");
	appTestAccount.setEmail("appTestAccount");

	UserAccount appTestAccountSaved = createUserAccountIfAbsent(appTestAccount);

	UserProfile appTestProfile = new UserProfile();
	appTestProfile.setAccount(appTestAccountSaved);
//	appTestProfile.setSecondaryEmail("hantsybai@tom.com");
//	appTestProfile.setSecondaryMobileNumber("138001380000");
//	appTestProfile.setInstantMessager("QQ:332343423");
	createUserProfileIfAbsent(appTestProfile);

	UserProfile userProfile = new UserProfile();
	userProfile.setAccount(savedTestUser1);
//	userProfile.setSecondaryEmail("hantsybai@tom.com");
//	userProfile.setSecondaryMobileNumber("138001380000");
//	userProfile.setInstantMessager("QQ:332343423");
	createUserProfileIfAbsent(userProfile);

	UserAccount admin = userRepository.findByUsername("admin");

	Address addr = new Address();
	addr.setCity("上海");
	addr.setStreet("LUJIAZUI");
	addr.setZipcode("200050");

	StaffProfile staffProfile = new StaffProfile("IT", "", "", "", savedTestUser1);
	StaffProfile staffProfile1 = new StaffProfile("IT1", "", "", "", saved2);

	createStaffProfileIfAbsent(staffProfile);
	createStaffProfileIfAbsent(staffProfile1);

//	Enterprise enterprise
//		= new Enterprise("alibaba", "001", "13472858302", "02188889999", "00100", "", "上海银行", "", "", "", "", "",
//						 Enterprise.Type.PERNAS, "", "430621199908147715", "02111112222", "yangkuimsg@hotmail.com",
//						 Enterprise.AccountTypes.BANK_BOOK, "", addr, addr, addr, true, "SHBANK",
//						 Enterprise.VerifyStatus.APPROVED, "", "88888", null, null, "123456");
//	enterprise.setUserAccount(savedroy);
//	enterprise = createEnterpriseIfAbsent(enterprise);
//
//	Enterprise enterprise1
//		= new Enterprise("microsoft", "002", "18572858302", "02188889999", "00200", "", "上海建设银行", "", "", "", "",
//						 "", Enterprise.Type.PERNAS, "", "430621199908147715", "02111112222", "yangkuimsg@hotmail.com",
//						 Enterprise.AccountTypes.BANK_BOOK, "", addr, addr, addr, true, "ICBC",
//						 Enterprise.VerifyStatus.PENDING, "", "88888", saved2, null, "654321");
//	enterprise1.setUserAccount(saveyang);
//	enterprise1 = createEnterpriseIfAbsent(enterprise1);

//	  QualificationTopCategory category = new QualificationTopCategory("工程");
//
//	  QualificationMiddleCategory middleCategory = new QualificationMiddleCategory("工程", category);
//	  QualificationLastCategory lastCategory = new QualificationLastCategory("甲级", middleCategory);

	IndustryCategory industryCategory0 = new IndustryCategory("全部");
	industryCategory0 = createIndustryCategory(industryCategory0);
	IndustryCategory industryCategory1 = new IndustryCategory("设计");
	industryCategory1 = createIndustryCategory(industryCategory1);
	IndustryCategory industryCategory2 = new IndustryCategory("施工");
	industryCategory2 = createIndustryCategory(industryCategory2);
	IndustryCategory industryCategory3 = new IndustryCategory("设备");
	industryCategory3 = createIndustryCategory(industryCategory3);
	IndustryCategory industryCategory4 = new IndustryCategory("物料");
	industryCategory4 = createIndustryCategory(industryCategory4);
	IndustryCategory industryCategory5 = new IndustryCategory("监理");
	industryCategory5 = createIndustryCategory(industryCategory5);
	IndustryCategory industryCategory6 = new IndustryCategory("人力资源");
	industryCategory6 = createIndustryCategory(industryCategory6);
	IndustryCategory industryCategory7 = new IndustryCategory("软件服务");
	industryCategory7 = createIndustryCategory(industryCategory7);


  }

  private UserProfile createUserProfileIfAbsent(UserProfile profile) {
	if (log.isDebugEnabled()) {
	  log.debug("findByAccountId @" + profile.getAccount().getId());
	}
	final UserProfile existedProfile = userProfileRepository.findByAccountId(profile.getAccount().getId());
	if (existedProfile == null) {
	  return userProfileRepository.save(profile);
	}
	return existedProfile;
  }

  private StaffProfile createStaffProfileIfAbsent(StaffProfile profile) {
	final StaffProfile existedProfile
		= staffProfileRepository.findByUserAccountId(profile.getUserAccount().getId());
	if (existedProfile == null) {
	  return staffProfileRepository.save(profile);
	}
	return existedProfile;
  }

  private void createGrantedPermissionIfAbsent(GrantedPermission grantedPermission) {
	if (this.grantedPermissionRepository.findByRoleAndPermission(grantedPermission.getRole(),
																 grantedPermission.getPermission()) == null) {
	  grantedPermissionRepository.save(grantedPermission);
	}
  }

  private void createGrantedPermissionIfAbsent(List<GrantedPermission> grantedPermissionList) {
	grantedPermissionList.forEach(g -> this.createGrantedPermissionIfAbsent(g));
  }

  private Enterprise createEnterpriseIfAbsent(Enterprise enterprise) {
	final Enterprise entFindByName = this.enterpriseRepository.findByName(enterprise.getName());
	if (entFindByName == null) {
	  return this.enterpriseRepository.save(enterprise);
	}
	return entFindByName;
  }

  // private void loadReservedProductData() {
  //
  // if (log.isInfoEnabled()) {
  // log.info("loading reserved enterprise...");
  // }
  //
  // Address address = new Address();
  // address.setCity("上海");
  // address.setStreet("浦东");
  // address.setZipcode("200023");
  // UserAccount userAccount2 = new UserAccount("test3", passwordEncoder.encode("test123"),
  // "Test User 3", true,
  // "TEST");
  //
  // userAccount2.setRoles(Arrays.asList("TEST"));
  //
  // UserAccount saved2 = createUserAccountIfAbsent(userAccount2);
  //
  // Product product = new Product();
  // product.setName("");
  // product.setActive(true);
  //
  // // this.createProductIfAbsent(product);
  // }
  //

  private void initialzePostDatas() {
	ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
	populator.addScript(resourceLoader.getResource("classpath:/post_data.sql"));
	populator.setContinueOnError(true);
	DatabasePopulatorUtils.execute(populator, dataSource);
  }
}
