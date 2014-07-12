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

        var EditableTable = function () {

            return {

                //main function to initiate the module
                init: function (eTable, editors) {
                    function restoreRow(oTable, nRow) {
                        var aData = oTable.fnGetData(nRow);
                        var jqTds = $('>td', nRow);

                        for (var i = 0, iLen = jqTds.length; i < iLen; i++) {
                            oTable.fnUpdate(aData[i], nRow, i, false);
                        }

                        oTable.fnDraw();
                    }

                    function editRow(oTable, nRow) {
                        var aData = oTable.fnGetData(nRow);
                        var jqTds = $('>td', nRow);

                        var index = 1;
                        for(; index < jqTds.length - 1; index ++) {
                            var id = jqTds[index].id;
                            var hiddenObject = $("input[type='hidden'][name='"+id+"']")[0];
                            $("#"+id).html(editors.get(index - 1, hiddenObject.value));//'<input type="text" class="form-control small" value="' + hiddenObject.value + '">';
                        }
                        jqTds[index].innerHTML = '<a class="edit" href="">保存</a>&nbsp;<a class="cancel" href="">取消</a>';
                    }

                    function saveRow(oTable, nRow) {
                        var jqInputs = $('input', nRow);
                        var jqTds = $('>td', nRow);
                        var index = 1;
                        for(; index < jqTds.length - 1; index ++) {
                            var id = jqTds[index].id;
                            var hiddenObject = $("input[type='hidden'][name='"+id+"']")[0];
                            hiddenObject.value = editors.getValue(index - 1)//jqInputs[index - 1].value;
                            oTable.fnUpdate(editors.getDisplayValue(index - 1), nRow, index, false);
                        }
                        oTable.fnUpdate('<a class="edit" href="">编辑</a>', nRow, index, false);
                        oTable.fnDraw();
                    }

                    var oTable = eTable.dataTable({
                        "bFilter": false,
                        "bPaginate": false,
                        "bScrollInfinite": true,
                        "sDom": "<'row'<'col-lg-6'l><'col-lg-6'f>r>t<'row'<'col-lg-6'i><'col-lg-6'p>>",
                        "aoColumnDefs": [{
                            'bSortable': false,
                            'aTargets': [0]
                        }],
                        "oLanguage": {
                            "sProcessing": "正在加载中......",
                            "sLengthMenu": "每页显示 _MENU_ 条记录",
                            "sZeroRecords": "对不起，查询不到相关数据！",
                            "sEmptyTable": "表中无数据存在！",
                            "sInfo": "",//"当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
                            "sInfoFiltered": "", //"数据表中共为 _MAX_ 条记录",
                            "sSearch": "搜索",
                            "oPaginate": {
                                "sFirst": "首页",
                                "sPrevious": "上一页",
                                "sNext": "下一页",
                                "sLast": "末页"
                            }
                        } //多语言配置
                    });

//                        jQuery('#' + tableId + '_wrapper .dataTables_filter input').addClass("form-control medium"); // modify table search input
//                        jQuery('#' + tableId + '_wrapper .dataTables_length select').addClass("form-control xsmall"); // modify table per page dropdown

                    var nEditing = null;

                    eTable.on('click', 'a.cancel', function (e) {
                        e.preventDefault();
                        restoreRow(oTable, nEditing);
                        nEditing = null;
                    });

                    eTable.on('click', 'a.edit', function (e) {
                        e.preventDefault();
                        /* Get the row as a parent of the link that was clicked on */
                        //                    var nRow = $(this)[0];
                        var nRow = $(this).parents('tr')[0];

                        if (nEditing !== null && nEditing != nRow) {
                            /* Currently editing - but not this row - restore the old before continuing to edit mode */
                            restoreRow(oTable, nEditing);
                            editRow(oTable, nRow);
                            nEditing = nRow;
                        } else if (nEditing == nRow && this.innerHTML == "保存") {
                            /* Editing this row and want to save it */
                            saveRow(oTable, nEditing);
                            nEditing = null;
//                                alert("Updated! Do not forget to do some ajax to sync with backend :)");
                        } else {
                            /* No edit in progress - let's start one */
                            editRow(oTable, nRow);
                            nEditing = nRow;
                        }
                    });
                }

            };

        }();

    </script>
    <!--external css-->
    <title>studyInput.jsp</title>
</head>
<body>


<section id="container" >
    <div class="row">
        <div class="col-md-3" style="height: 100%; overflow: auto;">
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
                                            <a href="#<s:property value="#dataBean.view.id" />_<s:property value='#document.id'/>">
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
        <div class="col-md-9" style="height: 100%; overflow: auto;">
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