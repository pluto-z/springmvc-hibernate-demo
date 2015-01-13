[#macro body]
    [@d.form enctype="multipart/form-data" name="userEditForm" action="/security/user/update" target="contentDiv" class="form-horizontal" theme="form"  validateOutputMode="label"]
        [@d.formhead]
        <h4>用户[#if (user.id)??]编辑[#else]新建[/#if]</h4>
        [/@]
        [@d.formbody cssClass="col-md-12"]
            [@d.textfield name="username" label="用户名" required="true" value=(user.username)!/]
            [@d.password name="password" label="密码" required="true"/]
            [@d.password name="password2" label="密码确认" required="true"/]
            [@d.validity]
            $("input[name='password2']").assert(function () {
            return $("input[name='password']").val() == $("input[name='password2']").val();
            }, "两次输入不一致");
            [/@]
            [@d.textfield name="fullName" label="姓名" required="true" value=(user.fullName)!/]
            [@d.email name="email" label="电子邮箱" required="true" value=(user.email)!/]
            [@d.radios name="enabled" label="是否启用" value=(user.enabled)! /]
            [@d.checkboxes name="role" items=roles label="角色" required="true" value=(user.roles)!/]

        [/@]
        [@d.formfoot]
            [#if (user.id)??]
            <input type="hidden" name="id" value="${(user.id)!}"/>
            [/#if]
            [@d.button id="closeBtn" cssClass="btn-default" value="关闭"/]
            [@d.submit cssClass="btn-primary" value="提交"/]
        [/@]
    [/@]
<script>
    $(function () {
        $("#closeBtn").click(function () {
            _.go("${base}/security/user/list", "#contentDiv");
        })
    })
</script>
[/#macro]
