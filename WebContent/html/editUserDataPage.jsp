<%@ include file="../pages/commonHeader.jsp"%>
<s:include value="%{data}"></s:include>
<script>
    <s:iterator value="userData" id="sup">
       <s:if test="key!='_id' && key!='recordId'">
        try{
            var arrayValue = <s:property value="value.toString()" escape="false"/>;
            jQuery('[name="<s:property value="key"/>"]').val(arrayValue);
         }catch(e){
             
         }
       </s:if>
    </s:iterator>
    jQuery("#rbform").append("<input type='hidden' name='recordId' value='<s:property value="#parameters.recordId"/>'/>");
</script>