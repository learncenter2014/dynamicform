<div class="col-sm-${resolution!6}">
    <div class="form-group">
        <label class="col-sm-3 control-label">${name!}</label>
        <div class="col-sm-9">
        <#list entryCodeBeanList as option>
            <div class="radio">
                <label>
                    <input type="radio" name="${document.code!}_${code!}" value="${option.value}"> ${option.name}
                </label>
            </div>
        </#list>
            <script type="text/javascript">
                $('input[name="${document.code!}_${code!}"][value="${'$'}{${document.code!}_${code!}}"]').attr("checked",'checked');
            </script>
        </div>
    </div>
</div>