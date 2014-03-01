<div class="col-sm-${resolution!2}">
    <div class="form-group">
        <label class="col-sm-3 control-label" for="${id}">${label!}</label>
        <div class="col-sm-9">
            <select id="${id}" name="${name}" class="form-control m-bot15">
                <#list options as option>
                    <option id="${option.value!}">${option.name!}</option>
                </#list>
            </select>
            <script type="text/javascript">
                $("#${id}").val("${'$'}{${name}!'${value!}'}");
            </script>
        </div>
    </div>
</div>