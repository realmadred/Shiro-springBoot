$(function () {
    $("#backHome").click(function () {
        if (history.length) {
            history.back();
        } else {
            location.href = "/html/login.html";
        }
    });
    $("#toLogin").click(function () {
        location.href = "/html/login.html";
    });
})