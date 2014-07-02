<!DOCTYPE html>
<html lang="en">
<%@ include file="../commonHeader.jsp" %>
<head>
</head>
<body>

<!--main content start-->
<section class="panel">
    <div class="panel-body">
        <%@include file="studyNavigation.jsp"%>
        <form id="studyForm" class="form-horizontal tasi-form" action="${rootPath}/study/savewizardView.action" method="post">
            <input name="study.id" type="hidden" value="${study.id}"/>
            <input name="activeWizard" type="hidden" value="${activeWizard}"/>
            <iframe src="${rootPath}/view/index.action?studyId=${study.id}" width="100%" height="300px" scrolling="auto" frameborder="0"></iframe>
            <%@ include file="/pages/study/studyAction.jsp"%>
            <script>
                jQuery("#studyActionSave").css("display","none");
            </script>
        </form>
    </div>
</section>
</body>
</html>