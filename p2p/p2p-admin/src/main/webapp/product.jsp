<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta charset="utf-8" />
	<title>产品管理 - P2P后台管理系统</title>
	<meta name="description" content="产品管理，P2P后台管理系统" />
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
	<!-- 导入头部导航条start -->
	<jsp:include page="commons/topNav.jsp" />
	<!-- 导入头部导航条start -->
	
	<!-- 页面主体内容start -->
	<div class="main-container ace-save-state" id="main-container">
		
		<!-- 导入左侧导航栏start -->
		<jsp:include page="commons/leftNav.jsp" />
		<!-- 导入左侧导航栏end -->
		
		<!-- 页面右侧主体内容start -->
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
										<c:if test="${productType ne 2}">
										<div class="col-xs-6">
										<div id="dynamic-table_filter" class="dataTables_filter">
										<label>
										<c:if test="${productType eq 0}"> 
											<c:set var="href" value="/admin/toAddTiyan" />
										</c:if>
										<c:if test="${productType eq 1}"> 
											<c:set var="href" value="/admin/toAddYouxuan"/>
										</c:if>
										<c:if test="${productType eq 2}"> 
											<c:set var="href" value="/admin/toAddSanbiao"/>
										</c:if>
										<a class="blue" href="${pageContext.request.contextPath}${href}">							
										<i class="ace-icon fa fa-search-plus bigger-130"></i>新增产品
										</a>
										</label>
										</div>
										</div>
										</c:if>
										</div>
										<table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
											<thead>
												<tr role="row">
												<th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">序号</th>
												<th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">产品名称</th>
												<th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Price: activate to sort column ascending">产品利率</th>
												<th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">产品期限</th>
												<th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Update: activate to sort column ascending">产品金额</th>
												<th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Status: activate to sort column ascending">产品状态</th>
												<th class="sorting_disabled" rowspan="1" colspan="1" aria-label="">操作管理</th>
												</tr>
											</thead>

											<tbody>
											
											<!-- 产品列表数据start -->
											<c:forEach items="${loanInfoList}" var="loanInfo" varStatus="index">
											<c:if test="${index.count%2 == '0'}">
										  	<tr role="row" class="odd">
										  	</c:if>
										  	<c:if test="${obj.count%2 != '0'}">
											<tr role="row" class="even">
											</c:if>
													<td><span class="lbl">${index.count}</span></td>
													<td><a href="#">${loanInfo.productName}</a></td>
													<td>${loanInfo.rate} %</td>
													<td class="hidden-480">${loanInfo.cycle} 个月</td>
													<td>${loanInfo.productMoney} 元</td>

													<td class="hidden-480">
														<span class="label label-sm label-warning">
															<c:choose>
															<c:when test="${loanInfo.productStatus eq -1}">
															未发布
															</c:when>
															<c:when test="${loanInfo.productStatus eq 0}">
															募集中
															</c:when>
															<c:when test="${loanInfo.productStatus eq 1}">
															募集完成
															</c:when>
															<c:otherwise>
															满标已生成收益计划
															</c:otherwise>
															</c:choose>
														</span>
													</td>
													<td>
														<div class="hidden-sm hidden-xs action-buttons">
															<a class="green" href="javascript:toUpdate(${loanInfo.id})">
																<i class="ace-icon fa fa-pencil bigger-130"></i>
															</a>
															<a class="red" id="bootbox-confirm" href="javascript:deleteProduct(${loanInfo.id}, ${loanInfo.productStatus})">
																<i class="ace-icon fa fa-trash-o bigger-130"></i>
															</a>
														</div>
													</td>
												</tr>
												</c:forEach>
											</tbody>
										</table>
										<div class="row">
										<div class="col-xs-6">
										<div class="dataTables_info" id="dynamic-table_info" role="status" aria-live="polite">显示 ${startRow+1} 到  ${startRow+pageSize} 共${totalPage}页 ${totalRows} 条</div>
										</div><div class="col-xs-6">
										<div class="dataTables_paginate paging_simple_numbers" id="dynamic-table_paginate">
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
										<input type="hidden" id="loanId" name="loanId" value=""/>
										<input type="hidden" id="productType" name="productType" value="${productType}"/>
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
		<!-- 页面右侧主体内容end -->
		
		<!-- 导入页脚start -->
		<jsp:include page="commons/footer.jsp" />
		<!-- 导入页脚end -->
	</div><!-- /.main-container -->
	<!-- 页面主体内容end -->
	
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
		//左侧菜单激活
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
		//分页
		function toPage(currentPage) {
			//获取一下产品类型
			var productType = "${productType}"
			if ("0" == productType) {
				//给提交的action修改一下
				$("#pageForm").attr("action", "${pageContext.request.contextPath}/admin/tiyan");
			} else if ("1" == productType) {
				$("#pageForm").attr("action", "${pageContext.request.contextPath}/admin/youxuan");
			} else if ("2" == productType) {
				$("#pageForm").attr("action", "${pageContext.request.contextPath}/admin/sanbiao");
			}
			$("#currentPage").val(currentPage);
			$("#pageForm").submit();
		}
		//切换分页的条数
		function changePageSize() {
			//获取一下产品类型
			var productType = "${productType}"
			if ("0" == productType) {
				//给提交的action修改一下
				$("#pageForm").attr("action", "${pageContext.request.contextPath}/admin/tiyan");
			} else if ("1" == productType) {
				$("#pageForm").attr("action", "${pageContext.request.contextPath}/admin/youxuan");
			} else if ("2" == productType) {
				$("#pageForm").attr("action", "${pageContext.request.contextPath}/admin/sanbiao");
			}
			var pageSize = $("#displayPageSize").val();
			$("#pageSize").val(pageSize);
			$("#currentPage").val(1);
			$("#pageForm").submit();
		}
		function toUpdate (loanId) {
			$("#loanId").val(loanId);
			$("#pageForm").attr("action", "${pageContext.request.contextPath}/admin/toEditProduct");
			$("#pageForm").submit();
		}
		//产品删除
		function deleteProduct(id, productStatus) {
			if (productStatus != -1) {
				//不允许删除
				alert("该产品状态不能删除！");
			} else {
				if (confirm("您确认要删除此数据吗？")) {
					var currentPage = $("#currentPage").val();
					var pageSize = $("#pageSize").val();
					$.ajax({
						url : "${pageContext.request.contextPath}/admin/deleteProduct",
						dataType : "text",
						type : "POST",
						data : {"loanId" : id},
						success : function (message) {
							if (message == 1) {
								//删除成功
								$("#pageSize").val(pageSize);
								$("#currentPage").val(currentPage);
								//获取一下产品类型
								var productType = "${productType}"
								if ("0" == productType) {
									//给提交的action修改一下
									$("#pageForm").attr("action", "${pageContext.request.contextPath}/admin/tiyan");
								} else if ("1" == productType) {
									$("#pageForm").attr("action", "${pageContext.request.contextPath}/admin/youxuan");
								} else if ("2" == productType) {
									$("#pageForm").attr("action", "${pageContext.request.contextPath}/admin/sanbiao");
								}
								$("#pageForm").submit();
							} else {
								alert("产品删除失败！");
							}
						},
						error : function () {
							alert("网络错误");
						}
					});
				}
			}
		}
	</script>
</body>
</html>
