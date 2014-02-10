<!DOCTYPE html>
<%@ include file="pages/commonHeader.jsp"%>
<html style="height: 100%">
<head>
<title>Form wizard tool</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<link rel="stylesheet" type="text/css" href="css/dynamic-base.css">

<style type="text/css">

</style>

<script type="text/javascript" src="js/dynamic-base.js"></script>
<script type="text/javascript" src="js/jquery-dynamicform-1.0.0.js"></script>


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
      <li id="Date"><img src="img/new_date_picker.png"> DatePicker</li>
      <li id="Button"><img src="img/new_button.png"> Button</li>
    </ul>
    <ul id="rbMenu2" class="rb_menu" style="margin-top:5px">
      <li id="ElementEdit"><img src="img/property.png"> Edit</li>
      <li id="ElementDelete"><img src="img/delete.png"> Delete</li>
    </ul>
    <ul id="rbMenu3" class="rb_menu" style="margin-top:5px">
      <li id="FieldSet"><img src="img/property.png"> FieldSet</li>
      <li id="formEdit"><img src="img/property.png"> Edit Form</li>
      <li id="loadXml"><img src="img/load_xml.png"> Load</li>
      <li id="saveXml"><img src="img/save_xml.png"> Save</li>
    </ul>
  </div>
  <form id="maindynamicform"></form>
  <div id="dialog_elt" title="Input zone definition"></div>

  <script type="text/javascript">
            jQuery(document).ready(function() {

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
                                initDrop();
                                enableRowSelectable(".handle");
                                jQuery(this).dialog('close');
                            }
                        },
                        Cancel : function() {
                            enableRowSelectable(".handle");
                            jQuery(this).dialog('close');
                        }
                    },
                    close : function() {
                    }
                });

                jQuery('#ElementEdit').click(function() {
                    showControlPanel();
                });
                jQuery('#ElementDelete').click(function() {
                    deleteControlPanel();
                });
                
                jQuery('#formEdit').click(function() {
                    showControlPanel("maindynamicform");
                });
                jQuery('#loadXml').click(function() {
                    showFilePanel();
                });
                jQuery('#saveXml').click(function() {
                    save_xml();
                });

                jQuery.dynamicplugin.initForm("maindynamicform");
                jQuery("#maindynamicform").resizable({
                    stop : function(event, ui) {
                        var fieldsetObj = jQuery.dynamicplugin.elementArray["maindynamicform"];
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
                initDrop();
                enableRowSelectable(".handle");
            });
        </script>
</body>
</html>