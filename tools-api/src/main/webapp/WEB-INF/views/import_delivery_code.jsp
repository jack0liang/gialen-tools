<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: lupeibo
  Date: 2019-06-28
  Time: 17:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>批量导入物流单号</title>
    <script src="${ctx}/static/js/jquery-3.4.1.min.js"></script>
    <style>
        .tableReport-button {
            display: inline-block;
            position: relative;
            top: 15px;
            left: 4%;
            overflow: hidden;
            text-align: center;
            width: auto;
            background-color: #0056C0;
            border: solid 1px #ddd;
            border-radius: 4px;
            padding: 5px 15px;
            font-size: 12px;
            font-weight: normal;
            line-height: 19px;
            color: #fff;
            text-decoration: none;
        }
        .tableReport-button input[type="file"] {
            position: absolute;
            top: 0;
            right: 0;
            font-size: 14px;
            background-color: #fff;
            transform: translate(-300px, 0px) scale(4);
            height: 40px;
            opacity: 0;
            filter: alpha(opacity=0);
        }
        .tableReport-button input[type="button"] {
            position: absolute;
            top: 0;
            right: 0;
            font-size: 14px;
            background-color: #fff;
            transform: translate(-300px, 0px) scale(4);
            height: 40px;
            opacity: 0;
            filter: alpha(opacity=0);
            line-height: inherit;
        }
        .tableReport-button:link {color:#fff;}
        .tableReport-button:visited {color: #fff;}
        .tableReport-button:hover{color: #fff;}
        .tableReport-button:active {color:#fff;}
        .tableReport-button-hide {
            cursor: default !important;
            top: 70px !important;
            left: -10% !important;

        }
        .fileinput-button {
            position: relative;
            overflow: hidden;
            background: #1E9FFF;
            width: 70px;
            height: 30px;
            color: #F7F7F7;
            font-size: 14px;
            text-align: center;
            border: 0;
            border-radius: 5%;
            line-height: 30px;
        }
        .fileinput-button:hover {
            background: #FF8650;
        }
        .fileinput-button input {
            position: absolute;
            top: 0;
            right: 0;
            margin: 0;
            opacity: 0;
            -ms-filter: 'alpha(opacity=0)';
            font-size: 200px;
            direction: ltr;
            cursor: pointer;
        } 
    </style>
</head>

<body>
    <div>
        选择文件：<input type="file" id="file" name="file"/><br>
    </div>

    <div>
        <input id="btn" type="button" class="tableReport-button" onclick="postData();" value="提交" />
    </div><br>
    <div><a href="${ctx}/static/muban.xlsx" style="margin-left: 50px;">模版下载</a></div>

</body>
<script>
    function postData() {
        if($("#file")[0].files[0] == null) {
            alert("请选择导入文件");
            return false;
        }
        var formData = new FormData();
        formData.append("file",$("#file")[0].files[0]);
        var oBtn = document.getElementById('btn');
        oBtn.disabled = 'disabled';
        oBtn.value = '处理中，请稍后...';
        $.ajax({
            url:'${ctx}/delivery/importDeliveryCode',
            type:'post',
            data: formData,
            contentType: false,
            processData: false,
            success:function(res){
                oBtn.disabled = '';
                oBtn.value = '提交';
                if(res.success){
                    alert('成功');
                }else{
                    alert(res.msg);
                }
            }
        })
    }
</script>
</html>
