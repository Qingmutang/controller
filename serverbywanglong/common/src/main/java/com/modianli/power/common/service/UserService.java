/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.modianli.power.common.service;

import com.google.common.collect.Lists;

import com.modianli.power.DTOUtils;
import com.modianli.power.common.exception.CaptchaMismatchedException;
import com.modianli.power.common.exception.EnterpriseUserExistedException;
import com.modianli.power.common.exception.IncorrectMailboxFormatException;
import com.modianli.power.common.exception.PasswordMismatchedException;
import com.modianli.power.common.exception.ResourceNotFoundException;
import com.modianli.power.common.exception.RoleNameExistedException;
import com.modianli.power.common.exception.UsernameExistedException;
import com.modianli.power.domain.jpa.Area;
import com.modianli.power.domain.jpa.City;
import com.modianli.power.domain.jpa.Enterprise;
import com.modianli.power.domain.jpa.GrantedPermission;
import com.modianli.power.domain.jpa.Permission;
import com.modianli.power.domain.jpa.PrivateMessage;
import com.modianli.power.domain.jpa.Province;
import com.modianli.power.domain.jpa.Role;
import com.modianli.power.domain.jpa.StaffProfile;
import com.modianli.power.domain.jpa.UserAccount;
import com.modianli.power.domain.jpa.UserCertification;
import com.modianli.power.domain.jpa.UserProfile;
import com.modianli.power.model.CategorizedPermission;
import com.modianli.power.model.EmailModel;
import com.modianli.power.model.NewPasswordForm;
import com.modianli.power.model.PasswordForm;
import com.modianli.power.model.PermissionDetails;
import com.modianli.power.model.PermissionForm;
import com.modianli.power.model.PrivateMessageDetails;
import com.modianli.power.model.ProfileForm;
import com.modianli.power.model.RoleDetails;
import com.modianli.power.model.RoleForm;
import com.modianli.power.model.SignupForm;
import com.modianli.power.model.SupplierForm;
import com.modianli.power.model.SystemUserDetails;
import com.modianli.power.model.SystemUserForm;
import com.modianli.power.model.UpdateMobileNumberForm;
import com.modianli.power.model.UserAccountDetails;
import com.modianli.power.model.UserCertificationForm;
import com.modianli.power.model.UserEnterpriseCertificateForm;
import com.modianli.power.model.UserPasswordForm;
import com.modianli.power.model.UserProfileDetail;
import com.modianli.power.model.UserProfileDetails;
import com.modianli.power.model.UserSearchCriteria;
import com.modianli.power.persistence.repository.jpa.AreaRepository;
import com.modianli.power.persistence.repository.jpa.EnterpriseRepository;
import com.modianli.power.persistence.repository.jpa.GrantedPermissionRepository;
import com.modianli.power.persistence.repository.jpa.PermissionRepository;
import com.modianli.power.persistence.repository.jpa.PrivateMessageRepository;
import com.modianli.power.persistence.repository.jpa.PrivateMessageSpecifications;
import com.modianli.power.persistence.repository.jpa.RoleRepository;
import com.modianli.power.persistence.repository.jpa.StaffProfileRepository;
import com.modianli.power.persistence.repository.jpa.UserCertificationRepository;
import com.modianli.power.persistence.repository.jpa.UserProfileRepository;
import com.modianli.power.persistence.repository.jpa.UserProfileSpecifications;
import com.modianli.power.persistence.repository.jpa.UserRepository;
import com.modianli.power.persistence.repository.jpa.UserSpecifications;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

/**
 * @author
 */
@Service
@Transactional
public class UserService {

  public final static Logger log = LoggerFactory.getLogger(UserService.class);

  @Inject
  private UserRepository userRepository;

  @Inject
  private RoleRepository roleRepository;

  @Inject
  private PermissionRepository permissionRepository;

  @Inject
  private GrantedPermissionRepository grantedPermissionRepository;

  @Inject
  private StaffProfileRepository staffProfileRepository;

  @Inject
  private PasswordEncoder passwordEncoder;

  @Inject
  private UserProfileRepository userProfileRepository;

  @Inject
  private UserCertificationRepository userCertificationRepository;

  @Inject
  private SmsService smsService;

  @Inject
  private AreaRepository areaRepository;

  @Inject
  private PrivateMessageRepository privateMessageRepository;

