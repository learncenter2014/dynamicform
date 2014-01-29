(function($) {
    /* abstract class for all Elements */
    function Component(options) {
        this.id = options.id;
        this.label = options.label;
        this.name = options.name;
        this.type = undefined;
        this.toXml = function() {
            alert("Please implement toXml method");
        }
    }
    var component = new Component({});

    /* abstract class for Input */
    function Input(options) {
        Component.call(this, options);
        this.value = options.value;
        this.size = options.size;
        this.maxlength = options.maxlength;
        this.required = options.required;
        this.readonly = options.readonly;
    }
    Input.protottype = component;

    /* sub-class for FieldSet */
    function FieldSet(options) {
        Component.call(this, options);
        this.width = options.width;
        this.height = options.height;
        this.legend = options.legend;
        this.type = "fieldset";
        this.toXml = function() {
            alert("Please implement toXml method");
        }
    }
    FieldSet.prototype = component;

    /* sub-class for Image */
    function Image(options) {
        Component.call(this, options);
        this.src = options.src;
        this.type = "image";
        this.toXml = function() {
            alert("Please implement toXml method");
        }
    }
    Image.prototype = component;

    /* sub-class for Button */
    function Button(options) {
        Component.call(this, options);
        this.type = "button";
        this.toXml = function() {
            alert("Please implement toXml method");
        }
    }
    Button.prototype = component;

    /* sub-class for Checkbox */
    function Checkbox(options) {
        Component.call(this, options);
        this.type = "checkbox";
        this.toXml = function() {
            alert("Please implement toXml method");
        }
    }
    Checkbox.prototype = component;

    /* sub-class for Radio */
    function Radio(options) {
        Component.call(this, options);
        this.type = "radio";
        this.toXml = function() {
            alert("Please implement toXml method");
        }
    }
    Radio.prototype = component;

    /* sub-class for Text */
    function Text(options) {
        Component.call(this, options);
        this.regexp = options.regexp;
        this.type = "text";
        this.toXml = function() {
            alert("Please implement toXml method");
        }
    }
    Text.prototype = component;

    /* sub-class for TextArea */
    function TextArea(options) {
        Component.call(this, options);
        this.type = "textarea";
        this.toXml = function() {
            alert("Please implement toXml method");
        }
    }
    TextArea.prototype = component;

    /* sub-class for Select */
    function Select(options) {
        Component.call(this, options);
        this.listvalue = options.listvalue;
        this.type = "button";
        this.toXml = function() {
            alert("Please implement toXml method");
        }
    }
    Select.prototype = component;

    /* sub-class for Date */
    function DateElement(options) {
        Component.call(this, options);
        this.format = options.format;
        this.type = "date";
        this.toXml = function() {
            alert("Please implement toXml method");
        }
    }
    Date.prototype = component;

    /**
     * binding plugin dynamicform into Jquery.
     * 
     * Element Factory for these lifecycle of object.
     * 
     */
    $.dynamicplugin = {
        pageStorage : [],
        createElementFactory : function(options) {
            var type = options.type_html;
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
        storeElements : function(object) {
            this.pageStorage.push(object);
        },
        deleteELement : function(id) {
            for ( var i = 0; i < this.pageStorage.length; i++) {
                if (id == this.pageStorage[0].id) {
                    this.pageStorage.splice(i, 1);
                }
            }
        },
        returnXml : function() {

        }
    };

})(jQuery);