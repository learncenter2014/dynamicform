<!DOCTYPE html>
<%@ include file="/pages/commonHeader.jsp" %>

<s:iterator value="pageBeans" var="page">
    <s:iterator value="#page.templateList" var="tem">
        <s:include value="/datainput/dataRecordInput.action?dataId=%{#id}&templateId=%{#tem.name}"/>
    </s:iterator>
</s:iterator>
