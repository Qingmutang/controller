1.修改配置文件就可以运行了.
2.常用命令整理
    var userName = $.trim($("#userName").val());
    url : "${pageContext.request.contextPath}/admin/login"
    前台请求和后台请求的区别就是一个项目路径 ${pageContext.request.contextPath}
    登陆成功后直接跳转:
    window.location.href= "${pageContext.request.contextPath}/admin/profile";
    ${userInfoDetail.staffInfo.sex eq 1 ? '男' : '女'} 
    <fmt:formatDate value="${userInfoDetail.lastlogintime}" pattern="yyyy年MM月dd日 HH:mm:ss"/>
  
  
  <c:choose>
	<c:when test="${productType eq 0}">体验产品</c:when>
	<c:when test="${productType eq 1}">优选产品</c:when>
	<c:otherwise>散标产品</c:otherwise>
  </c:choose>
  
  c:if test="${productType ne 2}"
  <c:forEach items="${loanInfoList}" var="loanInfo" varStatus="index">
  
3.多数据源:
   就是自己定义数据源 SqlSessionTemplate  ,然后指定作用域(包)就可以了
    数据源所需要的数据,从配置文件获取到类中.
    
4.权限管理:
   分为:menu,url,button.
   将权限类化==>影响显示.
   
5.webservice  远程的http服务.
  通过xml传递信息,使用工具生成需要访问的类.
 
  
  
  