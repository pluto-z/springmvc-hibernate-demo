[#ftl]
<nav id="main-navbar" class="navbar navbar-default navbar-static-top hidden-xs" role="navigation">
    <div class="container">
        <div class="collapse navbar-collapse">
        		<ul class="nav navbar-nav">
                <li class="text"><i class="glyphicon glyphicon-time"></i></li>
                <li class="text info-date"><b><span id="date"></span></b></li>
            </ul>
            <ul class="nav navbar-nav">
                [#list menus as m]
                <li class="dropdown">
                    <a id="${m.id}" href="javascript:;" class="dropdown-toggle" data-toggle="dropdown">
                        <span class="${m.icon!'glyphicon glyphicon-file'}"></span>
                        <span class="nav-title">${m.name}</span>
                    </a>
                    [@recursionMenu m/]
                </li>
                [/#list]
            </ul>
        </div>
    </div>
</nav>
