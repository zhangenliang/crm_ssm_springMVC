<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		$(function () {
		    //当前页面为顶层页面
            if (window.top!=window){
                window.top.location=window.location;
            }
		    //页面加载完毕之后，将用户文本框内的值清空
			$("#loginAct").val("");
		    //在页面加载完毕之后，让用户的文本框自动获得焦点
			$("#loginAct").focus();
			//为登陆按钮绑定事件执行登陆操作
			$("#submitBtn").click(function () {
			    login();
            })

			//为当前登陆页窗口绑定敲键盘事件
			//event 这个参数获取的是敲击的是那个键的值
			$(window).keydown(function (event) {
			    //回车键  键位码值是13
                if(event.keyCode==13){
				    login();
                }
            })

        })
		//登陆验证的方法.注意：写在$(function)外面
		function login() {
		    //验证账号密码不能为空
			//取得账号密码
			//将文本中得左右空格去掉，以前用 .trim() 方法。公司里面常用$.trim(文本)
			var loginAct=$.trim($("#loginAct").val());
			var loginPwd=$.trim($("#loginPwd").val());
			var user={"loginAct":loginAct,"loginPwd":loginPwd}
            if (loginPwd=="" || loginAct==""){
			    $("#msg").html("账号或者密码不能为空");
				return false;
            }
            //验证登陆
            $.ajax({
                url:"settings/user/login.do",
                data:user,
				type:"post",
                dataType:"json",
                success:function (data) {
                    //后台需要给前端一共一个 true或者false，还有msg
                    //如果登陆成功
					//这里注意：是 data.success 而不是 data.success()
                    if (data.success){
                        //跳转到工作台的欢迎页
                        window.location.href="workbench/index.jsp";
                    } else{
                        //让后台提供，如果登陆失败了，是那里错了
                        $("#msg").html(data.msg);
                    }
                }
            })
        }
	</script>
</head>
<body>
	<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
		<img src="image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 50px;">
	</div>
	<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
		<div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">CRM &nbsp;<span style="font-size: 12px;">&copy;2017&nbsp;动力节点</span></div>
	</div>
	
	<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
		<div style="position: absolute; top: 0px; right: 60px;">
			<div class="page-header">
				<h1>登录</h1>
			</div>
			<form action="workbench/index.jsp" class="form-horizontal" role="form">
				<div class="form-group form-group-lg">
					<div style="width: 350px;">
						<input class="form-control" type="text" placeholder="用户名" id="loginAct">
					</div>
					<div style="width: 350px; position: relative;top: 20px;">
						<input class="form-control" type="password" placeholder="密码" id="loginPwd">
					</div>
					<div class="checkbox"  style="position: relative;top: 30px; left: 10px;">
						
							<span id="msg" style="color: red"></span>
						
					</div>
					<!--注意：按钮写在form表单中，默认的行为就是提交表单
					    一定要将按钮的类型设置为button
					    按钮所触发的行为应该是由我们自己手动写js代码来决定
					-->
					<button type="button" id="submitBtn" class="btn btn-primary btn-lg btn-block"  style="width: 350px; position: relative;top: 45px;">登录</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>