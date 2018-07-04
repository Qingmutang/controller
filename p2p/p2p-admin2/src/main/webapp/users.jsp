<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta charset="utf-8" />
	<title>Tables - Ace Admin</title>
	<meta name="description" content="Static &amp; Dynamic Tables" />
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
							<li class="active">用户列表</li>
						</ul><!-- /.breadcrumb -->

					</div>

					<div class="page-content">
						
						<div class="page-header">
							<h1>
								账户管理
								<small>
									<i class="ace-icon fa fa-angle-double-right"></i>
									用户列表
								</small>
							</h1>
						</div><!-- /.page-header -->

						<div>
							<div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer"><div class="row"><div class="col-xs-6"><div class="dataTables_length" id="dynamic-table_length">
							<label>显示 
							<select id="displayPageSize" name="dynamic-table_length" aria-controls="dynamic-table" class="form-control input-sm" onchange="changePageSize()">
								<option value="10" <c:if test="${pageSize eq 10}">selected</c:if>>10</option>
								<option value="15" <c:if test="${pageSize eq 15}">selected</c:if>>15</option>
								<option value="20" <c:if test="${pageSize eq 20}">selected</c:if>>20</option>
								<option value="25" <c:if test="${pageSize eq 25}">selected</c:if>>25</option>
								<option value="30" <c:if test="${pageSize eq 30}">selected</c:if>>30</option>
								<option value="40" <c:if test="${pageSize eq 40}">selected</c:if>>40</option>
								<option value="50" <c:if test="${pageSize eq 50}">selected</c:if>>50</option>
							</select> 条记录
							</label>
							</div>
							</div>
							<div class="col-xs-6">
							<div id="dynamic-table_filter" class="dataTables_filter">
							<label>
							
							<a class="blue" href="${pageContext.request.contextPath}/admin/toAddUser">
							<i class="ace-icon fa fa-search-plus bigger-130"></i>
							添加用户
							</a>
							</label>
							</div>
							</div></div>
							
							<table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
								<thead>
									<tr role="row">
										<th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">序号</th>
										<th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">用户名</th>
										<th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">姓名</th>
										<th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">手机</th>
										<th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">邮箱</th>
										<th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">性别</th>
										<th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">职位</th>
										<th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">员工编号</th>
										<th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">入职时间</th>
										<th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">所在部门</th>
										<th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">操作</th>
									</tr>
								</thead>

								<tbody>
							
								<c:forEach items="${userInfoList}" var="userInfo" varStatus="userStat">
								<tr role="row" class="odd">
										<td class="center">${userStat.count}</td>
										<td>${userInfo.username}</td>
										<td>${userInfo.staffInfo.realname}</td>
										<td>${userInfo.staffInfo.phone}</td>
										<td>${userInfo.staffInfo.email}</td>
										<td>${userInfo.staffInfo.sex eq 1 ? '男' : '女'}</td>
										<td>${userInfo.staffInfo.jobtitle}</td>
										<td>${userInfo.staffInfo.staffno}</td>
										<td><fmt:formatDate value="${userInfo.staffInfo.hiredate}" pattern="yyyy年MM月dd日"/></td>
										<td>${userInfo.orgInfo.orgName}.${userInfo.orgInfo.pOrgName}</td>
										<td>
											<div class="hidden-sm hidden-xs action-buttons">
												<a class="green" href="javascript:toUpdate(${userInfo.id})">
													<i class="ace-icon fa fa-pencil bigger-130"></i>
												</a>
												<a class="red" href="javascript:deleteUser(${userInfo.id})">
													<i class="ace-icon fa fa-trash-o bigger-130"></i>
												</a>
											</div>
											<div class="hidden-md hidden-lg">
												<div class="inline pos-rel">
													<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto">
														<i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
													</button>
													<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
														<li>
															<a href="#" class="tooltip-info" data-rel="tooltip" title="View">
																<span class="blue">
																	<i class="ace-icon fa fa-search-plus bigger-120"></i>
																</span>
															</a>
														</li>
														<li>
															<a href="#" class="tooltip-success" data-rel="tooltip" title="Edit">
																<span class="green">
																	<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																</span>
															</a>
														</li>
														<li>
															<a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">
																<span class="red">
																	<i class="ace-icon fa fa-trash-o bigger-120"></i>
																</span>
															</a>
														</li>
													</ul>
												</div>
											</div>
										</td>
									</tr>
									</c:forEach>
								</tbody>
							</table><div class="row"><div class="col-xs-6">
							<div class="dataTables_info" id="dynamic-table_info" role="status" aria-live="polite">
							显示${startRow + 1}到${startRow + pageSize}条，共${totalPage}页${totalRows}条记录
							</div>
							</div>
							
							<div class="col-xs-6">
							
							<div class="dataTables_paginate paging_simple_numbers" id="dynamic-table_paginate">
							<form id="pageForm" action="${pageContext.request.contextPath}/admin/users" method="post">
							<ul class="pagination">
								<c:choose>
								<c:when test="${currentPage eq 1}">
									<li class="paginate_button previous disabled" aria-controls="dynamic-table" tabindex="0" id="dynamic-table_previous">
									<a href="#">上一页</a>
									</li>
								</c:when>
								<c:otherwise>
									<li class="paginate_button previous" aria-controls="dynamic-table" tabindex="0" id="dynamic-table_previous">
									<a href="javascript:toPage(${currentPage-1})">上一页</a>
									</li>
								</c:otherwise>
								</c:choose>
								
								<c:choose>
									<c:when test="${totalPage le 10}">
										<c:forEach begin="1" end="${totalPage}" var="i">
											<li class="paginate_button <c:if test="${currentPage eq i}">active</c:if>" aria-controls="dynamic-table" tabindex="0">
											<a href="javascript:toPage(${i})">${i}</a>
											</li>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${currentPage lt 10}">
												<c:forEach begin="1" end="10" var="i">
													<li class="paginate_button <c:if test="${currentPage eq i}">active</c:if>" aria-controls="dynamic-table" tabindex="0">
													<a href="javascript:toPage(${i})">${i}</a>
													</li>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${(totalPage-currentPage) ge 5}">
														<c:forEach begin="${currentPage-4}" end="${currentPage + 5}" var="i">
															<li class="paginate_button <c:if test="${currentPage eq i}">active</c:if>" aria-controls="dynamic-table" tabindex="0">
															<a href="javascript:toPage(${i})">${i}</a>
															</li>
														</c:forEach>
													</c:when>
													<c:otherwise>
														<c:forEach begin="${totalPage-9}" end="${totalPage}" var="i">
															<li class="paginate_button <c:if test="${currentPage eq i}">active</c:if>" aria-controls="dynamic-table" tabindex="0">
															<a href="javascript:toPage(${i})">${i}</a>
															</li>
														</c:forEach>
													</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
								
								<c:choose>
								<c:when test="${totalPage eq currentPage}">
									<li class="paginate_button next disabled" aria-controls="dynamic-table" tabindex="0" id="dynamic-table_next">
									<a href="#">下一页</a>
									</li>
								</c:when>
								<c:otherwise>
									<li class="paginate_button next" aria-controls="dynamic-table" tabindex="0" id="dynamic-table_next">
									<a href="javascript:toPage(${currentPage+1})">下一页</a>
									</li>
								</c:otherwise>
								</c:choose>
							</ul>
							<input type="hidden" id="currentPage" name="currentPage" value="${currentPage}">
							<input type="hidden" id="pageSize" name="pageSize" value="${pageSize}">
							<input type="hidden" id="userId" name="userId" value="">
							</form>
							</div>
							
							</div>
							
							</div>
							</div>
						</div>
										
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
		<script src="${pageContext.request.contextPath}/assets/js/jquery.dataTables.min.js"></script>
		<script src="${pageContext.request.contextPath}/assets/js/jquery.dataTables.bootstrap.min.js"></script>
		<script src="${pageContext.request.contextPath}/assets/js/dataTables.buttons.min.js"></script>
		<script src="${pageContext.request.contextPath}/assets/js/buttons.flash.min.js"></script>
		<script src="${pageContext.request.contextPath}/assets/js/buttons.html5.min.js"></script>
		<script src="${pageContext.request.contextPath}/assets/js/buttons.print.min.js"></script>
		<script src="${pageContext.request.contextPath}/assets/js/buttons.colVis.min.js"></script>
		<script src="${pageContext.request.contextPath}/assets/js/dataTables.select.min.js"></script>
		<!-- ace scripts -->
		<script src="${pageContext.request.contextPath}/assets/js/ace-elements.min.js"></script>
		<script src="${pageContext.request.contextPath}/assets/js/ace.min.js"></script>
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			//左侧菜单展示及选中样式
			$("#account").addClass("active open");
			$("#account-account").addClass("active");
			function toPage(currentPage) {
				$("#currentPage").val(currentPage);
				$("#pageForm").submit();
			}
			function changePageSize() {
				var pageSize = $("#displayPageSize").val();
				$("#pageSize").val(pageSize);
				$("#currentPage").val(1);
				$("#pageForm").submit();
			}
			function deleteUser (userId) {
				var currentPage = $("#currentPage").val();
				var pageSize = $("#pageSize").val();
				$.ajax({
					url : "${pageContext.request.contextPath}/admin/delete",
					dataType : "text",
					type : "POST",
					data : {"userId" : userId},
					success : function (message) {
						alert(message);
						if (message == 1) {
							//删除成功
							$("#pageSize").val(pageSize);
							$("#currentPage").val(currentPage);
							$("#pageForm").submit();
						} else {
							alert("用户删除失败！");
						}
					},
					error : function () {
						alert("网络错误");
					}
				});
			}
			function toUpdate (userId) {
				$("#userId").val(userId);
				$("#pageForm").attr("action", "${pageContext.request.contextPath}/admin/toEditUser");
				$("#pageForm").submit();
			}
		</script>
	</body>
</html>
