
/** 
 * cellFormatter
 */
cellFormatter["sex"] = function ( data, type, full ) {
    if(data == 1){
        return '男';
    }else if(data == 2){
        return '女';
    }else{
       return '未知';
    } 
}
cellFormatter["lock"] = function ( data, type, full ) {
    if(data == 1){
        return '已锁';
    }else{
       return '正常';
    } 
}

/** 
 * actions
 */
actions.unshift({
     "sButtonText":"(解)锁",
     "sExtends":"select_single",
     "fnClick": function ( nButton, oConfig, oFlash ) {
          if($(nButton).hasClass("DTTT_disabled")){
              return;
          }
          var tableObj = $('#'+tableId).dataTable();
          var selectedRows = tableObj.$('tr.DTTT_selected');
          if(selectedRows.length != 1){
              alert("Please select one record!");
          }else{
             var selectRowData =  tableObj.fnGetData( selectedRows[0] );
             window.location.href = actionPrex + "/lock.action?id=" + selectRowData[idName];
          }
     }
})