<div class="col-sm-${resolution!2}">
    <div class="form-group">
        <label class="col-sm-3 control-label">${label!}</label>
        <div class="col-sm-9">
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
</div>