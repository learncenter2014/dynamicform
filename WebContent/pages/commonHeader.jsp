<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<%-- Listing of all the taglibs that we reference in this application. --%>

<%-- Force the pages to not be cached, so that they are always reloaded. --%>
<%
  response.setHeader ("Cache-Control","no-cache"); //HTTP 1.1
  response.setHeader ("Pragma","no-cache"); //HTTP 1.0
  response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>
