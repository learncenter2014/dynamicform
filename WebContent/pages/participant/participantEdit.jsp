<!DOCTYPE html>
<html lang="en">
<%@ include file="../commonHeader.jsp"%>
<head>
    <style type="text/css">
        table tbody tr.even.row_selected td{
            background-color: #B0BED9 !important;
        }
    </style>

    <script type="text/javascript">
        function handleCancel() {
            window.location.href = "patient/index.action"
        }
    </script>
    <!--external css-->
    <title>Participant Edit</title>
</head>
<body>

    <section class="panel">
        <header class="panel-heading">
            随访注册
        </header>
        <div class="panel-body">
            <div class="stepy-tab">
                <ul id="default-titles" class="stepy-titles clearfix">
                    <li id="default-title-0" class="current-step">
                        <div>第一步</div>
                    </li>
                    <li id="default-title-1" class="">
                        <div>第二步</div>
                    </li>
                    <li id="default-title-2" class="">
                        <div>第三步</div>
                    </li>
                </ul>
            </div>
            <form class="form-horizontal" id="default" action="${rootPath}/participant/registerSubmit.action" method="post">
                <s:hidden name="patientId" />
                <fieldset title="Step1" class="step" id="default-step-0">
                    <legend> </legend>
                    <div class="form-group">
                        <label class="col-lg-2 control-label">方案选择</label>
                        <div class="col-lg-10">
                            <s:select list="studyList" cssClass="form-control m-bot15"  listKey="id" listValue="name"
                                      name="participant.studyId" value="#participant.studyId"/>
                        </div>
                    </div>
                    <%--<div class="form-group">--%>
                        <%--<label class="col-lg-2 control-label">Email Address</label>--%>
                        <%--<div class="col-lg-10">--%>
                            <%--<input type="text" class="form-control" placeholder="Email Address">--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="form-group">--%>
                        <%--<label class="col-lg-2 control-label">User Name</label>--%>
                        <%--<div class="col-lg-10">--%>
                            <%--<input type="text" class="form-control" placeholder="Username">--%>
                        <%--</div>--%>
                    <%--</div>--%>

                </fieldset>
                <fieldset title="Step 2" class="step" id="default-step-1" >
                    <legend> </legend>
                    <div class="form-group">
                        <label class="col-lg-2 control-label">基线时间</label>
                        <div class="col-lg-10">
                            <s:select list="#{0:'出院时间',1:'门诊时间',2:'手术时间'}" cssClass="form-control m-bot15"
                                      name="participant.baseTime" value="participant.baseTime"/>
                        </div>
                    </div>
                    <%--<div class="form-group">--%>
                        <%--<label class="col-lg-2 control-label">Mobile</label>--%>
                        <%--<div class="col-lg-10">--%>
                            <%--<input type="text" class="form-control" placeholder="Mobile">--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="form-group">--%>
                        <%--<label class="col-lg-2 control-label">Address</label>--%>
                        <%--<div class="col-lg-10">--%>
                            <%--<textarea class="form-control" cols="60" rows="5"></textarea>--%>
                        <%--</div>--%>
                    <%--</div>--%>

                </fieldset>
                <fieldset title="Step 3" class="step" id="default-step-2" >
                    <legend> </legend>
                    <div class="form-group">
                        <label class="col-lg-2 control-label">对象编码</label>
                        <div class="col-lg-10">
                            <input type="text" class="form-control" name="participant.participantCode" placeholder="Mobile">
                        </div>
                    </div>

                </fieldset>
                <input type="submit" class="finish btn btn-danger" value="保存"/>
            </form>
        </div>
    </section>

    <script src="${rootPath}/jslib/flatlab/js/jquery.stepy.js"></script>


    <script>

        //step wizard

        $(function() {
            $('#default').stepy({
                backLabel: '上一页',
                block: true,
                nextLabel: '下一页',
                titleClick: true,
                titleTarget: '.stepy-tab'
            });
        });
    </script>
</body>
</html>
