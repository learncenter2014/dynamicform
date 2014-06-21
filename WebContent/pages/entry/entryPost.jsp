<%--
  User: peter
  Date: 14-4-10
  Time: 下午8:32
--%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<script>

    options['entrycodelist'] = {
        'title':'实体列表',
        'html': '<button title="编码列表" style="margin-left:5px" class="btn btn-info btn-xs" onclick="options[\'entrycodelist\'].onClick(this)"><i class="fa fa-edit"></i></button>',
        'onClick' : function(button){
            var tableObj = $('#'+tableId).dataTable();
            var nTr = $(button).parents('tr')[0];
            var selectRowData =  tableObj.fnGetData( nTr );
            window.location = "${rootPath}/entryCode/index.action?entryId=" + selectRowData["id"];
        }
    }
</script>