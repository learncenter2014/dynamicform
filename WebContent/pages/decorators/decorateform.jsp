<!DOCTYPE html>
<%@ include file="/pages/commonHeader.jsp" %>
<html lang="en">
<head>
        <div id="decoratetemplate">
            <jsp:include page="/page/decorateform.action"/>
        </div>
        <div id="decoratebutton">
        </div>
        <script>
            jQuery(document).ready(function () {
                var buttons = jQuery("[type='submit']").parent().clone();
                jQuery("[type='submit']").parent().remove();
                jQuery("#decoratebutton").html(buttons);
                jQuery("[type='submit']").click(function (event) {
                    event.stopPropagation();
                    jQuery("#decoratetemplate form").each(function () {
                        this.submit();
                    });
                    jQuery("#decoratebody form").submit();
                });
            });
        </script>
</html>
