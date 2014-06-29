<!DOCTYPE html>
<html lang="en">
<%@ include file="../commonHeader.jsp" %>
<head>
</head>
<body>

<!--main content start-->
<section class="panel">
    <div class="panel-body">
        <a id="top0"></a>
        <br>
        <br>
        <%@include file="studyNavigation.jsp" %>
        <form id="studyForm" class="form-horizontal tasi-form" action="${rootPath}/study/savewizardDocument.action"
              method="post">
            <input name="study.id" type="hidden" value="${study.id}"/>

            <div class="mail-box">
                <div class="sm-side" style="float:left;width:200px;">
                    <span><a href="#top0"><h3>模块选择</h3></a></span>
                    <ul class="inbox-nav">
                        <s:iterator value="documentBeanList" status="dindex">
                            <li>
                                <a href="#D<s:property value='id'/>">
                                    <input type="checkbox" name="savedDocumentBeanList[<s:property value="#dindex.index"/>].documentId" value="<s:property value="id"/>">
                                    <span><s:property value="name"/></span>
                                </a>
                            </li>
                        </s:iterator>
                    </ul>
                </div>
                <div class="inbox-body" style="float:left;margin-left:30px;padding:0px;width:60%">
                    <span><h3>元素选择</h3></span>
                    <s:iterator value="documentBeanList" status="dindex">
                        <section clas="panel">
                            <header class="panel-heading" id="basicInfo">
                                <a id="D<s:property value='id'/>"></a>
                                <s:property value="name"/>
                            </header>
                            <div class="panel-body">
                                <div class="form-group has-success">
                                    <s:iterator value="entryBeanList" status="eindex">
                                            <div class="col-lg-2">
                                                <input type="checkbox" name="savedDocumentBeanList[<s:property value="#dindex.index"/>].entryId" value="<s:property value="id"/>">
                                                <span><s:property value="code"/></span> <span><s:property value="name"/></span>
                                            </div>
                                    </s:iterator>
                                </div>
                            </div>
                        </section>
                    </s:iterator>
                </div>
            </div>
            <br>
            <br>
            <%@ include file="/pages/study/studyAction.jsp" %>
        </form>
    </div>
</section>
</body>
</html>