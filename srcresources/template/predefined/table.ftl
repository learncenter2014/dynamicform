
        <#list rows as row>
            <#list columns as column>
                <input type="hidden" name="${row.code!}_${column.code!}" value="${"$"}{${row.code!}_${column.code!}!}">
            </#list>
        </#list>

        <script type="text/javascript">
            var KeyValueMap_${id!} = new function() {
                return {
                    data: new Object(),

                    init: function() {
                        <#list columns as column>
                            <#list column.entryCodeBeanList as code>
                                this.data["${column.code}_${code.value}"] = "${code.name}";
                            </#list>
                        </#list>
                    },

                    get: function(code, key) {
                        var value = this.data[code + "_" + key];
                        if(null != value && 'undefined' != value) {
                            return value;
                        }
                        return key;
                    }
                }
            };
            KeyValueMap_${id!}.init();
        </script>

        <table class="table table-striped table-hover table-bordered" id="grid_${id!}">
            <thead>
            <tr>
                <th>#</th>
                <#list columns as column>
                    <th>${column.name!}</th>
                </#list>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
                <#list rows as row>
                    <tr>
                        <td>${row.name}</td>
                        <#list columns as column>
                            <td id="${row.code!}_${column.code!}" class="data-cell">
                                <#if column.subElementType == 2>
                                    <#if row.pseudoReferenceLowerValue?? && row.pseudoReferenceUpperValue??>
                                        ${row.pseudoReferenceLowerValue}~${row.pseudoReferenceUpperValue}
                                    <#elseif row.pseudoReferenceLowerValue??>
                                        >${row.pseudoReferenceLowerValue}
                                    <#elseif row.pseudoReferenceUpperValue??>
                                        <${row.pseudoReferenceUpperValue}
                                    </#if>
                                <#elseif column.subElementType == 4>
                                    <#if row.pseudoReferenceUnitCode??>
                                        ${row.pseudoReferenceUnitCode}
                                    <#elseif column.pseudoReferenceUnitCode??>
                                        ${column.pseudoReferenceUnitCode}
                                    </#if>
                                <#elseif column.htmlType == 0>
                                    ${column.name}
                                <#elseif column.htmlType == 1>
                                    ${"$"}{${row.code!}_${column.code!}!}
                                <#elseif column.htmlType == 3 || column.htmlType == 5>
                                    <script type="text/javascript">
                                        $("#${row.code!}_${column.code!}").html(KeyValueMap_${id!}.get("${column.code}", "${'$'}{${row.code!}_${column.code!}!}"));
                                    </script>
                                <#elseif column.htmlType == 4>
                                    <script type="text/javascript">
                                        var values = "${'$'}{${row.code!}_${column.code!}!}".split(";");
                                        var labels = "";
                                        for(var index in values) {
                                            if(labels.length > 0) {
                                                labels = labels + "," + KeyValueMap_${id!}.get("${column.code}", values[index]);
                                            } else {
                                                labels = KeyValueMap_${id!}.get("${column.code}", values[index]);
                                            }
                                        }

                                        $("#${row.code!}_${column.code!}").html(labels);
                                    </script>
                                </#if>
                            </td>
                        </#list>
                        <td><a class="edit" href="javascript:;">编辑</a></td>
                    </tr>
                </#list>
            </tbody>
        </table>

        <script>
            window.validationRules_${id!} = [];

            var editor_${id!} = new function() {
                return {
                    data : [
                            <#list columns as column>
                                <#if column.htmlType == 0>
                                <#--label-->
                                {
                                    html:"",
                                    getHtml: function(cellId, value) {
                                        return $("#"+cellId).html().trim();
                                    },
                                    getValue : function(cellId) {
                                        return $("#"+cellId).html().trim();
                                    },
                                    getDisplayValue: function(cellId) {
                                        return $("#"+cellId).html().trim();
                                    }
                                }
                                <#elseif column.htmlType == 1>
                                <#--text-->
                                {
                                    html:   "<input id='${id!}_column_editor_${column_index}' name='${id!}_column_editor_${column_index}' type='text' value='###value###' class='form-control small'>" +
                                            "<script type='text/javascript'>" +
                                                "window.validationRules_${id!}['${id!}_column_editor_${column_index}'] = {" +
                                                    "maxlength:${column.maxLength}" +
                                                <#if column.dataType == 1>
                                                    ", digits:true" +
                                                    ", range:[${column.minValue},${column.maxValue}]" +
                                                <#elseif column.dataType == 2>
                                                    ", number:true" +
                                                    ", range:[${column.minValue},${column.maxValue}]" +
                                                </#if>
                                                "};" +
                                            "<\/script>",
                                    getHtml: function(cellId, value) {
                                        return this.html.replace(/###value###/g, value);
                                    },
                                    getValue : function(cellId) {
                                        return $("#${id!}_column_editor_${column_index}").val();
                                    },
                                    getDisplayValue: function(cellId) {
                                        return $("#${id!}_column_editor_${column_index}").val();
                                    }
                                }
                                <#elseif column.htmlType == 3>
                                <#--select-->
                                {
                                    html:   "<select id='${id!}_column_editor_${column_index}' class='form-control m-bot15' >" +
                                            <#list column.entryCodeBeanList as option>
                                                "<option value='${option.value!}'>${option.name!}</option>" +
                                            </#list>
                                            "<\/select>" +
                                            "<script type='text/javascript'>" +
                                                "$('#${id!}_column_editor_${column_index}').val('###value###');" +
                                            "<\/script>",
                                    getHtml: function(cellId, value) {
                                        return this.html.replace(/###value###/g, value);
                                    },
                                    getValue: function(cellId) {
                                        return $("#${id!}_column_editor_${column_index}").val();
                                    },
                                    getDisplayValue: function(cellId) {
                                        return $("#${id!}_column_editor_${column_index} option:selected").text();
                                    }
                                }
                                <#elseif column.htmlType == 4>
                                <#--checkbox-->
                                {
                                    html:   <#list column.entryCodeBeanList as option>
                                                "<label class='checkbox-inline'>" +
                                                    "<input type='checkbox' id='${id!}_column_editor_${column_index}' name='${id!}_column_editor_${column_index}' value='${option.value}'>" +
                                                    "${option.name}" +
                                                "</label>" +
                                            </#list>
                                            "<script type='text/javascript'>" +
                                                "var checkedValueStr = '###value###';" +
                                                "var checkedValues = checkedValueStr.split(';');" +
                                                "for(index in checkedValues){" +
                                                    "$(\"input[name=${id!}_column_editor_${column_index}][value=\"+checkedValues[index]+\"]\").attr('checked','checked');" +
                                                "}" +
                                            "<\/script>",
                                    getHtml: function(cellId, value) {
                                        return this.html.replace(/###value###/g, value);
                                    },
                                    getValue: function(cellId) {
                                        var resultValue = "";
                                        $("input[name=${id!}_column_editor_${column_index}]:checked").each(
                                            function(){
                                                resultValue = resultValue + ";" + $(this).val();
                                            }
                                        );
                                        return resultValue;
                                    },
                                    getDisplayValue: function(cellId) {
                                        var resultValue = "";
                                        $("input[name=${id!}_column_editor_${column_index}]:checked").each(
                                            function(){
                                                if(resultValue.length > 0) {
                                                    resultValue = resultValue + "," + $(this).parent().text();
                                                } else {
                                                    resultValue = $(this).parent().text();
                                                }
                                            }
                                        );
                                        return resultValue;
                                    }
                                }
                                <#elseif column.htmlType == 5>
                                <#--radio-->
                                {
                                    html:   <#list column.entryCodeBeanList as option>
                                                "<label class='radio-inline'>" +
                                                    "<input type='radio' name='${id!}_column_editor_${column_index}' value='${option.value}'> ${option.name}" +
                                                "</label>" +
                                            </#list>
                                            "<script type='text/javascript'>" +
                                                "$(\"input[name=${id!}_column_editor_${column_index}][value=###value###]\").attr('checked','checked');" +
                                            "<\/script>"
                                            ,
                                    getHtml: function(cellId, value) {
                                        return this.html.replace(/###value###/g, value);
                                    },
                                    getValue: function(cellId) {
                                        return $("input[name=${id!}_column_editor_${column_index}]:checked").val();
                                    },
                                    getDisplayValue: function(cellId) {
                                        return $("input[name=${id!}_column_editor_${column_index}]:checked").parent().text();
                                    }
                                }
                                <#elseif column.htmlType == 6>
                                <#--date-->
                                {
                                    html:
                                            "<input id='${id!}_column_editor_${column_index}' name='{id!}_column_editor_${column_index}' data-date-format="${format!'yyyy-mm-dd'}" value='###value###'>"
                                            "<script type='text/javascript'>" +
                                            "$('#${id!}_column_editor_${column_index}').datepicker({format: 'yyyy-mm-dd'});" +
                                            "<\/script>"
                                    ,
                                    getHtml: function(cellId, value) {
                                        return this.html.replace(/###value###/g, value);
                                    },
                                    getValue: function(cellId) {
                                        return $('#${id!}_column_editor_${column_index}').val();
                                    },
                                    getDisplayValue: function(cellId) {
                                        return $('#${id!}_column_editor_${column_index}').val();
                                    }
                                }
                                </#if>
                                ,
                            </#list>
                            ],

                    get : function(index, cellId, value) {
                        if(index < this.data.length) {
                            return this.data[index].getHtml(cellId, value);
                        }
                        return "";
                    } ,

                    getValue : function(index, cellId) {
                        if(index < this.data.length) {
                            return this.data[index].getValue(cellId);
                        }
                        return "";
                    } ,

                    getDisplayValue : function(index, cellId) {
                        if(index < this.data.length) {
                            return this.data[index].getDisplayValue(cellId);
                        }
                        return "";
                    } ,

                    validate : function() {
                        var rules = window.validationRules_${id!};
                        for(var ruleId in rules) {
                            var rule = rules[ruleId];
                            var objValue = $("#" + ruleId).val();
                            if(rule.required) {
                                if(!objValue || objValue.length < 1) {
                                    this.showValidateMessage(0, ruleId);
                                    return false;
                                }
                            }
                            if(rule.maxlength) {
                                if(objValue && objValue.length > rule.maxlength) {
                                    this.showValidateMessage(1, ruleId, rule.maxlength);
                                    return false;
                                }
                            }
                            if(rule.number) {
                                if (!(/^(\+|-)?\d+($|\.\d+$)/.test(objValue))) {
                                    this.showValidateMessage(2, ruleId);
                                    return false;
                                }
                            }
                            if(rule.digits) {
                                if (!(/^(\+|-)?\d+$/.test(objValue))) {
                                    this.showValidateMessage(3, ruleId);
                                    return false;
                                }

                            }

                            if(rule.number || rule.digits) {
                                if(rule.range && rule.range.length == 2) {
                                    if(objValue < rule.range[0] || objValue > rule.range[1]) {
                                        this.showValidateMessage(6, ruleId, rule.range[0], rule.range[1]);
                                    }
                                }
                                if(rule.min) {
                                    if(objValue < rule.min) {
                                        this.showValidateMessage(4, ruleId, rule.min);
                                    }
                                }
                                if(rule.max) {
                                    if(objValue < rule.max) {
                                        this.showValidateMessage(5, ruleId, rule.max);
                                    }
                                }
                            }
                        }
                        return true;
                    },

                    showValidateMessage : function(type, objId, value1, value2) {
                        //type 0: required; 1: maxlengeth; 2:number; 3:digits; 4: minValue; 5:maxValue; 6:range;
                        var resultMsg;
                        switch(type) {
                            case 0:
                                resultMsg = jQuery.validator.messages.required;
                                break;
                            case 1:
                                resultMsg = jQuery.validator.messages.maxlength(value1);
                                break;
                            case 2:
                                resultMsg = jQuery.validator.messages.number;
                                break;
                            case 3:
                                resultMsg = jQuery.validator.messages.digits;
                                break;
                            case 4:
                                resultMsg = jQuery.validator.messages.min(value1);
                                break;
                            case 5:
                                resultMsg = jQuery.validator.messages.max(value1);
                                break;
                            case 6:
                                resultMsg = jQuery.validator.messages.range(value1, value2);
                                break;
                        }
                        var validateHtml = "<label for=" + objId + " class='error'>" + resultMsg + "</label>";
                        $("label[for='"+objId+"']").remove();
                        $("#"+objId).parent().append(validateHtml);
                    }
                }
            }
            EditableTable.init($("#grid_${id!}"), editor_${id!});
        </script>
