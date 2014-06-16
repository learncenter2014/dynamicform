<!DOCTYPE html>
<html lang="en">
<%@ include file="../commonHeader.jsp"%>
<script type="application/javascript">
    jQuery.fn.dataTableExt.oPagination.iFullNumbersShowPages=20;
</script>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="Mosaddek">
    <meta name="keyword" content="FlatLab, Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">
    <link rel="shortcut icon" href="img/favicon.png">
    <style type="text/css">
        .dataTables_info{width:25%}
        .paging_full_numbers{width:70%}
    </style>
    <title>Data Table</title>
</head>
<body>
<section class="panel">
    <!-- page start-->
    <s:if test="tableTitle != null && tableTitle.length() > 0">
        <header class="panel-heading">${tableTitle}</header>
    </s:if>
    <div class="panel-body">
        <form class="form-horizontal tasi-form">

            <section class="panel">
                <header class="panel-heading" onclick="$('#panelbody').toggle();$('#panelbodybullet').toggleClass('fa fa-chevron-up');$('#panelbodybullet').toggleClass('fa fa-chevron-down');" style="cursor: pointer">
                    <div id="operationbutton"></div>
                      <span class="tools pull-right">
                        <span id="panelbodybullet" class="fa fa-chevron-up" style="cursor: pointer">查询区域</span>
                      </span>
                </header>
                <div class="panel-body" id="panelbody" style="display: none">
                    <div class="form-group">
                        <s:set id="counter" value="0"/>
                        <s:iterator value="tableInit.aoColumns" var="column">
                            <s:if test="%{#column.isbSearchable()==true}">
                                <s:set id="counter" value="%{#counter+1}"/>
                                <s:if test="#counter % 3 ==1">
                                    <div class="col-lg-12" style="height: 40px;">
                                </s:if>
                                <s:if test="#column.searchOptions==null">
                                    <label class="col-lg-2 control-label">${column.sTitle}</label>
                                    <div class="col-lg-2">
                                        <input id="${column.mData}" type="text" class="form-control" value="" name="${column.mData}">
                                    </div>
                                </s:if>
                                <s:else>
                                    <label class="col-lg-2 control-label">${column.sTitle}</label>
                                    <div class="col-lg-2">
                                        <select id="${column.mData}" type="text" class="form-control" value="" name="${column.mData}">
                                            <option value="">&nbsp;</option>
                                            <s:iterator value="#column.searchOptions[0]" status="rowstatus">
                                                <option value="<s:property value="#column.searchOptions[0][#rowstatus.index]"/>"><s:property value="#column.searchOptions[1][#rowstatus.index]"/></option>
                                            </s:iterator>
                                        </select>
                                    </div>
                                </s:else>
                                <s:if test="#counter % 3 ==0">
                                    </div>
                                </s:if>
                            </s:if>
                        </s:iterator>
                        <s:if test="#counter % 3 !=0">
                    </div>
                    </s:if>
                    <a class="btn btn-success pull-right" style="margin-right:15px;margin-top: 15px;" onclick="$('#errorarea').html('');$('#${tableId}').dataTable()._fnAjaxUpdate()">
                        <i class="fa fa-check"></i>
                        查询
                    </a>
                </div>
    </div>
</section>
</form>
<div id="errorarea"><%@include file="../strutsMessage.jsp"%></div>
<form id="exportForm" action="${actionPrex}/exportTable.action" method="post">
</form>
<div class="adv-table dataTables_wrapper form-inline" style="padding:12px">
    <table  id="${tableId}"   class="table table-striped table-advance table-hover display  table-bordered"> </table>
</div>

</div>
</section>
<!-- page end-->

<script type="text/javascript">
jQuery.fn.dataTableExt.oPagination.iFullNumbersShowPages = 12;
var idName;
var tableId = "${tableId}";
var actionPrex = "${actionPrex}";
var cellFormatter = {};
window.exportExcel = false;
/*ALL OPTIONS*/
var options = {
    'edit':{
        'title':'修改',
        'html': '<button title="修改" style="margin-left:5px" class="btn btn-primary btn-xs" onclick="options[\'edit\'].onClick(this)"><i class="fa fa-pencil"></i></button>',
        'onClick' : function(button){
            var tableObj = $('#'+tableId).dataTable();
            var nTr = $(button).parents('tr')[0];
            var selectRowData =  tableObj.fnGetData( nTr );
            window.location = actionPrex + "/edit.action?${addButtonParameter}&id=" + selectRowData[idName];
        }
    },
    'delete': {
        'title':'删除',
        'html' : '<button title="删除" style="margin-left:5px" class="btn btn-danger btn-xs" onclick="options[\'delete\'].onClick(this)"><i class="fa fa-trash-o "></i></button>',
        'onClick' : function(button){
            if (confirm("您确定要删除吗?")){
                var tableObj = $('#'+tableId).dataTable();
                var nTr = $(button).parents('tr')[0];
                var selectRowData =  tableObj.fnGetData( nTr );
                window.location = actionPrex + "/delete.action?${addButtonParameter}&id=" + selectRowData[idName];
            }
        }
    }
}
/* Formating function for row details */
function fnFormatDetails ( oTable, nTr ){
    var aData = oTable.fnGetData( nTr );
    var aoColumns = oTable.fnSettings().aoColumns;
    var sOut = '<table cellpadding="5" cellspacing="0" border="0">';
    for(var i=0;i<aoColumns.length;i++){
        if(aoColumns[i].bVisible == false){
            sOut += '<tr><td>'+ aoColumns[i].sTitle+':</td><td>'+aData[aoColumns[i].mData]+'</td></tr>';
        }
    }
    sOut += '</table>';
    return sOut;
}

