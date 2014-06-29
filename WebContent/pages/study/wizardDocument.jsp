<!DOCTYPE html>
<html lang="en">
<%@ include file="../commonHeader.jsp" %>
<head>
</head>
<body>

<!--main content start-->
<section class="panel">
    <div class="panel-body">
        <a id="position0">我是位置0</a>
        <br>
        <br>
        <%@include file="studyNavigation.jsp" %>
        <form id="studyForm" class="form-horizontal tasi-form" action="${rootPath}/study/savewizardDocument.action" method="post">
            <input name="study.id" type="hidden" value="${study.id}"/>

            <div class="mail-box">
                <aside class="sm-side" style="position:fixed;width:200px">
                    <span><a href="#position0"><h3>模块选择</h3></a></span>
                    <ul class="inbox-nav inbox-divider">
                        <li>
                            <a href="#position2"><i class="fa fa-envelope-o"></i> 人口统计学</a>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-bookmark-o"></i> 检查项目</a>
                        </li>
                        <li>
                            <a href="#"><i class=" fa fa-external-link"></i> 血常规</a>
                        </li>
                        <li>
                            <a href="#"><i class=" fa fa-trash-o"></i> 尿常规</a>
                        </li>
                    </ul>
                </aside>
                <div class="inbox-body" style="margin-left:200px">
                    <section clas="panel">
                        <header class="panel-heading" id="basicInfo">
                            基本信息
                        </header>
                        <div class="panel-body">
                            <div class="form-group">
                                <div class="row m-bot15">

                                    <div class="col-lg-3 col-md-3">
                                        <label for="code">个人健康档案号</label>
                                        <input name="code" class="form-control input-sm" id="code" type="text"
                                               maxlength="30" readonly="readonly" placeholder="编号" value="025-00019"
                                               data-mask="999-99999">
                                    </div>

                                    <div class="col-lg-3 col-md-3">
                                        <label for="createdUnitName">建档单位</label>
                                        <input name="createdUnitName" class="form-control input-sm" id="createdUnitName"
                                               type="text" maxlength="30" readonly="readonly" placeholder="建档单位"
                                               value="江宁社区医院">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>
                    <div style="height:1200px"></div>
                    <section clas="panel">
                        <a id="position2">我是位置2</a>
                    </section>
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