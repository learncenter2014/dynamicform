<!DOCTYPE html>
<%@ include file="commonHeader.jsp"%>
<html>
<head>
<base href="pages/form.jsp"></base>
<link rel="stylesheet" href="../javascripts/dojo-release-1.9.2/dijit/themes/claro/claro.css">
<script>
    dojoConfig = {
        async : true,
        parseOnLoad : true
    }
</script>
<script type="text/javascript" src="../javascripts/dojo-release-1.9.2/dojo/dojo.js"></script>
</head>

<script>
    require([ "dojo/parser", "dijit/form/ValidationTextBox" ]);
</script>

</head>
<body class="claro">
  <label for="phone">Phone number, no spaces:</label>
  <input type="text" name="phone" id="phone" value="someTestString" required="true" data-dojo-type="dijit/form/ValidationTextBox"
    data-dojo-props="regExp:'[\\w]+', invalidMessage:'Invalid Non-Space Text.'" />
  <input type="text" name="number" id="number" value="1234" required="true" data-dojo-type="dijit/form/ValidationTextBox" data-dojo-props="regExp:'[\\d]+', invalidMessage:'Invalid number.'" />
  <input type="submit" name="Submit" />
</body>
</html>