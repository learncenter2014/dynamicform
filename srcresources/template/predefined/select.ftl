<div class="form-group">
    <label class="col-sm-2 control-label" for="${id!}">${label!}</label>
    <div class="col-sm-8">
        <select id="${id!}" class="form-control m-bot15">
            <#list options as option>
                <option id="${option.value!}">${option.name!}</option>
            </#list>
        </select>
    </div>
</div>
