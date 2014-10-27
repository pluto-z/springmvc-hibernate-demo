[#ftl]
<!DOCTYPE html>
<html>
<head>
    <title>Daily</title>
    <meta charset="utf-8" />
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0,user-scalable=0" />
    <link rel="stylesheet" type="text/css" href="static/plugins/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="static/plugins/validity/css/jquery.validity.css"/>
    <style>
        html {
            background: url("static/images/background.jpg") no-repeat center center
            fixed;
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

        .incenter {
            margin-top: 10%;
        }
    </style>
</head>
<body>
<div class="container container-fluid incenter">
    <div class="row">
        <div class="col-md-4 col-md-offset-4 panel panel-default loginBox">
            <h1 class="margin-base-vertical text-center">用户登陆</h1>
            <form name="loginForm" method="post" action="login.action" class="margin-base-vertical">
                <p class="input-group">
						<span class="input-group-addon">
							<span class="glyphicon glyphicon-user"></span>
						</span>
                    <input type="text" title="用户名" value="" name="username" placeholder="请输入邮箱" class="form-control input-lg"/>
                </p>
                <p class="input-group">
						<span class="input-group-addon">
							<span	class="glyphicon glyphicon-lock"></span>
						</span>
                    <input type="password" title="密码" name="password" class="form-control input-lg"/>
                </p>
                [#if needCaptcha??]
                    <p class="input-group">
						<span class="input-group-addon">
							<span class="glyphicon glyphicon-align-justify"></span>
						</span>
                        <input type="input" title="验证码" name="captcha" class="form-control input-lg"/>
                        <img src="captcha.action" alt="显示图片" id="captcha"/>
                    </p>
                [/#if]
                <p class="text-center">
                    <input type="checkbox" name="rememberMe" />下次自动登录
                </p>
                <p class="text-center btn-group-lg">
                    <input type="submit" value="登录 " class="btn btn-success">
                    <input type="button" value="注册 " class="btn btn-success">
                </p>
            </form>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="static/plugins/jquery/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="static/plugins/validity/js/jquery.validity.min.js"></script>
<script type="text/javascript" src="static/plugins/validity/lang/jquery.validity.lang.zh.js"></script>
<script>
    $(function(){
        $("form[name='loginForm']").validity(function(){
            $("input[name='username']").require().match('email').maxLength(32);
            $("input[name='password']").require().maxLength(16).minLength(6);
        });
        $("#captcha").click(function(){
           this.src = this.src+"?d=" + new Date().getTime();
        });
    })
</script>
</html>