  @Inject
  private EnterpriseRepository enterpriseRepository;

  @Inject
  private EmailService emailService;

  public Page<SystemUserDetails> findUsers(String keyword, String role, String activeStatus, String locked, Pageable page) {

	if (log.isDebugEnabled()) {
	  log.debug("findUsers by keyword@" + keyword + ", role:" + role + ", locked@" + locked + ", page@" + page);
	}

	Page<StaffProfile> users
		= staffProfileRepository.findAll(
		UserSpecifications.filterUserAccountsByKeywordAndRole(keyword, role, activeStatus, locked), page);

	if (log.isDebugEnabled()) {
	  log.debug("total elements@" + users.getTotalElements());
	}

	return DTOUtils.mapPage(users, SystemUserDetails.class);
  }

  public Page<UserAccountDetails> findUserAccounts(UserSearchCriteria criteria, Pageable page) {

	if (log.isDebugEnabled()) {
	  log.debug("findUsers by criteria@" + criteria + ", page@" + page);
	}

	Page<UserAccount> users
		= userRepository.findAll(
		UserSpecifications.filterUserAccountsByKeyword(
			criteria.getQ(),
			UserAccount.Type.USER,
			Role.ROLE_USER,
			criteria.getActive(),
			criteria.getLocked()

		), page);

	if (log.isDebugEnabled()) {
	  log.debug("total elements@" + users.getTotalElements());
	}

	return DTOUtils.mapPage(users, UserAccountDetails.class);
  }

  public UserAccountDetails saveUser(SystemUserForm form) {
	Assert.notNull(form, " @@ SystemUserForm is null");
	Assert.notNull(form.getUserAccount(), " @@ SystemUserForm.getUserAccount is null");

	if (log.isDebugEnabled()) {
	  log.debug("saving user@" + form);
	}

	if (userRepository.findByUsername(form.getUserAccount().getUsername()) != null) {
	  throw new UsernameExistedException(form.getUserAccount().getUsername());
	}

	UserAccount user = DTOUtils.map(form.getUserAccount(), UserAccount.class);

	if (StringUtils.hasText(form.getUserAccount().getPassword())) {
	  user.setPassword(passwordEncoder.encode(user.getPassword()));
	} else {
	  user.setPassword(passwordEncoder.encode("test@123456"));
	}

	user.setType(UserAccount.Type.STAFF);
	user.setActive(true);
	user.setLocked(false);

	UserAccount saved = userRepository.save(user);

	StaffProfile sysUser = DTOUtils.map(form, StaffProfile.class);

	sysUser.setUserAccount(saved);

	staffProfileRepository.save(sysUser);

	if (log.isDebugEnabled()) {
	  log.debug("saved user @" + saved);
	}

	return DTOUtils.map(saved, UserAccountDetails.class);
  }

  public void updateUser(Long id, SystemUserForm form) {
	Assert.notNull(id, "user id can not be null");
	Assert.notNull(form.getUserAccount(), " @@ SystemUserForm.getUserAccount is null");

	if (log.isDebugEnabled()) {
	  log.debug("find user by id @" + id);
	}

	StaffProfile systemUser = staffProfileRepository.findOne(id);

	if (systemUser == null) {
	  throw new ResourceNotFoundException(id);
	}

	DTOUtils.mapTo(form, systemUser);

	StaffProfile saved = staffProfileRepository.save(systemUser);

	UserAccount user = userRepository.findOne(saved.getUserAccount().getId());

	if (user == null) {
	  throw new ResourceNotFoundException(id);
	}

	user.setPassword(user.getPassword());
	user.setName(form.getUserAccount().getName());
	user.setRoles(form.getUserAccount().getRoles());
	user.setMobileNumber(form.getUserAccount().getMobileNumber());
	user.setEmail(form.getUserAccount().getEmail());
	user.setGender(UserAccount.Gender.valueOf(form.getUserAccount().getGender()));

	UserAccount userSaved = userRepository.save(user);

	if (log.isDebugEnabled()) {
	  log.debug("updated user @" + saved);
	}
  }

