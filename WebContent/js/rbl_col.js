var MAX_COL = 10 ;
var nb_col = 0 ;
var rblColArray = new Array();
var lastCOL = "" ;

function rbl_col( _id ) { 

	this.e_id    = _id ;
	this.e_name   ; 

	this.e_label  ;

	this.e_width  ;
	this.e_height ;

  this.saveForm = function() { 
		this.e_id		  = jQuery("#e_id").val();
		this.e_name 	=  jQuery("#e_name").val();
		this.e_width	=  jQuery("#e_width").val();
		this.e_height	=  jQuery("#e_height").val();
		
  } 

  this.showForm = function() { 
  	
		jQuery("#e_id").val(this.e_id )  ;
		jQuery("#e_name").val( this.e_name )  ;
		
		jQuery("#e_width").val( this.e_width )  ;
		jQuery("#e_height").val( this.e_height )  ;
  } 
	
  this.drawCol = function() { 
		var ma_col = jQuery("#" + e_id) ;
		
		if ( ma_col.length == 0 ) {
			alert("colonne null !" ) ;
		}	
		else {
			ma_col.attr('id', e_id)
	  			  .attr('name',this.e_name) ;
			
			ma_col.parent().css("width", this.e_width) ;
			ma_col.parent().css("height", this.e_height) ;
		 
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
    jQuery("#dialog_col").load("pages/panel_dialog/dialog_col.html",
			function() { 
				rblColArray[ _no ].showForm();
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
    
}

function supCol( _no ) {
	
	jQuery("#" + _no).parent( ).find('li').each( function(){ supElt( jQuery(this).attr('id') ) ;	});
											  
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
	rblColArray[ _id ].e_width = jQuery(elt).attr('width') ;
	rblColArray[ _id ].e_height = jQuery(elt).attr('height');
				
	jQuery("#" + IDFS).append( "<fieldset><legend>g1</legend><ul "+
									 "class='connectedSortable"+ "'><li id='" + _id + "'><span><b>&nbsp;</b></span></li></fieldset>" ) ;
				
				
	rblColArray[ _id ].drawCol() ;
}