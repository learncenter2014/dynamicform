<div class="col-sm-${resolution!6}">
    <div class="form-group">
        <label class="col-sm-3 control-label" for="${document.code!}_${code!}">${name!'Label not defined'}</label>
        <div class="col-sm-9">
            <input class="form-control" id="${document.code!}_${code!}" name="${document.code!}_${code!}" placeholder="${name!}" data-date-format="${format!'mm/dd/yyyy'}" value="${"$"}{${document.code!}_${code!}}">
            <script>
                $("#${document.code!}_${code!}").datepicker();
            </script>
        </div>
    </div>
</div>