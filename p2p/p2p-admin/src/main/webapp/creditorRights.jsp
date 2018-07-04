<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta charset="utf-8" />
	<title>债权管理 - P2P后台管理系统</title>
	<meta name="description" content="债权管理，P2P后台管理系统" />
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

							<div class="row">
								<div class="col-xs-12">
									<!-- div.table-responsive -->
									<!-- div.dataTables_borderWrap -->
									<div>
										<div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
										<div class="row">
										<div class="col-xs-6">
										<div class="dataTables_length" id="dynamic-table_length">
										<label>
										显示
										<select id="displayPageSize" name="dynamic-table_length" aria-controls="dynamic-table" class="form-control input-sm" onchange="changePageSize()">
											<option value="10" <c:if test="${pageSize eq 10}">selected</c:if>>10</option>
											<option value="15" <c:if test="${pageSize eq 15}">selected</c:if>>15</option>
											<option value="20" <c:if test="${pageSize eq 20}">selected</c:if>>20</option>
											<option value="25" <c:if test="${pageSize eq 25}">selected</c:if>>25</option>
											<option value="30" <c:if test="${pageSize eq 30}">selected</c:if>>30</option>
											<option value="40" <c:if test="${pageSize eq 40}">selected</c:if>>40</option>
											<option value="50" <c:if test="${pageSize eq 50}">selected</c:if>>50</option>
											<option value="100" <c:if test="${pageSize eq 100}">selected</c:if>>100</option>
										</select> 
										条记录
										</label>
										</div>
										</div>
										<div class="col-xs-6">
										<div id="dynamic-table_filter" class="dataTables_filter">
										<label>
										<a class="blue" href="${pageContext.request.contextPath}/admin/toAddCreditorRights">
										<i class="ace-icon fa fa-search-plus bigger-130"></i>录入债权
										</a>
										</label>
										</div>
										</div>
										</div>
										<table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
											<thead>
												<tr role="row">
												<th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">序号</th>
												<th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">借款申请编号</th>
												<th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Price: activate to sort column ascending">进件来源</th>
												<th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">申请借款产品</th>
												<th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Update: activate to sort column ascending">申请借款金额</th>
												<th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Status: activate to sort column ascending">申请借款期限</th>
												<th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Status: activate to sort column ascending">借款人真实姓名</th>
												<th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Status: activate to sort column ascending">借款人性别</th>
												<th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Status: activate to sort column ascending">借款人年龄</th>
												<th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Status: activate to sort column ascending">债权状态</th>
												<th class="sorting_disabled" rowspan="1" colspan="1" aria-label="">操作管理</th>
												</tr>
											</thead>

											<tbody>
											<!-- 债权列表数据start -->
											<c:forEach items="${creditorRightsList}" var="creditorRights" varStatus="index">
											<c:if test="${index.count%2 == '0'}">
										  	<tr role="row" class="odd">
										  	</c:if>
										  	<c:if test="${obj.count%2 != '0'}">
											<tr role="row" class="even">
											</c:if>
													<td class="center">
														<span class="lbl">${index.count}</span>
													</td>
													<td>
														<a href="#">${creditorRights.applyNo}</a>
													</td>
													<td>${creditorRights.intoSource}</td>
													<td class="hidden-480">${creditorRights.applyProduct}</td>
													<td>${creditorRights.applyMoney}</td>
													<td>${creditorRights.applyTerm}</td>
													<td>${creditorRights.borrowerRealname}</td>
													<td class="hidden-480">${creditorRights.borrowerSex}</td>
													<td>${creditorRights.borrowerAge}</td>
													<td class="hidden-480">
														<span class="label label-sm label-warning">
															<c:choose>
															<c:when test="${creditorRights.matchStatus eq 0}">
															等待匹配
															</c:when>
															<c:when test="${creditorRights.matchStatus eq 1}">
															匹配完成
															</c:when>
															<c:otherwise>
															待发布
															</c:otherwise>
															</c:choose>
														</span>
													</td>
													<td>
														<div class="hidden-sm hidden-xs action-buttons">
															<c:if test="${creditorRights.matchStatus ne 0 and creditorRights.matchStatus ne 1}">
															<a class="green" href="javascript:toUpdate(${creditorRights.id})">
																修改
															</a>
															<a class="red" href="javascript:deleteCreditor(${creditorRights.id}, '${creditorRights.matchStatus}')">
																删除
															</a>
															<a class="green" href="javascript:toAddProduct(${creditorRights.id}, 2)">
																发布散标
															</a>
															</c:if>
														</div>
													</td>
												</tr>
												</c:forEach>
											</tbody>
										</table><div class="row"><div class="col-xs-6"><div class="dataTables_info" id="dynamic-table_info" role="status" aria-live="polite">显示 ${startRow+1} 到  ${startRow+pageSize} 共${totalPage}页 ${totalRows} 条</div></div><div class="col-xs-6"><div class="dataTables_paginate paging_simple_numbers" id="dynamic-table_paginate">
										<!-- 分页start -->
										<form id="pageForm" action="${pageContext.request.contextPath}/admin/youxuan" method="post">
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
										<input type="hidden" id="currentPage" name="currentPage" value="${currentPage}"/>
										<input type="hidden" id="pageSize" name="pageSize" value="${pageSize}"/>
										<input type="hidden" id="creditorRightsId" name="creditorRightsId" value=""/>
										<input type="hidden" id="creditorRightsType" name="creditorRightsType" value="${creditorRightsType}"/>
										</form>
										<!-- 分页end -->
										</div>
										</div>
										</div>
										</div>
									</div>
								</div>
							</div>
							<!-- PAGE CONTENT ENDS -->
						</div><!-- /.col -->
					</div>
					
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
	<script type="text/javascript">
		//激活左侧菜单
		var creditorRightsType = "${creditorRightsType}"
		if ("0" == creditorRightsType) {
			$("#rights").addClass("active open");
			$("#rights-zizhu").addClass("active");
		} else if ("1" == creditorRightsType) {
			$("#rights").addClass("active open");
			$("#rights-sanfang").addClass("active");
		}
		function changeDisplayNumber() {
			var checkValue = $("#pageSize").val();//获取Select选择的Value
			window.location.href="${pageContext.request.contextPath}/admin/creditorRights?creditorRightsType=${creditorRightsType}&pageSize="+checkValue;
		}
		//切换分页的条数
		function changePageSize() {
			//获取一下产品类型
			var creditorRightsType = "${creditorRightsType}"
			if ("0" == creditorRightsType) {
				//给提交的action修改一下
				$("#pageForm").attr("action", "${pageContext.request.contextPath}/admin/zizhu");
			} else if ("1" == creditorRightsType) {
				$("#pageForm").attr("action", "${pageContext.request.contextPath}/admin/sanfang");
			} 
			var pageSize = $("#displayPageSize").val();
			$("#pageSize").val(pageSize);
			$("#currentPage").val(1);
			$("#pageForm").submit();
		}
		//分页
		function toPage(currentPage) {
			//获取一下产品类型
			var creditorRightsType = "${creditorRightsType}"
			if ("0" == creditorRightsType) {
				//给提交的action修改一下
				$("#pageForm").attr("action", "${pageContext.request.contextPath}/admin/zizhu");
			} else if ("1" == creditorRightsType) {
				$("#pageForm").attr("action", "${pageContext.request.contextPath}/admin/sanfang");
			}
			$("#currentPage").val(currentPage);
			$("#pageForm").submit();
		}
		function toUpdate (creditorRightsId) {
			$("#creditorRightsId").val(creditorRightsId);
			$("#pageForm").attr("action", "${pageContext.request.contextPath}/admin/toEditCreditorRights");
			$("#pageForm").submit();
		}
		//产品删除
		function deleteCreditor(creditorRightsId, creditorStatus) {
			if (creditorStatus != '' && creditorStatus != -1) {
				//不允许删除
				alert("该债权状态不能删除！");
			} else {
				if (confirm("您确认要删除此数据吗？")) {
					var currentPage = $("#currentPage").val();
					var pageSize = $("#pageSize").val();
					$.ajax({
						url : "${pageContext.request.contextPath}/admin/deleteCreditorRights",
						dataType : "text",
						type : "POST",
						data : {"creditorRightsId" : creditorRightsId},
						success : function (message) {
							if (message == 1) {
								//删除成功
								$("#pageSize").val(pageSize);
								$("#currentPage").val(currentPage);
								//获取一下产品类型
								var creditorRightsType = "${creditorRightsType}"
								if ("0" == creditorRightsType) {
									//给提交的action修改一下
									$("#pageForm").attr("action", "${pageContext.request.contextPath}/admin/zizhu");
								} else if ("1" == productType) {
									$("#pageForm").attr("action", "${pageContext.request.contextPath}/admin/sanfang");
								} 
								$("#pageForm").submit();
							} else {
								alert("债权删除失败！");
							}
						},
						error : function () {
							alert("网络错误");
						}
					});
				}
			}
		}
		//债权发布产品
		function toAddProduct(creditorRightsId, productType) {
			$("#creditorRightsId").val(creditorRightsId);
			$("#pageForm").attr("action", "${pageContext.request.contextPath}/admin/toAddSanbiao");
			$("#pageForm").submit();
		}
	</script>
</body>
</html>