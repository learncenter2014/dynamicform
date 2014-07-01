operationButtons = [];
options = {
    'followUp':{
        'title':'开始随访',
        'html': '<button title="开始随访" style="margin-left:5px" class="btn btn-primary btn-xs" onclick="options[\'followUp\'].onClick(this)"><i class="fa fa-pencil"></i></button>',
        'onClick' : function(button){
            var tableObj = $('#'+tableId).dataTable();
            var nTr = $(button).parents('tr')[0];
            var selectRowData =  tableObj.fnGetData( nTr );
            window.location = actionPrex + "/studyInput.action?participantId=" + selectRowData[idName];
        }
    }
};

cellFormatter["birthDay"]=cellFormatter["dischargeDate"]=cellFormatter["latestFollowUpDate"] = function ( data, type, full ) {
    if(data!=null){
        return formatDateTime(data.time,false);
    }else{
        return "";
    }
}