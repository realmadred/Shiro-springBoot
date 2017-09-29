const contentPath = "http://127.0.0.1:8080/";
const htmlPath = contentPath + "html/";
/**页面*/
const FORBIDDEN_PATH = htmlPath + "403.html";
const LOGIN_PATH = htmlPath + "login.html";


const USER_INFO = "userInfo";
const userPerms = "userPerms";
const DEFAULT_POST = "post";
const DEFAULT_GET = "get";
const DEFAULT_JSON = "json";

/**ajax post请求封装*/
function ajaxPost(url, data, success, dataType, error) {
    if (!error) {
        error = myError;
    }

    $.ajax({
        url: url,
        method: DEFAULT_POST,
        contentType: "application/json",
        data: JSON.stringify(data),
        dataType: dataType || DEFAULT_JSON,
        success: success,
        error: error
    });
}

function ajaxPostOneParam(url, data, success, dataType, error) {
    if (!error) {
        error = myError;
    }
    $.ajax({
        url: url,
        method: DEFAULT_POST,
        data: data,
        dataType: dataType || DEFAULT_JSON,
        success: success,
        error: error
    });
}

/**ajax get请求封装*/
function ajaxGet(url, success, dataType, error) {
    if (!error) {
        error = myError;
    }
    $.ajax({
        url: url,
        method: DEFAULT_GET,
        dataType: dataType || DEFAULT_JSON,
        success: success,
        error: error
    });
}

const myError = function () {
    alert("error!");
};

const failHandler = function (data) {
    console.log(data);
    if (data.status === 403) {
        $("#main").load(FORBIDDEN_PATH);
    } else if (data.status === 401) {
        location.href = LOGIN_PATH;
    } else {
        alert("操作失败！");
    }
};

/**保存session*/
const sessionStorageSave = function(key,value) {
    sessionStorage.setItem(key, JSON.stringify(value));
};

/**加载session*/
const sessionStorageLoad = function (key) {
    const item = sessionStorage.getItem(key);
    if (item) {
        return JSON.parse(item)
    } else {
        return false
    }
}