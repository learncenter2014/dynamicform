<section>
    <form id="${id!}" name="${name!}" action="datainput/dataRecordInputSubmit.action" class="form-horizontal">
        ${innerHTML!}

        <input type="hidden" name="studyId" value="${"$"}{studyId!}"/>
        <input type="hidden" name="viewId" value="${"$"}{viewId!}"/>
        <input type="hidden" name="participantId" value="${"$"}{participantId!}"/>
        <input type="hidden" name="recordId" value="${"$"}{recordId!}"/>
        <input type="hidden" name="tableName" value="${study.code}"/>
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