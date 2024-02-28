/*登陆操作*/
$("#headPortraitDivByLongin").click(function () {
    if ($("#headPortraitImg").attr("src") == '../../../../img/localImg/login.png') {
        /*显示笼罩div和登录框*/
        $("#shroudedInDiv").css('display','block')
        $("#loginDiv").css('display','block')
        //滑轮消失
        document.body.style.overflow = "hidden";
    } else {
        //跳转至个人信息页面
        window.open('/forum/view/user', '_blank');
    }
})
/*退出登录小界面*/
$("#crossPlot").click(function () {
    $("#shroudedInDiv").css('display','none')
    $("#loginDiv").css('display','none')
    //滑轮出现
    document.body.style.overflow = "auto";
})

/*未登录时点击车间弹出登录框*/
$("#aLiteraryCreation").click(function () {
    if ($("#headPortraitImg").attr("src") == '../../../../img/localImg/login.png') {
        /*显示笼罩div和登录框*/
        $("#shroudedInDiv").css('display','block')
        $("#loginDiv").css('display','block')
        //滑轮消失
        document.body.style.overflow = "hidden";
    } else {
        window.location.href='/forum/view/workshop?time='+new Date().getTime()
    }
})