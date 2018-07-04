<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta charset="utf-8" />
	<title>Form Elements - Ace Admin</title>
	<meta name="description" content="Common form elements and layouts" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	<!-- bootstrap & fontawesome -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/font-awesome/4.5.0/css/font-awesome.min.css" />
	<!-- page specific plugin styles -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jquery-ui.min.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jquery-ui.custom.min.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/chosen.min.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap-datepicker3.min.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap-timepicker.min.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/daterangepicker.min.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap-datetimepicker.min.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap-colorpicker.min.css" />
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
	<!-- 头部导航公共页面 -->
	<jsp:include page="commons/topNav.jsp"/>
	
	<div class="main-container ace-save-state" id="main-container">
		<script type="text/javascript">
			try{ace.settings.loadState('main-container')}catch(e){}
		</script>

		<!-- 左侧导航公共页面 -->
		<jsp:include page="commons/leftNav.jsp"/>
		
		<div class="main-content">
			<div class="main-content-inner">
				<div class="breadcrumbs ace-save-state" id="breadcrumbs">
					<ul class="breadcrumb">
						<li>
							<i class="ace-icon fa fa-home home-icon"></i>
							<a href="#">首页</a>
						</li>
						<li>
							<a href="#">账户管理</a>
						</li>
						<li class="active">添加用户</li>
					</ul><!-- /.breadcrumb -->

					<div class="nav-search" id="nav-search">
						<form class="form-search">
							<span class="input-icon">
								<input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off" />
								<i class="ace-icon fa fa-search nav-search-icon"></i>
							</span>
						</form>
					</div><!-- /.nav-search -->
				</div>
				<div class="page-content">
					<div class="page-header">
						<h1>
							账户管理
							<small>
								<i class="ace-icon fa fa-angle-double-right"></i>
								添加用户
							</small>
						</h1>
					</div><!-- /.page-header -->
					<div class="row">
						<div class="col-xs-12">
							<!-- PAGE CONTENT BEGINS -->
							<form class="form-horizontal" role="form">
								<div class="space-4"></div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-2">用户账号</label>
									<div class="col-sm-9">
										<input type="text" id="username" name="username" placeholder="请输入用户账号" class="col-xs-10 col-sm-5" value="${user.username}"/>
										<span class="help-inline col-xs-12 col-sm-7">
											<span class="middle">Inline help text</span>
										</span>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-2">用户密码</label>
									<div class="col-sm-9">
										<input type="password" id="password" name="password" placeholder="请输入用户密码" class="col-xs-10 col-sm-5" value=""/>
										<span class="help-inline col-xs-12 col-sm-7">
											<span class="middle">Inline help text</span>
										</span>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-2">用户手机</label>
									<div class="col-sm-9">
										<input type="text" id="phone" name="phone" placeholder="请输入用户手机" class="col-xs-10 col-sm-5" value="${user.staffInfo.phone}"/>
										<span class="help-inline col-xs-12 col-sm-7">
											<span class="middle">Inline help text</span>
										</span>
									</div>
								</div>
								<div class="space-4"></div>
								<div class="clearfix form-actions">
									<div class="col-md-offset-3 col-md-9">
										<input type="hidden" id="userId" name="userId" value="${user.id}">
										<button class="btn btn-info" type="button" onclick="addUser()">
											<i class="ace-icon fa fa-check bigger-110"></i>
											提 交
										</button>
										&nbsp; &nbsp; &nbsp;
										<button class="btn" type="reset">
											<i class="ace-icon fa fa-undo bigger-110"></i>
											重 置
										</button>
									</div>
								</div>
							</form>

							<div class="hr hr-18 dotted hr-double"></div>

						</div><!-- /.col -->
					</div><!-- /.row -->
				</div><!-- /.page-content -->
			</div>
		</div><!-- /.main-content -->
		<!-- 尾部版权信息公共页面 -->
		<jsp:include page="commons/footer.jsp"/>
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
	<!--[if lte IE 8]>
	<script src="${pageContext.request.contextPath}/assets/js/excanvas.min.js"></script>
	<![endif]-->
	<script src="${pageContext.request.contextPath}/assets/js/jquery-ui.min.js"></script>
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
	<!-- ace scripts -->
	<script src="${pageContext.request.contextPath}/assets/js/ace-elements.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets/js/ace.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jQuery.md5.js"></script>
	<!-- inline scripts related to this page -->
	<script type="text/javascript">
		jQuery(function($) {
			/**
				autocomplete函数
				jQuery UI Autocomplete常用的参数有：
				Source：用于指定数据来源，类型为String、Array、Function
				String：用于ajax请求的服务器端地址，返回Array/JSON格式，默认传递的参数名称为term
				Array：即字符串数组 或 JSON数组
				Function(request, response)：通过request.term获得输入的值，response([Array])来呈现数据；(JSONP是这种方式)
				minLength：当输入框内字符串长度达到minLength时，激活Autocomplete
				autoFocus：当Autocomplete选择菜单弹出时，自动选中第一个
				delay：即延迟多少毫秒激活Autocomplete
			*/
			$("#phone").autocomplete({
				source: "../admin/getStaffPhone",
			    minLength: 1,
			    delay: 300,
			    autoFocus: true
			});
		});
		//左侧菜单展示及选中样式
		$("#account").addClass("active open");
		$("#account-account").addClass("active");
		//添加用户
		function addUser() {
			var username = $.trim($("#username").val());
			var password = $.trim($("#password").val());
			var phone = $.trim($("#phone").val());
			var userId = $("#userId").val();
			$.ajax({
				url : "${pageContext.request.contextPath}/admin/addUser",
				type : "POST",
				dataType : "json",
				data : {"username" : username, "password" : $.md5(password), "phone" : phone, "userId" : userId},
				success : function (message) {
					if (message.errorCode == 0) {
						//操作成功
						alert("操作成功");
						window.location.href="${pageContext.request.contextPath}/admin/users";
					} else {
						alert("操作失败");
						return;
					}
				},
				error : function () {
					alert("网络错误！");
					return;
				}
			});
		}
	</script>
</body>
</html>