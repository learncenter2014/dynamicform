
/** 
 * cellFormatter
 */
cellFormatter["age"] = function ( data, type, full ) {
    if(data == 1){
        return 'Male';
    }else if(data == 2){
        return 'Female';
    }else{
       return 'Unkown';
    } 
}
/** 
 * actions
 */
actions=[
         {
             "title":"Lock",
             "multiSelect":true,
             "action": function (thisObj){
                 alert("Lock for Table "+$(thisObj).attr("for")); 
             }
         },{
             "title":"Edit",
             "multiSelect":false,
             "action": function (thisObj){
                 alert("Edit for Table "+$(thisObj).attr("for")); 
             }
         },{
             "title":"Delete",
             "multiSelect":true,
             "action": function (thisObj){
                 alert("Delete for Table "+$(thisObj).attr("for")); 
             }
         }
 ];