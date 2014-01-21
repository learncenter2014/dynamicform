<%@ include file="../pages/commonHeader.jsp"%>
<s:include value="%{data}"></s:include>
<script>
    <s:iterator value="userData" id="sup">
     try{
        jQuery('[name="<s:property value="key"/>"]').val('<s:property value="value[0].replace(\"\r\n\",\"&#10;\")" escape="false"/>'.replace(/&#10;/g,"\n"));
     }catch(e){
         
     }
    </s:iterator>
    jQuery("#rbform").append("<input type='hidden' name='recordId' value='<s:property value="#parameters.recordId"/>'/>");
</script>