  public void updatePassword(Long id, PasswordForm form) {
	Assert.notNull(id, "user id can not be null");
	if (log.isDebugEnabled()) {
	  log.debug("find user by id @" + id);
	}

	UserAccount user = userRepository.findOne(id);

	if (!passwordEncoder.matches(form.getOldPassword(), user.getPassword())) {
	  throw new PasswordMismatchedException("msg is not match");
	}

	user.setPassword(passwordEncoder.encode(form.getNewPassword()));

	UserAccount saved = userRepository.save(user);

	if (log.isDebugEnabled()) {
	  log.debug("updated user @" + saved);
	}
  }

  public void findPassword(UserPasswordForm form) {
	Assert.notNull(form, "form can not be null");
	Assert.notNull(form.getCaptchaResponse(), "form.getCaptchaResponse() can not be null");
	if (log.isDebugEnabled()) {
	  log.debug("find user by id @" + form.getCaptchaResponse().getMobileNumber());
	}

	String username = form.getCaptchaResponse().getMobileNumber();
	UserAccount user = userRepository.findByUsername(username);
	if (null == user) {
	  throw new ResourceNotFoundException("can not find user by username" + username);
	}

	if (form.getCaptchaResponse() == null || !smsService.validate(form.getCaptchaResponse().getMobileNumber(),
																  form.getCaptchaResponse().getCaptchaValue())) {
	  throw new CaptchaMismatchedException();
	}

	user.setPassword(passwordEncoder.encode(form.getNewPassword()));

	UserAccount saved = userRepository.save(user);

	if (log.isDebugEnabled()) {
	  log.debug("updated user @" + saved);
	}
  }

  public void deactivateUser(Long id) {
	Assert.notNull(id, "user id can not be null");
	userRepository.updateActiveStatus(id, false);
  }

  public void activateUser(Long id) {
	Assert.notNull(id, "user id can not be null");
	userRepository.updateActiveStatus(id, true);
  }

  public SystemUserDetails findUserById(Long id) {
	Assert.notNull(id, "user id can not be null");
	if (log.isDebugEnabled()) {
	  log.debug("find user by id @" + id);
	}

	StaffProfile user = staffProfileRepository.findOne(id);

	if (user == null) {
	  throw new ResourceNotFoundException(id);
	}

	return DTOUtils.map(user, SystemUserDetails.class);
  }

  public UserAccountDetails findUserAccountById(Long id) {
	Assert.notNull(id, "user id can not be null");
	if (log.isDebugEnabled()) {
	  log.debug("find user by id @" + id);
	}

	UserAccount user = userRepository.findOne(id);

	if (user == null) {
	  throw new ResourceNotFoundException(id);
	}

	return DTOUtils.map(user, UserAccountDetails.class);
  }

  public UserAccountDetails findUserByUsername(String username) {
	Assert.notNull(username, "username can not be null");
	if (log.isDebugEnabled()) {
	  log.debug("find user by id @" + username);
	}

	UserAccount user = userRepository.findByUsername(username);

	if (user == null) {
	  throw new ResourceNotFoundException(username + " not found");
	}

	UserAccountDetails userAccountDetails = DTOUtils.map(user, UserAccountDetails.class);

	if (user.getType().equals(UserAccount.Type.USER)) {
	  UserProfile userProfile = userProfileRepository.findByAccount(user);
	  UserProfileDetail userProfileDetail = new UserProfileDetail();
	  if (null != userProfile.getUserCertification()) {
		userProfileDetail = DTOUtils.strictMap(userProfile.getUserCertification(), UserProfileDetail.class);

		if (UserProfile.CertificateType.REJECTED.equals(userProfile.getCertificateStatus())) {
		  userProfileDetail.setReason(userProfile.getRejectedReason());
		}
	  }
	  userProfileDetail.setHeadImage(userProfile.getHeadImage());
	  userProfileDetail.setStatus(userProfile.getCertificateStatus().name());
	  userAccountDetails.setUserProfileDetail(userProfileDetail);
	}

	return userAccountDetails;
  }

  public void assignRoles(Long userId, String... roles) {
	Assert.notNull(userId, "user id can not be null");
	Assert.notEmpty(roles, "roles be can empty");

	if (log.isDebugEnabled()) {
	  log.debug(">>>assign roles @" + roles + " to user@" + userId);
	}

	UserAccount account = userRepository.findOne(userId);

	account.getRoles().clear();
	account.setRoles(Arrays.asList(roles));

	userRepository.saveAndFlush(account);
  }

