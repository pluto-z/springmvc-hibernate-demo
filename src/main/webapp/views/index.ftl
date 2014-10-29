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
    <div class="login-container">
        <div class="panel panel-default loginBox">
            <h1 class="margin-base-vertical text-center">用户登陆</h1>

            <form name="loginForm" method="post" action="login" class="margin-base-vertical">
                <div class="panel-body">
                [#if shiroLoginFailure?has_content]
                    <div class="alert alert-danger fade in">
                        <span class="glyphicon glyphicon-warning-sign"></span> ${shiroLoginFailure}
                    </div>
                [/#if]
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-user"></span>
                            </span>
                            <input type="text" title="用户名" value="${username!?js_string}" name="username"
                                   placeholder="请输入邮箱"
                                   class="form-control input-lg"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-lock"></span>
                            </span>
                            <input type="password" title="密码" name="password" class="form-control input-lg"/>
                        </div>
                    </div>
                [#if needCaptcha??]
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon">
								<span class="glyphicon glyphicon-align-justify"></span>
							</span>
                            <input type="text" title="验证码" name="captcha" class="form-control input-lg"/>

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
                        <button type="button" class="btn btn-danger">注册</button>
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
            $("input[name='username']").require().match('email').maxLength(32);
            $("input[name='password']").require().maxLength(16).minLength(6);
        [#if needCaptcha??]
            $("input[name='captcha']").require();
        [/#if]
        });
        $("#captchaImg").click(function () {
            $(this).attr("src", "get-captcha?date = " + new Date() + Math.floor(Math.random() * 24));
        });
    })
</script>
</html>
