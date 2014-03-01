<!DOCTYPE html>
<%@ include file="/pages/commonHeader.jsp" %>
<html>

<head>

    <title>Page Edit</title>

    <script>

    </script>
</head>

<body>

<h4 style="text-align: center;">页面构建</h4>
<s:a action="pageedit.action" namespace="page">增加规则</s:a>
<br>
<hr>
<s:iterator value="pageBeans" var="page">
    <div>
        规则名：<s:property value="%{#page.label}"/>&nbsp;&nbsp;
        <s:a action="pageedit.action" namespace="page"><s:param name="name" value="%{#page.name}"/>编辑</s:a>&nbsp;&nbsp;
        <s:a action="pagedelete.action" namespace="page"><s:param name="name" value="%{#page.name}"/>删除</s:a><br>
        <label>路径：<s:property value="%{#page.source}"/></label><br>
        模版列表:
        <s:iterator value="%{#page.templateList}" var="template">
            <p style="margin-left: 10px;display:inline">
                <s:checkbox value="true" label="%{#template.label}" fieldValue="%{#template.name}" name="%{#template.name}" disabled="true"/>
                <s:property value="%{#template.label}"/>
            </p>
        </s:iterator>
    </div>
    <hr>
</s:iterator>

</body>

</html>