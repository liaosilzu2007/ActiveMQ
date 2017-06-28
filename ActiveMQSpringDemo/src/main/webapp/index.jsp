<%--
  Created by IntelliJ IDEA.
  User: liaosi
  Date: 2017/6/17
  Time: 23:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>ActiveMQ测试</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/res_static/jquery-1.11.0.min.js"></script>
    <style type="text/css">
        .h1 {
            margin: 0 auto;
        }

        #producer{
            width: 48%;
            border: 1px solid blue;
            height: 80%;
            align:center;
            margin:0 auto;
        }

        body{
            text-align :center;
        }
        div {
            text-align :center;
        }
        textarea{
            width:80%;
            height:100px;
            border:1px solid gray;
        }
        button{
            background-color: rgb(62, 156, 66);
            border: none;
            font-weight: bold;
            color: white;
            height:30px;
        }
    </style>
    <script type="text/javascript">

        function send(requestMap) {
            if ($("#message").val() == "") {
                $("#message").css("border", "1px solid red");
                return;
            } else {
                $("#message").css("border","1px solid gray");
            }
            $.ajax({
                type:"post",
                url:"${pageContext.request.contextPath}/activemq/" + requestMap,
                dataType:"text",
                data:{"message":$("#message").val()},
                success:function (data) {
                    //alert(data);
                    if (data == "success") {
                        $("#status").html("<font color=green>发送成功</font>");
                        setTimeout(clear, 1000);
                    } else {
                        $("#status").html("<font color=green>" + data + "</font>");
                        setTimeout(clear, 5000);
                    }
                },
                error:function (data) {
                    //alert(data);
                    $("#status").html("<font color=red>ERROR:"+data["status"]+","+data["statusText"]+"</font>");
                    setTimeout(clear,5000);
                }
            });
        }

        function clear() {
            $("#status").html("");
        }

    </script>
</head>
<body>
<h1>ActiveMQ-Spring 整合 Demo</h1>
<div id="producer">
    <h2>Producer</h2>
    <textarea id="message"></textarea>
    <br>
    <button onclick="send('sendToQueue')">发送Queue消息</button>
    <button onclick="send('sendToTopic')">发送Topic消息</button>
    <br>
    <span id="status"></span>
</div>
</body>
</html>
