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

    <title>Data Table</title>

    <!-- Bootstrap core CSS -->
    <link href="<%=request.getContextPath()%>/jslib/flatlab/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/jslib/flatlab/css/bootstrap-reset.css" rel="stylesheet">
    <!--external css-->
    <link href="<%=request.getContextPath()%>/jslib/flatlab/assets/font-awesome/css/font-awesome.css" rel="stylesheet" />

    <link href="<%=request.getContextPath()%>/jslib/flatlab/assets/advanced-datatable/media/css/demo_page.css" rel="stylesheet" />
    <link href="<%=request.getContextPath()%>/jslib/flatlab/assets/advanced-datatable/media/css/demo_table.css" rel="stylesheet" />
    <link href="<%=request.getContextPath()%>/jslib/flatlab/assets/data-tables/DT_bootstrap.css" rel="stylesheet" />
    <link href="<%=request.getContextPath()%>/jslib/flatlab/assets/advanced-datatable/extras/TableTools/media/css/TableTools.css" rel="stylesheet" />
    
    


    <!-- Custom styles for this template -->
    <link href="<%=request.getContextPath()%>/jslib/flatlab/css/style.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/jslib/flatlab/css/style-responsive.css" rel="stylesheet" />
   
    <link href="<%=request.getContextPath()%>/css/datatable.css" rel="stylesheet" />
    
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 tooltipss and media queries -->
    <!--[if lt IE 9]>
      <script src="<%=request.getContextPath()%>/jslib/flatlab/js/html5shiv.js"></script>
      <script src="<%=request.getContextPath()%>/jslib/flatlab/js/respond.min.js"></script>
    <![endif]-->
  </head>
  
<body>
  
       <!-- page start-->
       <section class="panel">
           <header class="panel-heading">
               DataTables hidden row details example
           </header>
           <div class="panel-body">
                 <div class="adv-table">
                     <table cellpadding="0" cellspacing="0" border="0" class="display table table-bordered" id="example">
                       <thead>
                       </thead>
                      <tbody>
                          <tr><td colspan="4" class="dataTables_empty">Loading data from server</td></tr>
                      </tbody>
                          
                     </table>
                </div>
          </div>
     </section>
     <!-- page end-->
           

    <!-- js placed at the end of the document so the pages load faster -->
    <!--<script src="<%=request.getContextPath()%>/jslib/flatlab/js/jquery.js"></script>-->
    <script src="<%=request.getContextPath()%>/jslib/flatlab/assets/advanced-datatable/media/js/jquery.js" type="text/javascript" language="javascript" ></script>
    <script src="<%=request.getContextPath()%>/jslib/flatlab/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath()%>/jslib/flatlab/js/jquery.dcjqaccordion.2.7.js" class="include" type="text/javascript" ></script>
    <script src="<%=request.getContextPath()%>/jslib/flatlab/js/jquery.scrollTo.min.js"></script>
    <script src="<%=request.getContextPath()%>/jslib/flatlab/js/jquery.nicescroll.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/jslib/flatlab/js/respond.min.js" ></script>
    <script src="<%=request.getContextPath()%>/jslib/flatlab/assets/advanced-datatable/media/js/jquery.dataTables.js" type="text/javascript" language="javascript" ></script>
    <script src="<%=request.getContextPath()%>/jslib/flatlab/assets/data-tables/DT_bootstrap.js" type="text/javascript" ></script>
    <!--common script for all pages-->
    <script src="<%=request.getContextPath()%>/jslib/flatlab/js/common-scripts.js"></script>

