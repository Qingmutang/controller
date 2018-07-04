<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery-ui-timepicker-addon.min.css" />
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
								<form id="uploadForm" action="${pageContext.request.contextPath}/admin/uploadFile" method="post" enctype="multipart/form-data" class="form-horizontal" role="form" target="innerIFrame">
									<div class="space-4"></div>
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-2">轮播图名称</label>
										<div class="col-sm-9">
											<input type="text" id="slideTitle" name="slideTitle" placeholder="请输入轮播图名称" class="col-xs-10 col-sm-5" value="${slideShow.slideTitle}"/>
											<span class="help-inline col-xs-12 col-sm-7">
												<span class="middle">Inline help text</span>
											</span>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-2">轮播图链接</label>
										<div class="col-sm-9">
											<input type="text" id="slideUrl" name="slideUrl" placeholder="请输入轮播图链接" class="col-xs-10 col-sm-5" value="${slideShow.slideUrl}"/>
											<span class="help-inline col-xs-12 col-sm-7">
												<span class="middle">Inline help text</span>
											</span>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-2">轮播图生效时间</label>
										<div class="col-sm-9">
											<input type="text" id="slideStartTime" name="slideStartTime" placeholder="请输入轮播图生效时间" class="col-xs-10 col-sm-5" value="<fmt:formatDate value="${slideShow.slideStartTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" />
											<span class="help-inline col-xs-12 col-sm-7">
												<span class="middle">Inline help text</span>
											</span>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-2">轮播图失效时间</label>
										<div class="col-sm-9">
											<input type="text" id="slideEndTime" name="slideEndTime" placeholder="请输入轮播图失效时间" class="col-xs-10 col-sm-5" value="<fmt:formatDate value="${slideShow.slideEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
											<span class="help-inline col-xs-12 col-sm-7">
												<span class="middle">Inline help text</span>
											</span>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-2">轮播图状态</label>
										<div class="col-sm-9">
											<span class="input-icon block input-icon-right">
												<select class="col-xs-10 col-sm-5" id="slideStatus" name="slideStatus">
													<option value="">请选择</option>
													<option value="1" <c:if test='${slideShow.slideStatus eq 1}'>selected</c:if>>启用</option>
													<option value="2" <c:if test='${slideShow.slideStatus eq 2}'>selected</c:if>>禁用</option>
												</select>
												<i class="ace-icon fa red"></i>
											</span>
											<span class="help-inline col-xs-12 col-sm-7">
												<span class="middle">Inline help text</span>
											</span>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-2">上传图片</label>
										<div class="col-sm-9">
											<input type="file" id="fileName" name="fileName" placeholder="请选择上传的图片" class="col-xs-10 col-sm-5" onchange="uploadFile()"/>
											<span class="help-inline col-xs-12 col-sm-7">
												<span class="middle">Inline help text</span>
											</span>
										</div>
									</div>
									
									<div id="showSlideImage" class="form-group" 
									<c:if test='${empty slideShow}'> style="display:none;" </c:if>
									<c:if test='${not empty slideShow}'> style="display:block;" </c:if>
									>
										<label class="col-sm-3 control-label no-padding-right" for="form-field-2"></label>
										<div class="col-sm-9">
											<img id="slideImage" src="${slideShow.slideImageUrl}" style="width:200px;height:150px;">
										</div>
									</div>
									
									<div id="uploadTip" class="form-group" style="display:none;">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-2">&nbsp;</label>
										<div id="uploadTip-tip" class="col-sm-9" style="color:blue;"></div>
									</div>

									<div class="space-4"></div>

									<div class="clearfix form-actions">
										<div class="col-md-offset-3 col-md-9">
										    <input type="hidden" id="slideImageUrl" name="slideImageUrl" value="${slideShow.slideImageUrl}">
										    <input type="hidden" id="slideShowId" name="slideShowId" value="${slideShow.id}">
											<button class="btn btn-info" type="button" onclick="addSlideShow()">
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
			
			<iframe id="innerIFrame" name="innerIFrame" style="display:none;"></iframe>
			
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
		<script src="${pageContext.request.contextPath}/assets/js/jquery-ui.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/jquery-ui-timepicker-addon.min.js"></script>

		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			jQuery(function($) {
				$("#slideStartTime").datetimepicker({
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
				$("#slideEndTime").datetimepicker({
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
				
				//激活左侧菜单导航
				$("#slideshow").addClass("active open");
				$("#slideshow-slideshow").addClass("active");
			});
			function uploadFile() {
				$("#uploadForm").submit();
			}
			//文件上传后回调该JS函数
			function callback (stat, imgUrl) {
				$("#slideImageUrl").val(imgUrl);
				$("#showSlideImage").show();
				$("#slideImage").attr("src", imgUrl);
				if (stat == '0') {
					$("#uploadTip-tip").html("文件上传成功");
					$("#uploadTip").show();
				}
			}
			//提交保存轮播图
			function addSlideShow() {
				var slideTitle = $("#slideTitle").val();
				var slideUrl = $("#slideUrl").val();
				var slideStartTime = $("#slideStartTime").val();
				var slideEndTime = $("#slideEndTime").val();
				var slideStatus = $("#slideStatus").val();
				var slideImageUrl = $("#slideImageUrl").val();
				var slideShowId = $("#slideShowId").val();
				if (slideTitle == "") {
					alert("请输入轮播图名称");
					return;
				} else if (slideUrl == "") {
					alert("请输入轮播图名称");
					return;
				} else if (slideStartTime == "") {
					alert("请输入轮播图名称");
					return;
				} else if (slideEndTime == "") {
					alert("请输入轮播图名称");
					return;
				} else if (slideStatus == "") {
					alert("请输入轮播图名称");
					return;
				} else if (slideImageUrl == "") {
					alert("请输入轮播图名称");
					return;
				}
				$.ajax({
					url : "${pageContext.request.contextPath}/admin/addSlideShow",
					type : "POST",
					dataType : "json",
					data : {
						"slideTitle" : slideTitle,
						"slideUrl" : slideUrl,
						"slideStartTime" : slideStartTime,
						"slideEndTime" : slideEndTime,
						"slideStatus" : slideStatus,
						"slideImageUrl" : slideImageUrl,
						"slideShowId" : slideShowId
					}, 
					success : function (message) {
						if (message.errorCode == '0') {
							$("#uploadTip-tip").html("轮播图保存成功，3秒后自动跳转到轮播图列表页面");
							$("#uploadTip").show();
							//停留3秒，然后跳转到轮播图列表页面
							setTimeout(toLink, 3000);
						} else {
							//失败
							$("#uploadTip-tip").html("轮播图保存失败");
							$("#uploadTip").show();
						}
					},
					error : function () {
						$("#uploadTip-tip").html("网络错误");
						$("#uploadTip").show();
					}
				})
			}
			function toLink () {
				window.location.href="${pageContext.request.contextPath}/admin/slideShow";
			}
		</script>
	</body>
</html>