  public void grantPermissions(String roleName, String... perms) {
	Assert.notNull(roleName, "roleName can not be null");
	Assert.notEmpty(perms, "perms be can empty");

	if (log.isDebugEnabled()) {
	  log.debug(">>>grant permissions @" + perms + "  to role @" + roleName);
	}

	for (String p : perms) {
	  createGrantedPermissionIfAbsent(roleName, p);
	}
  }

  public void revokePermissions(String roleName, String[] perms) {
	Assert.notNull(roleName, "roleName can not be null");
	Assert.notEmpty(perms, "perms be can empty");

	if (log.isDebugEnabled()) {
	  log.debug(">>>revoke permissions @" + perms + "  of role @" + roleName);
	}

	for (String p : perms) {
	  grantedPermissionRepository.removeByRoleNameAndPermission(roleName, p);
	}
  }

  public List<String> findGrantedPermissionNamesByRoleName(String role) {
	if (log.isDebugEnabled()) {
	  log.debug("find granted permissions by role @" + role);
	}

	List<GrantedPermission> perms = grantedPermissionRepository.findByRole(role);

	List<String> results = new ArrayList<>();

	for (GrantedPermission perm : perms) {

	  results.add(perm.getPermission());
	}

	return results;
  }

  public List<CategorizedPermission> findAllCategorizedPermissions() {
	if (log.isDebugEnabled()) {
	  log.debug("find all active permission names categorized by Category @");
	}

	List<Permission> permissions = permissionRepository.findByActiveIsTrue();

	Map<String, List<String>> categorizedPermissions = new HashMap<>();

	for (Permission p : permissions) {
	  List<String> permNames = categorizedPermissions.get(p.getCategory().name());
	  if (permNames == null) {
		permNames = new ArrayList<>();
		categorizedPermissions.put(p.getCategory().name(), permNames);
	  }
	  if (!permNames.contains(p.getName())) {
		permNames.add(p.getName());
	  }
	}

	List<CategorizedPermission> catePerms = new ArrayList<>();

	for (Map.Entry<String, List<String>> entry : categorizedPermissions.entrySet()) {
	  catePerms.add(new CategorizedPermission(entry.getKey(), entry.getValue()));
	}

	if (log.isDebugEnabled()) {
	  log.debug("return categorized permissions@" + categorizedPermissions);
	}

	return catePerms;
  }

  // role
  public RoleDetails saveRole(RoleForm form) {
	if (log.isDebugEnabled()) {
	  log.debug("saving role@" + form);
	}

	if (roleRepository.findByName(form.getName()) != null) {
	  throw new RoleNameExistedException(form.getName());
	}

	Role role = DTOUtils.map(form, Role.class);

	Role saved = roleRepository.save(role);

	if (log.isDebugEnabled()) {
	  log.debug("saved role @" + saved);
	}

	return DTOUtils.map(saved, RoleDetails.class);
  }

  public Page<RoleDetails> findRoles(String name, boolean active, Pageable page) {
	if (log.isDebugEnabled()) {
	  log.debug("findUsers by name@" + name + ", page@" + page);
	}
	Page<Role> roles = roleRepository.findAll(UserSpecifications.filterRoleByRoleName(name, active), page);

	if (log.isDebugEnabled()) {
	  log.debug("total elements@" + roles.getTotalElements());
	}
	return DTOUtils.mapPage(roles, RoleDetails.class);
  }

  public List<Role> findActiveRoles() {
	if (log.isDebugEnabled()) {
	  log.debug("call findActiveRoles ");
	}
	List<Role> roles = roleRepository.findByActiveIsTrue();

	if (log.isDebugEnabled()) {
	  log.debug("total elements@" + roles);
	}
	return roles;
  }

  public RoleDetails findRoleById(Long id) {
	Assert.notNull(id, "role id can not be null");
	if (log.isDebugEnabled()) {
	  log.debug("find role by id @" + id);
	}

	Role role = roleRepository.findOne(id);

	if (role == null) {
	  throw new ResourceNotFoundException(id);
	}

	return DTOUtils.map(role, RoleDetails.class);
  }

  public void updateRole(Long id, RoleForm form) {
	Assert.notNull(id, "role id can not be null");
	if (log.isDebugEnabled()) {
	  log.debug("find role by id @" + id);
	}
	Role role = roleRepository.findOne(id);

	if (role == null) {
	  throw new ResourceNotFoundException(id);
	}
	DTOUtils.strictMapTo(form, role);

	Role saved = roleRepository.save(role);

	if (log.isDebugEnabled()) {
	  log.debug("updated Role @" + saved);
	}
  }

