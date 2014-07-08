<table class="table table-striped table-hover table-bordered" id="editable-sample">
    <thead>
    <tr>
        <th>Username</th>
        <th>Full Name</th>
        <th>Points</th>
        <th>Notes</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    </thead>
    <tbody>
    <tr class="">
        <td>Jondi Rose</td>
        <td>Alfred Jondi Rose</td>
        <td>1234</td>
        <td class="center">super user</td>
        <td><a class="edit" href="javascript:;">Edit</a></td>
        <td><a class="delete" href="javascript:;">Delete</a></td>
    </tr>
    <tr class="">
        <td>Dulal</td>
        <td>Jonathan Smith</td>
        <td>434</td>
        <td class="center">new user</td>
        <td><a class="edit" href="javascript:;">Edit</a></td>
        <td><a class="delete" href="javascript:;">Delete</a></td>
    </tr>
    <tr class="">
        <td>Sumon</td>
        <td> Sumon Ahmed</td>
        <td>232</td>
        <td class="center">super user</td>
        <td><a class="edit" href="javascript:;">Edit</a></td>
        <td><a class="delete" href="javascript:;">Delete</a></td>
    </tr>
    <tr class="">
        <td>vectorlab</td>
        <td>dk mosa</td>
        <td>132</td>
        <td class="center">elite user</td>
        <td><a class="edit" href="javascript:;">Edit</a></td>
        <td><a class="delete" href="javascript:;">Delete</a></td>
    </tr>
    <tr class="">
        <td>Admin</td>
        <td> Flat Lab</td>
        <td>462</td>
        <td class="center">new user</td>
        <td><a class="edit" href="javascript:;">Edit</a></td>
        <td><a class="delete" href="javascript:;">Delete</a></td>
    </tr>
    <tr class="">
        <td>Rafiqul</td>
        <td>Rafiqul dulal</td>
        <td>62</td>
        <td class="center">new user</td>
        <td><a class="edit" href="javascript:;">Edit</a></td>
        <td><a class="delete" href="javascript:;">Delete</a></td>
    </tr>
    <tr class="">
        <td>Jhon Doe</td>
        <td>Jhon Doe </td>
        <td>1234</td>
        <td class="center">super user</td>
        <td><a class="edit" href="javascript:;">Edit</a></td>
        <td><a class="delete" href="javascript:;">Delete</a></td>
    </tr>
    <tr class="">
        <td>Dulal</td>
        <td>Jonathan Smith</td>
        <td>434</td>
        <td class="center">new user</td>
        <td><a class="edit" href="javascript:;">Edit</a></td>
        <td><a class="delete" href="javascript:;">Delete</a></td>
    </tr>
    <tr class="">
        <td>Sumon</td>
        <td> Sumon Ahmed</td>
        <td>232</td>
        <td class="center">super user</td>
        <td><a class="edit" href="javascript:;">Edit</a></td>
        <td><a class="delete" href="javascript:;">Delete</a></td>
    </tr>
    <tr class="">
        <td>vectorlab</td>
        <td>dk mosa</td>
        <td>132</td>
        <td class="center">elite user</td>
        <td><a class="edit" href="javascript:;">Edit</a></td>
        <td><a class="delete" href="javascript:;">Delete</a></td>
    </tr>
    <tr class="">
        <td>Admin</td>
        <td> Flat Lab</td>
        <td>462</td>
        <td class="center">new user</td>
        <td><a class="edit" href="javascript:;">Edit</a></td>
        <td><a class="delete" href="javascript:;">Delete</a></td>
    </tr>
    <tr class="">
        <td>Rafiqul</td>
        <td>Rafiqul dulal</td>
        <td>62</td>
        <td class="center">new user</td>
        <td><a class="edit" href="javascript:;">Edit</a></td>
        <td><a class="delete" href="javascript:;">Delete</a></td>
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
                    jqTds[0].innerHTML = '<input type="text" class="form-control small" value="' + aData[0] + '">';
                    jqTds[1].innerHTML = '<input type="text" class="form-control small" value="' + aData[1] + '">';
                    jqTds[2].innerHTML = '<input type="text" class="form-control small" value="' + aData[2] + '">';
                    jqTds[3].innerHTML = '<input type="text" class="form-control small" value="' + aData[3] + '">';
                    jqTds[4].innerHTML = '<a class="edit" href="">Save</a>';
                    jqTds[5].innerHTML = '<a class="cancel" href="">Cancel</a>';
                }

                function saveRow(oTable, nRow) {
                    var jqInputs = $('input', nRow);
                    oTable.fnUpdate(jqInputs[0].value, nRow, 0, false);
                    oTable.fnUpdate(jqInputs[1].value, nRow, 1, false);
                    oTable.fnUpdate(jqInputs[2].value, nRow, 2, false);
                    oTable.fnUpdate(jqInputs[3].value, nRow, 3, false);
                    oTable.fnUpdate('<a class="edit" href="">Edit</a>', nRow, 4, false);
                    oTable.fnUpdate('<a class="delete" href="">Delete</a>', nRow, 5, false);
                    oTable.fnDraw();
                }

                function cancelEditRow(oTable, nRow) {
                    var jqInputs = $('input', nRow);
                    oTable.fnUpdate(jqInputs[0].value, nRow, 0, false);
                    oTable.fnUpdate(jqInputs[1].value, nRow, 1, false);
                    oTable.fnUpdate(jqInputs[2].value, nRow, 2, false);
                    oTable.fnUpdate(jqInputs[3].value, nRow, 3, false);
                    oTable.fnUpdate('<a class="edit" href="">Edit</a>', nRow, 4, false);
                    oTable.fnDraw();
                }

                var oTable = $('#editable-sample').dataTable({
//                    "aLengthMenu": [
//                        [5, 15, 20, -1],
//                        [5, 15, 20, "All"] // change per page values here
//                    ],
                    // set the initial value
//                    "iDisplayLength": 5,
                    "sDom": "<'row'<'col-lg-6'l><'col-lg-6'f>r>t<'row'<'col-lg-6'i><'col-lg-6'p>>",
//                    "sPaginationType": "bootstrap",
//                    "oLanguage": {
//                        "sLengthMenu": "_MENU_ records per page",
//                        "oPaginate": {
//                            "sPrevious": "Prev",
//                            "sNext": "Next"
//                        }
//                    },
                    "aoColumnDefs": [{
                        'bSortable': false,
                        'aTargets': [0]
                    }
                    ]
                });

                jQuery('#editable-sample_wrapper .dataTables_filter input').addClass("form-control medium"); // modify table search input
                jQuery('#editable-sample_wrapper .dataTables_length select').addClass("form-control xsmall"); // modify table per page dropdown

                var nEditing = null;

                $('#editable-sample_new').click(function (e) {
                    e.preventDefault();
                    var aiNew = oTable.fnAddData(['', '', '', '',
                        '<a class="edit" href="">Edit</a>', '<a class="cancel" data-mode="new" href="">Cancel</a>'
                    ]);
                    var nRow = oTable.fnGetNodes(aiNew[0]);
                    editRow(oTable, nRow);
                    nEditing = nRow;
                });

                $('#editable-sample').on('click', 'a.delete', function (e) {
                    e.preventDefault();

                    if (confirm("Are you sure to delete this row ?") == false) {
                        return;
                    }

                    var nRow = $(this).parents('tr')[0];
                    oTable.fnDeleteRow(nRow);
                    alert("Deleted! Do not forget to do some ajax to sync with backend :)");
                });

                $('#editable-sample').on('click', 'a.cancel', function (e) {
                    e.preventDefault();
                    if ($(this).attr("data-mode") == "new") {
                        var nRow = $(this).parents('tr')[0];
                        oTable.fnDeleteRow(nRow);
                    } else {
                        restoreRow(oTable, nEditing);
                        nEditing = null;
                    }
                });

                $('#editable-sample').on('click', 'tr', function (e) {
                    e.preventDefault();
                    console.log($(this)[0]);
                    /* Get the row as a parent of the link that was clicked on */
                    var nRow = $(this)[0];

                    if (nEditing !== null && nEditing != nRow) {
                        /* Currently editing - but not this row - restore the old before continuing to edit mode */
//                        restoreRow(oTable, nEditing);
                        editRow(oTable, nRow);
                        nEditing = nRow;
//                    } else if (nEditing == nRow && this.innerHTML == "Save") {
//                        /* Editing this row and want to save it */
//                        saveRow(oTable, nEditing);
//                        nEditing = null;
//                        alert("Updated! Do not forget to do some ajax to sync with backend :)");
                    } else if (nEditing == null){
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
