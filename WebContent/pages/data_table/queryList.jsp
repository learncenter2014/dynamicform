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
      function fnFormatDetails ( oTable, nTr )
      {
          var aData = oTable.fnGetData( nTr );
          var sOut = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';
          sOut += '<tr><td>Rendering engine:</td><td>'+aData[1]+' '+aData[4]+'</td></tr>';
          sOut += '<tr><td>Link to source:</td><td>Could provide a link here</td></tr>';
          sOut += '<tr><td>Extra info:</td><td>And any further details here (images etc)</td></tr>';
          sOut += '</table>';

          return sOut;
      }

      $(document).ready(function() {
          <%-- --%>
     
     var tableUrl = "<%=request.getContextPath()%>/datatable/initTable.action";
     var param = {};
     $.getJSON( tableUrl, param, function (initParam) { 
     
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
	 		 
	 		"fnDrawCallback": function ( oSettings ) {
	            var that = this;
	 
	            /* Need to redo the counters if filtered or sorted */
	            if ( oSettings.bSorted || oSettings.bFiltered )
	            {
	                this.$('td:first-child', {"filter":"applied"}).each( function (i) {
	                  //  that.fnUpdate( i+3, this.parentNode, 0, false, false );
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
	 			 oSettings.jqXHR = $.ajax( {
	                 "dataType": 'json',
	                 "type": "POST",
	                 "url": sSource,
	                 "data": aoData,
	                 "success": function(result,status,response){
	                    // Do whatever additional processing you want on the callback, then tell DataTables
	                    /*
	                    var headerList = initParam.aoColumns;
	                     if(headerList == null || headerList == 'undefined'|| headerList.length == 0){
	                         return;
	                     }
	                     
	                     var hasFilter = false;
	                     for(var i = 0; i < headerList.length; i++){
	                         var header = headerList[i];
	                         if(header.bSearchable){
	                             hasFilter = true;
	                             break;
	                         }
	                     }
	                  */
	                     fnCallback(result);
	                     /*
	                     var nCloneTr = document.createElement( 'tr' );
	                     var nCloneTd = document.createElement( 'td' );
	                     nCloneTd.className = "center";
	
	                     $('#example thead').each( function (i) {
	                         this.insertBefore( nCloneTr, this.childNodes[0] );
	                     } );
	
	                     $('#example thead tr').each( function (i) {
	                         for(var i = 0; i < headerList.length; i++){
	                             var header = headerList[i];
	                             if(header.bSearchable){
	                                  
	                             }
	                             this.insertBefore(  nCloneTd.cloneNode( true ), this.childNodes[0] );
	                         }
	                         return false;
	                     } );
	                     */
	                 }
	               } );
	 			//========method two END==================   
	          }
	        });
 	} );
     
     /*
      * Insert a 'details' column to the table
      */
     var nCloneTh = document.createElement( 'th' );
     var nCloneTd = document.createElement( 'td' );
     nCloneTd.innerHTML = '<img src="<%=request.getContextPath()%>/jslib/flatlab/assets/advanced-datatable/examples/examples_support/details_open.png">';
     nCloneTd.className = "center";

     $('#example thead tr').each( function () {
         this.insertBefore( nCloneTh, this.childNodes[0] );
     } );

     $('#example tbody tr').each( function () {
         this.insertBefore(  nCloneTd.cloneNode( true ), this.childNodes[0] );
     } );
     
     /* Add event listener for opening and closing details
      * Note that the indicator for showing which row is open is not controlled by DataTables,
      * rather it is done here
      */
     $('#example tbody td img').live('click', function () {
         var nTr = $(this).parents('tr')[0];
         if ( oTable.fnIsOpen(nTr) )
         {
             /* This row is already open - close it */
             this.src = "<%=request.getContextPath()%>/jslib/flatlab/assets/advanced-datatable/examples/examples_support/details_open.png";
             oTable.fnClose( nTr );
         }
         else
         {
             /* Open this row */
             this.src = "<%=request.getContextPath()%>/jslib/flatlab/assets/advanced-datatable/examples/examples_support/details_close.png";
             oTable.fnOpen( nTr, fnFormatDetails(oTable, nTr), 'details' );
         }
     } );
     
     /* Add/remove class to a row when clicked on */
     $('#example tr').live('click', function() {
         $(this).toggleClass('row_selected');
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