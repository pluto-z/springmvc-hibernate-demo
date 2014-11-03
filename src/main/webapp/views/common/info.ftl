[#ftl]
<nav id="navbar-top" class="main-navbar navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">

            <div class="pull-left">
                <a href="javascript:;" class="navbar-xs dropdown-toggle visible-xs-block" data-toggle="dropdown">
                    <span class="glyphicon glyphicon-th"></span>
                </a>
                <ul class="dropdown-menu" role="menu">
                    [#list menus as m]
                        <li class="dropdown-submenu">
                            <a id="${m.id}" href="javascript:;" class="dropdown-toggle" data-toggle="dropdown">
                                <span class="${m.icon!'glyphicon glyphicon-file'}"></span>
                                <span class="nav-title">${m.name}</span>
                            </a>
                            [@recursionMenu m/]
                        </li>
                    [/#list]
                </ul>
            </div>

            <div class="pull-right">
                <a href="javascript:;" class="dropdown-toggle visible-xs-block" data-toggle="dropdown">
                    <img class="img-circle user-portrait user-portrait-xs" src="${base}/account/user/get-portrait?name=middle.jpeg" onerror="this.src='${base}/resource/image/empty.png';">
                </a>

                <ul class="dropdown-menu" role="menu">
                    [@shiro.authenticated]
                    <li>
                        <a data-toggle="modal" data-target="#profile-seeting" href="javascript:;">
                            <span class="glyphicon glyphicon-cog"></span> 修改个人信息
                        </a>
                    </li>
                    [/@shiro.authenticated]
                    [@shiro.notAuthenticated]
                    <li>
                        <a href="${base}/login">
                            <span class="glyphicon glyphicon-cog"></span> 修改个人信息
                        </a>
                    </li>
                    [/@shiro.notAuthenticated]
                    <li role="presentation" class="divider"></li>
                    <li>
                        <a href="${base}/logout">
                            <span class="glyphicon glyphicon-off"></span> 退出
                        </a>
                    </li>
                </ul>

            </div>

        </div>
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li class="text"><i class="glyphicon glyphicon-time"></i></li>
                <li class="text info-date"><b><span id="date"></span></b></li>
            </ul>
            <ul class="nav navbar-nav navbar-right pull-right">
                <li class="dropdown">
                    <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown">
                        <img class="img-circle user-portrait" src="${base}/account/user/get-portrait?name=middle.jpeg" onerror="this.src='${base}/resource/image/empty.png';">
                        <span class="hidden-xs" id="info-nickname">
                            嗨, ${user.fullName !user.username}
                        </span>
                        <b class="caret hidden-xs"></b>
                    </a>
                    <ul class="dropdown-menu" role="menu">
                        [@shiro.authenticated]
                        <li>
                            <a data-toggle="modal" data-target="#profile-seeting" href="javascript:;">
                                <span class="glyphicon glyphicon-cog"></span> 修改个人信息
                            </a>
                        </li>
                        [/@shiro.authenticated]
                        [@shiro.notAuthenticated]
                        <li>
                            <a href="${base}/login">
                                <span class="glyphicon glyphicon-cog"></span> 修改个人信息
                            </a>
                        </li>
                        [/@shiro.notAuthenticated]
                        <li role="presentation" class="divider"></li>
                        <li>
                            <a href="${base}/logout">
                                <span class="glyphicon glyphicon-off"></span> 退出
                            </a>
                        </li>
                    </ul>
                </li>
            </ul>


            <ul class="nav navbar-nav navbar-right pull-right">
            [#list menus as m]
                <li class="dropdown">
                    <a id="${m.id}" href="javascript:;" class="dropdown-toggle no-padding text" data-toggle="dropdown">
                        <span class="${m.icon!'glyphicon glyphicon-file'}"></span>
                    </a>
                        [@recursionMenu m/]
                </li>
            [/#list]
            </ul>
        </div>
    </div>
</nav>
