<div class="col-sm-${resolution!6}">
    <div class="form-group">
        <label class="col-sm-3 control-label">${name}</label>
        <div class="col-sm-9">
            <input type="hidden" name="${document.code!}_${code!}" value="" />
            <#list entryCodeBeanList as option>
                <label class="checkbox-inline">
                    <input type="checkbox" name="${document.code!}_${code!}" value="${option.value}">
                    ${option.name}
                </label>
            </#list>
            <script type="text/javascript">
            ${"
            <#if ${document.code!}_${code!}??>
                <#list ${document.code!}_${code!}?split(';') as checkedValue>
                    $(\"input[name=${document.code!}_${code!}][value='${'$'}{checkedValue!}']\").attr('checked','checked');
                </#list>
            </#if>
            "}
            </script>
        </div>
    </div>
</div>