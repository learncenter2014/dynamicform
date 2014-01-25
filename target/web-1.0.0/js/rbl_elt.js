var rblEltArray = new Array();
var nbelt = 0;
/**
 * Element controller and draw this element by the type of element.
 * 
 * @param _id
 * @param _type
 * @returns
 */
function rbl_elt(_id, _type) {

    this.e_id    = _id ;
    this.e_type  = _type.toLowerCase() ;
    this.e_name  = _id ; 
    
    this.e_label = "" ;
    this.e_size  = "10" ;

    this.e_typeint  = "S" ;
    this.e_val_liste = "" ;

    this.e_mask     = "" ;
    this.e_freemask = "" ;
    this.e_regexp   = "" ;
    
    this.e_maxlength  = "" ;
    this.e_accesskey  = "" ;
    
    this.e_help_text  = "" ;
    this.e_val_defaut = "" ;
    
    this.e_memorytable = "" ;
    this.e_group       = "" ;
    this.e_css_class   = "" ;
    
    this.e_required = false ;
    this.e_readonly = false ;
    this.e_autoskip = true ;

    this.getType = function() {
        var s = this.e_type;
        return s;
    }

    this.saveForm = function() {

        this.e_name = jQuery("#name").val();
        this.e_label = jQuery("#titre").val();
        this.e_size = jQuery("#size").val();

        this.e_typeint = jQuery("#type_interne").val();

        this.e_mask = jQuery("#mask").val();
        this.e_freemask = jQuery("#freemask").val();
        this.e_regexp = jQuery("#regexp").val();
        this.e_maxlength = jQuery("#maxlength").val();
        this.e_accesskey = jQuery("#accesskey").val();
        this.e_val_liste = jQuery("#val_liste").val();

        this.e_val_defaut = jQuery("#val_defaut").val();
        this.e_help_text = jQuery("#help_text").val();
        this.e_group = jQuery("#group").val();
        this.e_css_class = jQuery("#css_class").val();

        this.e_required = jQuery("#required").attr('checked');
        this.e_autoskip = jQuery("#autoskip").attr('checked');
        this.e_readonly = jQuery("#readonly").attr('checked');
    }

    this.showForm = function() {
        jQuery("#type_html").val(this.e_type);

        jQuery("#name").val(this.e_name);
        jQuery("#titre").val(this.e_label);
        jQuery("#size").val(this.e_size);
        jQuery("#type_interne").val(this.e_typeint);

        jQuery("#mask").val(this.e_mask);
        jQuery("#freemask").val(this.e_freemask);
        jQuery("#regexp").val(this.e_regexp);
        jQuery("#maxlength").val(this.e_maxlength);
        jQuery("#accesskey").val(this.e_accesskey);

        jQuery("#val_liste").val(this.e_val_liste);

        jQuery("#val_defaut").val(this.e_val_defaut);
        jQuery("#help_text").val(this.e_help_text);
        jQuery("#group").val(this.e_group);
        jQuery("#css_class").val(this.e_css_class);

        jQuery("#required").attr('checked', this.e_required);
        jQuery("#autoskip").attr('checked', this.e_autoskip);
        jQuery("#readonly").attr('checked', this.e_readonly);
    }

    this.drawElement = function() {
        var str = "";
        str = this.drawElement0(false);
        return str;
    }
    this.drawElement0 = function(isHt) {
        var str = "";

        str = str + this.drawBeforeElt(isHt);

        switch (this.e_type.toUpperCase()) {
        case 'WIZARD':
            str = this.drawWizard(isHt);
            break;
        case 'IMAGE':
            str = this.drawImage();
            break;
        case 'LABEL':
            str = this.drawText() + "&nbsp;";
            break;
        case 'PASSWORD':
        case 'FILE':
        case 'TEXT':
            str = str + this.drawInputText();
            break;
        case 'BUTTON':
            str = this.drawButton(isHt) + "&nbsp;";
            break;
        case 'TEXTAREA':
            str = str + this.drawTextarea();
            break;
        case 'DATE':
            str = str + this.drawDate(isHt);
            break;
        case 'RADIO':
        case 'CHECKBOX':
            str = str + this.drawRadio();
            break;
        case 'SELECT':
        case 'LISTE':
            str = str + this.drawListe();
            break;
        default:
        }

        str = str + this.drawAfterElt(isHt);

        return str;
    }

    this.drawBeforeElt = function(isHt) {
        var str = "";

        str = this.drawLabel(isHt);

        return str;
    }
    this.drawAfterElt = function(isHt) {
        var str = "";
        var reg = new RegExp('\'', "g");

        if (this.e_help_text && this.e_help_text.length > 0
                && this.e_type != "wizard" && this.e_type != "label"
                && this.e_type != "image" && this.e_type != "button") {
            str = "<img class='imghelp'  src='img/rbl/help-browser.png' border='0' title='"
                    + this.e_help_text.replace(reg, "`")
                    + "' alt='"
                    + this.e_help_text.replace(reg, "`")
                    + "' width='16' height='16' hspace='10'>";
        }

        return str;
    }

    this.drawText = function() {
        var str = "";

        str += "<label" + this.drawClass() + ">" + this.drawAide() + "</label>";

        return str;
    }

    this.drawButton = function(isHt) {
        var str = "";

        str = "<input type='submit' " + this.drawName() + this.drawSize()
                + this.drawClass() + " value='" + this.drawValue() + "'";

        if (isHt == false)
            str += " onclick='return false;'";

        str += "/>";
        return str;
    }

    this.drawImage = function() {
        var str = "";

        str = "<img border='0' src='" + this.e_label + "' " + this.drawClass()
                + "/>";
        return str;
    }

    this.drawInputText = function() {
        var str = "";

        str = "<input type='" + this.e_type + "' " + this.drawName()
                + this.drawAutoskip() + this.drawReadonly()
                + this.drawRequired() + this.drawSize() + this.drawMaxlength()
                + this.drawMask() + this.drawClass() + "/>";
        return str;
    }

    this.drawDate = function(isHt) {
        var str = "<input class='ui-datepicker-trigger' type='text' " + this.drawName() + this.drawAutoskip()
                + this.drawReadonly() + this.drawRequired() + this.drawSize()
                + this.drawMaxlength() + this.drawMask() + this.drawClass()
                + "/>";
        return str;
    }

    this.drawWizard = function(isHt) {
        var str = "";
        var ss;

        if (isHt && this.e_type == "wizard") {
            str += "</li></ul><div class='btWiz'>";
        }

        /* bouton previous */
        if (this.e_autoskip == true) {
            if (this.e_val_defaut && this.e_val_defaut.length > 0) {
                ss = this.e_val_defaut;
            } else {
                ss = "Prev";
            }
            str += "<input type='button' class='btP' value='" + ss
                    + "' onclick=\"javascript:rbl_onoff('" + PRVFS + "');\">";
        }

        PRVFS = CURFS;
        CURFS = 'x' + this.e_id;

        /* bouton Next */
        if (this.e_readonly == true) {
            if (this.e_val_liste && this.e_val_liste.length > 0) {
                ss = this.e_val_liste;
            } else {
                ss = "Next";
            }
            str += "<input type='button' class='btN' value='" + ss
                    + "' onclick=\"javascript:rbl_onoff('" + CURFS + "');\">";
        }
        if (isHt) {
            str += "</div>";
            if (this.e_readonly == true) {
                str += "</fieldset><fieldset id='x" + this.e_id
                        + "' style='display:none;'>";
                if (this.e_label && this.e_label.length > 0) {
                    str += "<legend>" + this.e_label + "</legend>";
                }
                str += lastCOL.replace('xIDx', 'c' + this.e_id);
            }
        }

        return str;
    }

    this.drawListe = function() {
        var str = "";
        var elt = this.e_val_liste.split(";");
        var grpOpen = false;

        str = "<select " + this.drawName() + this.drawReadonly()
                + this.drawRequired() + this.drawSize() + this.drawClass()
                + ">";

        for (var k = 0; k < elt.length; k++) {
            var val = elt[k].split("=");
            if (val.length > 1) {
                if (val[0] == "$G$") {
                    if (grpOpen) {
                        str += "</optgroup>";
                    }
                    str += "<optgroup label='" + val[1] + "'>";
                    grpOpen = true;
                } else {
                    str += "<option value='" + val[0] + "' > " + val[1]
                            + "</option>";
                }
            }
        }
        if (grpOpen) {
            str += "</optgroup>";
            grpOpen = false;
        }

        str = str + "</select>";

        return str;
    }

    this.drawTextarea = function() {
        var str = "";

        str += "<textarea " + this.drawName() + this.drawReadonly()
                + this.drawRequired() + this.drawSizeArea() + this.drawClass()
                + "></textarea>";

        return str;
    }

    this.drawRadio = function() {
        var str = "";
        var elt = this.e_val_liste.split(";");

        for (var k = 0; k < elt.length; k++) {
            var val = elt[k].split("=");
            if (val.length > 1) {
                str = str + "<input type='" + this.e_type + "' " + this.drawName()
                        + this.drawReadonly() + this.drawRequired()
                        + this.drawClass() + "value='" + val[0] + "' /> "
                        + val[1];
                if (this.e_autoskip)
                    str += "<br/>";
                else
                    str += " &nbsp; ";
            }
        }

        return str;
    }

    this.drawName = function() {
        var str = "";

        str = " id='" + this.e_id + "'";

        if (this.e_name && this.e_name.length > 0) {
            str = str + " name='" + this.e_name + "' ";
        }

        return str;
    }

    this.drawValue = function() {
        var str = "";

        if (this.e_val_defaut && this.e_val_defaut.length > 0) {
            str = str + this.e_val_defaut;
        }

        return str;
    }

    this.drawMask = function() {
        var str = "";

        if (this.e_mask && this.e_mask.length > 0) {
            str = " mask='" + this.e_mask + "' ";
        }

        if (this.e_freemask && this.e_freemask.length > 0) {
            str = " freemask='" + this.e_freemask + "' ";
        }

        if (this.e_regexp && this.e_regexp.length > 0) {
            str = " regexp='" + this.e_regexp + "' ";
        }

        return str;
    }

    this.drawSizeArea = function() {
        var str = "";

        if (this.e_size && this.e_size.length > 0) {
            str = " rows='" + this.e_size + "' ";
        }
        if (this.e_maxlength && this.e_maxlength.length > 0) {
            str = " cols='" + this.e_maxlength + "' ";
        }

        return str;
    }

    this.drawSize = function() {
        var str = "";

        if (this.e_size && this.e_size.length > 0) {
            str = " size='" + this.e_size + "' ";
        }

        return str;
    }

    this.drawValListe = function() {
        var str = "";

        if (this.e_val_liste && this.e_val_liste.length > 0) {
            str += " val_liste='";

            var elt = this.e_val_liste.split(";");

            for (var k = 0; k < elt.length; k++) {
                var val = elt[k].split("=");
                if (val.length > 1) {
                    str += val[0] + "=" + val[1] + ";";
                }
            }
            str += "'";
        }

        return str;
    }

    this.drawMaxlength = function() {
        var str = "";

        if (this.e_maxlength && this.e_maxlength.length > 0) {
            str = " maxlength='" + this.e_maxlength + "' ";
        }

        return str;
    }

    this.drawLabel = function(isHt) {
        var str = "";

        if (isHt == false) {
            str += "<img src='img/arrow.png' alt='move' width='16' height='16' class='handle' />";
        }

        str += "<label for='" + this.e_id + "'" + this.drawAccesskey() + ">";
        if (this.e_accesskey && this.e_accesskey.length > 0) {
            str += this.e_label.replace(this.e_accesskey, "<u>"
                    + this.e_accesskey + "</u>");
        } else {
            str += this.e_label;
        }

        if (this.e_required == true) {
            str += "<img class='imgstar' src='img/rbl/star.png' border='0' alt='obligatoire' title='obligatoire' hspace='0'>";
        } else {
            str += "<img class='imgstar' src='img/rbl/star_transparent.png' border='0' hspace='0'>";
        }
        str += "</label>";
        if (isHt)
            str += "\r\n  ";

        return str;
    }

    this.drawAccesskey = function() {
        var str = "";

        if (this.e_accesskey && this.e_accesskey.length > 0) {
            str = " accesskey='" + this.e_accesskey + "' ";
        }

        return str;
    }

    this.drawClass = function() {
        var str = "";

        if (this.e_css_class && this.e_css_class.length > 0) {
            str = " class='" + this.e_css_class + "' ";
        }

        return str;
    }

    this.drawRequired = function() {
        var str = "";

        if (this.e_required == true) {
            str = " required='Y' ";
        }

        return str;
    }

    this.drawReadonly = function() {
        var str = "";

        if (this.e_readonly == true) {
            str = " readonly='Y' ";
        }

        return str;
    }

    this.drawAutoskip = function() {
        var str = "";

        if (this.e_autoskip == true) {
            str = " autoskip='O' ";
        }

        return str;
    }

    this.drawAide = function(isHtml) {
        var str = "";

        if (this.e_help_text && this.e_help_text.length > 0) {
            if (isHtml) {
                str = " a faire ";
            } else {
                str = str
                        + this.e_help_text.replace(/^\s+/g, '').replace(
                                /\s+$/g, '');
            }
        }

        return str;
    }

    this.drawXml = function(_col, _ligne) {
        var str = "";

        str = "<element type_html='" + this.e_type + "'" + " idco='" + _col
                + "' idli='" + _ligne + "' ";

        if (this.e_label && this.e_label.length > 0)
            str += " label='" + this.e_label + "' ";

        str += this.drawName() + this.drawAutoskip() + this.drawReadonly()
                + this.drawRequired() + this.drawSize() + this.drawMaxlength()
                + this.drawMask() + this.drawClass() + this.drawAccesskey()
                + this.drawValListe() + " val_defaut='" + this.drawValue()
                + "'" + ">" + this.drawAide(false) + "</element>";

        return str;
    }

}

