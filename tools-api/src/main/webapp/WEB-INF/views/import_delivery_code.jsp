<%--
  Created by IntelliJ IDEA.
  User: lupeibo
  Date: 2019-06-28
  Time: 17:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>批量导入物流单号</title>
</head>
<body>
<form action="/delivery/importDeliveryCode" method="post">
    选择文件：<input type="file" id="file" name="file" />
</form>

</body>
</html>
