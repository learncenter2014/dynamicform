<div class="col-sm-${resolution!2}">
    <div class="form-group">
        <label class="col-sm-3 control-label" for="${id!}">${label!'Label not defined'}</label>
        <div class="col-sm-9">
            <input class="form-control" id="${id!}" name="${name!}" placeholder="${label!}" data-date-format="${format!'mm/dd/yyyy'}" value="${"$"}{${name}!'${value!}'}">
            <script>
                $("#${id!}").datepicker();
            </script>
        </div>
    </div>
</div>