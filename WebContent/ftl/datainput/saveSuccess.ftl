
<script type="text/javascript">
    function handleReturn(dataId, templateId) {
        window.location.href = "datainput/dataTemplateView.action?dataId="+dataId;
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
<button type="button" class="btn btn-info" onclick="handleReturn('${dataId}', '${templateId}')">返回</button>

