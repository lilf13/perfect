/*验证码*/
var verificationCode = ''
/*计数器*/
var timer
/*密码登陆方式切换*/
$("#passwordLi").click(function () {
    modeSwitching($("#passwordLi"),$("#verificationCodeLi"),$("#emailVerificationDiv"),$("#passwordDiv"))
    /*清空内容*/
    $('#mailbox').val('')
    $('#verificationCode').val('')
    $('#emailError').html('')
    $('#verificationCodeError').html('')
    //撤回验证码获取操作
    $("#verificationCodeAcquisition").prop('disabled', false)
    $("#verificationCodeAcquisition").val('获取邮箱验证码')
    clearInterval(timer);
    verificationCode = ''
})

/*验证码登陆方式切换*/
$("#verificationCodeLi").click(function () {
    modeSwitching($("#verificationCodeLi"),$("#passwordLi"),$("#passwordDiv"),$("#emailVerificationDiv"))
    /*清空内容*/
    $('#mailboxByPassword').val('')
    $('#password').val('')
    $('#emailErrorByPs').html('')
    $('#passwordError').html('')
})

/*登陆方式切换公用方法*/
function modeSwitching(a,b,c,d) {
    /*改变字体状态*/
    a.css('border-bottom','3px solid #fc5531')
    a.css('font-weight','bold')
    b.css('border-bottom','initial')
    b.css('font-weight','normal')
    /*隐藏验证码登录的div*/
    c.css('display','none')
    /*显示密码登录的div*/
    d.css('display','block')
}


/*验证码获取*/
$("#verificationCodeAcquisition").click(function () {
    /*判断邮箱是否填写*/
    if ($("#mailbox").val() != ''&&$("#mailbox").val()!='请输入邮箱') {
        // 定义一个匹配邮箱格式的正则表达式
        let regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
        if (regex.test($("#mailbox").val())==true) {
            /*执行发送验证码操作*/
            let $button = $(this); // 保存按钮元素的引用
            $button.prop('disabled', true);
            let expirationTime = 180;
            let countdown = 60;
            let originalText = $button.val();
            $button.val(countdown + 's后重新获取');
            timer = setInterval(function() {
                expirationTime--;
                countdown--;
                console.log(expirationTime)
                if (expirationTime<=120 && expirationTime>0) {
                    $button.prop('disabled', false);
                    $button.val(originalText);
                } else if (expirationTime <= 0) {
                    /*验证码过期*/
                    verificationCode = ''
                    /*显示验证码已过期信息*/
                    alert("验证码已过期，请重新获取")
                    clearInterval(timer);
                } else {
                    $button.val(countdown + 's后重新获取');
                }
            }, 1000);
            $.get({
                url:"/forum/validate/verificationCode",
                data:{"email":$("#mailbox").val()},
                dataType:"text",
                success:function (str){
                    alert(str)
                    //将验证码
                    verificationCode = str
                }
            })
        } else {
            //邮箱不符合格式
            $("#emailError").html("邮箱不符合格式")
        }
    } else {
        /*邮箱输入框爆红*/
        $("#mailbox").val('请输入邮箱')
        $("#mailbox").css('color','red')
    }

})
/*邮箱输入框获得焦点*/
$("#mailbox").focus(function () {
    mb($("#mailbox"),$("#emailError"))
})
$("#mailboxByPassword").focus(function () {
    mb($("#mailboxByPassword"),$("#emailErrorByPs"))
})
function mb(a,b) {
    if (a.val() == "请输入邮箱") {
        a.val('')
        a.css('color','')
    }
    b.html("")
}
/*验证码输入框获得焦点*/
$("#verificationCode").focus(function () {
    if ($("#verificationCode").val() == "请输入验证码") {
        $("#verificationCode").val('')
        $("#verificationCode").css('color','')
    }
    $("#verificationCodeError").html("")
})
/*密码输入框获得焦点*/
$("#password").focus(function () {
    if ($("#password").val() == "请输入验证码") {
        $("#password").val('')
        $("#password").css('color','')
    }
})
/*登录或注册操作(验证码登录界面)*/
$("#loginOrRegistrationBut").click(function () {
    //获取邮箱输入框和验证码输入框的信息
    let mailbox = $("#mailbox").val()
    let verificationCodeValue = $("#verificationCode").val()
    /*判断输入是否填写*/
    if (mailbox == '') {
        /*邮箱输入框爆红*/
        $("#mailbox").val('请输入邮箱')
        $("#mailbox").css('color', 'red')
    }
    if (verificationCodeValue == '') {
        /*验证码输入框爆红*/
        $("#verificationCode").val('请输入验证码')
        $("#verificationCode").css('color', 'red')
    }
    /*提交登录或注册请求*/
    if (mailbox != '' && mailbox != '请输入邮箱' && verificationCodeValue != '' && verificationCodeValue != '请输入验证码' && $("#verificationCodeError").html()=='' && $("#emailError").html()=='') {
        //判断验证码是否正确
        if (verificationCodeValue != verificationCode) {
            $("#verificationCodeError").html('验证码错误')
        } else {
            $.post({
                url:"/forum/validate/vcLogin",
                data:{"mailbox":mailbox,"verificationCodeValue":verificationCodeValue},
                dataType:"text",
                success:function (str){
                    // 退出登录界面
                    if ('true' == str) {
                        $("#shroudedInDiv").css('display','none')
                        $("#loginDiv").css('display','none')
                        // 将页面滚动到顶部
                        window.scrollTo(0, 0);
                        //动态刷新页面
                        location.reload();
                    }
                }
            })
        }
    }
})

/*登陆操作(密码登录界面)*/
$("#RegistrationBut").click(function () {
    /*判断输入框是否填写*/
    if ($("#mailboxByPassword").val() == '') {
        /*邮箱输入框爆红*/
        $("#mailboxByPassword").val('请输入邮箱')
        $("#mailboxByPassword").css('color', 'red')
    } else {
        //判断邮箱格式
        let regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
        if (regex.test($("#mailboxByPassword").val()) == false) {
            $("#emailErrorByPs").html('邮箱不符合格式')
        }
    }
    if ($("#mailboxByPassword").val() != '' && $("#password").val() != '' && $("#emailErrorByPs").html() == '') {
        alert(66)
    }
})

