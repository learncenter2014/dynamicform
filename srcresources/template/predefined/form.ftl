<section>
    <form id="${id!}" name="${name!}" action="datainput/dataRecordInputSubmit.action" class="form-horizontal">
    ${innerHTML!}

        <input type="hidden" name="patientId" value="${"$"}{patientId!}"/>
        <input type="hidden" name="templateId" value="${"$"}{templateId!}"/>
    </form>
    <script>
        $("#${id!null}").submit(function() {
            // inside event callbacks 'this' is the DOM element so we first
            // wrap it in a jQuery object and then invoke ajaxSubmit
            //$(this).ajaxSubmit();
            $.post(
                    "datainput/dataRecordInputSubmit.action",
                    this.serialize(),
                    function(data, textStatus, jqXHR) {
                        alert(data);
                    }
            );
            // !!! Important !!!
            // always return false to prevent standard browser submit and page navigation
            return false;
        });
    </script>
</section>