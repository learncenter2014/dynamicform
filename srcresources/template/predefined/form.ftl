<section>
    <form id="${id!}" name="${name!}" action="datainput/dataRecordInputSubmit.action" class="form-horizontal">
        ${innerHTML!}
        <section class="panel">

            <div class="panel-body">
                <button type="submit" class="btn btn-info">保存</button>
            </div>
        </section>

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
                url:"${"$"}{rootPath!}/dataInput/dataRecordInputSubmit.action",
                data:$("#${id!null}").serialize(),
                cache: false,
                success : function(data, status) {
                    display(data);
                }
            });
            // !!! Important !!!
            // always return false to prevent standard browser submit and page navigation
            return false;
        });
    </script>
</section>