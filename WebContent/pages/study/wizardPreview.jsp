<!DOCTYPE html>
<html lang="en">
<%@ include file="../commonHeader.jsp" %>
<head>
</head>
<body>

<!--main content start-->
<section class="panel">
    <header class="panel-heading">
        基本信息
    </header>
    <div class="panel-body">
        <%@include file="studyNavigation.jsp"%>
        <form id="studyForm" class="form-horizontal tasi-form" action="${rootPath}/study/savewizardPreview.action" method="post">
            <input name="study.id" type="hidden" value="${study.id}"/>
            <input name="activeWizard" type="hidden" value="${activeWizard}"/>
            <%@ include file="/pages/study/studyAction.jsp"%>
        </form>
    </div>
</section>
</body>
</html>