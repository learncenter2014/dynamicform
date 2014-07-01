<!DOCTYPE html>
<html lang="en">
<%@ include file="../commonHeader.jsp"%>
<head>
    <title>
         模块维护
    </title>
</head>
<body>

<!--main content start-->
<section class="panel">
    <header class="panel-heading">
        模块维护
    </header>
    <div class="panel-body">
        <s:actionerror/><s:actionmessage/>
        <form id="viewForm" class="form-horizontal tasi-form" action="${rootPath}/view/save.action" method="post">
            <input name="view.id" type="hidden" value="${view.id}"/>
            <input name="view.studyId" type="hidden" value="${view.studyId}"/>
            <input name="studyId" type="hidden" value="${view.studyId}"/>
            <div class="form-group">
                <div class="col-lg-offset-2 col-lg-6">
                    <button class="btn btn-info" type="submit">保存</button>

                    <a href="${rootPath}/view/index.action?studyId=${studyId}">
                      <div class="btn btn-info">取消</div>
                    </a>
                </div>
            </div>

        </form>
    </div>
</section>
</body>
</html>