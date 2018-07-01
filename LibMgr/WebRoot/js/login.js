/**
 * login
 */
function login() {
	//alert("login?"+$("#loginform").serialize());
	$.ajax({
		type : "POST",
		url : "login?"+$("#loginform").serialize(),
		data :{},
		cache : false,
		dataType:"json",
		success : function(data) {
			if (data["status"] == "OK")
				window.location.href = "index";
			else {
				alert("登录失败，账号或密码错误！");
			}
		},
		error : function(xhr) {
			alert("error: " + xhr.responseText);
		}
	})
}