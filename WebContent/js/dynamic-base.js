function deleteControlPanel() {
    if (jQuery.dynamicplugin.selected) {
        var _no = jQuery(jQuery.dynamicplugin.selected).attr('id');

        if (!!_no) {
            //remove cache data from memory.
            delete jQuery.dynamicplugin.elementArray[_no.substring("img_".length)];
            //remove DOM node from DOM tree of Form.
            jQuery("#block_"+_no.substring("img_".length)).remove();
        }
    }
}

function initDrop() {

    jQuery('ul#rbMenu li,#FieldSet').draggable({
        cursor : 'move',
        helper : 'clone',
        opacity : '0.5',
        zIndex : 1000,
        revert : 'invalid'
    });
    jQuery('#maindynamicform').droppable({
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
            event.stopPropagation();
            jQuery.dynamicplugin.addNewElement(ui, this);
        }
    });
}

/**
 * saveInputPanel
 */
function saveInputPanel(_no) {
    var element = jQuery.dynamicplugin.elementArray[_no];
    if (element != null) {
        element.saveElement();
        var elementInstance = jQuery.dynamicplugin.createElementFactory(element);
        var outputHtml = elementInstance.toHtml();
        var blockId = "#block_" + _no;
        jQuery(blockId).replaceWith(outputHtml);
        if(element.type=='fieldset'){
            jQuery(blockId).resizable({
                stop : function(event, ui) {
                    var fieldsetObj = jQuery.dynamicplugin.elementArray[_no];
                    if (fieldsetObj) {
                        if ( ui.size.width  != ui.originalSize.width)  {
                            fieldsetObj.width  = (ui.size.width);
                        }
                        if ( ui.size.height  != ui.originalSize.height)  {
                            fieldsetObj.height = (ui.size.height);
                        }
                    }
                }
         });
       }
    } else {
        alert("This element id " + _no + " is empty!");
    }
}

function showControlPanel(_no) {
    if (!_no || _no.length < 1) {
        _no = jQuery(jQuery.dynamicplugin.selected).attr('id');
        _no = _no.substring("img_".length);
    }
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

function enableRowSelectable(id) {
    jQuery(id).click(function() {
        if (jQuery.dynamicplugin.selected) {
            jQuery(jQuery.dynamicplugin.selected).removeClass("selected");
        }
        jQuery(this).addClass("selected");
        jQuery.dynamicplugin.selected = this;
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