var operationButtons = [
    '<a class="btn btn-success" href="${actionPrex}/add.action?${addButtonParameter}"><i class="fa fa-plus"></i> 添加 </a>'
];



$(document).ready(function() {
    //intial opration button
    $("#operationbutton").html(operationButtons.join("&nbsp;"));
    $(".btn.btn-success").click(function(event){event.stopPropagation();});

    //获取来自Dashborad发送过来的URL上的参数，然后设置查询条件.
    var foundSearch = false;
    <s:iterator value="#parameters">
    {
        var searcher  = $(".form-horizontal.tasi-form [name='<s:property value="key"/>']");
        searcher.val('<s:property value="value"/>');
        if(searcher.length>0){
            foundSearch = true;
        }
    }
    </s:iterator>
    if(foundSearch){
        //打开查询搜索条件框
        $('.form-horizontal.tasi-form .panel-heading').click();
    }

    var tableUrl = "${actionPrex}/initTable.action?${addButtonParameter}";
    var param = {};

    $.getJSON( tableUrl, param, function (initParam) {
        if(initParam.disableTools){
            $('#tableTools').css('display','none');
        }
        idName = initParam.idName;
        var columns = [];
        for(var i=0;i<initParam.aoColumns.length ; i++){
            if(typeof cellFormatter[initParam.aoColumns[i].mData] == "function"){
                initParam.aoColumns[i].mRender = cellFormatter[initParam.aoColumns[i].mData];
            }
            if(initParam.aoColumns[i].hiddenColumn != true){
                columns.push(initParam.aoColumns[i]);
            }
        }
        /*
         * Initialse DataTables, with no sorting on the 'details' column
         */

        var oTable = $('#${tableId}').dataTable( {
            "bProcessing": initParam.bProcessing,
            "bServerSide": initParam.bServerSide,
            "iDisplayLength":initParam.iDisplayLength,
            "aLengthMenu": initParam.aLengthMenu,
            "bStateSave": true, //save state that keep page in cookie.
            "sPaginationType":'full_numbers',
            "aoColumns": columns,
            "sAjaxSource": "${actionPrex}/queryTable.action?${addButtonParameter}",
            //"sDom": '<"H"lT><"clear">rt<"F"ip>',
            "sDom": 'rt<"F"ip>',
            "oLanguage": {
                "oPaginate": {
                    "sPrevious": "上一页",
                    "sNext":"下一页",
                    "sLast":"末页",
                    "sFirst":"首页"
                },
                "sLengthMenu": "每页显示 _MENU_ 条",
                "sZeroRecords": "无数据",
                "sInfo": "显示第 _START_ 到 _END_ 条, 共 _TOTAL_ 条.",
                "sInfoEmpty": "无数据",
                "sInfoFiltered": "(filtered from _MAX_ total records)"
            },
            "fnDrawCallback": function ( oSettings ) {
                if(initParam.hasDetails > 0){
                    // show details
                    if($('#${tableId} thead tr th:first[arias="showDetails"]').length == 0){
                        $('#${tableId} thead tr').each( function () {
                            var thObj =document.createElement( 'th' );
                            thObj.setAttribute("arias","showDetails");
                            this.insertBefore(thObj , this.childNodes[0] );
                        } );
                    }

                    var nCloneTd = document.createElement( 'td' );
                    nCloneTd.innerHTML = '<img class="operation" src="jslib/flatlab/assets/advanced-datatable/examples/examples_support/details_open.png">';
                    //nCloneTd.className = "center";
                    $(nCloneTd).css("width","50px");
                    $('#${tableId} tbody tr').each( function (i) {
                        this.insertBefore(  nCloneTd.cloneNode( true ) , this.childNodes[0] );
                    } );
                    $('#${tableId} tbody td').on('click','img.operation',function(){
                        var nTr = $(this).parents('tr')[0];
                        if ( oTable.fnIsOpen(nTr) ){
                            // This row is already open - close it
                            $(this).attr("src" , "jslib/flatlab/assets/advanced-datatable/examples/examples_support/details_open.png");
                            oTable.fnClose( nTr );
                        }else{
                            //   Open this row
                            $(this).attr("src" , "jslib/flatlab/assets/advanced-datatable/examples/examples_support/details_close.png");
                            oTable.fnOpen( nTr, fnFormatDetails(oTable, nTr), 'details' );
                            $('td.details',$(nTr).next()).attr("colspan",nTr.childNodes.length);
                        }
                    } );
                }
                // add options
                var totalOptions = 0;
                for(var p in options){
                    totalOptions++;
                }
                if(totalOptions > 0 && $('#${tableId} thead tr th:last[arias="options"]').length == 0){
                    $('#${tableId} thead tr').each( function () {
                        var thObj =document.createElement( 'th' );
                        thObj.setAttribute("arias","options");
                        thObj.innerHTML ="操作";
                        $(this).append(thObj);
                        $(thObj).css({width:''+totalOptions*40+'px'});
                    } );
                }
                if(totalOptions > 0){
                    $('#${tableId} tbody tr').each( function (i) {
                        var nCloneTd = document.createElement( 'td' );
                        $(this).append(nCloneTd);
                        for(var p in options){
                            $(nCloneTd).append(options[p].html);
                        }
                    });
                }
            },
            "fnServerData": function ( sSource, aoData, fnCallback, oSettings ) {
                /* //======= method one===========
                 // Add some extra data to the sender
                 aoData.push( { "name": "more_data", "value": "my_value" } );
                 $.getJSON( sSource, aoData, function (json) {
                 // Do whatever additional processing you want on the callback, then tell DataTables
                 fnCallback(json)
                 } );
                 //======= method one END=========== */

                //========method two==================
                var mDataObj = {};
                var sortObj = {};
                var iMax = 0;
                for(var n=0;n<aoData.length;n++){
                    if(aoData[n].name == "iColumns"){
                        iMax = aoData[n].value;
                    }
                    if(aoData[n].name == "mDataProp_0"){
                        for(var i = 0; i < iMax;i++){
                            mDataObj[aoData[n].name] = aoData[n].value;
                            n++;
                        }
                    }
                    if(aoData[n].name == "iSortCol_0"){
                        for(var i = 0; i < iMax;i++){
                            if(aoData[n].name == "iSortCol_"+i){
                                sortObj[mDataObj["mDataProp_"+ aoData[n].value]] = aoData[++n].value;
                                n++;
                            }else{
                                break;
                            }
                        }
                    }
                }
                for(var p in sortObj){
                    aoData.push( { "name": "sort['"+p+"']", "value": sortObj[p] } );
                }
                <s:iterator value="tableInit.aoColumns" var="column">
                <s:if test="%{#column.isbSearchable()==true}">
                if($('#${column.mData}').val() != ''){
                    aoData.push( { "name": "filter['${column.mData}']", "value": $('#${column.mData}').val() } );
                }
                </s:if>
                </s:iterator>
                if(window.exportExcel==true){
                    window.exportExcel = false;
                    var form = $("#exportForm");
                    //removed all hidden element in this exportForm.
                    form.empty();
                    for(var i =0;i< aoData.length;i++){
                        var hiddenElement = $("<input>");
                        hiddenElement.attr("type","hidden");
                        hiddenElement.attr("name",aoData[i].name);
                        hiddenElement.attr("value",aoData[i].value);
                        form.append(hiddenElement);
                    }
                    this.oApi._fnProcessingDisplay(oSettings,false);
                    form.submit();
                }else{
                    oSettings.jqXHR = $.ajax( {
                        "dataType": 'json',
                        "type": "POST",
                        "url": sSource,
                        "data": aoData,
                        "success": function(result,status,response){
                            // Do whatever additional processing you want on the callback, then tell DataTables
                            fnCallback(result);
                            $('#${tableId}').css("width","100%");
                        }
                    } );
                }
                //========method two END==================
            }
        });
    } );
} );
<s:if test="#session['backendSessionUser'].name=='admin'">
window.admin = true;
window.actionPrex = "${actionPrex}";
</s:if>
</script>
<s:if test="customJs != null && customJs.length() > 0">
    <script src="${customJs}" type="text/javascript"></script>
