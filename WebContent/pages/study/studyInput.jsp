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
    <title>studyInput.jsp</title>
</head>
<body>


<section id="container" >
    <div class="row">
        <div class="col-md-3">
            <section class="panel">
                <%--<header class="panel-heading">--%>
                    <%--Category--%>
                <%--</header>--%>
                <div class="panel-body">
                    <ul class="nav prod-cat">
                        <s:iterator value="viewBeanList" var="view">
                            <li>
                                <a class="active" href="#view.id"><i class=" fa fa-angle-right"></i>
                                    <s:property value="#view.name" />
                                </a>
                                <ul class="nav">
                                    <s:iterator value="#view.documentBeanList" var="document">
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
            </section>
        </div>
        <div class="col-md-9">
            <section class="panel">
                <%--<header class="panel-heading">--%>
                <%--Category--%>
                <%--</header>--%>
                <div class="panel-body">

                </div>
            </section>
        </div>
</div>
</section>
</body>
</html>