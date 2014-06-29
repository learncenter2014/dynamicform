<!DOCTYPE html>
<html lang="en">
<%@ include file="../commonHeader.jsp" %>
<head>
</head>
<body>

<!--main content start-->
<section class="panel">
    <header class="panel-heading">
    </header>
    <div class="panel-body">
        <%@include file="studyNavigation.jsp"%>
        <form id="studyForm" class="form-horizontal tasi-form" action="${rootPath}/study/savewizardStudy.action" method="post">
            <input name="study.id" type="hidden" value="${study.id}"/>
            <input name="activeWizard" type="hidden" value="${activeWizard}"/>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">方案编码</label>

                <div class="col-lg-6">
                    <input name="study.code" type="text" value="${study.code}" class="form-control"
                           required="required" placeholder="方案代码"/>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">方案名称</label>

                <div class="col-lg-6">
                    <input type="text" placeholder="方案名称" name="study.name" class="form-control"
                           required="required" value="${study.name}"/>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">英文名称</label>

                <div class="col-lg-6">
                    <input type="text" placeholder="英文名称" name="study.englishName" class="form-control"
                           required="required" value="${study.englishName}"/>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">方案简介</label>

                <div class="col-lg-6">
                    <textarea name="study.description" rows="5" cols="60" class="form-control"></textarea>
                </div>
            </div>

            <div class="form-group has-success">
                <label class="col-lg-2 control-label">针对病种</label>

                <div class="col-lg-6">
                    <s:select cssClass="form-control" name="study.diseaseId" list="#{0:'糖尿病',1:'肝癌',2:'脑癌',3:'白血病'}" value="study.diseaseId"/>
                </div>
            </div>

            <%@ include file="/pages/study/studyAction.jsp"%>
        </form>
    </div>
</section>
</body>
</html>