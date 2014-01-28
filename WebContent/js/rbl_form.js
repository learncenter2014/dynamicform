window.rblFormArray = new Array();
var IDFORM = "form";
var IDFS = "fs-1";
var CURFS = IDFS;
var PRVFS = "";

function rbl_form(_id, _name) {

    this.e_id = _id;
    this.e_name=_name;
    this.e_file;

    this.e_label;
    this.e_action;
    this.e_method;
    this.e_css_class;
    this.e_width;
    this.e_height;

    this.init = function(_name, _label, _action, _method, _layout, _css) {
        this.e_name = _name;
        this.e_label = _label;
        this.e_action = _action;
        this.e_method = _method;
        this.e_layout = _layout;
        this.e_css_class = _css;
    }

    this.saveForm = function() {
        
        this.e_name = jQuery("#e_name").val();
        this.e_file = jQuery("#e_file").val();
        this.e_label = jQuery("#e_label").val().replace('\'', "`");

        this.e_action = jQuery("#e_action").val();
        this.e_method = jQuery("#e_method").val();
        this.e_layout = jQuery("#e_layout").val();
        this.e_css_class = jQuery("#e_css_class").val();

        this.e_width = jQuery("#e_width").val();
        this.e_height = jQuery("#e_height").val();
    }

    this.showForm = function() {
        jQuery("#e_name").val(this.e_name);
        jQuery("#e_label").val(this.e_label);
        jQuery("#e_file").val(this.e_file);
        jQuery("#e_action").val(this.e_action);
        jQuery("#e_method").val(this.e_method);
        jQuery("#e_css_class").val(this.e_css_class);
        jQuery("#e_layout").val(this.e_layout);
        jQuery("#e_width").val(this.e_width);
        jQuery("#e_height").val(this.e_height);
    }

    this.drawForm = function() {
        var mon_form;
        var lgd;

        mon_form = jQuery("#" + this.e_id);

        if (this.e_css_class && this.e_css_class > 0) {
            jQuery("#main-f1").removeClass().addClass(this.e_css_class);
        }

        if (mon_form.length == 0) {
            var ss = "";
            if (this.e_label && this.e_label.length > 0) {
                ss = "<legend>" + this.e_label + "</legend>";
            }

            jQuery("#main-f1").append(
                    "<form" + this.drawName() + this.drawMethod()
                            + this.drawAction() + this.drawClass() + ">"
                            + "<fieldset id='" + IDFS
                            + "' style='padding: 0px 1px; margin: 0px 0px ;'>"
                            + ss + "</fieldset>" + "</form>");
        } else {
            mon_form.attr('id', this.e_id).attr('name', this.e_name).attr(
                    'method', this.e_method).attr('action', this.e_action);

            mon_form.removeClass();
            mon_form.addClass(this.e_css_class);

            lgd = mon_form.find("#" + IDFS).find('legend');
            if (this.e_label && this.e_label.length > 0) {
                if (lgd.length > 0) {
                    lgd.text(this.e_label);
                } else {
                    mon_form.find("#" + IDFS).prepend(
                            "<legend>" + this.e_label + "</legend>");
                }
            } else {
                if (lgd.length > 0) {
                    mon_form.find("#" + IDFS).remove('legend');
                }
            }
        }

    }
   
    this.returnForm = function() {
        var str = " onsubmit='return checkErrorSize();' ";
        return str;
    }
    
    this.drawStyle = function() {
        var str = "";
        if (this.e_width && this.e_width.length > 0) {
            str += "width:" + this.e_width + ";";
        }
        if (this.e_height && this.e_height.length > 0) {
            str += "height:" + this.e_height + ";";
        }

        if ((this.e_width && this.e_width.length > 0)
                || (this.e_height && this.e_height.length > 0)) {
            str = " style='" + str + "'";
        }

        return str;
    }
    this.drawWidth = function() {
        var str = "";
        if (this.e_width && this.e_width.length > 0) {
            str += " width='" + this.e_width + "'";
        }
        if (this.e_height && this.e_height.length > 0) {
            str += " height='" + this.e_height + "'";
        }
        return str;
    }

    this.drawName = function() {
        var str = "";

        str = " id='" + this.e_id + "'";

        if (this.e_name.length > 0) {
            str = str + " name='" + this.e_name + "'";
        }

        return str;
    }

    this.drawMethod = function() {
        var str = "";

        if (this.e_method.length > 0) {
            str = " method='" + this.e_method + "'";
        } else {
            str = " method='POST'";
        }

        return str;
    }

    this.drawAction = function() {
        var str = "";

        if (this.e_action.length > 0) {
            str = " action='" + this.e_action + "'";
        }

        return str;
    }

    this.drawLayout = function() {
        var str = "";

        if (this.e_layout && this.e_layout.length > 0) {
            str = " layout='" + this.e_layout + "'";
        }

        return str;
    }
    this.drawClass = function() {
        var str = "";

        if (this.e_css_class && this.e_css_class.length > 0) {
            str = " class='" + this.e_css_class + "'";
        }

        return str;
    }
    this.drawLabel = function() {
        var str = "";

        if (this.e_label && this.e_label.length > 0) {
            str = " label='" + this.e_label + "'";
        }

        return str;
    }

    this.startXml = function() {
        var str = "";

        str = str + "<form" + this.drawName() + this.drawMethod()
                + this.drawAction() + this.drawLayout() + this.drawClass()
                + this.drawLabel() + this.drawWidth() + ">";

        return str;
    }
    this.endXml = function() {
        var str = "";
        str = "</form>";
        return str;
    }

    this.startHtml = function() {
        var str = "";

        /* this.drawName() */

        str = str + "<form" + " id='rbform' name='" + this.e_name + "'"
                + this.drawMethod() + this.drawAction() + this.drawClass()
                + this.drawStyle() + ">" + "\r\n";

        str += "<fieldset id='x" + IDFS + "'>";
        if (this.e_label && this.e_label.length > 0) {
            str += "<legend>" + this.e_label + "</legend>";
        }

        CURFS = "x" + IDFS;
        PRVFS = "";

        return str;
    }
    this.endHtml = function() {
        var str = "";
        str = "</fieldset></form>";
        return str;
    }
}

