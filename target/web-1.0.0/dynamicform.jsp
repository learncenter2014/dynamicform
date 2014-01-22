<!DOCTYPE html>
<%@ include file="pages/commonHeader.jsp"%>
<html style="height: 100%">
<head>
<title>Form wizard tool</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<link rel="stylesheet" type="text/css" href="css/local.css">
<link rel="stylesheet" type="text/css" href="css/rbl_forms.css">

<link rel="stylesheet" type="text/css" href="js/lib/jquery-ui-1.10.4.custom/css/start/jquery-ui-1.10.4.custom.css">

<script type="text/javascript" src="js/rbl_base.js"></script>
<script type="text/javascript" src="js/rbl_form.js"></script>
<script type="text/javascript" src="js/rbl_col.js"></script>
<script type="text/javascript" src="js/rbl_elt.js"></script>
<script type="text/javascript" src="js/rbl_build.js"></script>

<script type="text/javascript" src="js/lib/jquery-ui-1.10.4.custom/js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="js/lib/jquery-ui-1.10.4.custom/js/jquery-ui-1.10.4.custom.min.js"></script>

</head>
<body style="height: 100%">
  <div style="height: 100%">
    <div style="margin-right: auto; margin-left: auto; text-align: center;height:10%">
      <h1>Dynamic Forms Builder</h1>
      <h2>V1.0.0</h2>
    </div>
    <!-- begin: main content area #main -->
    <div id="main" style="height: 85%;">

      <div id="tabs" style="width: 95%; height: 90%; margin-left: 2%; top: 40px;">
        <ul>
          <li><a href="#tabs-1">Form</a></li>
          <li><a href="#tabs-2">Html source</a></li>
          <li><a href="#tabs-3">Xml source</a></li>
          <li><a href="#tabs-4">Preview</a></li>
          <li><a href="#tabs-5">File list</a></li>
        </ul>

        <div id="tabs-1" style="text-align: left;">
          <div id="formBuilderMenu" style="float: left; margin-left: -10px; margin-right: 15px; width: 145px; border: 2px solid #D0D0D0; background-color: #f0f0f0;">
            <ul id="rbMenu" class="rb_menu">
              <li id="Label"><img src="img/new_label.png"> Label</li>
              <li id="Image"><img src="img/new_image.png"> Image</li>
              <li id="Text"><img src="img/new_text.png"> Input Text</li>
              <li id="Password"><img src="img/new_password.png"> Password</li>
              <li id="TextArea"><img src="img/new_textarea.png"> TextArea</li>
              <li id="Radio"><img src="img/new_radio.png"> Radio Button</li>
              <li id="Checkbox"><img src="img/new_checkbox.png"> Checkbox</li>
              <li id="Select"><img src="img/new_select.png"> Drop-Down Menu</li>
              <li id="Liste"><img src="img/new_list.png"> Selectable List</li>
              <li id="File"><img src="img/new_file.png"> File</li>
              <li id="Date"><img src="img/new_date_picker.png"> DatePicker</li>
              <li id="Button"><img src="img/new_button.png"> Button</li>
              <li id="Wizard"><img src="img/new_wizard.png"> Panel</li>
            </ul>
            <hr />
            <ul id="rbMenu2" class="rb_menu">
              <li id="formElemEdit"><img src="img/property.png"> Edit</li>
              <li id="formElemDelete"><img src="img/delete.png"> Delete</li>
            </ul>
            <hr />
            <ul id="rbMenu3" class="rb_menu">
              <li id="formEdit"><img src="img/property.png"> Edit Form</li>
              <li id="formAddCol"><img src="img/property.png"> Add Column</li>
            </ul>
            <hr />
            <ul id="rbMenu4" class="rb_menu">
              <li id="loadXml"><img src="img/load_xml.png"> Load</li>
              <li id="saveXml"><img src="img/save_xml.png"> Save</li>
            </ul>
          </div>
          <div id="main-f1" style="margin-left: 170px; background-color: #f8f8f8;"></div>
        </div>
        <div id="tabs-2" style="text-align: left;">
          <pre class="mxml" name="htmsource" id="htmsource"></pre>
        </div>
        <div id="tabs-3" style="text-align: left;">
          <pre class="mxml" name="xmlsource" id="xmlsource"></pre>
        </div>
        <div id="tabs-4" style="text-align: left;">
          <div id="previewdst"></div>
        </div>
        <div id="tabs-5" style="text-align: left;">
          <iframe id="iframefile" src="about:blank" width="100%" height="600px" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="auto" allowtransparency="yes"></iframe>
        </div>

      </div>
     
      <!-- form id tabs -->
      <div id="dialog_form" title="Form Definition"></div>
      <div id="dialog_col" title="Column definition"></div>
      <div id="dialog_elt" title="Input zone definition"></div>
      <div id="dialog_file" title="Load a form"></div>

    </div>
    <!-- fin : main  area #main -->
    <div style="margin-right: auto; margin-left: auto; margin-bottom:10px;text-align: center; height: 5%; border-top: 1px solid #2020A0;">
      &copy; 2014/01/11- Author: Peter contact info: +8618651806651 QQ:116352437<br />
    </div>
  </div>
  <script type="text/javascript">
            var selected = null;

            jQuery(document).ready(
                            function() {
                                jQuery.datepicker.setDefaults(jQuery.extend({
                                    showMonthAfterYear : false
                                }, jQuery.datepicker.regional['us']));
                                
                                jQuery("#tabs").tabs({ beforeActivate : function(event, ui) {
                                                       var index = ui.newPanel[0].id;
                                                        if (index == "tabs-2") {
                                                            // htmlStr = jQuery("#tabs-1").html() ;
                                                            htmlStr = build_html();
                                                            jQuery("#htmsource").text(htmlStr);
                                                        }
                                                        if (index == "tabs-3") {
                                                            xmlStr = build_xml_elt();
                                                            jQuery("#xmlsource").text(xmlStr);
                                                        }
                                                        if (index == "tabs-4") {
                                                            xmlStr = build_html();
                                                            jQuery("#previewdst").html(xmlStr);
                                                        }
                                                        if (index == "tabs-5") {
                                                            jQuery("#iframefile").attr("src", "templateFileList.action");
                                                        }
                                                    }
                                });
                                jQuery("#main-f1").resizable({
                                                    stop : function(event, ui) {

                                                        if (rblFormArray[IDFORM]) {
                                                            if (ui.size.width != ui.originalSize.width) {
                                                                rblFormArray[IDFORM].e_width = (ui.size.width - 20) + "px";
                                                            }
                                                            if (ui.size.height != ui.originalSize.height) {
                                                                rblFormArray[IDFORM].e_height = (ui.size.height - 40) + "px";
                                                            }
                                                            rblFormArray[IDFORM].drawForm();
                                                        }
                                                    }
                                });

                                jQuery("#menu_elt").dialog({
                                    autoOpen : true,
                                    width : 180,
                                    position : [ 0, 128 ],
                                    resizable : false,
                                    modal : false
                                });

                                jQuery("#dialog_form").dialog({
                                    bgiframe : true,
                                    autoOpen : false,
                                    height : 450,
                                    modal : true,
                                    buttons : {
                                        Save : function() {
                                            var bValid = true;

                                            if (bValid) {
                                                saveFormPanel();

                                                jQuery(this).dialog('close');
                                            }
                                        },
                                        Cancel : function() {
                                            jQuery(this).dialog('close');
                                        }
                                    },
                                    close : function() {
                                        //allFields.val('').removeClass('ui-state-error');
                                    }
                                });

                                jQuery("#dialog_file").dialog({
                                    bgiframe : true,
                                    autoOpen : false,
                                    height : 200,
                                    modal : true,
                                    buttons : {
                                        Save : function() {
                                            var bVal = jQuery("#x_file").val();

                                            if (bVal && bVal.length > 0) {
                                                load_xml(bVal);
                                                jQuery(this).dialog('close');
                                            }
                                        },
                                        Cancel : function() {
                                            jQuery(this).dialog('close');
                                        }
                                    },
                                    close : function() {
                                        //allFields.val('').removeClass('ui-state-error');
                                    }
                                });
                                jQuery("#dialog_col")
                                        .dialog(
                                                {
                                                    bgiframe : true,
                                                    autoOpen : false,
                                                    height : 400,
                                                    modal : true,
                                                    buttons : {
                                                        Save : function() {
                                                            var bValid = true;

                                                            if (bValid) {
                                                                saveColPanel(jQuery("#c_id").val());

                                                                jQuery(this).dialog('close');
                                                            }
                                                        },
                                                        Cancel : function() {
                                                            jQuery(this).dialog('close');
                                                        }
                                                    },
                                                    close : function() {
                                                        //allFields.val('').removeClass('ui-state-error');
                                                    }
                                                });

                                jQuery("#dialog_elt").dialog({
                                    bgiframe : true,
                                    autoOpen : false,
                                    height : 620,
                                    width : 480,
                                    modal : true,
                                    buttons : {
                                        Save : function() {
                                            var bValid = true;
                                            var _no = jQuery("#e_id").val();

                                            if (bValid) {
                                                saveInputPanel(_no);
                                                enableRowSelectable(".connectedSortable li");
                                                jQuery(this).dialog('close');
                                            }
                                        },
                                        Cancel : function() {
                                            jQuery(this).dialog('close');
                                        }
                                    },
                                    close : function() {
                                        //allFields.val('').removeClass('ui-state-error');
                                    }
                                });

                                jQuery('#formEdit').click(function() {
                                    showFormPanel();
                                });
                                jQuery('#formAddCol').click(function() {
                                    addCol();
                                });
                                jQuery('#formElemEdit').click(function() {
                                    showControlPanel();
                                });
                                jQuery('#formElemDelete').click(function() {
                                    deleteElement();
                                });
                                jQuery('#loadXml').click(function() {
                                    showFilePanel();
                                });
                                jQuery('#saveXml').click(function() {
                                    save_xml();
                                });

                                initDrop();

                                jQuery(".connectedSortable").resizable();

                                enableRowSelectable(".connectedSortable li");

                                initFormData();
                                addCol();
           });
        </script>
</body>
</html>