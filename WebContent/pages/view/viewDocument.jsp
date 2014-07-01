<%--
  User: peter
  Date: 14-7-1
  Time: 下午8:32
--%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<script>

    options['documentlist'] = {
        'title':'模块维护',
        'html': '<button title="模块维护" style="margin-left:5px" class="btn btn-info btn-xs" onclick="options[\'documentlist\'].onClick(this)"><i class="fa fa-edit"></i></button>',
        'onClick' : function(button){
            var tableObj = $('#'+tableId).dataTable();
            var nTr = $(button).parents('tr')[0];
            var selectRowData =  tableObj.fnGetData( nTr );
            window.location = "${rootPath}/view/documentList.action?${addButtonParameter}&id=" + selectRowData["id"];
        }
    }
</script>