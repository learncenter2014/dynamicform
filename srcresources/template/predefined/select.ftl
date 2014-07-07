<div class="col-sm-${resolution!6}">
    <div class="form-group">
        <label class="col-sm-3 control-label" for="${document.code!}_${code!}">${name!}</label>
        <div class="col-sm-9">
            <select id="${document.code!}_${code!}" name="${document.code!}_${code!}" class="form-control m-bot15">
                <#list entryCodeBeanList as option>
                    <option id="${option.value!}">${option.name!}</option>
                </#list>
            </select>
            <script type="text/javascript">
                $("#${document.code!}_${code!}").val("${'$'}{${document.code!}_${code!}}");
            </script>
        </div>
    </div>
</div>