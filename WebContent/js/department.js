/**
 * cellFormatter
 */
cellFormatter["fuRole"] = function ( data, type, full ) {
    if(data == 0){
        return '未分配';
    }else if(data == 1){
        return '管理';
    }else{
        return '执行';
    }
}
	
cellFormatter["hasLongDistance"] = function ( data, type, full ) {
    if(data == 0){
        return '有长途';
    }else if(data == 1){
        return '无长途';
    }
}