  public Permission findResourceById(Long id) {
	Assert.notNull(id, "resource id can not be null");
	if (log.isDebugEnabled()) {
	  log.debug("find resource by id @" + id);
	}

	Permission resource = permissionRepository.findOne(id);

	if (resource == null) {
	  throw new ResourceNotFoundException(id);
	}

	return DTOUtils.map(resource, Permission.class);
  }

  public void createGrantedPermissionIfAbsent(String roleName, String p) {
	if (log.isDebugEnabled()) {
	  log.debug("create or update GrantedPermission@ roleName @" + roleName + ", permision @" + p);
	}

	//  GrantedPermissionRepository
	GrantedPermission grantedPermission = grantedPermissionRepository.findByRoleAndPermission(roleName, p);

	if (grantedPermission == null) {
	  log.debug("can not find granted permision@" + p + "to roleName @" + roleName + ", create one now.");

	  grantedPermission = new GrantedPermission(roleName, p);

	  grantedPermissionRepository.save(grantedPermission);
	}

  }

  public void deactivateRole(Long id) {
	roleRepository.updateActiveStatus(id, false);
  }

  public void activateRole(Long id) {
	roleRepository.updateActiveStatus(id, true);
  }

  public Page<PermissionDetails> findAllPermissions(String q, Pageable page) {
	Page<Permission> perms = permissionRepository.findByNameLike("%" + q + "%", page);

	return DTOUtils.mapPage(perms, PermissionDetails.class);
  }

  public void updatePermission(Long id, PermissionForm form) {
	Assert.notNull(id, "id can not be null");
	Assert.notNull(form, "form can not be null");

	Permission permission = permissionRepository.findOne(id);

	DTOUtils.mapTo(form, permission);

	permissionRepository.save(permission);
  }

  public UserAccountDetails registerUser(SignupForm form) {
	Assert.notNull(form, "signup form can not be null");
	Assert.notNull(form.getUsername(), "username can not be null");
	Assert.notNull(form.getCaptchaResponse(), "captcha can not be null");

	if (log.isDebugEnabled()) {
	  log.debug("saving user@" + form);
	}

	if (userRepository.findByUsername(form.getUsername()) != null) {
	  throw new UsernameExistedException(form.getUsername());
	}

	if (form.getCaptchaResponse() == null || !smsService.validate(form.getCaptchaResponse().getMobileNumber(),
																  form.getCaptchaResponse().getCaptchaValue())) {
	  throw new CaptchaMismatchedException();
	}

	UserAccount user = DTOUtils.map(form, UserAccount.class);
	user.setRoles(Arrays.asList("USER"));
	user.setPassword(passwordEncoder.encode(user.getPassword()));
	user.setMobileNumber(form.getUsername());
	UserAccount saved = userRepository.save(user);

	UserProfile userProfile = new UserProfile();
	userProfile.setAccount(saved);
	userProfile.setCertificateStatus(UserProfile.CertificateType.UNAUTHORIZE);
	userProfileRepository.save(userProfile);

	if (log.isDebugEnabled()) {
	  log.debug("saved user @" + saved);
	}

	return DTOUtils.map(saved, UserAccountDetails.class);
  }

