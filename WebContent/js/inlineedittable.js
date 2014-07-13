/**
 * Created by pli on 14-7-12.
 * 实现内联的JS编辑器，提供高效的UI組件化
 * init参数说明：
 * tableId: xxx 针对哪个id结点
 * 支持文本框和下拉框
 * headerTypes:[{type:'text'},{type:'select',sourceList:[['label','value']]}]
 *
 */
(function ($) {
    $.editTable = {
        //main function to initiate the module
        init: function (initData) {
            if (typeof initData == 'undefined') {
                throw new Error("请传递合适的初始化参数");
            }
            var tableId = initData.tableId;
            var headers = initData.headerTypes;
            var editHtml = '<button title="编辑" style="margin-left:5px" class="edit btn btn-primary btn-xs"><i class="fa fa-pencil"></i></button>';
            var saveHtml = '<button title="保存" style="margin-left:5px" class="save btn btn-primary btn-xs"><i class="fa fa-save"></i></button>';
            var deleteHtml = '<button title="删除" style="margin-left:5px" class="delete btn btn-danger btn-xs"><i class="fa fa-trash-o"></i></button>';
            var cancelHtml = '<button title="取消" style="margin-left:5px" class="cancel btn btn-danger btn-xs"><i class="fa fa-undo"></i></button>';

            function restoreRow(oTable, nRow) {
                var aData = oTable.fnGetData(nRow);

                for (var i = 0, iLen = headers.length; i < iLen; i++) {
                    oTable.fnUpdate(aData[headers[i].mData], nRow, i, false);
                }

                oTable.fnDraw();
            }

            function editRow(oTable, nRow) {
                var aData = oTable.fnGetData(nRow);
                var jqTds = $('>td', nRow);
                for (var i = 0; i < headers.length; i++) {
                    if (headers[i].type == 'text') {
                        jqTds[i].innerHTML = '<input type="text" class="form-control small" value="' + aData[headers[i].mData] + '">';
                    } else if (headers[i].type == 'select') {
                        var createSelected = '<select class="form-control small" value="' + aData[headers[i].mData] + '">';
                        for (var j = 0; j < headers[i].sourceList.length; j++) {
                            createSelected += '<option value="' + headers[i].sourceList[j][1] + '">' + headers[i].sourceList[j][0] + '</option>';
                        }
                        createSelected += '</select>';
                        jqTds[i].innerHTML = createSelected;
                    }
                }
                jqTds[headers.length].innerHTML = saveHtml;
                jqTds[headers.length].innerHTML += cancelHtml;
            }

            function saveRow(oTable, nRow) {
                var jqInputs = $('input,select', nRow);
                for (var i = 0; i < headers.length; i++) {
                    oTable.fnUpdate(jqInputs[i].value, nRow, i, false);
                }
                oTable.fnDraw();
            }

            function cancelEditRow(oTable, nRow) {
                var jqInputs = $('input', nRow);
                for (var i = 0; i < headers.length; i++) {
                    oTable.fnUpdate(jqInputs[i].value, nRow, i, false);
                }
                oTable.fnDraw();
            }

            var operations = this.options;
            var oTable = $('#' + tableId).dataTable({
                // set the initial value
                "bPaginate": false,
                "sDom": "<'row'<'col-lg-6'l><'col-lg-6'f>r>t<'row'<'col-lg-6'i><'col-lg-6'p>>",
                "sPaginationType": "bootstrap",
                "aoColumns": headers,
                "bServerSide": false,
                "oLanguage": {
                    "oPaginate": {
                        "sPrevious": "上一页",
                        "sNext": "下一页",
                        "sLast": "末页",
                        "sFirst": "首页"
                    },
                    sProcessing: '正在努力加载，请稍后！',
                    "sLengthMenu": "每页显示 _MENU_ 条",
                    "sZeroRecords": "无数据",
                    "sInfo": "显示第 _START_ 到 _END_ 条, 共 _TOTAL_ 条.",
                    "sInfoEmpty": "无数据",
                    "sInfoFiltered": "(filtered from _MAX_ total records)"
                },
                "fnDrawCallback": function (oSettings) {
                    if ($('#' + tableId + ' thead tr th:last[arias="options"]').length == 0) {
                        $('#' + tableId + ' thead tr').each(function () {
                            var thObj = document.createElement('th');
                            thObj.setAttribute("arias", "options");
                            thObj.innerHTML = "操作";
                            $(this).append(thObj);
                            $(thObj).css({width: '120px'});
                        });
                    }
                    $('#' + tableId + ' tbody tr').each(function (ref) {
                        if (this.children.length > headers.length) {
                            this.children[headers.length].innerHTML = editHtml + deleteHtml;
                        } else {
                            var nCloneTd = document.createElement('td');
                            $(this).append(nCloneTd);
                            $(nCloneTd).append(editHtml + deleteHtml);
                        }
                    });
                }
            });

            var nEditing = null;

            $('#' + tableId + '_new').on('click', function (e) {
                e.preventDefault();
                var headers = initData.headerTypes;
                var arrays = {};
                for (var i = 0; i < headers.length; i++) {
                    arrays[headers[i].mData] = '';
                }
                var aiNew = oTable.fnAddData(arrays);
                var nRow = oTable.fnGetNodes(aiNew[0]);
                editRow(oTable, nRow);
                nEditing = nRow;
            });

            $('#' + tableId).on('click', 'button.delete', function (e) {
                e.preventDefault();

                if (confirm("Are you sure to delete this row ?") == false) {
                    return;
                }

                var nRow = $(this).parents('tr')[0];
                oTable.fnDeleteRow(nRow);
            });

            $('#' + tableId).on('click', 'button.cancel', function (e) {
                e.preventDefault();
                if ($(this).attr("data-mode") == "new") {
                    var nRow = $(this).parents('tr')[0];
                    oTable.fnDeleteRow(nRow);
                } else {
                    restoreRow(oTable, nEditing);
                    nEditing = null;
                }
            });

            $('#' + tableId).on('click', 'button.edit,button.save', function (e) {
                e.preventDefault();

                /* Get the row as a parent of the link that was clicked on */
                var nRow = $(this).parents('tr')[0];

                if (nEditing !== null && nEditing != nRow) {
                    /* Currently editing - but not this row - restore the old before continuing to edit mode */
                    restoreRow(oTable, nEditing);
                    editRow(oTable, nRow);
                    nEditing = nRow;
                } else if (nEditing == nRow && jQuery(this).hasClass('save')) {
                    /* Editing this row and want to save it */
                    saveRow(oTable, nEditing);
                    nEditing = null;
                } else {
                    /* No edit in progress - let's start one */
                    editRow(oTable, nRow);
                    nEditing = nRow;
                }
            });
            return oTable;
        }
    }

})(jQuery);