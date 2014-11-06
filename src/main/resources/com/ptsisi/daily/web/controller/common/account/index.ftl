[#ftl]
[@d.head title="daily"]
<style>
html {
    background: url("static/images/background.jpg") no-repeat center center fixed;
    -webkit-background-size: cover;
    -moz-background-size: cover;
    -o-background-size: cover;
    background-size: cover;
}

body {
    padding-top: 20px;
    font-size: 16px;
    font-family: "Open Sans", serif;
    background: transparent;
}

.panel {
    background-color: rgba(255, 255, 255, 0.7);
}

h1 {
    font-family: "Abel", Arial, sans-serif;
    font-weight: 400;
    font-size: 40px;
}

.margin-base-vertical {
    margin: 40px 0;
}

.loginBox {
    border: 1px solid #fff;
    box-shadow: 0 0 15px #222;
}

.login-container {
    margin: 0 auto;
    padding: 7% 0;
    max-width: 400px;
}

.data-content {
    margin: 0px 15px;
}
</style>
[/@]
<div class="data-content">
	<div class="login-container tab-content">
    	<div id="loginbox" class="panel panel-default loginBox tab-pane fade ${registerFail???string('','in active')}">
        	<h1 class="margin-base-vertical text-center">用户登陆</h1>
        	<div class="panel-body">
        		[#if shiroLoginFailure?has_content]
        		<div class="alert alert-danger fade in">
            	<span class="glyphicon glyphicon-warning-sign"></span> ${shiroLoginFailure}
        		</div>
            [/#if]
            [#if registerSuccess?has_content]
            <div class="alert alert-success fade in">
                <span class="glyphicon glyphicon-ok-sign"></span> ${registerSuccess}
            </div>
            [/#if]
				[@d.form name="loginForm" action="login" cssClass="margin-base-vertical"]
				 	<div class="form-group">
            		<div class="input-group">
                		<span class="input-group-addon">
                    		<span class="glyphicon glyphicon-user"></span>
                		</span>
            			[@d.textfield title="用户名" placeholder="用户名" cssClass="form-control input-lg" name="username" required="true" maxlength="32" value=username! /]
                	</div>
            	</div>
            	<div class="form-group">
               	<div class="input-group">
                    	<span class="input-group-addon">
                    		<span class="glyphicon glyphicon-lock"></span>
                    	</span>
                    	[@d.password title="密码" name="password" cssClass="form-control input-lg" required="true" placeholder="密码" /]
                	</div>
            	</div>
            	[#if needCaptcha??]
            	<div class="form-group">
                	<div class="input-group">
                		<span class="input-group-addon">
								<span class="glyphicon glyphicon-align-justify"></span>
							</span>
							[@d.textfield cssClass="captcha" required="true" name="captcha" title="验证码" placeholder="验证码" cssClass="form-control input-lg"/]
                    	<div class="input-group-addon" style="padding:0">
                    		<img src="get-captcha" alt="验证码"/>
            			</div>
                	</div>
            	</div>
            	[/#if]
            	<div class="form-group text-right">
        				[@d.checkbox name="rememberMe" value="1" title="下次自动登录"/]
            	</div>
            	<div class="form-group btn-group-lg text-center">
            		[@d.submit cssClass="btn-success" value="登陆"/]
            		[@d.button cssClass="btn-info" container="#loginbox" container_target="#registerbox" value="注册"/]
            	</div>
				[/@]
        	</div>
     	</div>
		<div id="registerbox" class="panel panel-default loginBox tab-pane fade ${registerFail???string('in active','')}">
			<h1 class="margin-base-vertical text-center">注册新用户</h1>
    		<div class="panel-body">
    			[#if registerFail?has_content]
    			<div class="alert alert-danger fade in">
        			<span class="glyphicon glyphicon-warning-sign"></span> ${registerFail}
    			</div>
    			[/#if]
				[@d.form name="registerForm" action="register" cssClass="margin-base-vertical"]
        		<div class="form-group">
    				<div class="input-group">
        				<span class="input-group-addon">
                    	<span class="glyphicon glyphicon-user"></span>
                	</span>
                	[@d.textfield required="true" maxlength="32" title="用户名" placeholder="用户名" value=username! cssClass="form-control input-lg" name="username"/]
            	</div>
        		</div>
        		<div class="form-group">
        			<div class="input-group">
            		<span class="input-group-addon">
                    	<span class="glyphicon glyphicon-lock"></span>
                	</span>
            		[@d.password title="密码" id="register_password" name="password" cssClass="form-control input-lg" required="true" placeholder="密码" /]
            	</div>
        		</div>
        		<div class="form-group">
        			<div class="input-group">
                	<span class="input-group-addon">
                		<span class="glyphicon glyphicon-repeat"></span>
                	</span>
                	[@d.password title="重复密码" id="register_password2" name="password2" cssClass="form-control input-lg" required="true" placeholder="重复密码" /]
                	[@d.validity]
                		$("#register_password2").assert(function () {
                			return  $("#register_password").val() == $("#register_password2").val();
            				}, "两次输入不一致");
                		[/@]
        			</div>
        		</div>
        		<div class="form-group">
            	<div class="input-group">
            		<span class="input-group-addon">
                    	<span class="glyphicon glyphicon-pencil"></span>
            		</span>
            		[@d.textfield required="true" maxlength="50" title="姓名" placeholder="姓名" value=fullName! cssClass="form-control input-lg" name="fullName"/]
            	</div>
        		</div>
        		<div class="form-group">
        			<div class="input-group">
        				<span class="input-group-addon">
                    	<span class="glyphicon glyphicon-envelope"></span>
                	</span>
                	[@d.email required="true" maxlength="50" title="邮箱" name="email" placeholder="邮箱" value=email! cssClass="form-control input-lg"/]
        			</div>
        		</div>
        		<div class="form-group">
        			<div class="input-group">
        				<span class="input-group-addon">
							<span class="glyphicon glyphicon-align-justify"></span>
						</span>
						[@d.textfield cssClass="captcha" required="true" name="captcha" title="验证码" placeholder="验证码" cssClass="form-control input-lg"/]
                	<div class="input-group-addon" style="padding:0">
                		<img src="get-captcha" alt="验证码"/>
                	</div>
            	</div>
        		</div>
	        	<div class="form-group btn-group-lg text-center">
	        		[@d.submit cssClass="btn-success" value="提交"/]
	        		[@d.button cssClass="btn-info" container="#registerbox" container_target="#loginbox" value="返回"/]
	        	</div>
    			[/@]
    		</div>
		</div>
	</div>
</div>
<script>
$(function () {
	$(".captcha").click(function () {
    	$(this).attr("src", "get-captcha?date = " + new Date() + Math.floor(Math.random() * 24));
	});
	$(".btn-info").click(function () {
		var $self = $($(this).attr("container"));
		var $target = $($(this).attr("container_target"));
    	$.validity.clear();
    	setTimeout(function () {
        	$self.removeClass("active");
        	$target.addClass('in').addClass("active");
        }, 150);
    	$self.removeClass('in');
    });
})
</script>
[@d.foot/]
