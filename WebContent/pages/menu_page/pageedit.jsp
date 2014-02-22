<!DOCTYPE html>
<%@ include file="/pages/commonHeader.jsp"%>
<html>

<head>

<title>Page Edit</title>

<script>
    
</script>
</head>

<body>

    <h4 style="text-align: center;">页面构建</h4>
    <s:form action="pagesave" namespace="/page">
       <s:iterator value="pageMap" var="page" id="pid">
          <div class="${pid.key}pageclass">
	        <p style="margin-right:20px">${pid.value}</p>
	        <s:iterator value="pageCheckbox[#pid.key]" var="sub" status="id">
	          <label style="margin-right:20px">
	              <s:checkbox value="true" name="%{#pid.key}" fieldValue="%{#sub.name}" label="%{#sub.label}"></s:checkbox>
	              <s:property value="%{#sub.label}"/> 
	          </label>
	        </s:iterator>
	        <s:iterator value="pageUnCheckbox[#pid.key]" var="sub" status="id">
              <label style="margin-right:20px">
                  <s:checkbox value="false" name="%{#pid.key}" fieldValue="%{#sub.name}" label="%{#sub.label}"></s:checkbox>
                  <s:property value="%{#sub.label}"/> 
              </label>
            </s:iterator>
	        <br>
	      <script>
	          jQuery(document).ready(function() {
	              jQuery("div.${pid.key}pageclass")
	              .sortable({
	                      items: 'label'
	               }).disableSelection();
	          });
          </script>  
         </div>  
       </s:iterator>
        <s:submit value="提交" cssClass="btn btn-info"></s:submit>
    </s:form>
</body>

</html>