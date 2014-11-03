[#ftl]
<!DOCTYPE html>
<html>
<head>
    <title>Daily</title>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0,user-scalable=0"/>
    <link rel="stylesheet" type="text/css" href="static/plugins/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="static/plugins/bootstrap/css/bootstrap-theme.min.css"/>
    <link rel="stylesheet" type="text/css" href="static/plugins/validity/css/jquery.validity.css"/>
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
</head>
<body>
<div class="data-content">
    <div class="login-container tab-content">
        <div id="loginbox" class="panel panel-default loginBox tab-pane fade ${registerFail???string('','in active')}">
            <h1 class="margin-base-vertical text-center">用户登陆</h1>

            <form name="loginForm" method="post" action="login" class="margin-base-vertical">
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
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-user"></span>
                            </span>
                            <input id="login_username" type="text" title="用户名" value="${username!?js_string}"
                                   name="username"
                                   placeholder="请输入用户名"
                                   class="form-control input-lg"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-lock"></span>
                            </span>
                            <input id="login_password" type="password" title="密码" name="password"
                                   placeholder="请输入密码"
                                   class="form-control input-lg"/>
                        </div>
                    </div>
                [#if needCaptcha??]
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon">
								<span class="glyphicon glyphicon-align-justify"></span>
							</span>
                            <input id="login_captcha" type="text" title="验证码" name="captcha"
                                   placeholder="请输入验证码"
                                   class="form-control input-lg"/>

                            <div class="input-group-addon" style="padding:0">
                                <img src="get-captcha" alt="验证码" id="captchaImg"/>
                            </div>
                        </div>
                    </div>
                [/#if]
                    <div class="form-group text-right">
                        <input type="checkbox" name="rememberMe"/>下次自动登录
                    </div>
                    <div class="form-group btn-group-lg text-center">
                        <button type="submit" class="btn btn-success">登录</button>
                        <button type="button" class="btn btn-info" id="registerBtn">注册</button>
                    </div>
                </div>
            </form>
        </div>
        <div id="registerbox" class="panel panel-default loginBox tab-pane fade ${registerFail???string('in active','')}">
            <h1 class="margin-base-vertical text-center">注册新用户</h1>

            <form name="registerForm" method="post" action="register" class="margin-base-vertical">
                <div class="panel-body">
                [#if registerFail?has_content]
                    <div class="alert alert-danger fade in">
                        <span class="glyphicon glyphicon-warning-sign"></span> ${registerFail}
                    </div>
                [/#if]
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-user"></span>
                            </span>
                            <input id="register_username" type="text" title="用户名" value="${username!?js_string}"
                                   name="username"
                                   placeholder="请输入用户名"
                                   class="form-control input-lg"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-lock"></span>
                            </span>
                            <input id="register_password" type="password" title="密码" name="password"
                                   placeholder="请输入密码"
                                   class="form-control input-lg"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-repeat"></span>
                            </span>
                            <input id="register_password2" type="password" title="确认密码" name="password2"
                                   placeholder="请确认密码"
                                   class="form-control input-lg"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-pencil"></span>
                            </span>
                            <input id="register_fullname" type="text" title="姓名" value="${fullName!?js_string}"
                                   name="fullName"
                                   placeholder="请输入姓名"
                                   class="form-control input-lg"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-envelope"></span>
                            </span>
                            <input id="register_email" type="text" title="邮箱" name="email"
                                   placeholder="请输入邮箱"
                                   class="form-control input-lg"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon">
								<span class="glyphicon glyphicon-align-justify"></span>
							</span>
                            <input id="register_captcha" type="text" title="验证码" name="captcha"
                                   placeholder="请输入验证码"
                                   class="form-control input-lg"/>

                            <div class="input-group-addon" style="padding:0">
                                <img src="get-captcha" alt="验证码" id="captchaImg_r"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group btn-group-lg text-center">
                        <button type="submit" class="btn btn-success">提交</button>
                        <button type="button" class="btn btn-info" id="loginBtn">返回</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="static/plugins/jquery/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="static/plugins/validity/js/jquery.validity.js"></script>
<script type="text/javascript" src="static/plugins/validity/lang/jquery.validity.lang.zh.js"></script>
<script>
    $(function () {
        $("form[name='loginForm']").validity(function () {
            $("#login_username").require().maxLength(32);
            $("#login_password").require().maxLength(10).minLength(6);
        [#if needCaptcha??]
            $("#login_captcha").require();
        [/#if]
        });
        $("form[name='registerForm']").validity(function () {
            $("#register_fullname").require().maxLength(50);
            $("#register_username").require().maxLength(32);
            $("#register_email").require().maxLength(50).match('email');
            $("#register_password").require().maxLength(10).minLength(6);
            $("#register_password2").require().maxLength(10).minLength(6).assert(function () {
                return  $("#register_password").val() == $("#register_password2").val();
            }, "两次输入不一致");
            $("#register_captcha").require();
        });
        $("#captchaImg").click(function () {
            $(this).attr("src", "get-captcha?date = " + new Date() + Math.floor(Math.random() * 24));
        });
        $("#captchaImg_r").click(function () {
            $(this).attr("src", "get-captcha?date = " + new Date() + Math.floor(Math.random() * 24));
        });
        $("#registerBtn").click(function () {
            var loginbox = $("#loginbox");
            var registerbox = $("#registerbox");
            setTimeout(function () {
                loginbox.removeClass("active");
                registerbox.addClass('in').addClass("active");
            }, 150);
            loginbox.removeClass('in');
        });
        $("#loginBtn").click(function () {
            var loginbox = $("#loginbox");
            var registerbox = $("#registerbox");
            setTimeout(function () {
                registerbox.removeClass("active");
                loginbox.addClass('in').addClass("active");
            }, 150);
            registerbox.removeClass('in');
        });
    })
</script>
</html>
