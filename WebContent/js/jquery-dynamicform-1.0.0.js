(function($) {
    /**
     * GUID是一种由算法生成的二进制长度为128位的数字标识符。GUID 的格式为“xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx”，
     * 其中的 x 是 0-9 或 a-f 范围内的一个32位十六进制数。在理想情况下，任何计算机和计算机集群都不会生成两个相同的GUID。
     * @returns
     */
    $.uuid = function() {
        var s = [];
        var hexDigits = "0123456789abcdef";
        for (var i = 0; i < 36; i++) {
            s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
        }
        s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
        s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
        s[8] = s[13] = s[18] = s[23] = "-";
     
        var uuid = s.join("");
        return uuid;
    };
    
    $.substitute = function(str, o, regexp) {
        /**
         * ${XXX} style will be replaced by this method.
         */
        var callback = function(match, name) {
            return (o[name] == undefined) ? '' : o[name];
        };
        return str.replace(regexp || /\$\{([^{}]+)\}/g, callback);
    };
    
    $.escapeXml = function(ss) {
       
        if (ss != null && typeof ss == "string") {
            var str = ss;
            var findReplace = [ [ /&/g, "&amp;" ], [ /</g, "&lt;" ], [ />/g, "&gt;" ], [ /"/g, "&quot;" ] ]

            for (var item=0;item< findReplace.length;item++)
                str = str.replace(findReplace[item][0], findReplace[item][1]);
            return str;
        } else {
            return ss;
        }
        
    };
    
    $.unescapeXml = function(ss) {
       if (ss != null && typeof ss == "string") { 
        var str = ss;
        var findReplace = [ [ /&amp;/g, "&" ], [ /&lt;/g, "<" ], [ /&gt;/g, ">" ], [ /&quot;/g, "\"" ] ]

        for (var item=0;item< findReplace.length;item++)
            str = str.replace(findReplace[item][0], findReplace[item][1]);
        return str;
       } else {
           return ss;
       }
    };

    /* abstract class for all Elements */
    function Component(options) {
        this.id = options.id;
        this.name = options.name;
        this.type = options.type;
        this.xmltemplate = "";
        this.operation='<div class="operation" id="operation_'+this.id+'"><span class="fa fa-edit"></span>&nbsp;<span class="fa fa-cut"></span></div>';
        //sub-class could overwrite this function.
        this.preInitHtml = function(){
        };
        this.preInitXml = function(){
            //here, need to get this position column of element from row.
            this.position = jQuery("#block_"+this.id).parent().index();
        };
        this.toXml = function() {
            this.preInitXml();
            return $.substitute('<element type="${type}" position="${position}">'+this.xmltemplate+'</element>', this);
        };
        this.htmltemplate = "";
        this.toHtml = function(){
            this.preInitHtml();
            return $.substitute('<div id="block_${id}" class="connectedSortable"><label for="${id}">${label}</label>'+this.htmltemplate+'${operation}</div>', this);
        };
    };
    
    var component = new Component({});
    
    /* abstract class for Input */
    function Input(options) {
        Component.call(this, options);
        this.label = options.label;
        this.value = options.value;
        this.size = options.size;
        this.maxlength = options.maxlength;
        this.required = options.required;
        this.readonly = options.readonly;
        this.helptext = options.helptext;
        this.preInitHtml = function(){
            Input.protottype.preInitHtml.apply(this,arguments);
            if(this.listvalue && this.listvalue.length>0){
                var items = this.listvalue.split(";");
                var templateString = "";
                if(this.type=="checkbox"){
                    templateString = '<label><input type="checkbox" id="${id}" name="${name}" value="${value}">${label}</label>';
                }else if(this.type=="radio"){
                    templateString = '<label><input type="radio" id="${id}" name="${name}" value="${value}">${label}</label>';  
                }else if(this.type=="select"){
                    templateString = '<option value="${value}">${label}</option>';
                }
                var stringBuffer = "";
                for(var i=0;i<items.length;i++){
                    var labelValue = items[i].split("=");
                    if(labelValue.length==2){
                       var name = this.name;
                       var option = $.substitute(templateString,{name:name, id:$.uuid(), label:labelValue[0],value:labelValue[1]});
                       stringBuffer +=option;
                    }
                }
                if(this.type=="select"){
                    this.suboptions = stringBuffer;
                }else{
                    this.htmltemplate = stringBuffer;
                }
            }
        };
    };
    
    Input.protottype = component;
    var input = new Input({});
    /* sub-class for FieldSet */
    function FieldSet(options) {
        this.constructor = FieldSet;
        Component.call(this, options);
        this.legend = options.legend;
        this.elements = [];
        this.addElement = function(obj) {
            this.elements.push(obj);
        };
        this.xmltemplate = '<fieldset id="${id}" name="" legend="${legend}">${subElementsXml}</fieldset>';
        this.toXml = function() {
            this.subElementsXml = "";
            for ( var i = 0; i < this.elements.length; i++) {
                this.subElementsXml += this.elements[i].toXml();
            }
            return $.substitute(this.xmltemplate, this);
        };
        this.htmltemplate = '<fieldset id="block_${id}" name="${id}" class="connectedSortable"><legend id="legend_${id}">${legend}</legend>${operation}${subElementsHtml}</fieldset>';
        this.toHtml = function(){
            this.subElementsHtml = "";
            for ( var i = 0; i < this.elements.length; i++) {
                this.subElementsHtml += this.elements[i].toHtml();
            }
            return $.substitute(this.htmltemplate, this);
        };
    };
    
    FieldSet.prototype = component;
    
    /* sub-class for Form */
    function Form(options) {
        Component.call(this, options);
        this.constructor = Form;
        this.label = options.label;
        this.action = options.action;
        this.method = options.method;
        this.operation='<div class="operation" id="operation_'+this.id+'"><span class="fa fa-edit"></span>&nbsp;<span class="fa fa-cut"></span></div>';
        this.xmltemplate = '<tns:form xmlns:tns="http://www.dynamic.org/dynamicform" id="${id}" name="${name}" action="${action}" method="${method}">${subElementsXml}</tns:form>';
        this.elements = [];
        this.addElement = function(obj) {
            this.elements.push(obj);
        };
        this.toXml = function() {
            this.subElementsXml = "";
            for ( var i = 0; i < this.elements.length; i++) {
                this.subElementsXml += this.elements[i].toXml();
            }
            return $.substitute(this.xmltemplate, this);
        };
        this.htmltemplate = '<form id="block_${id}" name="${name}" class="connectedSortable">${operation}${subElementsHtml}</form>';
        this.toHtml = function(){
            this.subElementsHtml = "";
            for ( var i = 0; i < this.elements.length; i++) {
                this.subElementsHtml += this.elements[i].toHtml();
            }
            return $.substitute(this.htmltemplate, this);
        };
    }
    Form.prototype = component;
    
    /* sub-class for Image */
    function Image(options) {
        Component.call(this, options);
        this.constructor = Image;
        this.src = options.src;
        this.width=options.wdith;
        this.height=options.height;
        this.helptext = options.helptext;
        this.xmltemplate = '<image id="${id}" name="${name}" src="${src}"></image>';
        this.htmltemplate = '<img id="${id}" name="${name}" src="${src}" style="width:${width}px;height:${height}px">';
    }
    Image.prototype = component;

    /* sub-class for Button */
    function Button(options) {
        Component.call(this, options);
        this.constructor = Button;
        this.value = options.value;
        this.xmltemplate = '<button id="${id}" name="${name}" value="${value}" helptext="${helptext}"/>';
        this.htmltemplate = '<button type="button" value="${value}" name="${name}" id="${id}" class="btn btn-info">${value}</button>';
    }
    Button.prototype = component;

    /* sub-class for Checkbox */
    function Checkbox(options) {
        Input.call(this, options);
        this.constructor = Checkbox;
        this.listvalue = options.listvalue;
        this.xmltemplate = '<checkbox id="${id}" name="${name}" label="${label}" value="${value}" size="${size}" maxlength="${maxlength}" required="${required}" readonly="${readonly}" listvalue="${listvalue}" helptext="${helptext}"/>';
        this.htmltemplate = '<input type="checkbox" id="${id}" name="${name}" value="${value}">';
    }
    Checkbox.prototype = input;

    /* sub-class for Radio */
    function Radio(options) {
        Input.call(this, options);
        this.constructor = Radio;
        this.listvalue = options.listvalue;
        this.xmltemplate = '<radio id="${id}" name="${name}" label="${label}" value="${value}" size="${size}" maxlength="${maxlength}" required="${required}" readonly="${readonly}" listvalue="${listvalue}" helptext="${helptext}"/>';
        this.htmltemplate = '<input type="radio" id="${id}" name="${name}" value="${value}">';
    }
    Radio.prototype = input;

    /* sub-class for Text */
    function Text(options) {
        Input.call(this, options);
        this.constructor = Text;
        this.regexp = options.regexp;
        this.xmltemplate = '<text id="${id}" name="${name}" label="${label}" value="${value}" size="${size}" maxlength="${maxlength}" required="${required}" readonly="${readonly}" regexp="${regexp}" helptext="${helptext}"/>';
        this.htmltemplate = '<input class="form-control" type="text" id="${id}" name="${name}" value="${value}">';
    }
    Text.prototype = input;
    
    /* sub-class for Text */
    function PassWord(options) {
        Input.call(this, options);
        this.constructor = PassWord;
        this.regexp = options.regexp;
        this.xmltemplate = '<password id="${id}" name="${name}" label="${label}" value="${value}" size="${size}" maxlength="${maxlength}" required="${required}" readonly="${readonly}" regexp="${regexp}" helptext="${helptext}"/>';
        this.htmltemplate = '<input class="form-control" type="password" id="${id}" name="${name}" value="${value}">';
    }
    PassWord.prototype = input;

    /* sub-class for TextArea */
    function TextArea(options) {
        Input.call(this, options);
        this.constructor = TextArea;
        this.xmltemplate = '<textarea id="${id}" name="${name}" label="${label}" value="${value}" size="${size}" maxlength="${maxlength}" required="${required}" readonly="${readonly}" helptext="${helptext}"/>';
        this.htmltemplate = '<textarea id="${id}" name="${name}" value="${value}" rows="${size}" cols="${maxlength}">${value}</textarea>';
    }
    TextArea.prototype = input;

    /* sub-class for Select */
    function Select(options) {
        Input.call(this, options);
        this.constructor = Select;
        this.listvalue = options.listvalue;
        this.xmltemplate = '<select id="${id}" name="${name}" label="${label}" value="${value}" size="${size}" maxlength="${maxlength}" required="${required}" readonly="${readonly}" listvalue="${listvalue}" helptext="${helptext}"/>';
        this.htmltemplate = '<select id="${id}" name="${name}" value="${value}" class="form-control m-bot15">${suboptions}</select>';
    }
    Select.prototype = input;

    /* sub-class for Date */
    function DateElement(options) {
        Input.call(this, options);
        this.constructor = DateElement;
        this.format = options.format;
        this.xmltemplate = '<date id="${id}" name="${name}" label="${label}" value="${value}" size="${size}" maxlength="${maxlength}" required="${required}" readonly="${readonly}" format="${format}" helptext="${helptext}"/>';
        this.htmltemplate = '<input class="form-control" type="text" id="${id}" name="${name}" value="${value}">';
    }
    DateElement.prototype = input;
    
    /* sub-class for Label */
    function Label(options) {
        Component.call(this, options);
        this.constructor = Label;
        this.label = options.label;
        this.xmltemplate = '<label id="${id}" name="" label="${label}"></label>';
        this.htmltemplate = '';
    }
    Label.prototype = component;

    /* sub-class for Label */
    function Row(options) {
        Component.call(this, options);
        this.constructor = Row;
        this.size = options.size;
        this.xmltemplate = '<row id="${id}" name="" size="${size}">${subElementsXml}</row>';

        this.elements = [];
        this.addElement = function(obj) {
            this.elements.push(obj);
        };
        this.toXml = function() {
            this.subElementsXml = "";
            for ( var i = 0; i < this.elements.length; i++) {
                this.subElementsXml += this.elements[i].toXml();
            }
            return $.substitute(this.xmltemplate, this);
        };

        this.htmltemplate = '<div id="block_${id}" name="" class="section connectedSortable">${subElementsHtml}${operation}<div style="clear:both;"></div></div>';

        this.toHtml = function(){
            this.subElementsHtml = "";
            var classname = "col-lg-"+parseInt(12/this.size);
            for ( var i = 0; i < parseInt(this.size); i++) {
                this.subElementsHtml += '<section class="'+classname+' panel panel-body">';
                for(var j=0;j<this.elements.length;j++){
                    if(this.elements[j].position == i){
                        this.subElementsHtml +=this.elements[j].toHtml();
                    }
                }
                this.subElementsHtml += '</section>';
            }
            return $.substitute(this.htmltemplate, this);
        };
    }
    Row.prototype = component;

    /**
     * binding plugin dynamicform into Jquery.
     * 
     * Element Factory for these lifecycle of object.
     * 
     */
    
    function UiInputDialog(_id,_type){
        this.id = _id;
        this.type = _type;
        this.name = "";
        this.label = "";
        this.size = 3;
        this.mask = "";
        this.freemask = "";
        this.regexp = "";
        this.maxlength = 20;
        this.accesskey = "";
        this.listvalue = "";
        this.helptext = "Please input promoted info";
        this.action = "";
        this.method = "post";
        this.width = 400;
        this.height = 400;
        
        this.required = false;
        this.readonly = false;  
        
        this.saveElement = function() {
            this.name = jQuery("#e_name").val();
            this.label = jQuery("#e_label").val();
            this.legend = jQuery("#e_legend").val();
            this.action = jQuery("#e_action").val();
            this.method = jQuery("#e_method").val();
            this.width = jQuery("#e_width").val();
            this.height = jQuery("#e_height").val();
            this.size = jQuery("#e_size").val();
            this.mask = jQuery("#e_mask").val();
            this.freemask = jQuery("#e_freemask").val();
            this.regexp = jQuery("#e_regexp").val();
            this.maxlength = jQuery("#e_maxlength").val();
            this.accesskey = jQuery("#e_accesskey").val();
            this.listvalue= jQuery("#e_listvalue").val();
            this.helptext = jQuery("#e_helptext").val();
            this.src = jQuery("#e_src").val();
            this.value = jQuery("#e_value").val();
            
            this.required = jQuery("#e_required")[0]?jQuery("#e_required")[0].checked:false;
            this.readonly = jQuery("#e_readonly")[0]?jQuery("#e_readonly")[0].checked:false;
        };

        this.showElement = function() {
            jQuery("#e_name").val(this.name);
            jQuery("#e_legend").val(this.legend);
            jQuery("#e_label").val(this.label);
            jQuery("#e_action").val(this.action);
            jQuery("#e_method").val(this.method);
            jQuery("#e_width").val(this.width);
            jQuery("#e_height").val(this.height);
            jQuery("#e_size").val(this.size);
            jQuery("#e_mask").val(this.mask);
            jQuery("#e_freemask").val(this.freemask);
            jQuery("#e_regexp").val(this.regexp);
            jQuery("#e_maxlength").val(this.maxlength);
            jQuery("#e_accesskey").val(this.accesskey);
            jQuery("#e_listvalue").val(this.listvalue);
            jQuery("#e_helptext").val(this.helptext);
            jQuery("#e_src").val(this.src);
            jQuery("#e_value").val(this.value);
            
            if(jQuery("#e_required")[0])
            jQuery("#e_required")[0].checked=this.required;
            if(jQuery("#e_readonly")[0])
            jQuery("#e_readonly")[0].checked=this.readonly;
        };
        this.parseFromXml = function(xmlObj){
            this.name = jQuery(xmlObj).attr("name");
            this.legend = jQuery(xmlObj).attr("legend");
            this.label = jQuery(xmlObj).attr("label");
            this.action = jQuery(xmlObj).attr("action");
            this.method = jQuery(xmlObj).attr("method");
            this.width = jQuery(xmlObj).attr("width");
            this.height = jQuery(xmlObj).attr("height");
            this.size = jQuery(xmlObj).attr("size");
            this.mask = jQuery(xmlObj).attr("mask");
            this.freemask = jQuery(xmlObj).attr("freemask");
            this.regexp = jQuery(xmlObj).attr("regexp");
            this.maxlength = jQuery(xmlObj).attr("maxlength");
            this.accesskey = jQuery(xmlObj).attr("accesskey");
            this.listvalue= jQuery(xmlObj).attr("listvalue");
            this.helptext = jQuery(xmlObj).attr("helptext");
            this.src = jQuery(xmlObj).attr("src");
            this.value = jQuery(xmlObj).attr("value");
            
            this.required = jQuery(xmlObj).attr("required");
            this.readonly = jQuery(xmlObj).attr("readonly");  
        };
    };
    
    $.dynamicplugin = {
        elementArray:{},
        formId:null,
        resetGlobalVar: function(){
            this.selected=null;
            this.idIndex=0;
            this.elementArray={};
            this.formId = null;
        },
        createElementFactory : function(options) {
            var type = options.type;
            var instanceObj = null;
            if (type != undefined) {
                switch (type.toLowerCase()) {
                case "text":
                    instanceObj = new Text(options);
                    break;
                case "password":
                    instanceObj = new PassWord(options);
                    break;
                case "textarea":
                    instanceObj = new TextArea(options);
                    break;
                case "select":
                    instanceObj = new Select(options);
                    break;
                case "date":
                    instanceObj = new DateElement(options);
                    break;
                case "checkbox":
                    instanceObj = new Checkbox(options);
                    break;
                case "image":
                    instanceObj = new Image(options);
                    break;
                case "radio":
                    instanceObj = new Radio(options);
                    break;
                case "button":
                    instanceObj = new Button(options);
                    break;
                case "fieldset":
                    instanceObj = new FieldSet(options);
                    break;
                case "form":
                    instanceObj = new Form(options);
                    break;
                case "label":
                    instanceObj = new Label(options);
                    break;
                case "row":
                    options.type='row';
                    instanceObj = new Row(options);
                    break;
                }
            }
            return instanceObj;
        },
        _toXml: function(form) {
            var stringBuffer = '<?xml version="1.0" encoding="UTF-8"?>';
            stringBuffer += form.toXml();
            return stringBuffer;
        },
        escapseOptions: function(options){
            var newObject = jQuery.extend(true, {}, options); 
            for(var i in newObject){
                newObject[i] = $.escapeXml(newObject[i]);
            }
            return newObject;
        },
        unescapseOptions: function(options){
            var newObject = jQuery.extend(true, {}, options); 
            for(var i in newObject){
                newObject[i] = $.unescapeXml(newObject[i]);
            }
            return newObject;
        },
        saveXml: function(){
            var pluginRef = this;

            var makeObject = function(objectId, container){
                var options = pluginRef.elementArray[objectId.substring("block_".length)];
                var createObject = pluginRef.createElementFactory(pluginRef.escapseOptions(options));
                container.addElement(createObject);
                return createObject;
            };

            var formName = pluginRef.elementArray[this.formId].name;
            var label = pluginRef.elementArray[this.formId].label;
            var options = this.elementArray[this.formId];
            var createForm = pluginRef.createElementFactory(pluginRef.escapseOptions(options));
            //loop fieldset
            jQuery("#block_"+this.formId).find("fieldset.connectedSortable").each(function(){
                var fieldSet = jQuery(this);
                var fieldId = fieldSet.attr("id");
                var createFiledset = makeObject(fieldId,createForm);
                //loop element per rows
                jQuery(this).find("div.section.connectedSortable").each(function(){
                    var rows = jQuery(this);
                    var rowId = rows.attr("id");
                    var createRow = makeObject(rowId,createFiledset);
                    //loop element per element
                    jQuery(this).find("div.connectedSortable").each(function(){
                        var element = jQuery(this);
                        var divId = element.attr("id");
                        makeObject(divId,createRow);
                    });
                });
            });
            //covert form object to xml structure.
            var dataXml = this._toXml(createForm);
            jQuery.ajax({
                async:false,
                type: "POST",
                url: "template/savexml.action",
                cache: false,
                data: ({name: formName,
                        label:label,
                        data :dataXml}),
                dataType: "text",
                success : function(data, status) {
                   jQuery("#dialog_message").html(data);
                   jQuery("#dialog_message").dialog();
                },
                error: function(){
                    jQuery("#dialog_message").html('<span style="color:red">后台错误!</span>');
                    jQuery("#dialog_message").dialog(); 
                }
            });
        },
        parseXml: function(parameters){
            //reset all cache data.
            this.resetGlobalVar();
            var htmlBuffer = "";
            jQuery.ajax({
                async:false,
                type: "GET",
                url: "template/getxml.action",
                cache: false,
                dataType: "xml",
                data:parameters,
                success : function(data, status) {
                    var pluginRef = jQuery.dynamicplugin;
                    var resp = data;

                    var makeObject = function(objectId, source, options, container){
                        options.parseFromXml(source);
                        options = pluginRef.unescapseOptions(options);
                        pluginRef.elementArray[objectId] = options;
                        var createObj = pluginRef.createElementFactory(options);
                        if(container!=null){
                            container.addElement(createObj);
                        }else{
                            container =  createObj;
                        }
                        return createObj;
                    };
                    var form = null;
                    //Chrome,Sarfari browser doesn't recognize namspace of xml.
                    var useragent = navigator.userAgent.toLowerCase();
                    if(useragent.indexOf('safari')!=-1|| useragent.indexOf('chrome')!=-1){
                        form = jQuery(resp).find('form');
                    }else{
                        form = jQuery(resp).find('tns\\:form');
                    }
                    var formId = form.attr("id");
                    //initial form id for edition operation.
                    pluginRef.formId = formId;
                    var options = new UiInputDialog(formId,"form");
                    jQuery(form).attr("label",parameters.label);
                    var createForm = makeObject(formId,form,options,null);

                    //loop element per fieldset
                    jQuery(form).find("fieldset").each(function(){
                        var fieldSet = jQuery(this);
                        var fieldId = fieldSet.attr("id");
                        var options = new UiInputDialog(fieldId,"fieldset");
                        var createFieldset = makeObject(fieldId,fieldSet,options,createForm);
                        //loop row within fieldset
                        jQuery(fieldSet).find("row").each(function(){
                            var row = jQuery(this);
                            var rowId = row.attr("id");
                            var options = new UiInputDialog(rowId,'row');
                            var createRow = makeObject(rowId,row,options,createFieldset);
                            //loop element within row
                            jQuery(row).find("element").each(function(){
                                var element = jQuery(this);
                                var type = element.attr("type");
                                var typeObj = element.find(type);
                                var elementId = typeObj.attr("id");
                                var options = new UiInputDialog(elementId,type);
                                var createElement = makeObject(elementId,typeObj,options,createRow);
                                createElement.position = element.attr("position");
                            });
                        });
                    });
                    
                    htmlBuffer = createForm.toHtml();
                },
                error: function(){
                    jQuery("#dialog_message").html('<span style="color:red">后台错误!</span>');
                    jQuery("#dialog_message").dialog(); 
                }
             });
            return htmlBuffer;
        },
        addNewElement:function(ui, dest){
            //global uuid that avoid to id conflict issue.
            var _id = $.uuid();
            var typeToAdd = ui.draggable.attr('id');  
            typeToAdd = !typeToAdd?"text":typeToAdd.toLowerCase();
            // fetching inputs data of dialog with object instance;
            var options = new UiInputDialog(_id,typeToAdd);
            this.elementArray[_id] = options;
            
            var elementInstance = this.createElementFactory(options);
            var outputHtml = elementInstance.toHtml();
            jQuery(dest).append(outputHtml);
            
            var url = "pages/panel_dialog/input_" + typeToAdd + ".html";
            jQuery("#dialog_elt").load(url, function() {
                jQuery("#e_id").val(_id);
                if (jQuery.dynamicplugin.elementArray[_id] != null) {
                    jQuery.dynamicplugin.elementArray[_id].showElement();
                }
                jQuery(this).dialog('open');
            });
        },
        updateElement: function(_no){
            var element = this.elementArray[_no];
            if (element != null) {
                element.saveElement();
                var blockId = "#block_" + _no;
                if (element.type != 'fieldset' && element.type != 'form') {
                    var elementInstance = this.createElementFactory(element);
                    var outputHtml = elementInstance.toHtml();
                    jQuery(blockId).replaceWith(outputHtml);
                }else if(element.type == 'fieldset'){
                    jQuery("#legend_"+_no).text(element.legend);
                }
            } else {
                alert("This element id " + _no + " is empty!");
            }  
        },
        deleteElement:function(id){
                var _no = id;
                if (_no != null) {
                    //remove cache data from memory.
                    
                    var ref = this.elementArray[_no];
                    if(ref.type=="form"){
                        //reset all cache data.
                        this.resetGlobalVar();
                        jQuery.ajax({
                            async : false,
                            type : "GET",
                            url : "template/deletetemplate.action?name="+ref.name,
                            cache : false,
                            success : function(data, status) {
                                jQuery("#dialog_message").html(data);
                                jQuery("#dialog_message").dialog();
                            }
                        });
                    }else{
                        delete this.elementArray[_no];
                    }
                    
                    //remove DOM node from DOM tree of Form.
                    jQuery("#block_"+_no).remove();
                }
        },
        createForm:function(options){
            //reset all cache data.
            this.resetGlobalVar();
            //global uuid that avoid to id conflict issue.
            var _id = $.uuid();
            //assign to global variable for form.
            this.formId = _id;
            var typeToAdd = !options.type?"text":options.type.toLowerCase();
            var options = new UiInputDialog(_id,typeToAdd);
            options.name = "template"+new Date().getTime();
            this.elementArray[_id] = options;
            var elementInstance = this.createElementFactory(options);
            var outputHtml = elementInstance.toHtml();
            return outputHtml;
        }
    };

})(jQuery);