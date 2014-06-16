<%--
  User: peter
  Date: 14-3-16
  Time: 上午10:22
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<style type="text/css">
    .actionMessage{
        margin: 0px;
        padding: 0px;
        list-style: none;
        color: green;
        text-align: center;
    }
    .actionMessage li{
        font-size: 12px;
     }
    .errorMessage{
        margin: 0px;
        padding: 0px;
        list-style: none;
        color: red;
        text-align: center;
    }
    .errorMessage li{
        font-size: 12px;
     }
    .fieldMessage{
        margin: 0px;
        padding: 0px;
        list-style: none;
        color: red;
        text-align: center;
    }
    .fieldMessage li{
        font-size: 12px;
     }
</style>
<div>
    <s:actionerror/>
    <s:actionmessage/>
    <s:fielderror/>
</div>