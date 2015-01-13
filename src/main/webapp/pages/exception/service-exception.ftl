[#macro body]
    [#if ex_head?? && ex_head]
    <!DOCTYPE html>
    <html>
    <head>
        <title>访问控制</title>
        <meta http-equiv="content-type" content="text/html;charset=utf-8"/>
        <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=0"/>
        <meta http-equiv="pragma" content="no-cache"/>
        <meta http-equiv="cache-control" content="no-cache"/>
        <meta http-equiv="expires" content="0"/>
        <meta http-equiv="content-style-type" content="text/css"/>
        <meta http-equiv="content-script-type" content="text/javascript"/>
        <link type="text/css" rel="stylesheet" href="${base}/static/plugins/bootstrap/css/bootstrap.min.css"/>
        <link type="text/css" rel="stylesheet" href="${base}/static/css/style.css"/>
    </head>
    <body>
    [/#if]
<div id="exception_container" class="server-exception">

    <h1><span class="glyphicon glyphicon-info-sign"></span></h1>

    <h3>处理失败</h3>
    <hr>
    <p>原因:${exception.message}</p>
    <hr>
    <p>
        <button type="button" class="btn btn-default" onclick="history.back();">
            <span class="glyphicon glyphicon-backward"></span> 返回
        </button>
    </p>
</div>
<script>
    var myResize = function () {
        var winWidth, winHeight;
        if (window.innerWidth)
            winWidth = window.innerWidth;
        else if ((document.body) && (document.body.clientWidth))
            winWidth = document.body.clientWidth;
        if (window.innerHeight)
            winHeight = window.innerHeight;
        else if ((document.body) && (document.body.clientHeight))
            winHeight = document.body.clientHeight;
        if (document.documentElement && document.documentElement.clientHeight && document.documentElement.clientWidth) {
            winHeight = document.documentElement.clientHeight;
            winWidth = document.documentElement.clientWidth;
        }
        var div = document.getElementById("exception_container");
        div.style.left = (winWidth / 2 - div.clientWidth / 2) + "px";
        div.style.top = (winHeight / 2 - div.clientHeight / 2) + "px";
    }
    window.onresize = myResize;
    window.onload = myResize;
    myResize();
</script>
    [#if ex_head?? && ex_head]
    </body>
    </html>
    [/#if]
[/#macro]
