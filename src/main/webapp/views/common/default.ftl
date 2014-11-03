[#ftl]
[#assign shiro=JspTaglibs["/WEB-INF/taglib/shiro.tld"] /]
[#assign base= rc.contextPath /]
<!DOCTYPE html>
<html>
<head>
    <title>[@title/]</title>
    <meta http-equiv="content-type" content="text/html;charset=utf-8"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0,user-scalable=0"/>
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="expires" content="0"/>
    <meta http-equiv="content-style-type" content="text/css"/>
    <meta http-equiv="content-script-type" content="text/javascript"/>
    <link rel="stylesheet" type="text/css" href="${base}/static/plugins/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${base}/static/plugins/bootstrap/css/bootstrap-theme.css">
[#if head??]
    [@head/]
[/#if]
</head>
<body>
[@shiro.user]
    [#include "info.ftl" /]
    [#include "profile-setting.ftl" /]
[/@]
<div class="main-container container">
    <div class="panel panel-default border-black">
        <div class="panel-heading large heading-background"></div>
        <div class="content-container">
            <div class="content-widget">
                <div class="content-widget">
                    <div class="content-widget" id="content-widget">
                    [@shiro.user]
                        [#include "navbar.ftl" /]
                    [/@]
                    [@body/]
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${base}/static/plugins/jquery/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${base}/static/plugins/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${base}/static/plugins/jquery-form/jquery.form.min.js"></script>
[#if foot??]
    [@foot/]
[/#if]
</body>
</html>

[#macro recursionMenu menu]
    [#if menu.children?size gt 0]
    <ul class="dropdown-menu">
        [#list menu.children as c]
            <li class="dropdown-submenu">
                <a id="${c.id}" href="${base}/${c.entry?substring(1,c.entry?length - 3)}">
                    <span class="${c.icon!'glyphicon glyphicon-file'}"> </span> ${c.name}
                </a>
                [@recursionMenu c /]
            </li>
        [/#list]
    </ul>
    [/#if]
[/#macro]

[#-- 分页宏pagination 将formId和page传入后自动生成分页条 --]
[#macro pagination formId page]
    <div class="paging-bar">
        <script type="text/javascript">
            //跳转到指定的页面
            function jumpToSpecificPage(pageNumber, form) {
                $("#pageNumber").val(pageNumber);
                $("#" + form).submit();
            }
        </script>
        <ul class="pagination pull-right">
            [#if !page.isFirst()]
                <li>
                    <a href="javascript:;" onclick="jumpToSpecificPage(0,'${formId!''}');">首页</a>
                </li>
            [/#if]
            [#if page.hasPrevious()]
                <li>
                    <a href="javascript:;" onclick="jumpToSpecificPage(${page.number - 1},'${formId!''}');">上一页</a>
                </li>
            [/#if]
            [#if page.hasNext()]
                <li>
                    <a href="javascript:;" onclick="jumpToSpecificPage(${page.number + 1},'${formId!''}');">下一页</a>
                </li>
            [/#if]
            [#if !page.isLast()]
                <li>
                    <a href="javascript:;"
                       onclick="jumpToSpecificPage(${page.totalPages - 1},'${formId!''}');">尾页</a>
                </li>
            [/#if]
            <li>
        <span>
            共 ${page.totalElements} 条 ${page.number + 1} / ${page.totalPages} 页
        </span>
            </li>
        </ul>
    </div>
[/#macro]
