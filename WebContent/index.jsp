<!DOCTYPE html>
<%@ include file="pages/commonHeader.jsp"%>
<s:if test="#session['sessionUser']==null">
  <jsp:include page="pages/menu_loginout/login.jsp"></jsp:include>
</s:if>
</html>
