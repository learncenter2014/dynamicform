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
       <div>
        <p style="margin-right:20px">病人管理配置</p>
        <s:iterator value="templateList" var="sub" status="id">
          <label for="patientCheckbox${id.index}" style="margin-right:20px">
            <s:if test="%{#sub[1] in patientCheckbox}">
              <s:checkbox id="patientCheckbox_%{#id.index}" value="true" name="patientCheckbox" fieldValue="%{#sub[1]}" label="%{#sub[0]}"></s:checkbox>
              <s:property value="%{#sub[0]}"/> 
            </s:if>
            <s:else>
              <s:checkbox id="patientCheckbox_%{#id.index}" value="false" name="patientCheckbox" fieldValue="%{#sub[1]}" label="%{#sub[0]}"></s:checkbox>
              <s:property value="%{#sub[0]}"/> 
            </s:else>
          </label>
        </s:iterator>
        <br>
        <s:submit value="提交" cssClass="btn btn-info"></s:submit>
       </div>
    </s:form>
</body>

</html>