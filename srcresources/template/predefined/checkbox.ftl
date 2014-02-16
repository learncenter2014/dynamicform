<div class="form-group">
    <label class="col-sm-2 control-label">${label}</label>
    <div class="col-sm-8">
        <#list options as option>
            <label class="checkbox-inline">
                <input type="checkbox" id="${id!}" name="${name!}" value="${option.value}">
                ${option.name}
            </label>
        </#list>
    </div>
</div>