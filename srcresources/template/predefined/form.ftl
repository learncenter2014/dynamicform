<section>
    <script type="text/javascript">
        window.validationRules = new Object();
        jQuery.extend(jQuery.validator.messages, {
            required: "必选字段",
            remote: "请修正该字段",
            email: "请输入正确格式的电子邮件",
            url: "请输入合法的网址",
            date: "请输入合法的日期",
            dateISO: "请输入合法的日期 (ISO).",
            number: "请输入合法的数字",
            digits: "只能输入整数",
            creditcard: "请输入合法的信用卡号",
            equalTo: "请再次输入相同的值",
            accept: "请输入拥有合法后缀名的字符串",
            maxlength: jQuery.validator.format("长度不能超过 {0}"),
            minlength: jQuery.validator.format("长度不能少于 {0}"),
            rangelength: jQuery.validator.format("请输入一个长度介于 {0} 和 {1} 之间的字符串"),
            range: jQuery.validator.format("请输入一个介于 {0} 和 {1} 之间的值"),
            max: jQuery.validator.format("请输入一个最大为 {0} 的值"),
            min: jQuery.validator.format("请输入一个最小为 {0} 的值")
        });
    </script>
    <form id="${id!}" name="${name!}" action="datainput/dataRecordInputSubmit.action" class="form-horizontal">
        ${innerHTML!}
        <section class="panel">

            <div class="panel-body">
                <button type="submit" class="btn btn-info">保存</button>
            </div>
        </section>

        <input type="hidden" name="studyId" value="${"$"}{studyId!}"/>
        <input type="hidden" name="viewId" value="${"$"}{viewId!}"/>
        <input type="hidden" name="participantId" value="${"$"}{participantId!}"/>
        <input type="hidden" name="recordId" value="${"$"}{recordId!}"/>
        <input type="hidden" name="tableName" value="${study.code}"/>

    </form>

    <script>
        $("#${id!null}").validate(
            {
                rules: window.validationRules,
                submitHandler: function() {
                    form${id!null}Submit();
                }
            }
        );

        function form${id!null}Submit() {
            // inside event callbacks 'this' is the DOM element so we first
            // wrap it in a jQuery object and then invoke ajaxSubmit
            //$(this).ajaxSubmit();
            jQuery.ajax({
                type: "POST",
                async:false,
                url:"${"$"}{rootPath!}/dataInput/dataRecordInputSubmit.action",
                data:$("#${id!null}").serialize(),
                cache: false,
                success : function(data, status) {
                    display(data);
                }
            });
            // !!! Important !!!
            // always return false to prevent standard browser submit and page navigation
            return false;
        }
    </script>
</section>