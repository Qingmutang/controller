<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta charset="utf-8" />
	<title>新增产品 - P2P后台管理系统</title>
	<meta name="description" content="新增产品，P2P后台管理系统" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	<!-- bootstrap & fontawesome -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/font-awesome/4.5.0/css/font-awesome.min.css" />
	
	<!-- page specific plugin styles -->
	
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
							<a href="javascript:void(0)">管理面板</a>
						</li>
						<li>
							<a href="javascript:void(0)">产品管理</a>
						</li>
						<li class="active">
						<c:choose>
							<c:when test="${productType eq 0}">体验产品</c:when>
							<c:when test="${productType eq 1}">优选产品</c:when>
							<c:otherwise>散标产品</c:otherwise>
						</c:choose>
						</li>
					</ul><!-- /.breadcrumb -->
				</div>

				<div class="page-content">
					<div class="page-header">
						<h1>
							产品管理
							<small>
								<i class="ace-icon fa fa-angle-double-right"></i>
								<c:choose>
									<c:when test="${productType eq 0}">体验产品</c:when>
									<c:when test="${productType eq 1}">优选产品</c:when>
									<c:otherwise>散标产品</c:otherwise>
								</c:choose>
							</small>
						</h1>
					</div><!-- /.page-header -->

					<div class="row">
						<div class="col-xs-12">
							<!-- PAGE CONTENT BEGINS -->
							<form class="form-horizontal" role="form">
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1">产品名称</label>
									<div class="col-sm-9">
										<input type="text" id="productName" name="productName" placeholder="请输入产品名称" class="col-xs-10 col-sm-5" value="${loanInfo.productName}"/>
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1-1">产品编号</label>
									<div class="col-sm-9">
										<input type="text" id="productNo" name="productNo" placeholder="请输入产品编号" class="col-xs-10 col-sm-5" value="${loanInfo.productNo}"/>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1-1">产品利率</label>
									<div class="col-sm-9">
										<input type="text" id="rate" name="rate" placeholder="请输入产品利率" class="col-xs-10 col-sm-5" value="${loanInfo.rate}"/>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1-1">产品期限</label>
									<div class="col-sm-9">
									<c:choose>
										<c:when test="${not empty loanInfo}">
											<input type="text" id="cycle" name="cycle" placeholder="请输入产品期限" class="col-xs-10 col-sm-5" value="${loanInfo.cycle}"/>
										</c:when>
										<c:when test="${not empty creditorRights}">
											<input type="text" id="cycle" name="cycle" placeholder="请输入产品期限" class="col-xs-10 col-sm-5" value="${creditorRights.auditLoanTerm}" readOnly/>
										</c:when>
										<c:otherwise>
											<input type="text" id="cycle" name="cycle" placeholder="请输入产品期限" class="col-xs-10 col-sm-5"/>
										</c:otherwise>
									</c:choose>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1-1">产品类型</label>
									<div class="col-sm-9">
										<select id="productType" name="productType" class="col-xs-10 col-sm-5">
											<option value="">请选择</option>
											<c:choose>
												<c:when test="${not empty loanInfo}">
													<c:forEach items="${dictionaryInfoList}" var="dictionaryInfo">
														<option value="${dictionaryInfo.typevalue}" <c:if test="${loanInfo.productType eq dictionaryInfo.typevalue}">selected</c:if>>${dictionaryInfo.typename}</option>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<c:forEach items="${dictionaryInfoList}" var="dictionaryInfo">
														<option value="${dictionaryInfo.typevalue}" <c:if test="${productType eq dictionaryInfo.typevalue}">selected</c:if>>${dictionaryInfo.typename}</option>
													</c:forEach>
												</c:otherwise>
											</c:choose>
										</select>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1-1">产品金额</label>
									<div class="col-sm-9">
										<c:choose>
										<c:when test="${not empty loanInfo}">
											<input type="text" id="productMoney" name="productMoney" placeholder="请输入产品金额" class="col-xs-10 col-sm-5" value="${loanInfo.productMoney}"/>
										</c:when>
										<c:when test="${not empty creditorRights}">
											<input type="text" id="productMoney" name="productMoney" placeholder="请输入产品金额" class="col-xs-10 col-sm-5" value="${creditorRights.auditLoanMoney}" readOnly/>
										</c:when>
										<c:otherwise>
											<input type="text" id="productMoney" name="productMoney" placeholder="请输入产品金额" class="col-xs-10 col-sm-5"/>
										</c:otherwise>
									</c:choose>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1-1">产品起投金额</label>
									<div class="col-sm-9">
										<input type="text" id="bidMinLimit" name="bidMinLimit" placeholder="请输入起投金额" class="col-xs-10 col-sm-5" value="${loanInfo.bidMinLimit}"/>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1-1">产品投资限额</label>
									<div class="col-sm-9">
										<input type="text" id="bidMaxLimit" name="bidMaxLimit" placeholder="请输入产品投资限额" class="col-xs-10 col-sm-5" value="${loanInfo.bidMaxLimit}"/>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1-1">产品描述</label>
									<div class="col-sm-9">
										<textarea id="productDesc" name="productDesc" placeholder="请输入产品描述" class="col-xs-10 col-sm-5" style="height:82px;">${loanInfo.productDesc}</textarea>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1-1"></label>
									<div class="col-sm-9" style="color:red;" id="addProductResult"></div>
								</div>

								<div class="clearfix form-actions">
									<div class="col-md-offset-3 col-md-9">
										<input type="hidden" id="loanId" name="loanId" value="${loanInfo.id}">
										<button class="btn btn-info" type="button" onclick="addProduct()">
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

		<!-- 导入页脚start -->
		<jsp:include page="commons/footer.jsp" />
		<!-- 导入页脚end -->
	</div><!-- /.main-container -->

	<!-- basic scripts -->
	<!--[if !IE]> -->
	<script src="${pageContext.request.contextPath}/assets/js/jquery-2.1.4.min.js"></script>
	<!-- <![endif]-->
	<!--[if IE]>
	<script src="${pageContext.request.contextPath}/assets/js/jquery-1.11.3.min.js"></script>
	<![endif]-->
	<script type="text/javascript">
	if('ontouchstart' in document.documentElement) document.write("<script src='assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
	</script>
	<script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
	<!-- page specific plugin scripts -->
	<!--[if lte IE 8]>
	<script src="${pageContext.request.contextPath}/assets/js/excanvas.min.js"></script>
	<![endif]-->
	<script src="${pageContext.request.contextPath}/assets/js/jquery-ui.custom.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets/js/jquery.ui.touch-punch.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets/js/chosen.jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets/js/spinbox.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets/js/bootstrap-datepicker.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets/js/bootstrap-timepicker.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets/js/moment.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets/js/daterangepicker.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets/js/bootstrap-datetimepicker.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets/js/bootstrap-colorpicker.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets/js/jquery.knob.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets/js/autosize.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets/js/jquery.inputlimiter.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets/js/jquery.maskedinput.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets/js/bootstrap-tag.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets/js/ace-elements.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets/js/ace.min.js"></script>
	<!-- inline scripts related to this page -->
	
	<script type="text/javascript">
		//激活左侧导航栏样式
		var productType = "${productType}"
		if ("0" == productType) {
			$("#product").addClass("active open");
			$("#product-tiyan").addClass("active");
		} else if ("1" == productType) {
			$("#product").addClass("active open");
			$("#product-youxuan").addClass("active");
		} else if ("2" == productType) {
			$("#product").addClass("active open");
			$("#product-sanbiao").addClass("active");
		}
		//添加产品提交
		function addProduct () {
			var productName = $.trim($("#productName").val());//产品名称
			var productNo = $.trim($("#productNo").val());//产品编号
			var rate = $.trim($("#rate").val());//产品利率
			var cycle = $.trim($("#cycle").val());//产品期限
			var productType = $.trim($("#productType").val());//产品类型
			var productMoney = $.trim($("#productMoney").val());//产品金额
			var bidMinLimit = $.trim($("#bidMinLimit").val());//产品起投金额
			var bidMaxLimit = $.trim($("#bidMaxLimit").val());//产品最大投资金额
			var productDesc = $.trim($("#productDesc").val());//产品描述、
			var loanId = $.trim($("#loanId").val());//产品id
			//需要对表单进行验证（省略）
			$.ajax({
				type: "POST",
				url: "../admin/addProduct",
				dataType: "text",
				data: {
					"productName" : productName,
					"productNo" : productNo,
					"rate" : rate,
					"cycle" : cycle,
					"productType" : productType,
					"productMoney" : productMoney,
					"bidMinLimit" : bidMinLimit,
					"bidMaxLimit" : bidMaxLimit,
					"productDesc" : productDesc,
					"loanId" : loanId
				},
				success: function(message) {
					var obj = jQuery.parseJSON(message);
					if (obj.errorCode == "0") {
						if (productType == 0) {
							window.location.href = "../admin/tiyan";
						} else if (productType == 1) {
							window.location.href = "../admin/youxuan";
						} else if (productType == 2) {
							window.location.href = "../admin/sanbiao";
						} else {
							window.location.href = "../admin/youxuan";
						}
					} else {
						$("#addProductResult").html("Error：" + obj.errorMessage);
					}
				},
			    error:function() {
			    	$("#addProductResult").html("Error：网络错误");
				}
			});
		}
	</script>
</body>
</html>
