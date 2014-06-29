<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/pages/commonHeader.jsp" %>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="dynamic manager technique">
    <meta name="author" content="LiLimin,GuDong,WangRonghua">
    <meta name="keyword" content="dynamicform,template">
    <title>动态表单管理系统</title>

    <link rel="shortcut icon" href="${rootPath}/jslib/flatlab/img/favicon.png">

    <!-- Bootstrap core CSS -->
    <link href="${rootPath}/jslib/flatlab/css/bootstrap.min.css" rel="stylesheet">
    <link href="${rootPath}/jslib/flatlab/css/bootstrap-reset.css" rel="stylesheet">
    <!--external css-->
    <link href="${rootPath}/jslib/flatlab/assets/font-awesome/css/font-awesome.css" rel="stylesheet" />
    <link href="${rootPath}/jslib/flatlab/assets/advanced-datatable/media/css/demo_page.css" rel="stylesheet" />
    <link href="${rootPath}/jslib/flatlab/assets/advanced-datatable/media/css/demo_table.css" rel="stylesheet" />
    <link href="${rootPath}/jslib/flatlab/assets/data-tables/DT_bootstrap.css" rel="stylesheet" />
    <link href="${rootPath}/jslib/flatlab/assets/advanced-datatable/extras/TableTools/media/css/TableTools.css" rel="stylesheet" />
    <link href="${rootPath}/jslib/flatlab/assets/bootstrap-datepicker/css/datepicker.css" rel="stylesheet" />
    <!-- Custom styles for this template -->
    <link href="${rootPath}/jslib/flatlab/css/style.css" rel="stylesheet">
    <link href="${rootPath}/jslib/flatlab/css/style-responsive.css" rel="stylesheet" />
    <link href="${rootPath}/jslib/jquery-ui-1.10.4.custom/css/start/jquery-ui-1.10.4.custom.min.css" rel="stylesheet" />
    <style type="text/css">
        .ui-dialog {
            z-index: 2000;
        }
        .form-control{
            color: #000000;
        }
    </style>
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 tooltipss and media queries -->
    <!--[if lt IE 9]>
    <script src="${rootPath}/jslib/flatlab/js/html5shiv.js"></script>
    <script src="${rootPath}/jslib/flatlab/js/respond.min.js"></script>
    <![endif]-->

    <!-- js placed at the end of the document so the pages load faster -->
    <script src="${rootPath}/jslib/flatlab/js/jquery.js"></script>
    <script src="${rootPath}/jslib/jquery-ui-1.10.4.custom/js/jquery-ui-1.10.4.custom.min.js"></script>
    <script src="${rootPath}/jslib/flatlab/js/bootstrap.min.js"></script>
    <script class="include" type="text/javascript" src="${rootPath}/jslib/flatlab/js/jquery.dcjqaccordion.2.7.js"></script>
    <script src="${rootPath}/jslib/flatlab/js/jquery.scrollTo.min.js"></script>
    <script src="${rootPath}/jslib/flatlab/js/jquery.nicescroll.js" type="text/javascript"></script>
    <script src="${rootPath}/jslib/flatlab/js/respond.min.js" ></script>

    <!-- js placed at the end of the document so the pages load faster -->
    <script src="${rootPath}/jslib/flatlab/assets/advanced-datatable/media/js/jquery.dataTables.js" type="text/javascript" language="javascript" ></script>
    <script src="${rootPath}/jslib/flatlab/assets/data-tables/DT_bootstrap.js" type="text/javascript" ></script>
    <!--common script for all pages-->
    <script src="${rootPath}/jslib/flatlab/assets/advanced-datatable/extras/TableTools/media/js/ZeroClipboard.js" type="text/javascript" charset="utf-8" ></script>
    <script src="${rootPath}/jslib/flatlab/assets/advanced-datatable/extras/TableTools/media/js/TableTools.js" type="text/javascript" charset="utf-8" ></script>
    <script src="${rootPath}/jslib/flatlab/assets/bootstrap-datepicker/js/bootstrap-datepicker.js" type="text/javascript" charset="utf-8" ></script>
    <decorator:head/>
</head>

<body>

<section id="container" class="panel">
    <!--main content start-->
            <div id="decoratebody" class="panel-body">
                <decorator:body />
            </div>
    <!--main content end-->
</section>
<!--common script for all pages-->
<script src="${rootPath}/jslib/flatlab/js/common-scripts.js"></script>
<div id="dialog_message" title="系统消息区"></div>
</body>
</html>
