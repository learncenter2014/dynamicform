<section>
<form id="${id!}" name="${name!}" action="${action!}" class="form-horizontal">
    ${innerHTML!}
</form>
<script>
    $("#${id!null}").submit(function() {
        // inside event callbacks 'this' is the DOM element so we first
        // wrap it in a jQuery object and then invoke ajaxSubmit
        //$(this).ajaxSubmit();
        $.post(
            "${action!}" ,
            this.serialize(),
            function(data, textStatus, jqXHR) {
                alert(data);
            }
        );
        // !!! Important !!!
        // always return false to prevent standard browser submit and page navigation
        return false;
    });
</script>
</section>