<!DOCTYPE html>
<html lang="en">
<%@ include file="../commonHeader.jsp"%>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="Mosaddek">
    <meta name="keyword" content="FlatLab, Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">
    <link rel="shortcut icon" href="img/favicon.png">
    
    <style type="text/css">
		 div.dataTables_length{
		    padding: 5px 0 !important;
		    margin: 0px !important;
		 }
		 div.DTTT_container{
		    padding: 5px 0 !important;
		    margin: 0px !important;
		 }
		 div.dataTables_info{
		    padding: 5px 0 !important;
		    margin: 0px !important;
		 }
		 div.dataTables_paginate{
		    padding: 5px 0 !important;
		    margin: 0px !important;
		 }
    </style>
    
    <title>Data Table</title>
  </head>
<body>
       <!-- page start-->
            <% if(request.getAttribute("tableTitle")!= null && request.getAttribute("tableTitle").toString().length() > 0 ){%>
		     <header class="panel-heading" style="text-align: center;">${tableTitle}</header>
		  <%} %>
           <div class="panel-body">
                 <div class="adv-table">
                     <table  id="${tableId}" cellpadding="0" cellspacing="0" border="0" class="display table table-bordered">
                       <thead>
                       </thead>
                      <tbody>
                          <tr><td colspan="4" class="dataTables_empty">Loading data from server</td></tr>
                      </tbody>
                          
                     </table>
                </div>
          </div>
     <!-- page end-->
    
<script type="text/javascript">
     var idName;
     var tableId = "${tableId}";
	 var actionPrex = "${actionPrex}";
     var cellFormatter = {};
     var actions = [
              {
                  "sButtonText":"Add",
                  "sExtends":"text",
                  "fnClick": function ( nButton, oConfig, oFlash ) {
                      window.location.href = actionPrex + "/add.action";
                  }
              },{
                  "sButtonText":"Edit",
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
                          window.location.href = actionPrex + "/edit.action?id=" + selectRowData[idName];
                      }
                  }
              },{
                  "sButtonText":"Delete",
                  "sExtends":"select",
                  "fnClick": function ( nButton, oConfig, oFlash ) {
                      if($(nButton).hasClass("DTTT_disabled")){
			              return;
			          }
                      var tableObj = $('#'+tableId).dataTable();
                      var selectedRows = tableObj.$('tr.DTTT_selected');
                      if(selectedRows.length == 0){
                          alert("Please select records!");
                      }else{
                         if (confirm("Are you sure delete selected records?")){
						    var par = "";
						    tableObj.$('tr.row_selected').each(function(i){
						        par = par + "ids="+tableObj.fnGetData(this)[idName] + "&";
						    });
                            window.location.href = actionPrex + "/delete.action?" + par;
						 }
                      }
                  }
              }
      ];
      /* Formating function for row details */
      function fnFormatDetails ( oTable, nTr ){
          var aData = oTable.fnGetData( nTr );
          var aoColumns = oTable.fnSettings().aoColumns;
          var sOut = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';
          for(var i=0;i<aoColumns.length;i++){
              if(aoColumns[i].bVisible == false){
                  sOut += '<tr><td>'+ aoColumns[i].sTitle+':</td><td>'+aData[aoColumns[i].mData]+'</td></tr>';
              }
          }
          sOut += '</table>';
          return sOut;
      }
 
 $(document).ready(function() {
     var tableUrl = "${actionPrex}/initTable.action";
     var param = {};
     $.getJSON( tableUrl, param, function (initParam) { 
         idName = initParam.idName;
         for(var i=0;i<initParam.aoColumns.length ; i++){
             if(typeof cellFormatter[initParam.aoColumns[i].mData] == "function"){
                 initParam.aoColumns[i].mRender = cellFormatter[initParam.aoColumns[i].mData];
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
	 		 "aoColumns": initParam.aoColumns,
	 		 "sAjaxSource": "${actionPrex}/queryTable.action",
	 		 "sDom": '<"H"lT><"clear">rt<"F"ip>',
	 		 "oTableTools": {
	 		   "sRowSelect": "multi",
		       "aButtons": actions
	 		 },
		     "fnDrawCallback": function ( oSettings ) {
		            if(initParam.hasDetails > 0){
		                if($('#${tableId} thead tr th:first[arias="showDetails"]').length == 0){
		                    $('#${tableId} thead tr').each( function () {
		                          var thObj =document.createElement( 'th' );
		                          thObj.setAttribute("arias","showDetails");
			                      this.insertBefore(thObj , this.childNodes[0] );
			                 } );
		                }
		                var nCloneTd = document.createElement( 'td' );
		                nCloneTd.innerHTML = '<img src="<%=request.getContextPath()%>/jslib/flatlab/assets/advanced-datatable/examples/examples_support/details_open.png">';
		                nCloneTd.className = "center";
			            $('#${tableId} tbody tr').each( function (i) {
			                this.insertBefore(  nCloneTd.cloneNode( true ) , this.childNodes[0] );
			            } );
			            $('#${tableId} tbody td').on('click','img',function(){
			                var nTr = $(this).parents('tr')[0];
			                if ( oTable.fnIsOpen(nTr) ){
			                    // This row is already open - close it 
			                     $(this).attr("src" , "<%=request.getContextPath()%>/jslib/flatlab/assets/advanced-datatable/examples/examples_support/details_open.png");
			                     oTable.fnClose( nTr );
			                }else{
			                   //   Open this row 
			                     $(this).attr("src" , "<%=request.getContextPath()%>/jslib/flatlab/assets/advanced-datatable/examples/examples_support/details_close.png");
			                     oTable.fnOpen( nTr, fnFormatDetails(oTable, nTr), 'details' );
			                     $('td.details',$(nTr).next()).attr("colspan",nTr.childNodes.length);
			                 }
			            } );
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
	 			$('#${tableId} thead tr th input[type="text"]').each( function (i) {
	 			  aoData.push( { "name": "filter['"+this.name+"']", "value": this.value } );
	 			});
	 			$('#${tableId} thead tr th select[name]').each( function (i) {
	 			  aoData.push( { "name": "filter['"+this.name+"']", "value": $(this).val() } );
		 		});
	 			 oSettings.jqXHR = $.ajax( {
	                 "dataType": 'json',
	                 "type": "POST",
	                 "url": sSource,
	                 "data": aoData,
	                 "success": function(result,status,response){
	                    // Do whatever additional processing you want on the callback, then tell DataTables
	                     fnCallback(result);
	                  }
	               } );
	 			//========method two END==================   
	          }
	        });
 	} );
 } );
  </script>
  <% if(request.getAttribute("customJs")!= null && request.getAttribute("customJs").toString().length() > 0 ){%>
     <script src="${customJs}" type="text/javascript"></script> 
  <%} %>
  </body>
</html>