<!DOCTYPE html>
<html lang="en">
<%@ include file="../commonHeader.jsp" %>
<head>
    <style type="text/css">
        table tbody tr.even.row_selected td {
            background-color: #B0BED9 !important;
        }
    </style>
    <!--external css-->
    <title>
        <s:if test="study.id.length() > 0">
            修改方案
        </s:if>
        <s:else>
            添加方案
        </s:else>
    </title>
</head>
<body>

<!--main content start-->
<section class="panel">
    <header class="panel-heading">
        基本信息
    </header>
    <div class="panel-body">
        <%@include file="studyNavigation.jsp"%>
        <form id="studyForm" class="form-horizontal tasi-form" action="study/savewizardView.action" method="post">
            <input name="study.id" type="hidden" value="${study.id}"/>

            <%@ include file="/pages/study/studyAction.jsp"%>
        </form>
    </div>
</section>
</body>
</html>