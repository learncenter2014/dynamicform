<div class="col-sm-${resolution!2}">
    <div class="form-group">
        <label class="col-sm-3 control-label" for="${id!}">${name!'Label not defined'}</label>
        <div class="col-sm-9">
            <input class="form-control" id="${id!}" name="${document.tableName!}_${entryName!}" value="${"$"}{${document.tableName!}_${entryName}!'${defaultValue!}'}">
        </div>
    </div>
</div>
