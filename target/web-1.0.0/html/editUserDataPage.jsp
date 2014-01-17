<%@ include file="../pages/commonHeader.jsp"%>
<s:include value="%{data}"></s:include>
<script>
    <s:iterator value="userData" id="sup">
     jQuery('input=[name="<s:property value="key"/>"]').attr("value",'<s:property value="value[0]" escape="false"/>');
    </s:iterator>
    jQuery("#rbform").append("<input type='hidden' name='recordId' value='<s:property value="#parameters.recordId"/>'/>");
</script>