<!DOCTYPE html>
<html lang="en">
<%@ include file="../commonHeader.jsp"%>
<head>
    <title>
        批量导入实体
    </title>
</head>
<body>

<!--main content start-->
<section class="panel" style="padding-left: 15px;">
    <header class="panel-heading">
        批量导入实体  <span style="margin-left:100px"><a href="${rootPath}/pages/entry/entry_import.xls">导出模板文件</a></span>
    </header>
    <%@include file="../strutsMessage.jsp"%>
    <form id="excelfile" class="form-horizontal tasi-form" action="${rootPath}/entry/batchimportsave.action" method="post" enctype="multipart/form-data">
        <div class="form-group has-error">
            <label for="myFile" class="control-label col-lg-2">请选择Excel文件</label>
            <div class="col-lg-10">
                <input type="hidden" name="documentId" value="${documentId}"/>
                <input class="form-control " id="myFile" name="myFile" type="file" placeholder="请选择Excel文件"  required="required"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-lg-offset-2 col-lg-10">
                <button class="btn btn-info" type="submit">导入</button>
                <button class="btn btn-info" type="button" onclick="window.location.href='${rootPath}/entry/entry/index.action?documentId=${documentId}'"/>取消</button>
            </div>
        </div>
    </form>
</body>
</html>