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
            items: 'div.connectedSortable.section'
        });

    jQuery("div.section.connectedSortable")
        .sortable({
            connectWith: 'div.section.connectedSortable',
            items: 'section.panel.panel-body'
        });

    jQuery('ul#rbMenu li,#FieldSet,#Row').draggable({
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
        accept : '#Row',
        helper : 'clone',
        opacity : '0.5',
        drop : function(event, ui) {
            jQuery.dynamicplugin.addNewElement(ui, this);
        }
    });

    jQuery('section.panel.panel-body').droppable({
        cursor : 'move',
        accept : 'ul#rbMenu li',
        helper : 'clone',
        opacity : '0.5',
        drop : function(event, ui) {
            jQuery.dynamicplugin.addNewElement(ui, this);
        }
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

function load_xml( options ) { 
    var htmlContent = jQuery.dynamicplugin.parseXml(options);
    jQuery("#maindynamicform").html(htmlContent);
    initDrop();
}

/**
 * xml and html
 */ 
function save_xml() {
    if(jQuery.dynamicplugin.formId==null){
        jQuery("#dialog_message").html('<span style="color:red">请创建新的模版或者打开已创建的模版!</span>');
        jQuery("#dialog_message").dialog(); 
    }else{
        jQuery.dynamicplugin.saveXml();
    }
    
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