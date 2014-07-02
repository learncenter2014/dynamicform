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
        <%@include file="studyNavigation.jsp" %>
        <form id="studyForm" class="form-horizontal tasi-form" action="${rootPath}/study/savewizardPreview.action"
              method="post">
            <input name="study.id" type="hidden" value="${study.id}"/>
            <s:if test="study.state==0">
              <input name="study.state" type="hidden" value="1"/>
            </s:if>
            <s:else>
                <input name="study.state" type="hidden" value="0"/>
            </s:else>
            <input name="activeWizard" type="hidden" value="${activeWizard}"/>
            <%@ include file="/pages/study/studyAction.jsp" %>
            <script>
                <s:if test="study.state==0">
                jQuery("#studyActionSave").text("发布");
                </s:if>
                <s:else>
                jQuery("#studyActionSave").text("草稿");
                </s:else>
            </script>
        </form>
    </div>
</section>
</body>
</html>