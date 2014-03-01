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
<s:form action="pagesave" namespace="/page">
    <label style="display: none"><s:textfield name="pageBean.name" cssClass="form-control"></s:textfield></label><br>
    <label>名称:<s:textfield name="pageBean.label" cssClass="form-control"></s:textfield></label><br>
    <label>路径:<s:textfield name="pageBean.source" cssClass="form-control"></s:textfield></label><br>
    <label>模版列表:
        <s:iterator value="templateExists" var="te" status="id">
            <s:set value="false" name="found"/>
            <s:iterator value="pageBean.templateList" var="sub">
                <s:if test="#sub.name==#te.name">
                    <s:set value="true" name="found" id="found"/>
                </s:if>
            </s:iterator>
            <s:if test="#found">
                <label style="margin-left: 20px"><s:checkbox name="pageBean.templateList[%{#id.index}].name" value="true"
                                                             fieldValue="%{#te.name}"
                                                             label="%{#te.label}"/>${te.label}</label>
            </s:if>
            <s:else>
                <label style="margin-left: 20px"><s:checkbox name="pageBean.templateList[%{#id.index}].name" value="false"
                                                             fieldValue="%{#te.name}"
                                                             label="%{#te.label}"/>${te.label}</label>
            </s:else>
        </s:iterator>
    </label><br>
    <s:submit value="提交" cssClass="btn btn-info"></s:submit><br>
</s:form>
</body>

</html>