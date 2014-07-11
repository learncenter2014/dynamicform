<section class="panel" id="${"$"}{viewId!}_${id!undefined}" name="${"$"}{viewId!}_${id!undefined}">
    <header class="panel-heading">
        ${name!}
        <span class="tools pull-right">
            <a class="fa fa-chevron-down" href="javascript:;"></a>
        </span>
    </header>
    <div class="panel-body">
        <#list rows as row>
            <#list columns as column>
                <input type="hidden" name="${row.code!}_${column.code!}" value="${"$"}{${row.code!}_${column.code!}!}">
            </#list>
        </#list>


        <table class="table table-striped table-hover table-bordered" id="grid_${id!}">
            <thead>
            <tr>
                <th>#</th>
                <#list columns as column>
                    <th>${column.name!}</th>
                </#list>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
                <#list rows as row>
                    <tr>
                        <td>${row.name}</td>
                        <#list columns as column>
                            <td id="${row.code!}_${column.code!}" class="data-cell">${"$"}{${row.code!}_${column.code!}!}</td>
                        </#list>
                        <td><a class="edit" href="javascript:;">编辑</a></td>
                    </tr>
                </#list>
            </tbody>
        </table>
        <script>
            EditableTable.init($("#grid_${id!}"));
        </script>
    </div>
</section>