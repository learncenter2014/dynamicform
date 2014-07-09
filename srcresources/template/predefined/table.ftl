<input type="hidden" name="C1_A1" value="C1">
<input type="hidden" name="C2_A1" value="C2">

<table class="table table-striped table-hover table-bordered" id="editable-sample">
    <thead>
    <tr>
        <th>A1</th>
        <th>Edit</th>
    </tr>
    </thead>
    <tbody>
    <tr class="">
        <td id="C1_A1">C1</td>
        <td><a class="edit" href="javascript:;">Edit</a></td>
    </tr>
    <tr class="">
        <td id="C2_A1">C2</td>
        <td><a class="edit" href="javascript:;">Edit</a></td>
    </tr>
    </tbody>
</table>
<script>
    var EditableTable = function () {

        return {

            //main function to initiate the module
            init: function () {
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
                    var id = jqTds[0].id;
                    var hiddenObject = $("input[type='hidden'][name='"+id+"']")[0];
                    console.log(hiddenObject);
                    jqTds[0].innerHTML = '<input type="text" class="form-control small" value="' + hiddenObject.value + '">';
                    jqTds[1].innerHTML = '<a class="edit" href="">Save</a>&nbsp;<a class="cancel" href="">Cancel</a>';;
                }

                function saveRow(oTable, nRow) {
                    var jqInputs = $('input', nRow);
                    var jqTds = $('>td', nRow);
                    var id = jqTds[0].id;
                    var hiddenObject = $("input[type='hidden'][name='"+id+"']")[0];
                    hiddenObject.value = jqInputs[0].value;
                    oTable.fnUpdate(jqInputs[0].value, nRow, 0, false);
                    oTable.fnUpdate('<a class="edit" href="">Edit</a>', nRow, 1, false);
                    oTable.fnDraw();
                }

                function cancelEditRow(oTable, nRow) {
                    var jqInputs = $('input', nRow);
                    oTable.fnUpdate(jqInputs[0].value, nRow, 0, false);
                    oTable.fnUpdate('<a class="edit" href="">Edit</a>', nRow, 1, false);
                    oTable.fnDraw();
                }

                var oTable = $('#editable-sample').dataTable({
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
                        "sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
                        "sInfoFiltered": "数据表中共为 _MAX_ 条记录",
                        "sSearch": "搜索",
                        "oPaginate": {
                            "sFirst": "首页",
                            "sPrevious": "上一页",
                            "sNext": "下一页",
                            "sLast": "末页"
                        }
                    } //多语言配置
                });

                jQuery('#editable-sample_wrapper .dataTables_filter input').addClass("form-control medium"); // modify table search input
                jQuery('#editable-sample_wrapper .dataTables_length select').addClass("form-control xsmall"); // modify table per page dropdown

                var nEditing = null;

                $('#editable-sample').on('click', 'a.cancel', function (e) {
                    e.preventDefault();
                    restoreRow(oTable, nEditing);
                    nEditing = null;
                });

                $('#editable-sample').on('click', 'a.edit', function (e) {
                    e.preventDefault();
                    /* Get the row as a parent of the link that was clicked on */
//                    var nRow = $(this)[0];
                    var nRow = $(this).parents('tr')[0];

                    if (nEditing !== null && nEditing != nRow) {
                        /* Currently editing - but not this row - restore the old before continuing to edit mode */
                        restoreRow(oTable, nEditing);
                        editRow(oTable, nRow);
                        nEditing = nRow;
                    } else if (nEditing == nRow && this.innerHTML == "Save") {
                        /* Editing this row and want to save it */
                        saveRow(oTable, nEditing);
                        nEditing = null;
                        alert("Updated! Do not forget to do some ajax to sync with backend :)");
                    } else {
                        /* No edit in progress - let's start one */
                        editRow(oTable, nRow);
                        nEditing = nRow;
                    }
                });
            }

        };

    }();

    EditableTable.init();

</script>
