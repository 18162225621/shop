<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">

<title>Insert title here</title>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>

<body> 
<div id="result" style="border:1px solid red;width:600px;height:200px;">
	<table border="1">
		<tr>
			<td>编号</td>
			<td>名称</td>
			<td>上线</td>
			<td>下线</td>
		</tr>
	<s:iterator value="cdns">
		<tr>
			<td>
				<s:property value="cdnId" />
			</td>
			<td>
				<s:property value="cdnName" />
			</td>
			<td>
				<s:property value="cdnOnline" />
			</td>
			<td>
				<s:property value="cdnOffine" />
			</td>
		</tr>
	</s:iterator>
	</table>
</div>
</body>
</html>