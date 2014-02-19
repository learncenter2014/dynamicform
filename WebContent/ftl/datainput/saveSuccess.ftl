
<script type="text/javascript">
    function handleReturn(patientId, templateId) {
        window.location.href = "/datainput/dataTemplateView.action?patientId="+patientId;
    }
</script>
<div class="alert alert-success alert-block fade in">
    <button data-dismiss="alert" class="close close-sm" type="button">
        <i class="fa fa-times"></i>
    </button>
    <h4>
        <i class="fa fa-ok-sign"></i>
        Success!
    </h4>
    <p>保存成功！</p>
</div>
<button type="button" class="btn btn-info" onclick="handleReturn('${patientId}', '${templateId}')">返回</button>

