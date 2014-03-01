<script type="text/javascript">
    function editTemplate(templateId) {
        window.location.href = "datainput/dataRecordInput.action?dataId=${dataId!}&templateId="+templateId;
    }
</script>

<div class="col-lg-12">
    <section class="panel">
        <header class="panel-heading">
            Templates
        </header>
        <table class="table table-striped table-advance table-hover">
            <thead>
            <tr>
                <th><i class="fa fa-bullhorn"></i> Name</th>
                <th><i class="fa fa-bullhorn"></i> Path</th>
                <th></th>
            </tr>
            </thead>
            <tbody>

            <#list templates as template>
            <tr>
                <td>${template.label!}</td>
                <td>${template.path!}</td>
                <td>
                    <button class="btn btn-primary btn-xs" onclick="editTemplate('${template.name}')"><i class="fa fa-pencil"></i></button>
                    <button class="btn btn-danger btn-xs"><i class="fa fa-trash-o "></i></button>
                </td>
            </tr>
            </#list>
            </tbody>
        </table>
    </section>
</div>