  public UserAccountDetails registerSupplier(SupplierForm form) {
	Assert.notNull(form, "signup form can not be null");
	Assert.notNull(form.getId(), "id can not be null");

	log.debug("saving user@{}", form);

	Enterprise enterprise = enterpriseRepository.findOne(form.getId());
	if (null == enterprise) {
	  throw new ResourceNotFoundException("enterprise can not find");
	}

	if (null != enterprise.getUserAccount()) {
	  throw new EnterpriseUserExistedException("user has existed");
	}

	String email = enterprise.getEmail();

	if (!StringUtils.hasText(email)) {
	  throw new IncorrectMailboxFormatException("email format is incorrect");
	}

	Pattern pattern = Pattern.compile("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
	Matcher matcher = pattern.matcher(email);
	if (!matcher.matches()) {
	  throw new IncorrectMailboxFormatException("email format is incorrect");
	}

	String username = "md_" + RandomStringUtils.random(8, true, true);
	while (userRepository.findByUsername(username) != null) {
	  username = "md_" + RandomStringUtils.random(8, true, true);
	}

	String password = RandomStringUtils.randomNumeric(8);

	UserAccount user = new UserAccount();
	user.setUsername(username);
	user.setRoles(Arrays.asList("ENTERPRISE"));
	user.setPassword(passwordEncoder.encode(password));
	user.setType(UserAccount.Type.ENTERPRISE);
	UserAccount saved = userRepository.save(user);

	enterprise.setUserAccount(saved);
	enterpriseRepository.save(enterprise);

	log.debug("saved user @ {}", saved);

	EmailModel model = new EmailModel(username, enterprise.getName(), password);
	log.debug("username @{} enterpriseName @{}  password @{}", username, enterprise.getName(), password);
	emailService.sendResetPasswordEmail(email, "用户名密码", model);

	return DTOUtils.map(saved, UserAccountDetails.class);
  }

  public void updatePassword(Long id) {
	Assert.notNull(id, "id can not be null");

	log.debug("update password @{}", id);

	Enterprise enterprise = enterpriseRepository.findOne(id);
	if (null == enterprise) {
	  throw new ResourceNotFoundException("enterprise can not find");
	}

	if (null == enterprise.getUserAccount()) {
	  throw new ResourceNotFoundException("user can not find");
	}

	UserAccount user = enterprise.getUserAccount();
	String email = enterprise.getEmail();
	Pattern pattern = Pattern.compile("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
	Matcher matcher = pattern.matcher(email);
	if (!StringUtils.hasText(email) || !matcher.matches()) {
	  throw new IncorrectMailboxFormatException("email format is incorrect");
	}

	String username = user.getUsername();
	String password = RandomStringUtils.randomNumeric(8);
	user.setPassword(passwordEncoder.encode(password));
	UserAccount saved = userRepository.save(user);

	log.debug("saved user @ {}", saved);

	EmailModel model = new EmailModel(username, enterprise.getName(), password);
	log.debug("username @{} enterpriseName @{}  password @{}", username, enterprise.getName(), password);
	emailService.sendResetPasswordEmail(email, "用户名密码", model);
  }

  public void retrievePasswordByMobileNumber(NewPasswordForm form) {

	Assert.notNull(form, " form can't  be null ");
	Assert.notNull(form.getMobileNumber(), " MobileNumber can't  be null ");

//	validateCaptcha(form.getCaptchaValue(), form.getMobileNumber());

	UserAccount user = userRepository.findByMobileNumber(form.getMobileNumber());

	if (null == user) {
	  throw new ResourceNotFoundException(" User not found");
	}
	user.setPassword(passwordEncoder.encode(form.getPassword()));

	UserAccount saved = userRepository.save(user);

	if (log.isDebugEnabled()) {
	  log.debug("updated user @" + saved);
	}
  }

  public UserProfileDetails findUserProfileByUserId(Long id) {
	Assert.notNull(id, " account id can't  be null ");

	if (log.isDebugEnabled()) {
	  log.debug("fetch user profile by account id@" + id);
	}

	UserProfile profile = userProfileRepository.findByAccountId(id);

	if (profile != null) {
	  return DTOUtils.map(profile, UserProfileDetails.class);
	}

	return null;
  }

  public void unlockUser(Long id) {
	Assert.notNull(id, "user id can not be null");
	userRepository.updateLockedStatus(id, false);
  }

  public void lockUser(Long id) {
	Assert.notNull(id, "user id can not be null");
	userRepository.updateLockedStatus(id, true);
  }

  public RoleDetails findRoleByName(String name) {
	Assert.notNull(name, "role name can not be null");
	if (log.isDebugEnabled()) {
	  log.debug("find role by name @" + name);
	}

	Role role = roleRepository.findByName(name);

	if (role == null) {
	  throw new ResourceNotFoundException(name);
	}

	return DTOUtils.map(role, RoleDetails.class);
  }

  public void saveUserCertification(UserAccount userAccount, UserCertificationForm form) {
	Assert.notNull(form, "form can not be null");
	Assert.notNull(form.getAddress(), "form.getAddress can not be null");
	Assert.notNull(form.getPhone(), "form.getPhone can not be null");
	Assert.notNull(form.getContacts(), "form.getContacts can not be null");
	Assert.notNull(form.getQualificationCertificate(), "form.getQualificationCertificate can not be null");
	Assert.notNull(form.getProvinceId(), "form.getProvinceId can not be null");
	Assert.notNull(form.getCityId(), "form.getCityId can not be null");
	Assert.notNull(form.getAreaId(), "form.getAreaId can not be null");

	Area area = areaRepository.findOne(form.getAreaId());
	if (null == area) {
	  throw new ResourceNotFoundException("area can not found by id" + form.getAreaId());
	}

	City city = area.getCity();
	if (!city.getId().equals(form.getCityId())) {
	  throw new ResourceNotFoundException("area is not belong this city by cityId" + form.getCityId());
	}

	Province province = city.getProvince();
	if (!province.getId().equals(form.getProvinceId())) {
	  throw new ResourceNotFoundException("city is not belong this province by provinceId" + form.getProvinceId());
	}

	UserProfile userProfile = userProfileRepository.findByAccount(userAccount);
	if (null == userProfile) {
	  throw new ResourceNotFoundException("can not find userProfile");
	}

	UserCertification userCertification = DTOUtils.strictMap(form, UserCertification.class);

	if (null != userProfile.getUserCertification()) {
	  userCertification.setId(userProfile.getUserCertification().getId());
	}

	UserCertification saved = userCertificationRepository.save(userCertification);

	userProfile.setUserCertification(saved);
	userProfile.setRejectedReason(null);
	userProfile.setCertificateStatus(UserProfile.CertificateType.AUTHORIZING);
	userProfileRepository.save(userProfile);

	Boolean flag = emailService.sendMail("1983245871@qq.com", "用户认证", "有新的用户提交了认证申请！", "2530654946@qq.com", "402244278@qq.com");
	if (!flag) {
	  log.warn("邮件发送失败！");
	}
  }

  public void saveUserCertification(Long id, UserCertificationForm form) {
	Assert.notNull(form, "form can not be null");
	Assert.notNull(form.getAddress(), "form.getAddress can not be null");
	Assert.notNull(form.getPhone(), "form.getPhone can not be null");
	Assert.notNull(form.getContacts(), "form.getContacts can not be null");
	Assert.notNull(form.getQualificationCertificate(), "form.getQualificationCertificate can not be null");
	Assert.notNull(form.getProvinceId(), "form.getProvinceId can not be null");
	Assert.notNull(form.getCityId(), "form.getCityId can not be null");
	Assert.notNull(form.getAreaId(), "form.getAreaId can not be null");

	UserProfile userProfile = userProfileRepository.findOne(id);
	if (null == userProfile) {
	  throw new ResourceNotFoundException("can not find userProfile");
	}

	UserCertification userCertification = DTOUtils.strictMap(form, UserCertification.class);

	if (null != userProfile.getUserCertification()) {
	  userCertification.setId(userProfile.getUserCertification().getId());
	}

	UserCertification saved = userCertificationRepository.save(userCertification);

	userProfile.setUserCertification(saved);
	userProfileRepository.save(userProfile);

  }

  public void confirmUserCertification(Long id) {
	UserProfile userProfile = userProfileRepository.findOne(id);
	if (null == userProfile) {
	  return;
	}
	userProfile.setCertificateStatus(UserProfile.CertificateType.AUTHORIZED);
	userProfileRepository.save(userProfile);
  }

  public void rejectedUserCertification(Long id, String reason) {
	UserProfile userProfile = userProfileRepository.findOne(id);
	if (null == userProfile) {
	  return;
	}
	userProfile.setCertificateStatus(UserProfile.CertificateType.REJECTED);
	userProfile.setRejectedReason(reason);
	userProfileRepository.save(userProfile);
  }

  @Transactional(readOnly = true)
  public UserProfileDetail getUserCertification(Long id) {
	UserProfile userProfile = userProfileRepository.findOne(id);
	if (null != userProfile) {
	  UserProfileDetail detail = DTOUtils.strictMap(userProfile.getUserCertification(), UserProfileDetail.class);
	  detail.setUserName(userProfile.getAccount().getUsername());
	  detail.setId(userProfile.getId());
	  detail.setHeadImage(userProfile.getHeadImage());
	  detail.setStatus(userProfile.getCertificateStatus().name());
	  detail.setReason(userProfile.getRejectedReason());
	  return detail;
	}
	return null;
  }

  @Transactional(readOnly = true)
  public Page searchUserProfiles(UserEnterpriseCertificateForm form, Pageable page) {

	Assert.notNull(form, "form can not be null");

	Page<UserProfile> profiles = userProfileRepository.findAll(
		UserProfileSpecifications.searchUserProfileSpecifications(form), page);
	if (null != profiles.getContent()) {
	  List<UserProfileDetail> details = Lists.newArrayList();
	  profiles.getContent().stream().forEach(p -> {
		UserProfileDetail detail = new UserProfileDetail();
		if (null != p.getUserCertification()) {
		  DTOUtils.strictMapTo(p.getUserCertification(), detail);
		} else {
		  detail.setCreatedDate(p.getCreatedDate());
		}
		detail.setUserName(p.getAccount().getUsername());
		detail.setId(p.getId());
		detail.setHeadImage(p.getHeadImage());
		detail.setStatus(p.getCertificateStatus().name());
		details.add(detail);
	  });

	  return new PageImpl<>(details,
							new PageRequest(profiles.getNumber(), profiles.getSize(), profiles.getSort()),
							profiles.getTotalElements());
	}
	return null;
  }

  public void updateUserProfile(ProfileForm u, UserAccount su) {
	Assert.notNull(u, "u can not be null");

	UserAccount user = userRepository.findByUsername(su.getUsername());

	BeanUtils.copyProperties(u, user, "username", "password", "role", "mobileNumber");

	userRepository.save(user);

	if (StringUtils.hasText(u.getHeadImage())) {
	  UserProfile userProfile = userProfileRepository.findByAccount(user);
	  userProfile.setHeadImage(u.getHeadImage());
	  userProfileRepository.save(userProfile);
	}
  }

  public void updateMobileNumber(UpdateMobileNumberForm u, UserAccount su) {
	Assert.notNull(u, "u can not be null");

	if (!smsService.validate(u.getMobileNumber(), u.getSmsCode())
		|| !smsService.validate(u.getNewMobileNumber(), u.getNewSmsCode())) {
	  throw new CaptchaMismatchedException();
	}

	su.setMobileNumber(u.getNewMobileNumber());
	userRepository.save(su);
  }

  public Page<PrivateMessageDetails> getReceivedMessages(Long userId, Boolean read, Pageable p) {
	Assert.notNull(userId, " userId can't  be null ");
	Page<PrivateMessage>
		messages =
		privateMessageRepository.findAll(PrivateMessageSpecifications.filterReceivedMessages(userId, read), p);

	if (log.isDebugEnabled()) {
	  log.debug("found mesages@" + messages.getTotalElements());
	}

	return DTOUtils.mapPage(messages, PrivateMessageDetails.class);
  }

  public long countUnreadMessages(Long userId) {
	Assert.notNull(userId, " userId can't  be null ");

	long
		count =
		privateMessageRepository.count(PrivateMessageSpecifications.filterReceivedMessages(userId, Boolean.valueOf(false)));

	if (log.isDebugEnabled()) {
	  log.debug("found mesages count@" + count);
	}

	return count;
  }

  public Page<PrivateMessageDetails> getSentMessages(Long userId, Pageable page) {
	Assert.notNull(userId, " userId can't  be null ");
	Page<PrivateMessage>
		messages =
		privateMessageRepository.findAll(PrivateMessageSpecifications.filterSentMessages(userId), page);

	if (log.isDebugEnabled()) {
	  log.debug("found mesages@" + messages.getTotalElements());
	}

	return DTOUtils.mapPage(messages, PrivateMessageDetails.class);

  }

  public PrivateMessageDetails getMessage(Long id) {
	Assert.notNull(id, "message id can not be null");
	PrivateMessage msg = privateMessageRepository.findOne(id);

	if (msg == null) {
	  throw new ResourceNotFoundException(id);
	}

	return DTOUtils.map(msg, PrivateMessageDetails.class);
  }

  public void deleteMessage(Long id) {
	Assert.notNull(id, "message id can not be null");
	privateMessageRepository.delete(id);
  }

  public void markAllMessagesAsRead(Long userId) {
	Assert.notNull(userId, " user id can't  be empty ");

	privateMessageRepository.batchUpdateReadStatus(userId);

  }


}
