<script type="text/javascript">
    function editPatient(patientId) {
        window.location.href = "datainput/dataTemplateView.action?dataId="+patientId;
    }
</script>

<div class="col-lg-12">
    <section class="panel">
        <header class="panel-heading">
            Paients
        </header>
        <table class="table table-striped table-advance table-hover">
            <thead>
            <tr>
                <th><i class="fa fa-bullhorn"></i> Name</th>
                <th></th>
            </tr>
            </thead>
            <tbody>

            <#list patients as patient>
            <tr>
                <td>${patient.name}</td>
                <td>
                    <button class="btn btn-primary btn-xs" onclick="editPatient('${patient.value}')"><i class="fa fa-pencil"></i></button>
                    <button class="btn btn-danger btn-xs"><i class="fa fa-trash-o "></i></button>
                </td>
            </tr>
            </#list>
            </tbody>
        </table>
    </section>
</div>