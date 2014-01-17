
if (navigator.browserLanguage)
  var xlang = navigator.browserLanguage;
else
  var xlang = navigator.language;

var LANG = 'us' ;
if (xlang.indexOf('fr') > -1) LANG = 'fr' ;


function deleteElement( ) {
	var _no = jQuery(selected).attr('id')   ;

	if ( _no.indexOf("f_col_") == 0) {
		supCol ( _no ) ;
	}
	else {
		supElt( _no ) ;
	}
}


function initDrop( ) {
	jQuery("ul.connectedSortable")
		.sortable({
			connectWith: '.connectedSortable',
			items: 'li.elt'
		 })
		.disableSelection()
		.resizable(
		{
			stop: function(event, ui) { 
				var _id = jQuery(this).attr("id_n") ; 
				if (rblColArray[ _id ]) {
					rblColArray[ _id ].e_width  = jQuery(this).css("width") ;
					rblColArray[ _id ].e_height = jQuery(this).css("height") ;
				}
			}
		});
		
	jQuery('ul#rbMenu li').draggable(
		{
		cursor: 'move',
	  	helper: 'clone',
		opacity: '0.5', 
		zIndex: 1000,
	  	revert: 'invalid'
		}
	);
	
	jQuery('.connectedSortable li').droppable(
	  {
		cursor: 'move',
		accept: 'ul#rbMenu li', 
		helper: 'clone', 
		opacity: '0.5', 
		drop: function(event, ui) {
			addNewElement( ui, this ) ;
		}
	  }
	);
	
}


/**
 * saveInputPanel
 */
function saveInputPanel(_no) {

	if ( rblEltArray[ _no]  != null) {
		rblEltArray[ _no].saveForm() ;
	} else {
		alert ("elt " + _no + " null") ; 
	}

	changeInput( _no ) ;
}


function showControlPanel( _no ) {
	if (  !_no  || _no.length < 1) {
		 _no = jQuery(selected).attr('id')  ;
	}
	
	if ( _no.indexOf("f_col_") != 0) 
	{
		if (_no.length > 2) {
			if (_no.charAt(0) == 'f' && _no.charAt(1) == '_' ) {
				_no = _no.substr(2) ;
			}
		}
	}
	
	if ( _no.indexOf("f_col_") == 0)  
	{
    jQuery("#dialog_col").load("panel/"+ LANG +"/dialog_col.html",
			function() { 
				jQuery("#e_id").val( _no ) ;
				rblColArray[ _no].showForm() ;
				jQuery('#dialog_col').dialog('open');
			});
	} 
	else 
	{
		var typeToAdd = "" ;
		if ( rblEltArray[ _no]  != null) 
		{
			typeToAdd = rblEltArray[ _no ].getType() ;
		}

		typeToAdd = typeToAdd.toLowerCase() ;
		if (typeToAdd.length == 0) 	 typeToAdd = "text"  ;
		if (typeToAdd == "checkbox") typeToAdd = "radio" ;
		if (typeToAdd == "password") typeToAdd = "text"  ;
		if (typeToAdd == "file") 	   typeToAdd = "text"  ;

    jQuery("#dialog_elt").load("panel/"+ LANG +"/input_" + typeToAdd + ".html",
						  function() { 
							jQuery("#e_id").val( _no ) ;
							if ( rblEltArray[ _no]  != null) 
							{
								rblEltArray[ _no].showForm() ;
							}
							jQuery(this).dialog('open');
						  });
	}
}


function enableRowSelectable(id) {
	    jQuery(id).live("click", 
			function(){
				jQuery(selected).removeClass("selected");
				jQuery(this).addClass("selected");
				selected = this;
			});

}

function escapeH( ss ) {
	var str = ss ;
	var findReplace = [[/&/g, "&amp;"], [/</g, "&lt;"], [/>/g, "&gt;"], [/"/g, "&quot;"]]

	for(item in findReplace)
		str = str.replace(item[0], item[1]);

	return str ;
}