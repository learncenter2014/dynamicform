<!DOCTYPE html>
<%@ include file="/pages/commonHeader.jsp"%>
<html style="height: 100%">
<head>
<title>Form wizard tool</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<link rel="stylesheet" type="text/css" href="css/dynamic-base.css">

<style type="text/css">

</style>

<script type="text/javascript" src="js/jquery-dynamicform-1.0.0.js"></script>
<script type="text/javascript" src="js/dynamic-base.js"></script>


</head>
<body>
  <!-- begin: main content area #main -->
 <div class="template" style="position:relative;">
  <div id="formBuilderMenu">
    <ul id="rbMenu" class="rb_menu">
      <li id="Label"><img src="img/new_label.png"> 标签</li>
      <li id="Image"><img src="img/new_image.png"> 图片</li>
      <li id="Text"><img src="img/new_text.png"> 输入框</li>
      <li id="Password"><img src="img/new_password.png"> 密码框</li>
      <li id="TextArea"><img src="img/new_textarea.png"> 文本框</li>
      <li id="Radio"><img src="img/new_radio.png"> 按钮</li>
      <li id="Checkbox"><img src="img/new_checkbox.png"> 单复选框</li>
      <li id="Select"><img src="img/new_select.png"> 下拉框</li>
      <li id="Date"><img src="img/new_date_picker.png"> 日历</li>
      <li id="Button"><img src="img/new_button.png"> 提交</li>
    </ul>
    <ul id="rbMenu3" class="rb_menu" style="margin-top:5px">
      <li id="Row"><img src="img/property.png"> 行域</li>
      <li id="FieldSet"><img src="img/property.png"> 分组域</li>
      <li></li>
      <li id="formCreate"><img src="img/property.png"> 创建表单模版</li>
      <li id="loadXml"><img src="img/load_xml.png"> 打开</li>
      <li id="saveXml"><img src="img/save_xml.png"> 保存</li>
    </ul>
  </div>
  <h4 style="text-align:center;position:absolute;left:0px;top:0px;width:100%">动态表单模版工作区</h4>
  <div id="maindynamicform" style="position:absolute;left:0px;top:50px;width:100%;height:90%"></div>
  <div id="dialog_elt" title="参数设置区"></div>
  <div id="dialog_file" title="系统模版列表区"></div>
  </div>
  <script type="text/javascript">
            jQuery(document).ready(function() {
                
                jQuery("#dialog_file").dialog({
                    bgiframe : true,
                    autoOpen : false,
                    height : 200,
                    modal : true,
                    buttons : {
                        Open : function() {
                            var name = jQuery("#x_file").val();
                            if(name==null){
                                jQuery("#dialog_message").html('<span style="color:red">没有有效的模版!</span>');
                                jQuery("#dialog_message").dialog(); 
                            }else{ 
                                var label = jQuery("#x_file option:selected").text();
                                load_xml({name:name,label:label});
                                jQuery(this).dialog('close');
                           }
                        },
                        Cancel : function() {
                            jQuery(this).dialog('close');
                        }
                    },
                    close : function() {
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

                            var _no = jQuery("#e_id").val();
                            saveInputPanel(_no);
                            initDrop();
                            jQuery(this).dialog('close');
                        },
                        Cancel : function() {
                            initDrop();
                            jQuery(this).dialog('close');
                        }
                    },
                    close : function() {
                    }
                });

                jQuery('#formCreate').on("click",function() {
                    createForm();
                });
                jQuery('#formEdit').on("click",function() {
                    editForm();
                });
                jQuery('#loadXml').on("click",function() {
                    showFilePanel();
                });
                jQuery('#saveXml').on("click",function() {
                    save_xml();
                });

                jQuery('#maindynamicform').on("click",".operation>.fa.fa-edit",function() {
                    showControlPanel(jQuery(this).parent().attr("id").substring("operation_".length));
                });
                jQuery('#maindynamicform').on("click",".operation>.fa.fa-cut",function() {
                    deleteControlPanel(jQuery(this).parent().attr("id").substring("operation_".length));
                });
            });
        </script>
</body>
</html>