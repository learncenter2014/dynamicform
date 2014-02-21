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
    <s:form action="pagesave" namespace="/page" cssStyle="margin-left:20px">
       <s:iterator value="pageMap" var="page" id="pid">
       <div style="margin-top:20px">
          <fieldset>
           <legend>${pid.value}</legend>
           <div class="tempelatelist">
    	        <p style="margin-right:20px">模板列表</p>
                <s:iterator value="templateList" var="sub" status="id">
                 <label style="margin-right:20px">
                    <s:checkbox name="%{#pid.key}" fieldValue="%{#sub[1]}" label="%{#sub[0]}" disabled="true"></s:checkbox>
                    <s:property value="%{#sub[0]}"/>
                    <span class="fa fa-plus ${pid.key}plus" style="margin-left:5px"></span>
                 </label>
                </s:iterator>
           </div>
	        <div class="${pid.key}pageclass">
	          <label style="margin-right:20px">
	            <s:iterator value="patientCheckbox[#pid.key]" var="ref">
	              <s:checkbox value="true" name="%{#pid.key}" fieldValue="%{#ref.name}" label="%{#ref.label}"></s:checkbox>
	              <s:property value="%{#ref.label}"/> 
	            </s:iterator>
	          </label>
	        </div>
	      </fieldset>
        </div>  
        <script>
          jQuery(document).ready(function() {
              jQuery("div.${pid.key}pageclass")
              .sortable({
                      items: 'label'
               }).disableSelection();
              jQuery(".${pid.key}plus").click(function(){
                  var clone = jQuery(this).parent().clone();
                  clone.find("input").attr("disabled",false);
                  jQuery("div.${pid.key}pageclass").append(clone);
              });
          });
        </script>        
       </s:iterator>
        <s:submit value="提交" cssClass="btn btn-info"></s:submit>
    </s:form>
</body>

</html>