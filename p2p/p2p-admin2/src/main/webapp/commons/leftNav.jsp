<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
			<a href="#" class="dropdown-toggle">
				<i class="menu-icon fa fa-tachometer"></i>
				<span class="menu-text">管理面板</span>
				<b class="arrow fa fa-angle-down"></b>
			</a>

			
			<ul class="submenu">
				<li id="yonghu-info">
					<a href="${pageContext.request.contextPath}/admin/profile">
						<i class="menu-icon fa fa-caret-right"></i>
						用户信息
					</a>
					<b class="arrow"></b>
				</li>
		      </ul>
		</li>

		<li id="product">
			<a href="#" class="dropdown-toggle">
				<i class="menu-icon fa fa-desktop"></i>
				<span class="menu-text">
					产品管理
				</span>
				<b class="arrow fa fa-angle-down"></b>
			</a>
			<b class="arrow"></b>
			<ul class="submenu">
				<li id="product-youxuan">
					<a href="${pageContext.request.contextPath}/admin/youxuan">
						<i class="menu-icon fa fa-caret-right"></i>
						优选产品
					</a>
					<b class="arrow"></b>
				</li>
				<li id="product-sanbiao">
					<a href="${pageContext.request.contextPath}/admin/sanbiao">
						<i class="menu-icon fa fa-caret-right"></i>
						散标产品
					</a>
					<b class="arrow"></b>
				</li>
				<li id="product-tiyan">
					<a href="${pageContext.request.contextPath}/admin/tiyan">
						<i class="menu-icon fa fa-caret-right"></i>
						体验产品
					</a>
					<b class="arrow"></b>
				</li>
			</ul>
		</li>

		<li id="rights">
			<a href="#" class="dropdown-toggle">
				<i class="menu-icon fa fa-list"></i>
				<span class="menu-text">债权管理</span>

				<b class="arrow fa fa-angle-down"></b>
			</a>
			<b class="arrow"></b>
			<ul class="submenu">
				<li id="rights-zizhu">
					<a href="${pageContext.request.contextPath}/admin/zizhu">
						<i class="menu-icon fa fa-caret-right"></i>
						自主债权
					</a>
					<b class="arrow"></b>
				</li>
				<li id="rights-sanfang">
					<a href="${pageContext.request.contextPath}/admin/sanfang">
						<i class="menu-icon fa fa-caret-right"></i>
						三方债权
					</a>
					<b class="arrow"></b>
				</li>
			</ul>
		</li>

		<li class="">
			<a href="#" class="dropdown-toggle">
				<i class="menu-icon fa fa-pencil-square-o"></i>
				<span class="menu-text">合同管理</span>

				<b class="arrow fa fa-angle-down"></b>
			</a>

			<b class="arrow"></b>

			<ul class="submenu">
				<li class="">
					<a href="${pageContext.request.contextPath}/admin/contract">
						<i class="menu-icon fa fa-caret-right"></i>
						合同管理
					</a>

					<b class="arrow"></b>
				</li>
			</ul>
		</li>
		
		<li id="account" class="">
			<a href="#" class="dropdown-toggle">
				<i class="menu-icon fa fa-list-alt"></i>
				<span class="menu-text">账户管理</span>

				<b class="arrow fa fa-angle-down"></b>
			</a>
			<b class="arrow"></b>
			<ul class="submenu">
				<li id="account-account" class="">
					<a href="${pageContext.request.contextPath}/admin/users">
						<i class="menu-icon fa fa-caret-right"></i>
						账户管理
					</a>

					<b class="arrow"></b>
				</li>
			</ul>
		</li>
		
		<li id="slideshow" class="">
			<a href="#" class="dropdown-toggle">
				<i class="menu-icon fa fa-calendar"></i>
				<span class="menu-text">轮播图管理</span>
				<b class="arrow fa fa-angle-down"></b>
			</a>
			<b class="arrow"></b>
			<ul class="submenu">
				<li id="slideshow-slideshow" class="">
					<a href="${pageContext.request.contextPath}/admin/slideShow">
						<i class="menu-icon fa fa-caret-right"></i>
						轮播图管理
					</a>
					<b class="arrow"></b>
				</li>
			</ul>
		</li>
		
	</ul><!-- /.nav-list -->

	<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
		<i id="sidebar-toggle-icon" class="ace-icon fa fa-angle-double-left ace-save-state" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
	</div>
</div>
