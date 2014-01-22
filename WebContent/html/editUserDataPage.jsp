<%@ include file="../pages/commonHeader.jsp"%>
<s:include value="%{data}"></s:include>
<script>
   
   jQuery(document).ready(function(){
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
      
      //special hook for datepicker
      jQuery(".ui-datepicker-trigger").each(function(index,ref){
              jQuery(ref).datepicker({
                  showOn : 'button',
                  buttonImage : 'img/rbl/_cal.gif',
                  buttonImageOnly : true,
                  dateFormat : jQuery(this).attr("mask").replace(/yy/g, "y"),
                  showMonthAfterYear : false
              });
      });
   });  
</script>