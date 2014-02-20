<html:html>
<%@ include file="/pages/commonHeader.jsp"%>
<head>
<title>generalError.jsp</title>
</head>

<body>

	<h3>General Server Exception, please contact with Peter. +8618651806651 QQ:116352437</h3>
	<pre style="color: red">
		<s:property value="exception.message" />
	</pre>
	<br>
	<pre>
		<s:property value="exceptionStack" />
	</pre>
</body>
</html:html>
