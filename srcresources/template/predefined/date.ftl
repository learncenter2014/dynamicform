
<div class="form-group">
    <label class="col-sm-2 control-label" for="${id!}">${label!'Label not defined'}</label>
    <div class="col-sm-8">
        <input class="form-control" id="${id!}" name="${name!}" placeholder="${label!}" value="${value!}">
        <script>
            $("#${id!}").datepicker();
        </script>
    </div>
</div>
