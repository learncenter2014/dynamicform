<!DOCTYPE html>
<%@ include file="/pages/commonHeader.jsp" %>

<s:iterator value="pageBeans" var="page">
    <s:iterator value="#page.templateList" var="tem">
        <s:include value="/datainput/dataRecordInput.action?dataId=%{#session.dataId}&templateId=%{#tem.name}"/>
    </s:iterator>
</s:iterator>

<s:if test="pageBeans.size()!=0">
    <script>
        jQuery(document).ready(function () {
            var buttons = jQuery("[type='submit']").parent().clone();
            jQuery("[type='submit']").parent().remove();
            jQuery("#decoratebutton").html(buttons);
            jQuery("[type='submit']").click(function (event) {
                event.stopPropagation();
                jQuery("#decoratetemplate form").each(function () {
                    jQuery(this).submit();
                });
                jQuery("#decoratebody form").submit();
            });
        });
    </script>
</s:if>