<script src="<%=request.getContextPath()%>/jslib/flatlab/assets/advanced-datatable/extras/TableTools/media/js/ZeroClipboard.js" type="text/javascript" charset="utf-8" ></script>
<script src="<%=request.getContextPath()%>/jslib/flatlab/assets/advanced-datatable/extras/TableTools/media/js/TableTools.js" type="text/javascript" charset="utf-8" ></script>
    <script type="text/javascript">
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
      
      function showDetails(oTableObj,thisObj){
          var oTable = $('#example').dataTable();
          var oSettings = oTable.fnSettings();
          var nTr = $(thisObj).parents('tr')[0];
          if ( oTable.fnIsOpen(nTr) ){
             // This row is already open - close it 
              $('img',thisObj).attr("src" , "<%=request.getContextPath()%>/jslib/flatlab/assets/advanced-datatable/examples/examples_support/details_open.png");
              oTable.fnClose( nTr );
          }else{
            //   Open this row 
              $('img',thisObj).attr("src" , "<%=request.getContextPath()%>/jslib/flatlab/assets/advanced-datatable/examples/examples_support/details_close.png");
              oTable.fnOpen( nTr, fnFormatDetails(oTable, nTr), 'details' );
              $('td.details',$(nTr).next()).attr("colspan",nTr.childNodes.length);
          }
      }

 $(document).ready(function() {
     
     var tableUrl = "<%=request.getContextPath()%>/datatable/initTable.action";
     var param = {};
     $.getJSON( tableUrl, param, function (initParam) { 
         for(var i=0;i<initParam.aoColumns.length ; i++){
             if(initParam.aoColumns[i].mData == 'age'){
                 initParam.aoColumns[i].mRender = function ( data, type, full ) {
		            if(data == 1){
		                return "Male";
		            }else if(data == 0){
		                return "Female";
		            }else{
		                return "Unkown";
		            }
		        }
             }
         }
         
	     /*
	      * Initialse DataTables, with no sorting on the 'details' column
	      */
	     var oTable = $('#example').dataTable( {
	         "bProcessing": initParam.bProcessing,
	 		 "bServerSide": initParam.bServerSide,
	 		 "iDisplayLength":initParam.iDisplayLength,
	 		 "aLengthMenu": initParam.aLengthMenu,
	 		 "aoColumns": initParam.aoColumns,
	 		 "sAjaxSource": initParam.sAjaxSource,
	 		 "bFilter":false,
	 		 //"aaSorting": [[1, 'asc']],
	 		  
		     "fnDrawCallback": function ( oSettings ) {
		            if(initParam.actionHtml.length > 0){
		                if($('#example thead tr th:first[arias="actions"]').length == 0){
		                    $('#example thead tr').each( function () {
		                          var thObj =document.createElement( 'th' );
		                          thObj.setAttribute("arias","actions");
		                          thObj.innerHTML = '<img src="<%=request.getContextPath()%>/jslib/flatlab/assets/advanced-datatable/examples/examples_support/details_open.png"/><br/>ACTION';
			                      this.insertBefore(thObj , this.childNodes[0] );
			                      $('img',$(thObj)).live('click', function (i) {
					                    add();
					              });
			                 } );
		                }
			            $('#example tbody tr').each( function (i) {
			                var nCloneTd = document.createElement( 'td' );
			                nCloneTd.className = "center";
			                nCloneTd.innerHTML =  initParam.actionHtml ;
			                this.insertBefore(  nCloneTd , this.childNodes[0] );
			                
			                $('span[actionName]',$(nCloneTd)).live('click', function (i) {
			                    eval($(this).attr('actionName')+"(oTable,this)");
			                });
			            } );
		            }
		            /* Add/remove class to a row when clicked on */
		            $('#example tbody tr').live('click', function() {
		                $(this).toggleClass('row_selected');
		            } );
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
	 			 var i=0;
	 			 var mDataObj = {};
	 			 var sortObj = {};
	 			for(var n=0;n<aoData.length;n++){
	 			    if(aoData[n].name == "iColumns"){
	 			       iMax = aoData[n].value;
	 			       continue;
	 			    }
	 			   if(aoData[n].name == ("mDataProp_"+ i)){
	 			       i++;
	 			      mDataObj["mDataProp_"+ i] = aoData[n].value;
	 			     continue;
	 			   }
	 			   i = 0;
	 			  if(aoData[n].name == ("iSortCol_"+ i)){
	 			      i++;
	 			     sortObj["mDataProp_"+ aoData[n].value] = "";
	 			    continue;
	 			  }
	 			 i = 0;
	 			  if(aoData[n].name == ("sSortDir_"+ i)){
	 			      i++;
	 			     sortObj["mDataProp_"+ aoData[n].value] = aoData[n].value;
	 			    continue;
	 			  }
	 			}
	 			 for(var p in sortObj){
	 			    aoData.push( { "name": p, "value": sortObj[p] } );
	 			 }
	 			$('#example thead tr th input[type="text"]').each( function (i) {
	 			  aoData.push( { "name": "filter['"+this.name+"']", "value": this.value } );
	 			});
	 			$('#example thead tr th select[name]').each( function (i) {
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
      
      /*
       * I don't actually use this here, but it is provided as it might be useful and demonstrates
       * getting the TR nodes from DataTables
       */
      function fnGetSelected( oTableLocal )
      {
          return oTableLocal.$('tr.row_selected');
      }
  </script>

  </body>

</html>