<%@ page language="java" session="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>ERROR:404</title>
    <meta http-equiv="Expires" content="0">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-store">
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/static/plugins/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/static/plugins/bootstrap/css/bootstrap-theme.min.css">
</head>
<body>
<div class="main-container container" style="padding-top:150px">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title"><span class="glyphicon glyphicon-remove-circle"></span>404</h3>
        </div>
        <div class="panel-body">
            <div class="server-exception">
                <section class="text-center">
                    <h1><span class="glyphicon glyphicon-warning-sign"/></h1>
                    <h3>找不到页面</h3>
                </section>
                <hr/>
                <section>你访问的页面不存在或已经被删除.</section>
                <section>
                    <a class="btn btn-default" href="javascript:void(0)" onclick="history.back()">
                        <span class="glyphicon glyphicon-backward"/> 返回
                    </a>
                </section>
            </div>
        </div>
    </div>
</div>
</body>
</html>
