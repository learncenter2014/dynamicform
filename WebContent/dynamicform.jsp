<!DOCTYPE html>
<%@ include file="pages/commonHeader.jsp"%>
<html style="height: 100%">
<head>
<title>Form wizard tool</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<link rel="stylesheet" type="text/css" href="css/local.css">
<link rel="stylesheet" type="text/css" href="css/rbl_forms.css">


<script type="text/javascript" src="js/rbl_base.js"></script>
<script type="text/javascript" src="js/rbl_form.js"></script>
<script type="text/javascript" src="js/rbl_col.js"></script>
<script type="text/javascript" src="js/rbl_elt.js"></script>
<script type="text/javascript" src="js/rbl_build.js"></script>


</head>
<body>
    <!-- begin: main content area #main -->
          <div id="formBuilderMenu" style="float: left; margin-right: 15px; width: 180px;">
            <ul id="rbMenu" class="rb_menu">
              <li id="Label"><img src="img/new_label.png"> Label</li>
              <li id="Image"><img src="img/new_image.png"> Image</li>
              <li id="Text"><img src="img/new_text.png"> Input Text</li>
              <li id="Password"><img src="img/new_password.png"> Password</li>
              <li id="TextArea"><img src="img/new_textarea.png"> TextArea</li>
              <li id="Radio"><img src="img/new_radio.png"> Radio Button</li>
              <li id="Checkbox"><img src="img/new_checkbox.png"> Checkbox</li>
              <li id="Select"><img src="img/new_select.png"> Select</li>
              <li id="File"><img src="img/new_file.png"> File</li>
              <li id="Date"><img src="img/new_date_picker.png"> DatePicker</li>
              <li id="Button"><img src="img/new_button.png"> Button</li>
              <li id="formAddFieldSet"><img src="img/property.png"> FieldSet</li>
            </ul>
            <ul id="rbMenu2" class="rb_menu">
              <li id="formElemEdit"><img src="img/property.png"> Edit</li>
              <li id="formElemDelete"><img src="img/delete.png"> Delete</li>
            </ul>
            <hr/>
            <ul id="rbMenu3" class="rb_menu">
              <li id="formEdit"><img src="img/property.png"> Edit Form</li>
              <li id="saveXml"><img src="img/save_xml.png"> Save</li>
            </ul>
          </div>
      <div id="main-f1" style="margin-left: 150px;"></div>
      <div id="dialog_fieldset" title="Column definition"></div>
      <div id="dialog_elt" title="Input zone definition"></div>

  <script type="text/javascript">
            var selected = null;

            jQuery(document).ready(
                            function() {
                                
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

                                jQuery("#dialog_fieldset")
                                        .dialog(
                                                {
                                                    bgiframe : true,
                                                    autoOpen : false,
                                                    height : 500,
                                                    width: 480,
                                                    modal : true,
                                                    buttons : {
                                                        Save : function() {
                                                            var bValid = true;

                                                            if (bValid) {
                                                                saveColPanel(jQuery("#c_id").val());
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

                                jQuery("#dialog_elt").dialog({
                                    bgiframe : true,
                                    autoOpen : false,
                                    height : 500,
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