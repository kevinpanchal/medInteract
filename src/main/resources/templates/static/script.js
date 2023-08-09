const globalURL = "http://localhost:6969/";

window.count = 1;

window.stompClient = null;

function toastHTML(isError, title, msg) {
    if(isError){
        return `<div id="liveToast${count}" class="toast" role="alert" aria-live="assertive" aria-atomic="false"><div class="toast-header"><svg className="bd-placeholder-img rounded" width="20" height="20" xmlns="http://www.w3.org/2000/svg" aria-hidden="true" preserveAspectRatio="xMidYMid slice" focusable="false"><rect width="100%" height="100%" fill="#FF0000"></rect></svg><strong class="ms-2 me-auto">${title}</strong><button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button></div><div class="toast-body">${msg}</div></div>`
    }
    return `<div id="liveToast${count}" class="toast" role="alert" aria-live="assertive" aria-atomic="false"><div class="toast-header"><svg className="bd-placeholder-img rounded" width="20" height="20" xmlns="http://www.w3.org/2000/svg" aria-hidden="true" preserveAspectRatio="xMidYMid slice" focusable="false"><rect width="100%" height="100%" fill="#007aff"></rect></svg><strong class="ms-2 me-auto">${title}</strong><button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button></div><div class="toast-body">${msg}</div></div>`;
}

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
    if (getCookie("type") === "patient") {
        $('#navDiv').load('./nav.html');
    } else {
        $('#navDiv').load('./doctor_nav.html');
    }

    $.ajax({
        url: globalURL + "jwt/validateJWTToken",
        type: "POST",
        dataType: "json",
        data: getCookie("token")
    })
    .done(function(response) {
        try {
            let data = response.data;
            if(data.isError){
                addToast(true, "Error", data.msg);
                window.location.href="./index.html";
            } else {
                if (data.type === 'patient'){
                    setCookie("id", data.obj.id);
                    setCookie("name", data.obj.patientName);
                    setCookie("email", data.obj.patientEmail);
                    setCookie("city", data.obj.patientAddressCity);
                    setCookie("province", data.obj.patientAddressProvince);
                    setCookie("profilePicture", data.obj.profilePicture);
                    setCookie("type", "patient");
                } else if (data.type === 'doctor') {
                    setCookie("id", data.obj.id);
                    setCookie("name", data.obj.doctorName);
                    setCookie("email", data.obj.doctorEmail);
                    setCookie("city", data.obj.doctorAddressCity);
                    setCookie("province", data.obj.doctorAddressProvince);
                    setCookie("profilePicture", data.obj.profilePicture);
                    setCookie("type", "doctor");
                } else if (data.type === 'admin') {
                    window.location.href = './admin/dashboard.html'
                }
                var socket = new SockJS(globalURL + 'sockets');
                stompClient = Stomp.over(socket);
                stompClient.connect({}, function (frame) {
                    stompClient.subscribe("/user/errors", function(message) {
                        alert("Error " + message.body);
                    });

                    stompClient.subscribe("/user/" + getCookie("id") + "/" + getCookie("type"), function(message) {
                        let payload = JSON.parse(message.body);
                        if($('#demo-day-week-view').children().length != 0) {
                            if(payload.type === "appointment") {
                                let responseData = {};
                                const d = new Date();
                                let diff = 2*d.getTimezoneOffset()*60*1000*-1;
                                responseData["id"] = payload.data.id
                                responseData["title"] = payload.data.title
                                responseData["description"] = payload.data.description
                                responseData["start"] = new Date(new Date(payload.data.startTime).getTime() + new Date(payload.data.startTime).getTimezoneOffset()*60*1000*-1).toJSON();
                                responseData["end"] = new Date(new Date(payload.data.endTime).getTime() + new Date(payload.data.endTime).getTimezoneOffset()*60*1000*-1).toJSON();
                                responseData["color"] = payload.data.colorCode
                                responseData["doctorId"] = payload.data.doctorId;
                                responseData["patientId"] = payload.data.patientId;
                                if(payload.action === "create") {
                                    calendar.addEvent(responseData);
                                } else if(payload.action === "update") {
                                    calendar.updateEvent(responseData);
                                } else if(payload.action === "delete") {
                                    calendar.removeEvent(responseData);
                                }
                            }
                        }
                        if(payload.type !== "reminder") {
                            addToast(false, "New Notification", payload.message)
                        } else {
                            addToast(false, "Reminder", payload.message)
                        }
                    });
                });
            }
        } catch(err){
            addToast(true, "Error", "Some unknown error occurred. Pls try again later!")
            window.location.href="./index.html";
        }
    })
    .fail(function (jqXHR, textStatus, errorThrown){
        addToast(true, "Error", "Pls log in again!");
        window.location.href="./index.html";
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