function showFormPanel() {

    jQuery("#dialog_form").load("panel/" + LANG + "/dialog_form.html?b",
            function() {
                rblFormArray[IDFORM].showForm();
                jQuery('#dialog_form').dialog('open');
            });
}

/**
 * save Form Panel
 */
function saveFormPanel() {

    if (rblFormArray[IDFORM] != null) {
        rblFormArray[IDFORM].saveForm();
        rblFormArray[IDFORM].drawForm();
    } else {
        alert("FormPanel is empty");
    }

}

/**
 * init form data
 */
function initFormData() {
    if (rblFormArray[IDFORM] == null) {
        rblFormArray[IDFORM] = new rbl_form(IDFORM, IDFORM);
    }
    rblFormArray[IDFORM].init("frm1", "", "#", "POST", "", "");

    rblFormArray[IDFORM].drawForm();
}

function loadForm(elt, file) {
    if (rblFormArray[IDFORM] == null) {
        rblFormArray[IDFORM] = new rbl_form(IDFORM, IDFORM);
    }
    rblFormArray[IDFORM].init(jQuery(elt).attr('name'), jQuery(elt).attr(
            'label'), jQuery(elt).attr('action'), jQuery(elt).attr('method'),
            "", jQuery(elt).attr('class'));
    rblFormArray[IDFORM].e_file = file;
    rblFormArray[IDFORM].e_width = jQuery(elt).attr('width');
    rblFormArray[IDFORM].e_height = jQuery(elt).attr('height');

    rblFormArray[IDFORM].drawForm();
    drawMainDiv();
}
function drawMainDiv() {
    var str = "";
    var aa = jQuery("#main-f1");

    str = "auto";
    if (rblFormArray[IDFORM].e_width && rblFormArray[IDFORM].e_width.length > 0) {
        str = rblFormArray[IDFORM].e_width;
        if (str.indexOf('px') > 0) {
            str = (20 + parseInt(str)) + "px";
        }
    }
    aa.css("width", str);

    str = "auto";
    if (rblFormArray[IDFORM].e_height
            && rblFormArray[IDFORM].e_height.length > 0) {
        str = rblFormArray[IDFORM].e_height;
        if (str.indexOf('px') > 0) {
            str = (40 + parseInt(str)) + "px";
        }
    }
    aa.css("height", str);
}
