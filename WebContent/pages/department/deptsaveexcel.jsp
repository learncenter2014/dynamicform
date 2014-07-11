<!DOCTYPE html>
<html lang="en">
<%@ include file="../commonHeader.jsp" %>
<head>
    <title>
        批量导入实体
    </title>
</head>
<body>

<!--main content start-->
<section class="panel" style="padding-left: 15px;">
    <header class="panel-heading">
        <label class="form-group has-success">批量导入实体(${counters})个成功！</label>
    </header>
    <%@include file="../strutsMessage.jsp" %>
    <div class="form-group">
        <div class="col-lg-offset-2 col-lg-10">
            <button class="btn btn-info" type="button" onclick="window.location.href='${rootPath}/department/batchimportdept.action'"/>
            继续导入</button>
            <button class="btn btn-info" type="button" onclick="window.location.href='${rootPath}/department/index.action'"/>
            取消</button>
        </div>
    </div>
</body>
</html>