
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
actions.unshift({
    "title":"Lock",
    "multiSelect":true,
    "action": function (thisObj){
        alert("Lock for Table "+$(thisObj).attr("for")); 
    }
})