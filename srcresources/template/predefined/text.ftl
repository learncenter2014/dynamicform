<div class="col-sm-${resolution!6}">
    <div class="form-group">
        <label class="col-sm-3 control-label" for="${document.code!}_${code!}">${name!'Label not defined'}</label>
        <div class="col-sm-9">
            <input class="form-control" id="${document.code!}_${code!}" name="${document.code!}_${code!}" value="${"$"}{${document.code!}_${code}!'${defaultValue!}'}">
        </div>
    </div>
</div>
<script type="text/javascript">
//    window.validationRules("add", {
//        required: true,
//        minlength: 2,
//        messages: {
//            required: "Required input",
//            minlength: jQuery.format("Please, at least {0} characters are necessary")
//        }
//    });
    window.validationRules['${document.code!}_${code!}']={
        <#if dataType == 1>
            digits: true,
            range: [${minValue}, ${maxValue}],
        <#elseif dataType == 2>
            number: true,
            range: [${minValue}, ${maxValue}],
        <#elseif dataType == 3>
            date: true,
        </#if>

        <#if maxLength??>
            maxlength: ${maxLength},
        </#if>

        required: false
    }
</script>
