const globalURL = "http://localhost:6969/";

window.count = 1;

function toastHTML(isError, title, msg) {
    if(isError){
        return `<div id="liveToast${count}" class="toast" role="alert" aria-live="assertive" aria-atomic="false"><div class="toast-header"><svg className="bd-placeholder-img rounded" width="20" height="20" xmlns="http://www.w3.org/2000/svg" aria-hidden="true" preserveAspectRatio="xMidYMid slice" focusable="false"><rect width="100%" height="100%" fill="#FF0000"></rect></svg><strong class="ms-2 me-auto">${title}</strong><button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button></div><div class="toast-body">${msg}</div></div>`
    }
    return `<div id="liveToast${count}" class="toast" role="alert" aria-live="assertive" aria-atomic="false"><div class="toast-header"><svg className="bd-placeholder-img rounded" width="20" height="20" xmlns="http://www.w3.org/2000/svg" aria-hidden="true" preserveAspectRatio="xMidYMid slice" focusable="false"><rect width="100%" height="100%" fill="#007aff"></rect></svg><strong class="ms-2 me-auto">${title}</strong><button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button></div><div class="toast-body">${msg}</div></div>`;
}

//FF0000

//reference - https://www.w3schools.com/js/js_cookies.asp
function getCookie(cname) {
    let name = cname + "=";
    let decodedCookie = decodeURIComponent(document.cookie);
    let ca = decodedCookie.split(';');
    for(let i = 0; i <ca.length; i++) {
        let c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

function setCookie(name, value) {
    const d = new Date();
    d.setTime(d.getTime() + (7*24*60*60*1000));
    let expires = "expires="+ d.toUTCString();
    document.cookie = name + "=" + value + ";" + expires + ";path=/";
}

$(document).ready(function () {
    $('#navDiv').load('./nav.html');

    $(".nav-link").click(function (){
        $(".nav-link").removeClass("active");
        $(this).addClass("active");
        console.log(this)
    });

    $('#pending').on('click', function() {
        showPendingDoctors();
    });

    $(".nav-item").click(function (){
        console.log(this)
    });

    $.ajax({
        url: globalURL + "jwt/validateJWTToken",
        type: "POST",
        dataType: "json",
        data: getCookie("token")
    })
    .done(function(response) {
        let data = response;
        try {
            if(data.isError){
                addToast(true, "Error", data.msg);
                window.location.href="../index.html";
            } else {
                if (data.type == 'admin'){
                    setCookie("email", data.obj.adminEmail);
                    setCookie("type", "admin");
                } else if (data.type == 'patient' || data.type == 'doctor') {
                    window.location.href = '../dashboard.html'
                }
            }
        } catch(err){
            addToast(true, "Error", "Some unknown error occurred. Pls try again later!")
            window.location.href="../index.html";
        }
    })
    .fail(function (jqXHR, textStatus, errorThrown){
        addToast(true, "Error", "Pls log in again!");
        window.location.href="../index.html";
    });
})

function addToast(isError, title, msg){
    $('#toastDiv').append(toastHTML(isError, title, msg));
    $("#liveToast"+count).toast('show');
    count++;
}

function logout() {
    deleteCookies();
    window.location.href = "./index.html";
}

//reference https://stackoverflow.com/questions/179355/clearing-all-cookies-with-javascript
function deleteCookies() {
    const cookies = document.cookie.split(";");

    for (let i = 0; i < cookies.length; i++) {
        const cookie = cookies[i];
        const eqPos = cookie.indexOf("=");
        const name = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;
        document.cookie = name + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT" + ";path=/";
    }
}
