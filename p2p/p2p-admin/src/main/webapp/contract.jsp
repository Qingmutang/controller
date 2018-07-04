<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta charset="utf-8" />
	<title>合同管理 - P2P后台管理系统</title>
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

	<jsp:include page="commons/topNav.jsp" />

	<div class="main-container ace-save-state" id="main-container">
		<jsp:include page="commons/leftNav.jsp" />
		
		<div class="main-content">
			<div class="main-content-inner">
				
				<div class="breadcrumbs ace-save-state" id="breadcrumbs">
					<ul class="breadcrumb">
						<li>
							<i class="ace-icon fa fa-home home-icon"></i>
							<a href="${pageContext.request.contextPath}/admin/main">管理面板</a>
						</li>
						<li>
							<a href="javascript:void(0)">合同管理</a>
						</li>
						<li class="active">合同管理</li>
					</ul><!-- /.breadcrumb -->
				</div>

				<div class="page-content">
					<div class="page-header">
						<h1>
							合同管理
							<small>
								<i class="ace-icon fa fa-angle-double-right"></i>
								合同管理
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
										<select id="pageSize" name="dynamic-table_length" aria-controls="dynamic-table" class="form-control input-sm" onchange="changeDisplayNumber()">
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
										
										</div>
										</div>
										<table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
											<thead>
												<tr role="row">
												<th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">序号</th>
												<th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">进件来源</th>
												<th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Price: activate to sort column ascending">申请借款金额</th>
												<th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">借款人真实姓名</th>
												<th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Update: activate to sort column ascending">借款人年龄</th>
												<th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Status: activate to sort column ascending">借款合同</th>
												<th class="sorting_disabled" rowspan="1" colspan="1" aria-label="">操作管理</th>
												</tr>
											</thead>

											<tbody>
											
											<!-- 合同列表数据start -->
											<c:forEach items="${creditorRightsList}" var="creditorRights" varStatus="index">
											<c:if test="${index.count%2 == '0'}">
										  	<tr role="row" class="odd">
										  	</c:if>
										  	<c:if test="${obj.count%2 != '0'}">
											<tr role="row" class="even">
											</c:if>
													<td>
														<span class="lbl">${index.count}</span>
													</td>

													<td>
														<a href="#">${creditorRights.intoSource}</a>
													</td>
													<td>
													<span class="ace-thumbnails clearfix">
															<a href="${creditorRights.applyMoney}" data-rel="colorbox">${creditorRights.applyMoney}</a>
													</span>
													</td>
													<td class="hidden-480">${creditorRights.borrowerRealname}</td>
													<td>${creditorRights.borrowerAge}</td>

													<td class="hidden-480">
														<c:choose>
															<c:when test="${empty creditorRights.loanContractPath}">----</c:when>
															<c:otherwise><a href="${creditorRights.loanContractPath}" target="_blank">下载</a></c:otherwise>
														</c:choose>
													</td>
													<td>
														<div class="hidden-sm hidden-xs action-buttons">
															<a class="red" href="${pageContext.request.contextPath}/admin/generateContract?creditorRightsId=${creditorRights.id}&currentPage=${currentPage}&pageSize=${pageSize}">
																<c:choose>
																	<c:when test="${empty creditorRights.loanContractPath}">生成</c:when>
																	<c:otherwise>重新生成</c:otherwise>
																</c:choose>
															</a>
														</div>
													</td>
												</tr>
												</c:forEach>
											</tbody>
										</table>
										<div class="row">
										<div class="col-xs-6" style="width:45%;">
										<div class="dataTables_info" id="dynamic-table_info" role="status" aria-live="polite">显示 ${startRow+1} 到  ${startRow+pageSize} 共${totalPage}页 ${totalRows} 条</div>
										</div><div class="col-xs-6" style="width:55%;">
										<div class="dataTables_paginate paging_simple_numbers" id="dynamic-table_paginate">
										
										<!-- 分页start -->
										<ul class="pagination">
										<c:choose>
											<c:when test="${currentPage eq 1}">
											<li class="paginate_button previous disabled" aria-controls="dynamic-table" tabindex="0" id="dynamic-table_previous">
												<a href="javascript:void(0)">上一页</a>
											</li>
											</c:when>
											<c:otherwise>
											<li class="paginate_button previous" aria-controls="dynamic-table" tabindex="0" id="dynamic-table_previous">
												<a href="${pageContext.request.contextPath}/admin/slideShow?currentPage=${currentPage-1}&pageSize=${pageSize}">上一页</a>
											</li>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${totalPage le 10}">
												<c:forEach var="i" begin="1" end="${totalPage}" step="1">   
												<li class="paginate_button <c:if test="${currentPage eq i}">active</c:if>" aria-controls="dynamic-table" tabindex="0">
												<a href="${pageContext.request.contextPath}/admin/slideShow?currentPage=${i}&pageSize=${pageSize}">${i}</a>
												</li>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<c:if test="${currentPage lt 10}">
													<c:forEach var="i" begin="1" end="10" step="1">   
													<li class="paginate_button <c:if test="${currentPage eq i}">active</c:if>" aria-controls="dynamic-table" tabindex="0">
													<a href="${pageContext.request.contextPath}/admin/slideShow?currentPage=${i}&pageSize=${pageSize}">${i}</a>
													</li>
													</c:forEach>
												</c:if>
												<c:if test="${currentPage ge 10}">
													<c:if test="${(totalPage-currentPage) gt 5}">
														<c:forEach var="i" begin="${currentPage-4}" end="${currentPage-1}" step="1">   
														<li class="paginate_button <c:if test="${currentPage eq i}">active</c:if>" aria-controls="dynamic-table" tabindex="0">
														<a href="${pageContext.request.contextPath}/admin/slideShow?currentPage=${i}&pageSize=${pageSize}">${i}</a>
														</li>
														</c:forEach>
														<c:forEach var="i" begin="${currentPage}" end="${currentPage+5}" step="1">   
														<li class="paginate_button <c:if test="${currentPage eq i}">active</c:if>" aria-controls="dynamic-table" tabindex="0">
														<a href="${pageContext.request.contextPath}/admin/slideShow?currentPage=${i}&pageSize=${pageSize}">${i}</a>
														</li>
														</c:forEach>
													</c:if>
													<c:if test="${(totalPage-currentPage) le 5}">
														<c:forEach var="i" begin="${currentPage-(10-(totalPage-currentPage)-1)}" end="${currentPage-1}" step="1">   
														<li class="paginate_button <c:if test="${currentPage eq i}">active</c:if>" aria-controls="dynamic-table" tabindex="0">
														<a href="${pageContext.request.contextPath}/admin/slideShow?currentPage=${i}&pageSize=${pageSize}">${i}</a>
														</li>
														</c:forEach>
														<c:forEach var="i" begin="${currentPage}" end="${totalPage}" step="1">   
														<li class="paginate_button <c:if test="${currentPage eq i}">active</c:if>" aria-controls="dynamic-table" tabindex="0">
														<a href="${pageContext.request.contextPath}/admin/slideShow?currentPage=${i}&pageSize=${pageSize}">${i}</a>
														</li>
														</c:forEach>
													</c:if>
												</c:if>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${currentPage eq totalPage}">
											<li class="paginate_button disabled" aria-controls="dynamic-table" tabindex="0" id="dynamic-table_next">
												<a href="javascript:void(0)">下一页</a>
											</li>
											</c:when>
											<c:otherwise>
											<li class="paginate_button next" aria-controls="dynamic-table" tabindex="0" id="dynamic-table_next">
												<a href="${pageContext.request.contextPath}/admin/slideShow?currentPage=${currentPage+1}&pageSize=${pageSize}">下一页</a>
											</li>
											</c:otherwise>
										</c:choose>
										</ul>
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
	
	<!-- page specific plugin scripts -->
	<script src="${pageContext.request.contextPath}/assets/js/jquery.colorbox.min.js"></script>
	
	<!-- ace scripts -->
	<script src="${pageContext.request.contextPath}/assets/js/ace-elements.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets/js/ace.min.js"></script>
	<script type="text/javascript">
		function changeDisplayNumber() {
			var checkValue = $("#pageSize").val();//获取Select选择的Value
			window.location.href="${pageContext.request.contextPath}/admin/contract?pageSize="+checkValue;
		}
		$("#contract").addClass("active open");
		$("#contract-contract").addClass("active");
	</script>
</body>
</html>