<%@ page trimDirectiveWhitespaces="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- Listing of all the taglibs that we reference in this application. --%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%-- Force the pages to not be cached, so that they are always reloaded. --%>
<%  response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    if(request.getSession().getAttribute("sessionUser")==null){
        response.sendRedirect(basePath+"pages/menu_loginout/login.jsp");
    }
%>
    <base href="<%=basePath%>">
