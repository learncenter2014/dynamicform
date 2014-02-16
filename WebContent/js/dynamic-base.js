function deleteControlPanel(id) {
    jQuery.dynamicplugin.deleteElement(id);
}

function initDrop() {
    jQuery("form.connectedSortable")
    .sortable({
            connectWith: 'form.connectedSortable',
            items: 'fieldset.connectedSortable'
     });
    
    jQuery("fieldset.connectedSortable")
    .sortable({
            connectWith: 'fieldset.connectedSortable',
            items: 'div.connectedSortable'
     });
    
    jQuery('ul#rbMenu li,#FieldSet').draggable({
        cursor : 'move',
        helper : 'clone',
        opacity : '0.5',
        zIndex : 1000,
        revert : 'invalid'
    });
    jQuery('form.connectedSortable').droppable({
        cursor : 'move',
        accept : '#FieldSet',
        helper : 'clone',
        opacity : '0.5',
        drop : function(event, ui) {
            jQuery.dynamicplugin.addNewElement(ui, this);
        }
    });
    jQuery('fieldset.connectedSortable').droppable({
        cursor : 'move',
        accept : 'ul#rbMenu li',
        helper : 'clone',
        opacity : '0.5',
        drop : function(event, ui) {
            jQuery.dynamicplugin.addNewElement(ui, this);
        }
    });
    
    jQuery('.operation>.fa.fa-edit').click(function() {
        showControlPanel(jQuery(this).parent().attr("id").substring("operation_".length));
    });
    jQuery('.operation>.fa.fa-cut').click(function() {
        deleteControlPanel(jQuery(this).parent().attr("id").substring("operation_".length));
    });
    
}

/**
 * saveInputPanel
 */
function saveInputPanel(_no) {
    jQuery.dynamicplugin.updateElement(_no);
}

function showControlPanel(_no) {
    var typeToAdd = "";
    if (jQuery.dynamicplugin.elementArray[_no] != null) {
        typeToAdd = jQuery.dynamicplugin.elementArray[_no].type;
    }

    typeToAdd = typeToAdd.toLowerCase();
    if (typeToAdd.length == 0)
        typeToAdd = "text";
    if (typeToAdd == "checkbox")
        typeToAdd = "radio";
    if (typeToAdd == "password")
        typeToAdd = "text";
    if (typeToAdd == "file")
        typeToAdd = "text";

    jQuery("#dialog_elt").load(
            "pages/panel_dialog/input_" + typeToAdd + ".html", function() {
                jQuery("#e_id").val(_no);
                if (jQuery.dynamicplugin.elementArray[_no] != null) {
                    jQuery.dynamicplugin.elementArray[_no].showElement();
                }
                jQuery(this).dialog('open');
            });
}

function escapeH(ss) {
    var str = ss;
    var findReplace = [ [ /&/g, "&amp;" ], [ /</g, "&lt;" ], [ />/g, "&gt;" ],
            [ /"/g, "&quot;" ] ]

    for (item in findReplace)
        str = str.replace(item[0], item[1]);

    return str;
}


function load_xml( surl ) { 
    var htmlContent = jQuery.dynamicplugin.parseXml(surl);
    jQuery("#maindynamicform").html(htmlContent);
    initDrop();
}

/**
 * xml and html
 */ 
function save_xml( surl ) { 
    jQuery.dynamicplugin.saveXml(surl);
}

/**
 * show file panel
 */
function showFilePanel( ) {

    jQuery("#dialog_file").load("template/templateFormlist.action",
            function() { 
                jQuery('#dialog_file').dialog('open');
            });
}

function createForm(){
    var htmlContent = jQuery.dynamicplugin.createForm({type:'form'});
    jQuery("#maindynamicform").html(htmlContent);
    showControlPanel(jQuery.dynamicplugin.formId);
    initDrop();
}

function editForm(){
    var formId = jQuery.dynamicplugin.formId;
    if(!formId){
        alert("Please create a form or open a file in control pannel.");
        return;
    }else{
        showControlPanel(formId);
    }
}