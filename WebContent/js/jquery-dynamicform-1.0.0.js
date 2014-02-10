(function($) {
    $.substitute = function(str, o, regexp) {
        /**
         * ${XXX} style will be replaced by this method.
         */
        var callback = function(match, name) {
            return (o[name] == undefined) ? '' : o[name];
        };
        return str.replace(regexp || /\$\{([^{}]+)\}/g, callback);
    }
    /* abstract class for all Elements */
    function Component(options) {
        this.id = options.id;
        this.label = options.label;
        this.name = options.name;
        this.type = undefined;
        this.template = "";
        this.toXml = function() {
            return $.substitute(this.template, this);
        };
    }
    ;
    var component = new Component({});

    /* abstract class for Input */
    function Input(options) {
        Component.call(this, options);
        this.value = options.value;
        this.size = options.size;
        this.maxlength = options.maxlength;
        this.required = options.required;
        this.readonly = options.readonly;
        this.helptext = options.helptext;
    }
    Input.protottype = component;
    Input
    input = new Input({});
    /* sub-class for FieldSet */
    function FieldSet(options) {
        Component.call(this, options);
        this.width = options.width;
        this.height = options.height;
        this.legend = options.legend;
        this.elements = [];
        this.addElement = function(obj) {
            this.elements.push(obj);
        };
        this.deleteElement = function(id) {
            for ( var i = 0; i < this.elements.length; i++) {
                if (id == this.elements[i].id) {
                    this.elements.splice(i, 1);
                    break;
                }
            }
        };
        this.template = '<fieldset id="${id}" name="${name}" label="${label}" width="${width}" height="${height}" legend="${legend}">${subElements}\n</fieldset>';
        this.toXml = function() {
            this.subElements = "";
            for ( var i = 0; i < this.elements.length; i++) {
                this.subElements += "\n" + this.elements[i].toXml();
            }
            return $.substitute(this.template, this);
        };
    }
    FieldSet.prototype = component;

    /* sub-class for Image */
    function Image(options) {
        Component.call(this, options);
        this.src = options.src;
        this.template = '<element type="image"><image id="${id}" name="${name}" label="${label}" src="${src}"></image></element>';
    }
    Image.prototype = component;

    /* sub-class for Button */
    function Button(options) {
        Input.call(this, options);
        this.template = '<element type="button"><button id="${id}" name="${name}" label="${label}" value="${value}" helptext="${helptext}"/></element>';
    }
    Button.prototype = component;

    /* sub-class for Checkbox */
    function Checkbox(options) {
        Input.call(this, options);
        this.listvalue = options.listvalue;
        this.template = '<element type="checkbox"><checkbox id="${id}" name="${name}" label="${label}" value="${value}" size="${size}" maxlength="${maxlength}" required="${required}" readonly="${readonly}" listvalue="${listvalue}" helptext="${helptext}"/></element>';
    }
    Checkbox.prototype = input;

    /* sub-class for Radio */
    function Radio(options) {
        Input.call(this, options);
        this.listvalue = options.listvalue;
        this.template = '<element type="radio"><radio id="${id}" name="${name}" label="${label}" value="${value}" size="${size}" maxlength="${maxlength}" required="${required}" readonly="${readonly}" listvalue="${listvalue}" helptext="${helptext}"/></element>';
    }
    Radio.prototype = input;

    /* sub-class for Text */
    function Text(options) {
        Input.call(this, options);
        this.regexp = options.regexp;
        this.template = '<element type="text"><text id="${id}" name="${name}" label="${label}" value="${value}" size="${size}" maxlength="${maxlength}" required="${required}" readonly="${readonly}" regexp="${regexp}" helptext="${helptext}"/></element>';
    }
    Text.prototype = input;

    /* sub-class for TextArea */
    function TextArea(options) {
        Component.call(this, options);
        this.template = '<element type="textarea"><textarea id="${id}" name="${name}" label="${label}" value="${value}" size="${size}" maxlength="${maxlength}" required="${required}" readonly="${readonly}" helptext="${helptext}"/></element>';
    }
    TextArea.prototype = input;

    /* sub-class for Select */
    function Select(options) {
        Component.call(this, options);
        this.listvalue = options.listvalue;
        this.template = '<element type="select"><select id="${id}" name="${name}" label="${label}" value="${value}" size="${size}" maxlength="${maxlength}" required="${required}" readonly="${readonly}" listvalue="${listvalue}" helptext="${helptext}"/></element>';
    }
    Select.prototype = input;

    /* sub-class for Date */
    function DateElement(options) {
        Component.call(this, options);
        this.format = options.format;
    }
    Date.prototype = input;

    /**
     * binding plugin dynamicform into Jquery.
     * 
     * Element Factory for these lifecycle of object.
     * 
     */
    $.dynamicplugin = {
        fieldSets : [],
        createElementFactory : function(options) {
            var type = options.type;
            var instanceObj = null;
            if (type != undefined) {
                switch (type.toLowerCase()) {
                case "text":
                    instanceObj = new Text(options);
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
                case "feildset":
                    instanceObj = new FieldSet(options);
                    break;
                }
            }
            return instanceObj;
        },
        storeFieldSet : function(object) {
            this.fieldSets.push(object);
        },
        deleteELement : function(id) {
            for ( var i = 0; i < this.fieldSets.length; i++) {
                if (id == this.fieldSets[i].id) {
                    this.fieldSets.splice(i, 1);
                    break;
                } else {
                    this.fieldSets[i].deleteElement(id);
                }
            }
        },
        toXml : function() {
            var stringBuffer = '<?xml version="1.0" encoding="UTF-8"?>\n';
            stringBuffer += '<tns:form xmlns:tns="http://www.dynamic.org/dynamicform">\n';
            for ( var i = 0; i < this.fieldSets.length; i++) {
                stringBuffer += this.fieldSets[i].toXml() + "\n";
            }
            stringBuffer += '</tns:form>';
            return stringBuffer;
        }
    };

})(jQuery);