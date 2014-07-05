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

        function handleDataInput(recordId, tableName) {
            var actionUrl = "${rootPath}/dataInput/dataRecordInput.action?recordId=" + recordId + "&tableName=" + tableName;
            htmlobj = $.ajax({url:actionUrl,async:false});
            $("#contentDiv").html(htmlobj.responseText);
        }

        function handleViewSelect() {
            var actionUrl = "${rootPath}/dataInput/inputViewSelect.action?" +
                    "studyId=<s:property value="studyId"/>&participantId=<s:property value="participantId"/>";
            htmlobj = $.ajax({url:actionUrl,async:false});
            $("#contentDiv").html(htmlobj.responseText);
        }

        function display(content) {
            $("#contentDiv").html(content);
        }
    </script>
    <!--external css-->
    <title>studyInput.jsp</title>
</head>
<body>


<section id="container" >
    <div class="row">
        <div class="col-md-3">
            <div class="panel-group m-bot20">
                <div class="panel panel-default">
                    <ul class="nav prod-cat">
                        <s:iterator value="dynamicDataBeanList" var="dataBean">
                            <li>
                                <a href="#" onclick="handleDataInput('<s:property value="#dataBean.dataRecordId"/>',
                                        '<s:property value="#dataBean.tableName"/>')"><i class=" fa fa-angle-right"></i>
                                    <s:property value="#dataBean.view.name" />
                                </a>
                                <ul class="nav">
                                    <s:iterator value="#dataBean.view.documentBeanList" var="document">
                                        <li>
                                            <a href="#<s:property value='#document.id'/>">
                                                <s:property value='#document.name'/>
                                            </a>
                                        </li>
                                    </s:iterator>
                                </ul>
                            </li>
                        </s:iterator>
                    </ul>
                </div>
                <div class="panel panel-default">
                    <div class="panel-body">
                        <button class="btn btn-info">计划</button>
                        <button class="btn btn-info" onclick="handleViewSelect()">添加</button>

                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-9">
            <section class="panel">
                <%--<header class="panel-heading">--%>
                <%--Category--%>
                <%--</header>--%>
                <div id="contentDiv" class="panel-body">

                </div>
            </section>
        </div>
</div>
</section>
</body>
</html>