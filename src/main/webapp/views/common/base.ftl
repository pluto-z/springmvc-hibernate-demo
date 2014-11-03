[#ftl]
[#assign base= rc.contextPath/]
[#macro head title="Daily"]
[#local request = fetchRequest()/]
[#if !(request.getHeader('x-requested-with')??)]
<!DOCTYPE html>
<html>
<head>
    <title>${title}</title>
    <meta http-equiv="content-type" content="text/html;charset=utf-8" />
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0,user-scalable=0" />
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="expires" content="0"/>
    <meta http-equiv="content-style-type" content="text/css"/>
    <meta http-equiv="content-script-type" content="text/javascript"/>
    [#local min][#if request.getParameter("debug")??][#else].min[/#if][/#local]
    <link rel="stylesheet" type="text/css" href="${base}/static/plugins/bootstrap/css/bootstrap${min}.css">
    <link rel="stylesheet" type="text/css" href="${base}/static/plugins/bootstrap/css/bootstrap-theme${min}.css">
    <link rel="stylesheet" type="text/css" href="${base}/static/css/style.css">
    <script type="text/javascript" src="${base}/static/plugins/jquery/jquery-1.11.1${min}.js"></script>
    <script type="text/javascript" src="${base}/static/plugins/bootstrap/js/bootstrap${min}.js"></script>
</head>
<body>
[/#if]
[#nested /]
[#if !(request.getHeader('x-requested-with')??)]
</body>
</html>
[/#if]
[/#macro]

[#macro script src]
    [#local lsrc = base+src]
    [#if request.getParameter("debug")?? && lsrc?contains('.min')]
        [#local lsrc = lsrc?replace('.min','')/]
    [/#if]
    <script type="text/javascript" src="${lsrc}"></script>
[/#macro]