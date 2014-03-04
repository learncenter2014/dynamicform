<!DOCTYPE html>
<html lang="en">
<%@ include file="../commonHeader.jsp"%>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="Mosaddek">
    <meta name="keyword" content="FlatLab, Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">
    <link rel="shortcut icon" href="img/favicon.png">

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
    <title>Patient Edit</title>
</head>
<body>

<!--main content start-->
<section class="panel">
    <header class="panel-heading">
        Patient Edit
    </header>
    <form role="form" class="form-horizontal tasi-form" action="<%=request.getContextPath() %>/patient/save.action">

        <input name="patient.id" type="hidden" value="${patient.id}"/>
        <input name="patient.name" type="hidden" value="${patient.name}"/>

        <div class="form-group has-success">
            <label class="col-lg-2 control-label">患者编号</label>
            <div class="col-lg-10">
                <input type="text" placeholder="" id="f-key" name="patient.patientKey" class="form-control" required="required" value="${patient.patientKey}">
                <p class="help-block">请输入患者编号！</p>
            </div>
        </div>
        <div class="form-group has-success">
            <label class="col-lg-2 control-label">患者姓名</label>
            <div class="col-lg-10">
                <input type="text" placeholder="" id="f-name" name="patient.patientName" class="form-control" required="required" value="${patient.patientName}">
                <p class="help-block">请输入患者姓名！</p>
            </div>
        </div>
        <div class="form-group has-error">
            <label class="col-lg-2 control-label">性别</label>
            <div class="col-lg-10">
                <select class="form-control m-bot15" name="patient.sex">
                    <option value="1"
                    <s:if test="patient.sex==1">
                        selected="selected"
                    </s:if>
                    >Male</option>
                    <option value="2"
                    <s:if test="patient.sex==2">
                        selected="selected"
                    </s:if>
                    >Female</option>
                </select>
                <p class="help-block">请输入性别！</p>
            </div>
        </div>

        <div class="form-group has-warning">
            <label class="col-lg-2 control-label">出生日期</label>
            <div class="col-lg-10">
                <input id="birthday" placeholder="" name="patient.birthday"  class="form-control" value="<s:date name='patient.birthday' format='MM/dd/yyyy'/>">
                <script>
                    $("#birthday").datepicker();
                </script>
                <p class="help-block">请输入出生日期！</p>
            </div>
        </div>

        <div class="form-group has-warning">
            <label class="col-lg-2 control-label">手机号码</label>
            <div class="col-lg-10">
                <input  placeholder="" name="patient.cellphone"  class="form-control" value="${patient.cellphone}">
                <p class="help-block">请输入手机号码！</p>
            </div>
        </div>

        <div class="form-group">
            <div class="col-lg-offset-2 col-lg-10">
                <button class="btn btn-danger" type="submit">提交</button>
                <button class="btn btn-danger" onclick="handleCancel()">取消</button>
            </div>
        </div>
    </form>
</section>
<!--script for this page-->
<script type="text/javascript" src="<%=request.getContextPath() %>/jslib/flatlab/js/jquery.validate.min.js"></script>
<script src="<%=request.getContextPath() %>/jslib/flatlab/js/form-validation-script.js"></script>
</body>
</html>