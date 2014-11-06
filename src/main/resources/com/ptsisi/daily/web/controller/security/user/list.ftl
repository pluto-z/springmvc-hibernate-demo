[#macro body]
    [@d.grid url="json" filterable=true selectItemName="user.id" showAll=true maintainSelected=true]
        [@d.toolbar]
        bar.addCreate();
        bar.addUpdate();
        bar.addDelete();
        bar.addRetrieve();

        function roleFormatter(value, row) {
        value = "";
        for (var key in row.roles) {
        value += row.roles[key].name + " ";
        }
        return value;
        }
        [/@]
        [@d.row]
            [@d.boxcol/]
            [@d.col field="username" title="用户名"/]
            [@d.col field="email" title="邮箱"/]
            [@d.col field="roles" title="角色" formatter="roleFormatter" sortable=false/]
            [@d.col field="updatedAt" title="更新时间"/]
            [@d.col field="enabled" title="状态"/]
        [/@]
    [/@]
[/#macro]
