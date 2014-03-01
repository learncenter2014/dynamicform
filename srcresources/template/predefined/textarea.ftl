<div class="col-sm-${resolution!2}">
    <div class="form-group">
        <label class="col-sm-3 control-label" for="${id}">${label}</label>
        <div class="col-sm-9">
            <textarea id="${id}" name="${name}" rows="5" cols="60" class="form-control">${"$"}{${name}!'${value!}'}</textarea>
        </div>
    </div>
</div>