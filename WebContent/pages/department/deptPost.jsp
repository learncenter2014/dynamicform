<%--
  User: zlj
  Date: 14-07-08
  Version: 1.0
--%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<script>
    <s:if test="#session['sessionUser'].name=='admin'">
     	 operationButtons.push('<a class="btn btn-success" href="'+window.actionPrex+'/batchimportdept.action"><i class="fa fa-plus"></i> 批量导入 </a>');
    </s:if>
</script>