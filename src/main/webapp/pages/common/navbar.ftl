<div id="main-navbar" class="navbar navbar-default navbar-static-top bs-docs-nav" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse"
                    data-target="#menuDiv">
                <span class="sr-only">切换导航</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="javascript:void(0)">
                <img class="img-rounded img-responsive user-portrait" src=""
                     onerror="this.src='${base}/static/images/empty.png';">
            </a>
        </div>
        <div id="menuDiv" class="collapse navbar-collapse">
            <ul class="nav navbar-nav menubox">
                <li data-func='nav'><a href="javascript:void(0);" data-target="#contentDiv"
                                       data-href="${base}/home">首页</a></li>

            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="javascript:void(0);" class="dropdown-toggle no-padding" data-toggle="dropdown">
                        帐号
                        <b class="caret hidden-xs"></b>
                    </a>
                    <ul class="dropdown-menu" role="menu">
                        <li>
                            <a data-toggle="modal" data-href="" data-target="#profile-seting" href="javascript:void(0);">
                                <span class="glyphicon glyphicon-info-sign"></span> 我的账户
                            </a>
                        </li>
                        <li>
                            <a data-toggle="modal" data-href="" data-target="#profile-seting" href="javascript:void(0);">
                                <span class="glyphicon glyphicon-edit"></span> 修改信息
                            </a>
                        </li>
                        <li>
                            <a data-toggle="modal" data-href="" data-target="#profile-seting" href="javascript:void(0);">
                                <span class="glyphicon glyphicon-lock"></span> 修改密码
                            </a>
                        </li>
                        <li>
                            <a data-toggle="modal" data-href="" data-target="#profile-seting" href="javascript:void(0);">
                                <span class="glyphicon glyphicon-picture"></span> 修改头像
                            </a>
                        </li>
                        <li role="presentation" class="divider"></li>
                        <li>
                            <a href="${base}/logout">
                                <span class="glyphicon glyphicon-off"></span> 退出
                            </a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</div>
<script id="menuTemplate" type="x-jsrender">
    <li class="dropdown" data-active="{{:id}}">
        <a id="{{:id}}" href="javascript:void(0);" class="dropdown-toggle no-padding" data-toggle="dropdown">
            <i class="{{if icon}}{{:icon}}{{else}}glyphicon glyphicon-file{{/if}}"></i>
            {{:name}}<b class="caret  hidden-xs"></b>
        </a>
        {{if children.length > 0 tmpl="#subMenuTemplate" /}}
    </li>
</script>
<script id="subMenuTemplate" type="x-jsrender">
    <ul class="dropdown-menu">
        {{for children}}
        <li class="dropdown-submenu" data-func='nav'>
            <a id="{{:id}}" class="menu-entry" href="javascript:void(0)" data-href="${base}{{:entry}}" data-target="#contentDiv">
                <span class="{{if icon}}{{:icon}}{{else}}glyphicon glyphicon-file{{/if}}"></span>{{:name}}
            </a>
        </li>
        {{/for}}
    </ul>
</script>
<script>
    $(function () {
        $.ajax({
            url: _.contextPath + "/loginUser",
            cache: false,
            async: false,
            type: "GET",
            dataType: "json",
            complete: function (jqXHR) {
                var user = $.parseJSON(jqXHR.responseText);

            }
        })
        $(".user-portrait").attr("src", _.contextPath + "/security/my/get-portrait");
        $.ajax({
            url: _.contextPath + "/currentMenus",
            cache: false,
            async: false,
            type: "GET",
            dataType: "json",
            complete: function (jqXHR) {
                var menus = $.parseJSON(jqXHR.responseText);
                $.each(menus, function (i, menu) {
                    $(".menubox").append($("#menuTemplate").render(menu));
                })
            }
        })

        $("[data-func='nav']").on(
                "click.daily", "a",
                function () {
                    var $src = $(this);
                    _.go($src.attr("data-href"), $src.attr(
                            "data-target"), null, function () {
                        jQuery(window).trigger("daily.statechange");
                    });
                })
    })
</script>
