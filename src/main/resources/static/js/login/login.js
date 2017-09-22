$(function () {

    $(".signin").click(function(){
        this.className="signin focus";
        $(".signup")[0].className="signup";
        $(".input_signin")[0].className="input_signin active";
        $(".input_signup")[0].className="input_signup";
    });
    $(".signup").click(function(){
        this.className="signup focus";
        $(".signin")[0].className="signin";
        $(".input_signup")[0].className="input_signup active";
        $(".input_signin")[0].className="input_signin";
    });

    // 绑定单击事件
    $("#login").click(function () {
        var username = $("#login_user_name").val();
        var password = $("#login_password").val();
        $.ajax({
            url:"/login",
            type:"post",
            data:{username:username,
                password:password},
            dataType: "json",
            success: function (data) {
                if (data.code == 1){
                    location.href=htmlPath+"form.html";
                }else {
                    alert(data.msg);
                }
            },
            error: function () {
                alert("error");
            }
        })
    })
});