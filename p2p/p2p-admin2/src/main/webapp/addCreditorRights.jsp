<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta charset="utf-8" />
	<title>欢迎使用 - P2P后台管理系统</title>
	<meta name="description" content="Common form elements and layouts" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	<!-- bootstrap & fontawesome -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/font-awesome/4.5.0/css/font-awesome.min.css" />

	<!-- page specific plugin styles -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jquery-ui.min.css" />

	<!-- text fonts -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/fonts.googleapis.com.css" />

	<!-- ace styles -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style" />

	<!--[if lte IE 9]>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/ace-part2.min.css" class="ace-main-stylesheet" />
	<![endif]-->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/ace-skins.min.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/ace-rtl.min.css" />

	<!--[if lte IE 9]>
	  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/ace-ie.min.css" />
	<![endif]-->

	<!-- inline styles related to this page -->

	<!-- ace settings handler -->
	<script src="${pageContext.request.contextPath}/assets/js/ace-extra.min.js"></script>

	<!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->

	<!--[if lte IE 8]>
	<script src="${pageContext.request.contextPath}/assets/js/html5shiv.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets/js/respond.min.js"></script>
	<![endif]-->
</head>

<body class="no-skin">

	<jsp:include page="commons/topNav.jsp" />
	
	<div class="main-container ace-save-state" id="main-container">
		<jsp:include page="commons/leftNav.jsp" />
		
		<div class="main-content">
			<div class="main-content-inner">
				<div class="breadcrumbs ace-save-state" id="breadcrumbs">
					<ul class="breadcrumb">
						<li>
							<i class="ace-icon fa fa-home home-icon"></i>
							<a href="#">管理面板</a>
						</li>
						<li>
							<a href="#">债权管理</a>
						</li>
						<li class="active">自主债权</li>
					</ul><!-- /.breadcrumb -->
				</div>

				<div class="page-content">
					<div class="page-header">
						<h1>
							债权管理
							<small>
								<i class="ace-icon fa fa-angle-double-right"></i>
								自主债权
							</small>
						</h1>
					</div><!-- /.page-header -->

					<div class="row">
						<div class="col-xs-12">
							<!-- PAGE CONTENT BEGINS -->
							<form class="form-horizontal" role="form" id="creditorRightsForm" action="${pageContext.request.contextPath}/admin/addCreditorRights" method="post">
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1">借款申请编号 </label>
									<div class="col-sm-9">
										<input type="text" id="applyNo" name="applyNo" placeholder="请输入" class="col-xs-10 col-sm-5" value="<c:if test="${empty creditorRights.applyNo}">${applyNo}</c:if><c:if test="${not empty creditorRights.applyNo}">${creditorRights.applyNo}</c:if>"/>
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1-1">进件来源</label>
									<div class="col-sm-9">
										<select id="intoSource" name="intoSource" class="col-xs-10 col-sm-5">
											<option value="">请选择</option>
											<c:forEach items="${dictionaryInfoList}" var="dictionaryInfo">
												<c:choose>
													<c:when test="${dictionaryInfo.type eq 1}">
														<option value="${dictionaryInfo.typevalue}" <c:if test="${creditorRights.intoSource eq dictionaryInfo.typevalue}">selected</c:if>>${dictionaryInfo.typename}</option>
													</c:when>
												</c:choose>
											</c:forEach>
										</select>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1-1">申请借款产品</label>
									<div class="col-sm-9">
										<select id="applyProduct" name="applyProduct" class="col-xs-10 col-sm-5">
											<option value="">请选择</option>
											<c:forEach items="${dictionaryInfoList}" var="dictionaryInfo">
												<c:choose>
													<c:when test="${dictionaryInfo.type eq 3}">
														<option value="${dictionaryInfo.typevalue}" <c:if test="${creditorRights.intoSource eq dictionaryInfo.typevalue}">selected</c:if>>${dictionaryInfo.typename}</option>
													</c:when>
												</c:choose>
											</c:forEach>
										</select>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1-1">申请借款金额</label>
									<div class="col-sm-9">
										<input type="text" id="applyMoney" name="applyMoney" placeholder="请输入" class="col-xs-10 col-sm-5" value="${creditorRights.applyMoney}"/>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1-1">申请借款期限</label>
									<div class="col-sm-9">
										<select id="applyTerm" name="applyTerm" class="col-xs-10 col-sm-5">
											<option value="">请选择</option>
											<c:forEach items="${dictionaryInfoList}" var="dictionaryInfo">
												<c:choose>
													<c:when test="${dictionaryInfo.type eq 2}">
														<option value="${dictionaryInfo.typevalue}" <c:if test="${creditorRights.applyTerm eq dictionaryInfo.typevalue}">selected</c:if>>${dictionaryInfo.typename}</option>
													</c:when>
												</c:choose>
											</c:forEach>
										</select>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1-1">申请借款目的</label>
									<div class="col-sm-9">
										<select id="applyPurpose" name="applyPurpose" class="col-xs-10 col-sm-5">
											<option value="">请选择</option>
											<c:forEach items="${dictionaryInfoList}" var="dictionaryInfo">
												<c:choose>
													<c:when test="${dictionaryInfo.type eq 4}">
														<option value="${dictionaryInfo.typevalue}" <c:if test="${creditorRights.applyPurpose eq dictionaryInfo.typevalue}">selected</c:if>>${dictionaryInfo.typename}</option>
													</c:when>
												</c:choose>
											</c:forEach>
										</select>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1-1">借款人真实姓名</label>
									<div class="col-sm-9">
										<input type="text" id="borrowerRealname" name="borrowerRealname" placeholder="请输入" class="col-xs-10 col-sm-5" value="${creditorRights.borrowerRealname}"/>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1-1">借款人身份证号</label>
									<div class="col-sm-9">
										<input type="text" id="borrowerIdcard" name="borrowerIdcard" placeholder="请输入" class="col-xs-10 col-sm-5" value="${creditorRights.borrowerIdcard}"/>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1-1">借款人性别</label>
									<div class="col-sm-9">
										<select id="borrowerSex" name="borrowerSex" class="col-xs-10 col-sm-5">
											<option value="">请选择</option>
											<c:forEach items="${dictionaryInfoList}" var="dictionaryInfo">
												<c:choose>
													<c:when test="${dictionaryInfo.type eq 5}">
														<option value="${dictionaryInfo.typevalue}" <c:if test="${creditorRights.borrowerSex eq dictionaryInfo.typevalue}">selected</c:if>>${dictionaryInfo.typename}</option>
													</c:when>
												</c:choose>
											</c:forEach>
										</select>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1">借款人年龄</label>
									<div class="col-sm-9">
										<input type="text" id="borrowerAge" name="borrowerAge" placeholder="请输入" class="col-xs-10 col-sm-5" value="${creditorRights.borrowerAge}"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1">借款人学历</label>
									<div class="col-sm-9">
										<select id="borrowerEducation" name="borrowerEducation" class="col-xs-10 col-sm-5">
											<option value="">请选择</option>
											<c:forEach items="${dictionaryInfoList}" var="dictionaryInfo">
												<c:choose>
													<c:when test="${dictionaryInfo.type eq 6}">
														<option value="${dictionaryInfo.typevalue}" <c:if test="${creditorRights.borrowerEducation eq dictionaryInfo.typevalue}">selected</c:if>>${dictionaryInfo.typename}</option>
													</c:when>
												</c:choose>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1">借款人婚姻状况</label>
									<div class="col-sm-9">
										<select id="borrowerMarital" name="borrowerMarital" class="col-xs-10 col-sm-5">
											<option value="">请选择</option>
											<c:forEach items="${dictionaryInfoList}" var="dictionaryInfo">
												<c:choose>
													<c:when test="${dictionaryInfo.type eq 7}">
														<option value="${dictionaryInfo.typevalue}" <c:if test="${creditorRights.borrowerMarital eq dictionaryInfo.typevalue}">selected</c:if>>${dictionaryInfo.typename}</option>
													</c:when>
												</c:choose>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1">借款人现所在城市</label>
									<div class="col-sm-9">
										<select id="borrowerCity" name="borrowerCity" class="col-xs-10 col-sm-5">
											<option value="">请选择</option>
											<c:forEach items="${dictionaryInfoList}" var="dictionaryInfo">
												<c:choose>
													<c:when test="${dictionaryInfo.type eq 8}">
														<option value="${dictionaryInfo.typevalue}" <c:if test="${creditorRights.borrowerCity eq dictionaryInfo.typevalue}">selected</c:if>>${dictionaryInfo.typename}</option>
													</c:when>
												</c:choose>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1">借款人现住址</label>
									<div class="col-sm-9">
										<input type="text" id="borrowerPresentAddress" name="borrowerPresentAddress" placeholder="请输入" class="col-xs-10 col-sm-5" value="${creditorRights.borrowerPresentAddress}"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1">借款人银行账号</label>
									<div class="col-sm-9">
										<input type="text" id="borrowerBankAccount" name="borrowerBankAccount" placeholder="请输入" class="col-xs-10 col-sm-5" value="${creditorRights.borrowerBankAccount}"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1">借款人银行账号开户行</label>
									<div class="col-sm-9">
										<select id="borrowerOpenBank" name="borrowerOpenBank" class="col-xs-10 col-sm-5">
											<option value="">请选择</option>
											<c:forEach items="${dictionaryInfoList}" var="dictionaryInfo">
												<c:choose>
													<c:when test="${dictionaryInfo.type eq 9}">
														<option value="${dictionaryInfo.typevalue}" <c:if test="${creditorRights.borrowerOpenBank eq dictionaryInfo.typevalue}">selected</c:if>>${dictionaryInfo.typename}</option>
													</c:when>
												</c:choose>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1">借款人房产数</label>
									<div class="col-sm-9">
										<input type="text" id="borrowerHouseCount" name="borrowerHouseCount" placeholder="请输入" class="col-xs-10 col-sm-5" value="${creditorRights.borrowerHouseCount}"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1">借款人车产数</label>
									<div class="col-sm-9">
										<input type="text" id="borrowerCarCount" name="borrowerCarCount" placeholder="请输入" class="col-xs-10 col-sm-5" value="${creditorRights.borrowerCarCount}"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1">借款人工作</label>
									<div class="col-sm-9">
										<select id="borrowerWork" name="borrowerWork" class="col-xs-10 col-sm-5">
											<option value="">请选择</option>
											<c:forEach items="${dictionaryInfoList}" var="dictionaryInfo">
												<c:choose>
													<c:when test="${dictionaryInfo.type eq 10}">
														<option value="${dictionaryInfo.typevalue}" <c:if test="${creditorRights.borrowerWork eq dictionaryInfo.typevalue}">selected</c:if>>${dictionaryInfo.typename}</option>
													</c:when>
												</c:choose>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1">借款人公司名称</label>
									<div class="col-sm-9">
										<input type="text" id="borrowerCompanyName" name="borrowerCompanyName" placeholder="请输入" class="col-xs-10 col-sm-5" value="${creditorRights.borrowerCompanyName}"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1">借款人公司地址</label>
									<div class="col-sm-9">
										<input type="text" id="borrowerCompanyAddress" name="borrowerCompanyAddress" placeholder="请输入" class="col-xs-10 col-sm-5" value="${creditorRights.borrowerCompanyAddress}"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1">借款人工作年限</label>
									<div class="col-sm-9">
										<input type="text" id="borrowerWorkYear" name="borrowerWorkYear" placeholder="请输入" class="col-xs-10 col-sm-5" value="${creditorRights.borrowerWorkYear}"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1">借款人月薪</label>
									<div class="col-sm-9">
										<input type="text" id="borrowerMonthSalary" name="borrowerMonthSalary" placeholder="请输入" class="col-xs-10 col-sm-5" value="${creditorRights.borrowerMonthSalary}"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1">借款人信用报告认证状态</label>
									<div class="col-sm-9">
										<select id="borrowerCreditReportCertification" name="borrowerCreditReportCertification" class="col-xs-10 col-sm-5">
											<option value="">请选择</option>
											<c:forEach items="${dictionaryInfoList}" var="dictionaryInfo">
												<c:choose>
													<c:when test="${dictionaryInfo.type eq 11}">
														<option value="${dictionaryInfo.typevalue}" <c:if test="${creditorRights.borrowerCreditReportCertification eq dictionaryInfo.typevalue}">selected</c:if>>${dictionaryInfo.typename}</option>
													</c:when>
												</c:choose>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1">借款人信用报告认证时间</label>
									<div class="col-sm-9">
										<input type="text" id="borrowerCreditReportCertificationTime" name="borrowerCreditReportCertificationTime" placeholder="请输入" class="col-xs-10 col-sm-5" value="<fmt:formatDate value="${creditorRights.borrowerCreditReportCertificationTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" />
										<i class="ace-icon fa fa-calendar"></i>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1">借款人身份公安认证</label>
									<div class="col-sm-9">
										<select id="borrowerIdcardCertification" name="borrowerIdcardCertification" class="col-xs-10 col-sm-5">
											<option value="">请选择</option>
											<c:forEach items="${dictionaryInfoList}" var="dictionaryInfo">
												<c:choose>
													<c:when test="${dictionaryInfo.type eq 11}">
														<option value="${dictionaryInfo.typevalue}" <c:if test="${creditorRights.borrowerIdcardCertification eq dictionaryInfo.typevalue}">selected</c:if>>${dictionaryInfo.typename}</option>
													</c:when>
												</c:choose>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1">借款人身份公安认证时间</label>
									<div class="col-sm-9">
										<input type="text" id="borrowerIdcardCertificationTime" name="borrowerIdcardCertificationTime" placeholder="请输入" class="col-xs-10 col-sm-5" value="<fmt:formatDate value="${creditorRights.borrowerIdcardCertificationTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
										<i class="ace-icon fa fa-calendar"></i>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1">借款人工作认证</label>
									<div class="col-sm-9">
										<select id="borrowerWorkCertification" name="borrowerWorkCertification" class="col-xs-10 col-sm-5">
											<option value="">请选择</option>
											<c:forEach items="${dictionaryInfoList}" var="dictionaryInfo">
												<c:choose>
													<c:when test="${dictionaryInfo.type eq 11}">
														<option value="${dictionaryInfo.typevalue}" <c:if test="${creditorRights.borrowerWorkCertification eq dictionaryInfo.typevalue}">selected</c:if>>${dictionaryInfo.typename}</option>
													</c:when>
												</c:choose>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1">借款人工作认证时间</label>
									<div class="col-sm-9">
										<input type="text" id="borrowerWorkCertificationTime" name="borrowerWorkCertificationTime" placeholder="请输入" class="col-xs-10 col-sm-5" value="<fmt:formatDate value="${creditorRights.borrowerWorkCertificationTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
										<i class="ace-icon fa fa-calendar"></i>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1">借款人收入认证</label>
									<div class="col-sm-9">
										<select id="borrowerIncomeCertification" name="borrowerIncomeCertification" class="col-xs-10 col-sm-5">
											<option value="">请选择</option>
											<c:forEach items="${dictionaryInfoList}" var="dictionaryInfo">
												<c:choose>
													<c:when test="${dictionaryInfo.type eq 11}">
														<option value="${dictionaryInfo.typevalue}" <c:if test="${creditorRights.borrowerIncomeCertification eq dictionaryInfo.typevalue}">selected</c:if>>${dictionaryInfo.typename}</option>
													</c:when>
												</c:choose>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1">借款人收入认证时间</label>
									<div class="col-sm-9">
										<input type="text" id="borrowerIncomeCertificationTime" name="borrowerIncomeCertificationTime" placeholder="请输入" class="col-xs-10 col-sm-5" value="<fmt:formatDate value="${creditorRights.borrowerIncomeCertificationTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
										<i class="ace-icon fa fa-calendar"></i>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1">借款人房产认证</label>
									<div class="col-sm-9">
										<select id="borrowerHouseCertification" name="borrowerHouseCertification" class="col-xs-10 col-sm-5">
											<option value="">请选择</option>
											<c:forEach items="${dictionaryInfoList}" var="dictionaryInfo">
												<c:choose>
													<c:when test="${dictionaryInfo.type eq 11}">
														<option value="${dictionaryInfo.typevalue}" <c:if test="${creditorRights.borrowerHouseCertification eq dictionaryInfo.typevalue}">selected</c:if>>${dictionaryInfo.typename}</option>
													</c:when>
												</c:choose>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1">借款人房产认证时间</label>
									<div class="col-sm-9">
										<input type="text" id="borrowerHouseCertificationTime" name="borrowerHouseCertificationTime" placeholder="请输入" class="col-xs-10 col-sm-5" value="<fmt:formatDate value="${creditorRights.borrowerHouseCertificationTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
										<i class="ace-icon fa fa-calendar"></i>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1">借款人实地认证</label>
									<div class="col-sm-9">
										<select id="borrowerFieldCertification" name="borrowerFieldCertification" class="col-xs-10 col-sm-5">
											<option value="">请选择</option>
											<c:forEach items="${dictionaryInfoList}" var="dictionaryInfo">
												<c:choose>
													<c:when test="${dictionaryInfo.type eq 11}">
														<option value="${dictionaryInfo.typevalue}" <c:if test="${creditorRights.borrowerFieldCertification eq dictionaryInfo.typevalue}">selected</c:if>>${dictionaryInfo.typename}</option>
													</c:when>
												</c:choose>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1">借款人实地认证时间</label>
									<div class="col-sm-9">
										<input type="text" id="borrowerFieldCertificationTime" name="borrowerFieldCertificationTime" placeholder="请输入" class="col-xs-10 col-sm-5" value="<fmt:formatDate value="${creditorRights.borrowerFieldCertificationTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
										<i class="ace-icon fa fa-calendar"></i>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1">债权审批状态</label>
									<div class="col-sm-9">
										<input type="text" id="auditStatus" name="auditStatus" placeholder="请输入" class="col-xs-10 col-sm-5" value="${creditorRights.auditStatus}"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1">债权审批时间</label>
									<div class="col-sm-9">
										<input type="text" id="auditTime" name="auditTime" placeholder="请输入" class="col-xs-10 col-sm-5" value="<fmt:formatDate value="${creditorRights.auditTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
										<i class="ace-icon fa fa-calendar"></i>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1">债权审批借款期限（月）</label>
									<div class="col-sm-9">
										<input type="text" id="auditLoanTerm" name="auditLoanTerm" placeholder="请输入" class="col-xs-10 col-sm-5" value="${creditorRights.auditLoanTerm}"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1">债权审批借款金额</label>
									<div class="col-sm-9">
										<input type="text" id="auditLoanMoney" name="auditLoanMoney" placeholder="请输入" class="col-xs-10 col-sm-5" value="${creditorRights.auditLoanMoney}"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1">签订合同借款金额</label>
									<div class="col-sm-9">
										<input type="text" id="contractLoanMoney" name="contractLoanMoney" placeholder="请输入" class="col-xs-10 col-sm-5" value="${creditorRights.contractLoanMoney}"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1">咨询费</label>
									<div class="col-sm-9">
										<input type="text" id="consultingFee" name="consultingFee" placeholder="请输入" class="col-xs-10 col-sm-5" value="${creditorRights.consultingFee}"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1">审核费</label>
									<div class="col-sm-9">
										<input type="text" id="auditFee" name="auditFee" placeholder="请输入" class="col-xs-10 col-sm-5" value="${creditorRights.auditFee}"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1">服务费</label>
									<div class="col-sm-9">
										<input type="text" id="serviceFee" name="serviceFee" placeholder="请输入" class="col-xs-10 col-sm-5" value="${creditorRights.serviceFee}"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1">外访服务费</label>
									<div class="col-sm-9">
										<input type="text" id="accessFee" name="accessFee" placeholder="请输入" class="col-xs-10 col-sm-5" value="${creditorRights.accessFee}"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1">借款年利率</label>
									<div class="col-sm-9">
										<input type="text" id="yearRate" name="yearRate" placeholder="请输入" class="col-xs-10 col-sm-5" value="${creditorRights.yearRate}"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1">放款金额</label>
									<div class="col-sm-9">
										<input type="text" id="putMoney" name="putMoney" placeholder="请输入" class="col-xs-10 col-sm-5" value="${creditorRights.putMoney}"/>
									</div>
								</div>
								<div class="clearfix form-actions">
									<div class="col-md-offset-3 col-md-9">
										<input type="hidden" id="creditorRightsType" name="creditorRightsType" value="${creditorRightsType}"/>
										<input type="hidden" id="creditorRightsId" name="creditorRightsId" value="${creditorRights.id}"/>
										<button class="btn btn-info" type="button" onclick="addCreditorRights()">
											<i class="ace-icon fa fa-check bigger-110"></i>
											提交
										</button>

										&nbsp; &nbsp; &nbsp;
										<button class="btn" type="reset">
											<i class="ace-icon fa fa-undo bigger-110"></i>
											重置
										</button>
									</div>
								</div>
								<div class="hr hr-24"></div>
								<div class="space-24"></div>
							</form>
						</div><!-- /.col -->
					</div><!-- /.row -->
				</div><!-- /.page-content -->
			</div>
		</div><!-- /.main-content -->

		<jsp:include page="commons/footer.jsp" />
	</div><!-- /.main-container -->

	<!-- basic scripts -->

		<!--[if !IE]> -->
		<script src="${pageContext.request.contextPath}/assets/js/jquery-2.1.4.min.js"></script>

		<!-- <![endif]-->

		<!--[if IE]>
		<script src="${pageContext.request.contextPath}/assets/js/jquery-1.11.3.min.js"></script>
		<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='${pageContext.request.contextPath}/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		<script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>

		<!-- page specific plugin scripts -->
		<script src="${pageContext.request.contextPath}/assets/js/jquery-ui.min.js"></script>
		<script src="${pageContext.request.contextPath}/assets/js/jquery.ui.touch-punch.min.js"></script>

		<!-- ace scripts -->
		<script src="${pageContext.request.contextPath}/assets/js/ace-elements.min.js"></script>
		<script src="${pageContext.request.contextPath}/assets/js/ace.min.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery-ui-timepicker-addon.min.css" />
		<script src="${pageContext.request.contextPath}/js/jquery-ui-timepicker-addon.min.js"></script>
		
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
		jQuery(function($) {
			$("#borrowerCreditReportCertificationTime").datetimepicker({
				lang:"ch",
		        dateFormat: "yy-mm-dd",
				changeMonth: true,       
				changeYear: true,        
				timeFormat: 'hh:mm:ss',        
				stepHour: 1,        
				stepMinute: 5,        
				stepSecond: 10,        
				dayNamesMin: ["七","一", "二", "三", "四", "五", "六"],        
				monthNamesShort: ["一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"]    
			});
			
			$("#borrowerIdcardCertificationTime").datetimepicker({
				dateFormat: "yy-mm-dd",
				changeMonth: true,       
				changeYear: true,        
				timeFormat: 'hh:mm:ss',        
				stepHour: 1,        
				stepMinute: 5,        
				stepSecond: 10,        
				dayNamesMin: ["七","一", "二", "三", "四", "五", "六"],        
				monthNamesShort: ["一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"]    
			});
			$("#borrowerWorkCertificationTime").datetimepicker({
				dateFormat: "yy-mm-dd",
				changeMonth: true,       
				changeYear: true,        
				timeFormat: 'hh:mm:ss',        
				stepHour: 1,        
				stepMinute: 5,        
				stepSecond: 10,        
				dayNamesMin: ["七","一", "二", "三", "四", "五", "六"],        
				monthNamesShort: ["一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"]    
			});
			$("#borrowerIncomeCertificationTime").datetimepicker({
				dateFormat: "yy-mm-dd",
				changeMonth: true,       
				changeYear: true,        
				timeFormat: 'hh:mm:ss',        
				stepHour: 1,        
				stepMinute: 5,        
				stepSecond: 10,        
				dayNamesMin: ["七","一", "二", "三", "四", "五", "六"],        
				monthNamesShort: ["一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"]    
			});
			$("#borrowerHouseCertificationTime").datetimepicker({
				dateFormat: "yy-mm-dd",
				changeMonth: true,       
				changeYear: true,        
				timeFormat: 'hh:mm:ss',        
				stepHour: 1,        
				stepMinute: 5,        
				stepSecond: 10,        
				dayNamesMin: ["七","一", "二", "三", "四", "五", "六"],        
				monthNamesShort: ["一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"]    
			});
			$("#borrowerFieldCertificationTime").datetimepicker({
				dateFormat: "yy-mm-dd",
				changeMonth: true,       
				changeYear: true,        
				timeFormat: 'hh:mm:ss',        
				stepHour: 1,        
				stepMinute: 5,        
				stepSecond: 10,        
				dayNamesMin: ["七","一", "二", "三", "四", "五", "六"],        
				monthNamesShort: ["一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"]    
			});
			$("#auditTime").datetimepicker({
				dateFormat: "yy-mm-dd",
				changeMonth: true,       
				changeYear: true,        
				timeFormat: 'hh:mm:ss',        
				stepHour: 1,        
				stepMinute: 5,        
				stepSecond: 10,        
				dayNamesMin: ["七","一", "二", "三", "四", "五", "六"],        
				monthNamesShort: ["一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"]    
			});
			$("#matchFinishTime").datetimepicker({
				dateFormat: "yy-mm-dd",
				changeMonth: true,       
				changeYear: true,        
				timeFormat: 'hh:mm:ss',        
				stepHour: 1,        
				stepMinute: 5,        
				stepSecond: 10,        
				dayNamesMin: ["七","一", "二", "三", "四", "五", "六"],        
				monthNamesShort: ["一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"]    
			});
		});
		var creditorRightsType = "${creditorRightsType}"
		if ("0" == creditorRightsType) {
			$("#rights").addClass("active open");
			$("#rights-zizhu").addClass("active");
		} else if ("1" == creditorRightsType) {
			$("#rights").addClass("active open");
			$("#rights-sanfang").addClass("active");
		}
		//录入债权提交
		function addCreditorRights () {
			//提交之前需要对表单数据进行验证 (暂时省略)
			$("#creditorRightsForm").submit();
		}
		</script>
</body>
</html>
