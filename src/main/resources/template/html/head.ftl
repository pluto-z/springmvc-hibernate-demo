[#ftl]
[#if tag.needHead]
<!DOCTYPE html>
<html>
<head>
	<title>${tag.title!}</title>
	<meta http-equiv="content-type" content="text/html;charset=utf-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=0" />
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>
	<meta http-equiv="content-style-type" content="text/css"/>
	<meta http-equiv="content-script-type" content="text/javascript"/>
	[@js_head/]
	${tag.body}
</head>
	<body>
[/#if]
[#macro js_head]
	[#local min][#if tag.compressed].min[#else][/#if][/#local]
	<script type="text/javascript" src="${base}/static/plugins/jquery/jquery-1.11.1${min}.js"></script>
	<script type="text/javascript" src="${base}/static/script/app.js"></script>
[/#macro]
