function savePwd() {
	var type = $("#type").val();
	if(!type) {
		Dialog.alertWarn("系统繁忙,请联系开发人员");
		return;
	}
	
	var newPwd = $("#newPwd").val();
	var newPwd1 = md5(newPwd);
	console.log(newPwd1);
	$("input[name='newPwd']").val(newPwd1);

	var confirmPwd = $("#confirmPwd").val();
	var confirmPwd1=md5(confirmPwd);
	$("input[name='confirmPwd']").val(confirmPwd1);

	if(!newPwd) {
		Dialog.alertWarn("请输入密码!");
		return;
	}
	if(newPwd.length < 6 ||　newPwd.length > 20) {
		Dialog.alertWarn("密码应该在6~20个字符之间!");
		return;
	}
	if(!confirmPwd) {
		Dialog.alertWarn("请再输入密码!");
		return;
	}
	if(newPwd1 != confirmPwd1) {
		Dialog.alertWarn("两次输入密码不一致!");
		return;
	}
	
	var smsCode = $("#smsCode").val();
	if(!smsCode) {
		Dialog.alertWarn("请输入验证码!");
		return;
	}
	console.log("手机号"+$("#telPhone").val());
	var params = $("#pwdForm").serializeJson();
	if(params.type == 1) {
		$.post("/web/safemanage/updateloginpwd",params,function(data){
			if(data.code == 200) {
				Dialog.toLogin("操作成功,请重新登录");
				Dialog.clearData("pwdForm");
			} else {
				Dialog.alertWarn(data.message);
				$("input[name='newPwd']").val("");
				$("input[name='confirmPwd']").val("");
				$("input[name='smsCode']").val("");
			}
			
		},'json');
	} else if(params.type == 2) {
		$.post("/web/safemanage/updatepaypwd",params,function(data){
			if(data.code == 200) {
				Dialog.alertInfo("修改支付密码成功");
				Dialog.clearData("pwdForm");
			} else {
				Dialog.alertWarn(data.message);
			}
			
		},'json');
	} else {
		Dialog.alertError("系统内部错误，请联系开发人员!");
		return;
	}
//	
//	alert(JSON.stringify(params));
}

function setLoginType() {
	$("#type").val(1);
}

function setPayType() {
	$("#type").val(2);
}

//获取验证码
function getSmsCode() {
	var newPwd = $("#newPwd").val();
	var confirmPwd = $("#confirmPwd").val();
	if(!newPwd) {
		Dialog.alertWarn("请输入密码!");
		return;
	}
	if(newPwd.length < 6 ||　newPwd.length > 20) {
		Dialog.alertWarn("密码应该在6~20个字符之间!");
		return;
	}
	if(!confirmPwd) {
		Dialog.alertWarn("请再输入密码!");
		return;
	}
	if(newPwd != confirmPwd) {
		Dialog.alertWarn("两次输入密码不一致!");
		return;
	}
	console.log($("#telphone").val());
	ReadTime.readTime("smsId");
	var codeType = "TY20170101";
	var data = {"phone":$("#telphone").val(),"codeType":codeType};
	$.post("/web/sms/findPassword", data, function(data) {
			Dialog.alertWarn(data.message);
	}, 'json');
}