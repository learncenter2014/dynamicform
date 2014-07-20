
options['register'] = {
    'title':'随访注册',
    'html': '<button title="随访注册" style="margin-left:5px" class="btn btn-primary btn-xs" onclick="options[\'register\'].onClick(this)"><i class="fa fa-pencil"></i></button>',
    'onClick' : function(button){
        var tableObj = $('#'+tableId).dataTable();
        var nTr = $(button).parents('tr')[0];
        var selectRowData =  tableObj.fnGetData( nTr );
        window.location = actionPrex + "/register.action?patientId=" + selectRowData[idName];
    }
};

cellFormatter["birthDay"]=cellFormatter["dischargeDate"]=cellFormatter["latestFollowUpDate"] = function ( data, type, full ) {
    if(data!=null){
        return formatDateTime(data.time,false);
    }else{
        return "";
    }
}