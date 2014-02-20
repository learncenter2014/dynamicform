<div class="form-group">
    <label class="col-sm-2 control-label">${label!}</label>
    <div class="col-sm-8">
    <#list options as option>
        <div class="radio">
            <label>
                <input type="radio" name="${name}" value="${option.value}"> ${option.name}
            </label>
        </div>
    </#list>
        <script type="text/javascript">
            $('input[name=${name}][value="${'$'}{${name}!'${value!}'}"]').attr("checked",'checked');
        </script>
    </div>
</div>