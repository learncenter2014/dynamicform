<div class="form-group">
    <label class="col-sm-2 control-label">${label!}</label>
    <div class="col-sm-8">
    <#list options as option>
        <div class="radio">
            <label>
                <input type="radio" id="${id!}" name="${name!}" value="${option.value}"> ${option.name}
            </label>
        </div>
    </#list>
    </div>
</div>