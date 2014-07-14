<section class="panel" id="${"$"}{viewId!}_${id!}" name="${"$"}{viewId!}_${id!}">
    <header class="panel-heading">
        ${name!}
        <span class="tools pull-right">
            <a class="fa fa-chevron-down" href="javascript:;"></a>
        </span>
    </header>
    <div class="panel-body">
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
                                <#if column.htmlType == 0>
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

            var editor_${id!} = new function() {
                return {
                    data : [
                            <#list columns as column>
                                <#if column.htmlType == 0>
                                <#--text-->
                                {
                                    html:"${column.name}",
                                    getHtml: function(value) {
                                        return this.html.replace(/###value###/g, value);
                                    },
                                    getValue : function() {
                                        return "${column.name}";
                                    },
                                    getDisplayValue: function() {
                                        return "${column.name}";
                                    }
                                }
                                <#elseif column.htmlType == 1>
                                <#--text-->
                                {
                                    html:"<input id='${id!}_column_editor_${column_index}' type='text' value='###value###' class='form-control small'>",
                                    getHtml: function(value) {
                                        return this.html.replace(/###value###/g, value);
                                    },
                                    getValue : function() {
                                        return $("#${id!}_column_editor_${column_index}").val();
                                    },
                                    getDisplayValue: function() {
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
                                    getHtml: function(value) {
                                        return this.html.replace(/###value###/g, value);
                                    },
                                    getValue: function() {
                                        return $("#${id!}_column_editor_${column_index}").val();
                                    },
                                    getDisplayValue: function() {
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
                                    getHtml: function(value) {
                                        return this.html.replace(/###value###/g, value);
                                    },
                                    getValue: function() {
                                        var resultValue = "";
                                        $("input[name=${id!}_column_editor_${column_index}]:checked").each(
                                            function(){
                                                resultValue = resultValue + ";" + $(this).val();
                                            }
                                        );
                                        return resultValue;
                                    },
                                    getDisplayValue: function() {
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
                                    getHtml: function(value) {
                                        return this.html.replace(/###value###/g, value);
                                    },
                                    getValue: function() {
                                        return $("input[name=${id!}_column_editor_${column_index}]:checked").val();
                                    },
                                    getDisplayValue: function() {
                                        return $("input[name=${id!}_column_editor_${column_index}]:checked").parent().text();
                                    }
                                }
                                </#if>
                                ,
                            </#list>
                            ],

                    get : function(index, value) {
                        if(index < this.data.length) {
                            return this.data[index].getHtml(value);
                        }
                        return "";
                    } ,

                    getValue : function(index) {
                        if(index < this.data.length) {
                            return this.data[index].getValue();
                        }
                        return "";
                    } ,

                    getDisplayValue : function(index) {
                        if(index < this.data.length) {
                            return this.data[index].getDisplayValue();
                        }
                        return "";
                    }
                }
            }
            EditableTable.init($("#grid_${id!}"), editor_${id!});
        </script>
    </div>
</section>