<div class="form-group">
    <label class="col-sm-2 control-label">${label}</label>
    <div class="col-sm-8">
        <#list options as option>
        <div class="checkbox">
            <label>
                <input type="checkbox" id="testCheckbox1" value="${option.value}"> ${option.name}
            </label>
        </div>
        </#list>
    </div>
</div>