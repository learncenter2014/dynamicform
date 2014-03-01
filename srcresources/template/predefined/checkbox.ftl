<div class="col-sm-${resolution!2}">
    <div class="form-group">
        <label class="col-sm-3 control-label">${label}</label>
        <div class="col-sm-9">
            <#list options as option>
                <label class="checkbox-inline">
                    <input type="checkbox" id="${id!}" name="${name!}" value="${option.value}">
                    ${option.name}
                </label>
            </#list>
            <script type="text/javascript">
            ${"
            <#if ${name}??>
                <#list ${name}?split(';') as checkedValue>
                    $(\"input[name=${name}][value='${'$'}{checkedValue!}']\").attr('checked','checked');
                </#list>
            </#if>
            "}
            </script>
        </div>
    </div>
</div>