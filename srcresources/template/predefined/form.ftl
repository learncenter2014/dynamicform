<section>
    <form id="${id!}" name="${name!}" action="datainput/dataRecordInputSubmit.action" class="form-horizontal">
        ${innerHTML!}

        <input type="hidden" name="dataId" value="${"$"}{dataId!}"/>
        <input type="hidden" name="templateId" value="${"$"}{templateId!}"/>
        <input type="hidden" name="pageName" value="${"$"}{pageName!}"/>
    </form>
    <script>
        $("#${id!null}").submit(function() {
            // inside event callbacks 'this' is the DOM element so we first
            // wrap it in a jQuery object and then invoke ajaxSubmit
            //$(this).ajaxSubmit();
            jQuery.ajax({
                type: "POST",
                async:false,
                url:"datainput/dataRecordInputSubmit.action",
                data:$("#${id!null}").serialize(),
                cache: false,
                success : function(data, status) {
                    //alert(status);
                }
            });
            // !!! Important !!!
            // always return false to prevent standard browser submit and page navigation
            return false;
        });
    </script>
</section>