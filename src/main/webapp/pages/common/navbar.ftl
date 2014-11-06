[#macro recursionMenu menu target]
    [#if menu.children?size > 0]
    <ul class="dropdown-menu">
        [#list menu.children as c]
            <li class="dropdown-submenu" data-func='nav'>
                <a id="${c.id}" class="menu-entry" href="javascript:void(0)" data-href="${base}${c.entry}" data-target="${target}">
                    <span class="${c.icon!'glyphicon glyphicon-file'}"> </span> ${c.name}
                </a>
                [@recursionMenu c target/]
            </li>
        [/#list]
    </ul>
    [/#if]
[/#macro]

<div id="main-navbar" class="navbar navbar-default navbar-fixed-top bs-docs-nav" role="navigation">
	<div class="container">
		<div class="navbar-header">
        	<a class="navbar-brand" href="javascript:void(0)">
        		Daily
			</a>
		</div>
    	<div class="navbar-collapse collapse">
    		<ul class="nav navbar-nav">
	    		<li data-func='nav'><a href="javascript:void(0);" data-target="#contentDiv" data-href="${base}/home">首页</a></li>
				[#list menus as m]
	    			[#if !m.parent??]
	            <li class="dropdown" data-active="${m.id}">
	                <a id="${m.id}" href="javascript:void(0);" class="dropdown-toggle no-padding" data-toggle="dropdown">
	                    <i class="${m.icon!'glyphicon glyphicon-file'}"></i>
	                    ${m.name}<b class="caret  hidden-xs"></b>
	                </a>
	                [@recursionMenu m "#contentDiv"/]
	            </li>
	            [/#if]
	        	[/#list]
        	</ul>
        	<ul class="nav navbar-nav navbar-right">
            <li class="dropdown">
            	<a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown">
            		<img class="img-circle user-portrait" src="${base}/security/user/get-portrait" onerror="this.src='${base}/static/images/empty.jpg';">
                    	<span class="hidden-xs" id="info-nickname">
                            欢迎, ${user.fullName!user.username}
                    	</span>
                		<b class="caret hidden-xs"></b>
                </a>
                <ul class="dropdown-menu" role="menu">
                    <li>
                        <a data-toggle="modal" data-target="#profile-seting" href="javascript:void(0);">
                            <span class="glyphicon glyphicon-cog"></span> 修改个人信息
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
