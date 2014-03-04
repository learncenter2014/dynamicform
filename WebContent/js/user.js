
/** 
 * cellFormatter
 */
cellFormatter["sex"] = function ( data, type, full ) {
    if(data == 1){
        return 'Male';
    }else if(data == 2){
        return 'Female';
    }else{
       return 'Unkown';
    } 
}
cellFormatter["lock"] = function ( data, type, full ) {
    if(data == 1){
        return 'Lock';
    }else{
       return 'UnLock';
    } 
}

/** 
 * actions
 */
actions.unshift({
     "sButtonText":"Lock/Unlock",
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