/**
 * change all values in input form element
 */
function changeInput(_no) {

    var typeToAdd = rblEltArray[_no].getType();
    var eltToAdd = rblEltArray[_no].drawElement();

    jQuery("#f_" + _no).html(eltToAdd);

    if (typeToAdd == 'date') {
        jQuery("#f_" + _no).find("input").datepicker({
            showOn : 'button',
            buttonImage : 'img/rbl/_cal.gif',
            buttonImageOnly : true,
            dateFormat : rblEltArray[_no].e_mask.replace(/yy/g, "y"),
            showMonthAfterYear : false
        });
    }

}

/**
 * Suppression d'un element
 */
function supElt(_no) {

    if (_no && _no.length > 2) {
        if (_no.charAt(0) == 'f' && _no.charAt(1) == '_') {
            _no = _no.substr(2);
        }
    }

    jQuery("#f_" + _no).fadeOut(500, function() {
        jQuery(this).remove();
    });
    delete rblEltArray[_no];
}

/**
 * add a new element that is associated with orignal element
 */
function addNewElement(ui, dest) {
    var typeToAdd = ui.draggable.attr('id');

    nbelt++;
    var _id = "nb_" + nbelt;
    var eltToAdd = "";

    switch (typeToAdd) {
    case 'Menu':
        eltToAdd = "<select></select>";
        break;
    case 'Button':
        eltToAdd = "<input type=button size=15 value=''>";
        break;
    default:
        eltToAdd = "<input type=text size=15 value=''>";
        break;
    }

    jQuery(dest).parent().append("<li id='f_" + _id + "' class='elt'>"
                            + "<img src='img/arrow.png' alt='move' width='16' height='16' class='handle' />" + eltToAdd);

    rblEltArray[_id] = new rbl_elt(_id, typeToAdd);

    showControlPanel("f_" + _id);
    
}

