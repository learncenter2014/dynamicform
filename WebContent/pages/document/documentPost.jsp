<%--
  User: peter
  Date: 14-4-10
  Time: 下午8:32
--%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<script>

    options['entrylist'] = {
        'title':'实体列表',
        'html': '<button title="实体列表" style="margin-left:5px" class="btn btn-info btn-xs" onclick="options[\'entrylist\'].onClick(this)"><i class="fa fa-edit"></i></button>',
        'onClick' : function(button){
            var tableObj = $('#'+tableId).dataTable();
            var nTr = $(button).parents('tr')[0];
            var selectRowData =  tableObj.fnGetData( nTr );
            window.location = "${rootPath}/entry/index.action?subElementType=0&documentId=" + selectRowData["id"];
        }
    }
</script>