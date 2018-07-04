<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="sidebar" class="sidebar responsive ace-save-state">
	<script type="text/javascript">
		try{ace.settings.loadState('sidebar')}catch(e){}
	</script>
	<div class="sidebar-shortcuts" id="sidebar-shortcuts">
		<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
			<button class="btn btn-success">
				<i class="ace-icon fa fa-signal"></i>
			</button>
			<button class="btn btn-info">
				<i class="ace-icon fa fa-pencil"></i>
			</button>

			<button class="btn btn-warning">
				<i class="ace-icon fa fa-users"></i>
			</button>

			<button class="btn btn-danger">
				<i class="ace-icon fa fa-cogs"></i>
			</button>
		</div>

		<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
			<span class="btn btn-success"></span>

			<span class="btn btn-info"></span>

			<span class="btn btn-warning"></span>

			<span class="btn btn-danger"></span>
		</div>
	</div><!-- /.sidebar-shortcuts -->
	
	<ul class="nav nav-list">
		<li class="">
			<a href="index.html">
				<i class="menu-icon fa fa-tachometer"></i>
				<span class="menu-text">管理面板</span>
			</a>
			<b class="arrow"></b>
		</li>
		
		<c:set var="sq" value="${0}"/>
		<c:forEach var="menuPermissionInfo" items="${login_user_info.menuPermissionInfo}" varStatus="index">
		
		<c:if test="${menuPermissionInfo.parentid eq 1}">
		
		<c:set var="sq" value="${sq + 1}"/>
		
		<c:if test="${sq gt 1}">
						</ul>
					<!-- 二级菜单 end -->
				</li>
				<!-- 一级菜单 end -->
			</c:if>
		
		<!-- 一级菜单 start -->
		<li id="product">
			<a href="#" class="dropdown-toggle">
				<c:if test="${sq eq 1}"><i class="menu-icon fa fa-desktop"></i></c:if>
				<c:if test="${sq eq 2}"><i class="menu-icon fa fa-list"></i></c:if>
				<c:if test="${sq eq 3}"><i class="menu-icon fa fa-pencil-square-o"></i></c:if>
				<c:if test="${sq eq 4}"><i class="menu-icon fa fa-list-alt"></i></c:if>
				<c:if test="${sq eq 5}"><i class="menu-icon fa fa-calendar"></i></c:if>
				
				<span class="menu-text">
					${menuPermissionInfo.name}
				</span>
				<b class="arrow fa fa-angle-down"></b>
			</a>
			<b class="arrow"></b>
			
			<!-- 二级菜单 start -->
			<ul class="submenu">
			
		</c:if>
			
				<c:if test="${menuPermissionInfo.parentid ne 1}">
				<li id="product-youxuan">
					<a href="${pageContext.request.contextPath}${menuPermissionInfo.url}">
						<i class="menu-icon fa fa-caret-right"></i>
						${menuPermissionInfo.name}
					</a>
					<b class="arrow"></b>
				</li>
				</c:if>
				
				
		</c:forEach>
				</ul>
			<!-- 二级菜单 end -->
		</li>
		<!-- 一级菜单 end -->
	</ul><!-- /.nav-list -->

	<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
		<i id="sidebar-toggle-icon" class="ace-icon fa fa-angle-double-left ace-save-state" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
	</div>
</div>