function loadElement(elt, ncol, nbel) {
    var _id = "nb_" + nbel;

    rblEltArray[_id] = new rbl_elt(_id, jQuery(elt).attr('type_html'));
    rblEltArray[_id].e_id = _id;
    rblEltArray[_id].e_type = ((jQuery(elt).attr('type_html')) ? jQuery(elt)
            .attr('type_html').toLowerCase() : "text");
    rblEltArray[_id].e_name = ((jQuery(elt).attr('name')) ? jQuery(elt).attr(
            'name') : _id);
    rblEltArray[_id].e_label = ((jQuery(elt).attr('label')) ? jQuery(elt).attr(
            'label') : "");
    rblEltArray[_id].e_size = ((jQuery(elt).attr('size')) ? jQuery(elt).attr(
            'size') : "");

    rblEltArray[_id].e_typeint = ((jQuery(elt).attr('type_interne')) ? jQuery(
            elt).attr('type_interne') : "");

    rblEltArray[_id].e_mask = ((jQuery(elt).attr('mask')) ? jQuery(elt).attr(
            'mask') : "");
    rblEltArray[_id].e_freemask = ((jQuery(elt).attr('freemask')) ? jQuery(elt)
            .attr('freemask') : "");
    rblEltArray[_id].e_regexp = ((jQuery(elt).attr('regexp')) ? jQuery(elt)
            .attr('regexp') : "");
    rblEltArray[_id].e_maxlength = ((jQuery(elt).attr('maxlength')) ? jQuery(
            elt).attr('maxlength')
            : ((jQuery(elt).attr('max_length')) ? jQuery(elt)
                    .attr('max_length') : ""));
    rblEltArray[_id].e_accesskey = ((jQuery(elt).attr('accesskey')) ? jQuery(
            elt).attr('accesskey') : "");
    rblEltArray[_id].e_val_liste = ((jQuery(elt).attr('val_liste')) ? jQuery(
            elt).attr('val_liste') : "");

    rblEltArray[_id].e_val_defaut = ((jQuery(elt).attr('val_defaut')) ? jQuery(
            elt).attr('val_defaut') : "");
    rblEltArray[_id].e_css_class = ((jQuery(elt).attr('class')) ? jQuery(elt)
            .attr('class') : "");
    rblEltArray[_id].e_help_text = jQuery(elt).text();

    var zz = jQuery(elt).attr('autoskip');
    rblEltArray[_id].e_autoskip = (zz == 'O' || zz == 'Y');

    zz = jQuery(elt).attr('required');
    zz = ((jQuery(elt).attr('required')) ? jQuery(elt).attr('required')
            : jQuery(elt).attr('obligatoire'));
    rblEltArray[_id].e_required = (zz == 'O' || zz == 'Y');

    zz = jQuery(elt).attr('readonly');
    rblEltArray[_id].e_readonly = (zz == 'O' || zz == 'Y');

    jQuery('#sortable' + ncol).append("<li id='f_" + _id + "' class='elt'>" + "<img src='img/arrow.png' alt='move' width='16' height='16' class='handle' />");

    changeInput(_id);

}