<!DOCTYPE html>
<html lang="en">
<%@ include file="../commonHeader.jsp" %>
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
        <form id="viewForm" class="form-horizontal tasi-form" action="${rootPath}/view/saveDocumentList.action"
              method="post">
            <input name="view.id" type="hidden" value="${view.id}"/>
            <input name="view.studyId" type="hidden" value="${view.studyId}"/>
            <input name="studyId" type="hidden" value="${view.studyId}"/>

            <div class="form-group" style="text-align: center">
                <s:iterator value="studySelectedDocuments">
                    <div class="form-group has-success">
                        <s:if test="view.viewDocumentBeanList!=null && view.viewDocumentBeanList.{#this.documentId==id}">
                            <div class="col-lg-2">
                                <input type="checkbox" checked value="<s:property value="id"/>"
                                       name="viewDocumentList">
                            </div>
                        </s:if>
                        <s:else>
                            <div class="col-lg-2">
                                <input type="checkbox" value="<s:property value="id"/>"
                                       name="viewDocumentList">
                            </div>
                        </s:else>
                        <div class="col-lg-4"><s:property value="name"/></div>
                        <div class="col-lg-2"><s:property value="entryBeanList.size"/></div>
                    </div>
                </s:iterator>
            </div>
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