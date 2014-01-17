var MAX_COL = 3 ;
var nb_col = 0 ;
var rblColArray = new Array();
var lastCOL = "" ;

function rbl_col( _id ) { 

	var e_id    = _id ;
	var e_name   ; 

	var e_label  ;
	var e_layout ;
	var e_css_class  ;

	var e_width  ;
	var e_height ;

  this.saveForm = function() { 
		this.e_id		  = e_id  ;

		this.e_name 	=  jQuery("#c_name").val()  ;

		this.e_width	=  jQuery("#c_width").val()  ;
		this.e_height	=  jQuery("#c_height").val()  ;
		
		this.e_layout =  jQuery("#c_layout").val()  ;
		this.e_css_class  = jQuery("#c_css_class").val() ;
  } 

  this.showForm = function() { 
  	
		jQuery("#c_id").val(  e_id )  ;
		jQuery("#c_name").val( this.e_name )  ;
		
		jQuery("#c_width").val( this.e_width )  ;
		jQuery("#c_height").val( this.e_height )  ;
		
		jQuery("#c_css_class").val( this.e_css_class ) ;
		jQuery("#c_layout").val( this.e_layout ) ;
  } 
	
  this.drawCol = function() { 
		var ma_col ;
		
		ma_col = jQuery("#" + e_id) ;
		
		if ( ma_col.length == 0 ) {
			alert("colonne null !" ) ;
		}	
		else {
			ma_col.attr('id', e_id)
	  			  .attr('name',this.e_name) ;

			ma_col.removeClass( ) ;
			ma_col.addClass(this.e_css_class) ;
			ma_col.addClass('elt_head');
			ma_col.addClass('selected');
			
			ma_col.parent().css("width", this.e_width) ;
			ma_col.parent().css("height", this.e_height) ;
		 
			ma_col.parent().removeClass("cLab1 cLab2") ;
			ma_col.parent().addClass( this.e_layout ) ;
		 
	  }
	  
	}

  this.drawName = function() { 
		var str = "" ;

		str = " id='" + e_id + "'" ;
		
		if (this.e_name.length > 0 )
		{
			str = str +  " name='" + this.e_name + "'" ;
		}
		
		return str ;
	}
	
	
	this.startXml = function( ) { 
		var str = "" ;
		
		str = "<colonne id='" + e_id +"'" ;
		
		if ( this.e_width  && this.e_width.length > 0  ) str += " width='" + this.e_width + "'" ;
		if ( this.e_height && this.e_height.length > 0 ) str += " height='" + this.e_height + "'"  ;
		if ( this.e_layout && this.e_layout.length > 0 ) str += " layout='" + this.e_layout + "'" ;
		if ( this.e_name && this.e_name.length > 0 )     str +=  " name='" + this.e_name + "'" ;
		if ( this.e_css_class && this.e_css_class.length > 0 ) str += " class='" + this.e_css_class + "'" ;
									
		str += ">\r\n" ;
		
		return str ;	
	}	
	this.endXml = function( ) { 

		var str = "" ;
		
		str = "</colonne>\r\n" ;
		
		return str ;	
	}
	this.startHtml = function( ) { 
		var str = "" ;
		
		str = "<ul id='" + e_id +"'" ;
		
		if ( this.e_name && this.e_name.length > 0 )     str +=  " name='" + this.e_name + "'" ;
		
		str += " style='" ;
		if ( this.e_width  && this.e_width.length > 0  ) str += " width: " + this.e_width + ";" ;
		if ( this.e_height && this.e_height.length > 0 ) str += " height: " + this.e_height + ";"  ;
		str += "'" ;
		
		str += " class='connectedSortable" ;
		if ( this.e_layout && this.e_layout.length > 0 ) str += " "  + this.e_layout  ;
		if ( this.e_css_class && this.e_css_class.length > 0 ) str += " " +this.e_css_class ;
		str += "'" ;
		
		str += ">" ;
		
		lastCOL = str.replace( "id='" +e_id +"'" ,"id='xIDx'") ;
		
		return str ;	
	}	
	this.endHtml = function( ) { 
		var str = "" ;
		
		str = "</ul>" ;
		
		return str ;	
	}	
} 

function showColPanel( _no ) {
    jQuery("#dialog_col").load("panel/"+ LANG +"/dialog_col.html",
			function() { 
				rblColArray[ _no ].showForm() ;
				jQuery('#dialog_col').dialog('open');
			});
}

function saveColPanel(_no) {
	
	if ( rblColArray[ _no ]  != null) {
		rblColArray[ _no ].saveForm() ;
		rblColArray[ _no ].drawCol() ;
	} else {
		alert ("Colonne " + _no + " null") ; 
	}

}

function addCol(  ) {

	if (nb_col < MAX_COL) {
		jQuery(".connectedSortable ul")
		 .each( function(){
					jQuery(this).removeClass('p-' + nb_col + '-col') ;
					jQuery(this).addClass('p-' + (nb_col+1) + '-col') ;
				});
				
		nb_col = nb_col + 1 ;
		var id_col = nb_col ;
		
		if ( jQuery("#"   + IDFS).find("#sortable" + id_col).length != 0 ) {
			if ( jQuery("#" + IDFS).find("#sortable1" ).length == 0 ) id_col = 1 ; else
			if ( jQuery("#" + IDFS).find("#sortable2" ).length == 0 ) id_col = 2 ; else
			if ( jQuery("#" + IDFS).find("#sortable3" ).length == 0 ) id_col = 3 ;
	  }
	  
	  var _id = "f_col_" + id_col ;

		jQuery("#" + IDFS).append( "<ul id='sortable" + id_col + 
									 "' id_n='" + _id +
									 "' class='connectedSortable p-" + nb_col + 
									 "-col cLab1'><li id='" + _id +  
									 "' class='elt_head'><span><b>&nbsp;</b></span></li>" ) ;
	
		rblColArray[ _id ] = new rbl_col( _id );
	//	showColPanel( _id) ;
	
		initDrop() ;
		
		
	} 
	else {
		alert("exceed maxmium columns (" + MAX_COL + ") !" ) ;
  }
}

function supCol( _no ) {
	
	jQuery("#" + _no).parent( ).find('li')
											  .each( function(){ supElt( jQuery(this).attr('id') ) ;	});
											  
	jQuery("#" + _no).parent( ).fadeOut(500, function() { jQuery(this).remove(); });

	delete rblColArray[ _no ] ;

	jQuery(".connectedSortable")
	 .each( function(){
				jQuery(this).removeClass('p-' + nb_col + '-col') ;
				jQuery(this).addClass('p-' + (nb_col-1) + '-col') ;
			});
			
	nb_col = nb_col - 1 ;
	
}


function loadCol( elt, id_col ) {
	var _id = jQuery(elt).attr('id') ;
	
	rblColArray[ _id ] = new rbl_col( _id );

	rblColArray[ _id ].e_name =  jQuery(elt).attr('name') ; 
	rblColArray[ _id ].e_layout = jQuery(elt).attr('layout') ;
	rblColArray[ _id ].e_css_class = jQuery(elt).attr('class') ;

	rblColArray[ _id ].e_width = jQuery(elt).attr('width') ;
	rblColArray[ _id ].e_height = jQuery(elt).attr('height');
				
	jQuery("#" + IDFS).append( "<ul id='sortable" + id_col + 
									 "' id_n='" + _id +
									 "' class='connectedSortable " + rblColArray[ _id ].e_layout +
									 "'><li id='" + _id +  
									 "' class='elt_head'><span><b>&nbsp;</b></span></li>" ) ;
				
				
	rblColArray[ _id ].drawCol() ;
}