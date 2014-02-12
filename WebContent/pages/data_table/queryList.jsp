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
                     <table cellpadding="0" cellspacing="0" border="0" class="display table table-bordered" id="hidden-table-info">
                         <thead>
                         <tr>
                                <th>ID</th>
								<th>USERNAME</th>
								<th>AGE</th>
								<th>ADDRESS</th>
                         </tr>
                      </thead>
                      <tbody>
                          <tr><td colspan="4" class="dataTables_empty">Loading data from server</td></tr>
                      </tbody>
                         <tfoot>
							<tr>
								<th>ID</th>
								<th>USERNAME</th>
								<th>AGE</th>
								<th>ADDRESS</th>
							</tr>
						</tfoot>
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
          <%-- 
          /*
           * Insert a 'details' column to the table
           */
          var nCloneTh = document.createElement( 'th' );
          var nCloneTd = document.createElement( 'td' );
          nCloneTd.innerHTML = '<img src="<%=request.getContextPath()%>/jslib/flatlab/assets/advanced-datatable/examples/examples_support/details_open.png">';
          nCloneTd.className = "center";

          $('#hidden-table-info thead tr').each( function () {
              this.insertBefore( nCloneTh, this.childNodes[0] );
          } );

          $('#hidden-table-info tbody tr').each( function () {
              this.insertBefore(  nCloneTd.cloneNode( true ), this.childNodes[0] );
          } );
          
          /*
           * Initialse DataTables, with no sorting on the 'details' column
           */
          var oTable = $('#hidden-table-info').dataTable( {
              "aoColumnDefs": [
                  { "bSortable": false, "aTargets": [ 0 ] }
              ],
              "aaSorting": [[1, 'asc']]
          });

          /* Add event listener for opening and closing details
           * Note that the indicator for showing which row is open is not controlled by DataTables,
           * rather it is done here
           */
          $('#hidden-table-info tbody td img').live('click', function () {
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
     --%>
     
     /*
      * Initialse DataTables, with no sorting on the 'details' column
      */
     var oTable = $('#hidden-table-info').dataTable( {
         "bProcessing": true,
 		 "bServerSide": true,
 		 "iDisplayLength":10,
 		 "sAjaxSource": "<%=request.getContextPath()%>/datatable/queryList.action",
         "aaSorting": [[1, 'asc']],
         "aoColumns": [
              { "mData": "id", "sTitle":"ID" },
              { "mData": "username", "sTitle":"USERNAME"  },
              { "mData": "age", "sTitle":"AGE"  },
              { "mData": "address", "sTitle":"ADDRESS"  }
          ]
     });
      } );
  </script>


  </body>

</html>