</s:if>

<s:if test="customJsp != null && customJsp.length() > 0">
    <s:include value="%{customJsp}"/>
</s:if>

<script>
    <s:if test="#session['backendSessionUser'].name=='admin'">
    operationButtons.push('<a class="btn btn-success" onclick=\'window.exportExcel=true;$("#${tableId}").dataTable()._fnAjaxUpdate()\'><i class="fa fa-plus"></i> 批量导出 </a>');
    </s:if>
    // 格式化js时间
    var formatDateTime = function (obj, IsMi) {
        var myDate = new Date(obj);
        var year = myDate.getFullYear();
        var month = ("0" + (myDate.getMonth() + 1)).slice(-2);
        var day = ("0" + myDate.getDate()).slice(-2);
        var h = ("0" + myDate.getHours()).slice(-2);
        var m = ("0" + myDate.getMinutes()).slice(-2);
        var s = ("0" + myDate.getSeconds()).slice(-2);
        var mi = ("00" + myDate.getMilliseconds()).slice(-3);
        if (IsMi == true) {
            return year + "-" + month + "-" + day;
        }
        else {
            return year + "-" + month + "-" + day + " " + h + ":" + m + ":" + s;
        }
    };

    //格式化createTime  modifyTime
    cellFormatter["modifyTime"]=cellFormatter["createTime"] = function ( data, type, full ) {
        if(data!=null){
            return formatDateTime(data.time,false);
        }else{
            return "";
        }
    }

</script>
</body>
</html>