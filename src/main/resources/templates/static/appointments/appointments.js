function loadAppointments(type, id, editable = false) {
    mobiscroll.setOptions({
        locale: mobiscroll.localeEn,     // Specify language like: locale: mobiscroll.localePl or omit setting to use default
        theme: 'ios',                    // Specify theme like: theme: 'ios' or omit setting to use default
        themeVariant: 'light'        // More info about themeVariant: https://docs.mobiscroll.com/5-21-2/eventcalendar#opt-themeVariant
    });

    let oldEvent,
        tempEvent = {},
        deleteEvent,
        restoreEvent,
        colorPicker,
        tempColor,
        $title = $('#event-title'),
        $description = $('#event-desc'),
        $deleteButton = $('#event-delete'),
        $color = $('#event-color'),
        datePickerResponsive = {
            medium: {
                controls: ['calendar'],
                touchUi: false
            }
        },
        datetimePickerResponsive = {
            medium: {
                controls: ['calendar', 'time'],
                touchUi: false
            }
        },
        now = new Date();
    now.setDate(now.getDate() - 1);
    function createAddPopup(elm) {
        // hide delete button inside add popup
        $deleteButton.hide();

        deleteEvent = true;
        restoreEvent = false;

        // set popup header text and buttons for adding
        popup.setOptions({
            headerText: 'New event',
            buttons: ['cancel', {
                text: 'Add',
                keyCode: 'enter',
                handler: function () {
                    calendar.removeEvent(tempEvent);
                    let args = {
                        id: tempEvent.id,
                        title: tempEvent.title,
                        description: tempEvent.description,
                        start: tempEvent.start,
                        end: tempEvent.end,
                        color: tempEvent.color
                    };
                    let savePayloadData = {};
                    // savePayloadData["id"] = args.id;
                    savePayloadData["title"] = args.title;
                    savePayloadData["description"] = args.description? args.description: "";
                    savePayloadData["startTime"] = new Date(new Date(args.start).getTime() + args.start.getTimezoneOffset()*60*1000*-1).toJSON();
                    savePayloadData["endTime"] = new Date(new Date(args.end).getTime() + args.end.getTimezoneOffset()*60*1000*-1).toJSON();
                    // savePayloadData["startTime"] = args.start;
                    // savePayloadData["endTime"] = args.end;
                    savePayloadData["colorCode"] = args.color? args.color: "";
                    savePayloadData["active"] = true;
                    if (hasModifiedOverlap(args, calendar.getInst())) {
                        mobiscroll.toast({
                            message: 'Make sure not to double book'
                        });
                        return false;
                    }
                    if (hasInvalidOverlap(args, calendar.getInst())) {
                        mobiscroll.toast({
                            message: "Can't create this task on lunch break."
                        });
                        return false;
                    }
                    if(new Date() > new Date(savePayloadData.startTime) || new Date() > new Date(savePayloadData.endTime)) {
                        mobiscroll.toast({
                            message: 'Make sure to select a valid time'
                        });
                        return false;
                    }
                    if (type === "Patient" && getCookie("type") === "patient") {
                        if(id === getCookie("id")) {
                            addToast(true, "Error", "Unable to save appointment with yourself!")
                        } else {
                            addToast(true, "Error", "Unable to save appointment with another patient!")
                        }
                        return false;
                    } else if (type === "Doctor" && getCookie("type") === "patient") {
                        savePayloadData["patientId"] = getCookie("id");
                        args["patientId"] = getCookie("id");
                        savePayloadData["doctorId"] = id;
                        args["doctorId"] = id;
                    } else if (type === "Doctor" && getCookie("type") === "doctor") {
                        if(id === getCookie("id")) {
                            addToast(true, "Error", "Unable to save appointment with yourself!")
                        } else {
                            addToast(true, "Error", "Unable to save appointment with another doctor!")
                        }
                        return false;
                    } else if (type === "Patient" && getCookie("type") === "doctor") {
                        savePayloadData["doctorId"] = getCookie("id");
                        args["doctorId"] = getCookie("id");
                        savePayloadData["patientId"] = id;
                        args["patientId"] = id;
                    } else {
                        mobiscroll.toast({
                            message: 'Some unknown error occurred!'
                        });
                        return false;
                    }
                    $.ajax({
                        url: globalURL + "appointment/register",
                        type: "POST",
                        dataType: "json",
                        contentType: "application/json",
                        data: JSON.stringify(savePayloadData),
                    })
                    .done(function(response) {
                        try {
                            let data = response;
                            if(data.isError){
                                addToast(true, "Error", data.msg);
                                return false;
                            }
                        } catch(err){
                            addToast(true, "Error", "Some unknown error occurred. Unable to create an appointment!")
                            return false;
                        }
                    })
                    .fail(function (jqXHR, textStatus, errorThrown){
                        addToast(true, "Error", "Some unknown error occurred. Unable to create an appointment!");
                        return false;
                    });
                    // navigate the calendar to the correct view
                    calendar.navigateToEvent(tempEvent);
                    deleteEvent = false;
                    popup.close();
                },
                cssClass: 'mbsc-popup-button-primary'
            }]
        });

        // fill popup with a new event data
        $title.mobiscroll('getInst').value = tempEvent.title;
        $description.mobiscroll('getInst').value = '';
        range.setVal([tempEvent.start, tempEvent.end]);
        range.setOptions({ controls: ['datetime'], responsive: datetimePickerResponsive });
        selectColor('', true);

        // set anchor for the popup
        popup.setOptions({ anchor: elm });

        popup.open();
    }

    function createEditPopup(args, editable = true) {
        let ev = args.event;
        // show delete button inside edit popup
        $deleteButton.show();

        deleteEvent = false;
        restoreEvent = true;

        // set popup header text and buttons for editing
        if(!editable) {
            $deleteButton.hide();
            popup.setOptions({
                headerText: 'View Event',
                buttons: ['cancel']
            });
        } else {
            popup.setOptions({
            headerText: 'Edit event',
            buttons: ['cancel', {
                text: 'Save',
                keyCode: 'enter',
                handler: function () {
                    let date = range.getVal();
                    let eventToSave = {
                        id: ev.id,
                        title: $title.val(),
                        description: $description.val(),
                        start: date[0],
                        end: date[1],
                        color: ev.color,
                        patientId: ev.patientId,
                        doctorId: ev.doctorId
                    };
                    let savePayloadData = {};
                    savePayloadData["id"] = eventToSave.id;
                    savePayloadData["title"] = eventToSave.title;
                    savePayloadData["description"] = eventToSave.description;
                    // savePayloadData["startTime"] = eventToSave.start;
                    // savePayloadData["endTime"] = eventToSave.end;
                    savePayloadData["startTime"] = new Date(new Date(eventToSave.start).getTime() + eventToSave.start.getTimezoneOffset()*60*1000*-1).toJSON();
                    savePayloadData["endTime"] = new Date(new Date(eventToSave.end).getTime() + eventToSave.end.getTimezoneOffset()*60*1000*-1).toJSON();
                    savePayloadData["colorCode"] = eventToSave.color;
                    savePayloadData["patientId"] = eventToSave.patientId;
                    savePayloadData["doctorId"] = eventToSave.doctorId;
                    savePayloadData["active"] = true;
                    if (hasModifiedOverlap(eventToSave, calendar.getInst())) {
                        mobiscroll.toast({
                            message: 'Make sure not to double book'
                        });
                        return false;
                    }
                    if (hasInvalidOverlap(args, calendar.getInst())) {
                        mobiscroll.toast({
                            message: "Can't create this task on lunch break210."
                        });
                        return false;
                    }
                    if(new Date() > new Date(savePayloadData.startTime) || new Date() > new Date(savePayloadData.endTime)) {
                        mobiscroll.toast({
                            message: 'Make sure to select a valid time'
                        });
                        return false;
                    }
                    if (type === "Doctor" && getCookie("type") === "patient") {
                        if(getCookie("id") != savePayloadData["patientId"]) {
                            addToast(true, "Error", "Unable to update appointment of another patient!")
                            return false;
                        }
                    } else if (type === "Patient" && getCookie("type") === "doctor") {
                        if(getCookie("id") != savePayloadData["doctorId"]) {
                            addToast(true, "Error", "Unable to update appointment of another doctor!")
                            return false;
                        }
                    }
                    $.ajax({
                        url: globalURL + "appointment/update",
                        type: "POST",
                        dataType: "json",
                        contentType: "application/json",
                        data: JSON.stringify(savePayloadData),
                    })
                    .done(function(response) {
                        try {
                            let data = response;
                            if(data.isError){
                                addToast(true, "Error", data.msg);
                                return false;
                            } else {
                                // addToast(false, "Success", "Appointment updated successfully!")
                            }
                        } catch(err){
                            addToast(true, "Error", "Some unknown error occurred. Unable to update the appointment!")
                            return false;
                        }
                    })
                    .fail(function (jqXHR, textStatus, errorThrown){
                        addToast(true, "Error", "Some unknown error occurred. Unable to update the appointment!");
                        return false;
                    });
                    // update event with the new properties on save button click
                    calendar.updateEvent(eventToSave);
                    // navigate the calendar to the correct view
                    calendar.navigateToEvent(eventToSave);
                    restoreEvent = false;
                    popup.close();
                },
                cssClass: 'mbsc-popup-button-primary'
            }]
        });
        }

        // fill popup with the selected event data
        $title.mobiscroll('getInst').value = ev.title || '';
        $description.mobiscroll('getInst').value = ev.description || '';
        range.setVal([ev.start, ev.end]);
        selectColor(ev.color, true);

        // change range settings based on the allDay
        range.setOptions({
            controls: ev.allDay ? ['date'] : ['datetime'],
            responsive: ev.allDay ? datePickerResponsive : datetimePickerResponsive
        });

        // set anchor for the popup
        popup.setOptions({ anchor: args.domEvent.currentTarget });
        popup.open();
    }

    function hasOverlap(args, inst) {
        let ev = args.event;
        let events = inst.getEvents(ev.start, ev.end).filter(function (e) { return e.id !== ev.id });

        return events.length > 0;
    }

    function hasModifiedOverlap(args, inst) {
        let ev = args;
        let events = inst.getEvents(ev.start, ev.end).filter(function (e) { return e.id !== ev.id });

        return events.length > 0;
    }

    function hasInvalidOverlap(args, inst) {
        let ev = args;
        let events = inst.getInvalids(ev.start, ev.end).filter(function (e) { return e.id !== ev.id });
        return events.length > 0;
    }
    window.calendar = $('#demo-day-week-view').mobiscroll().eventcalendar({
        invalid: [
            {
                // More info about invalid: https://docs.mobiscroll.com/5-21-2/eventcalendar#opt-invalid
                start: "12:00",
                end: "13:00",
                title: "Lunch break",
                type: "lunch",
                recurring: {
                    repeat: "weekly",
                    weekDays: "MO,TU,WE,TH,FR", // More info about weekDays: https://docs.mobiscroll.com/5-21-2/eventcalendar#opt-weekDays
                },
            },
            {
                // More info about invalid: https://docs.mobiscroll.com/5-21-2/eventcalendar#opt-invalid
                // start: "09:00",
                // end: "18:00",
                // title: "Off",
                // type: "off",
                recurring: {
                    repeat: "weekly",
                    weekDays: "SA,SU", // More info about weekDays: https://docs.mobiscroll.com/5-21-2/eventcalendar#opt-weekDays
                },
            },
            {
                recurring: {
                    repeat: 'daily',
                    until: now
                }
            }
        ],
        view: {
            schedule: {
                type: "day",
                startDay: 0,
                endDay: 6,
                startTime: "09:00",
                endTime: "18:00",
            },                      // More info about view: https://docs.mobiscroll.com/5-21-2/eventcalendar#opt-view
            // calendar: {
            //     labels: true         // More info about labels: https://docs.mobiscroll.com/5-21-2/eventcalendar#opt-labels
            // }
        },
        renderHeader: function () {  // More info about renderHeader: https://docs.mobiscroll.com/5-21-2/eventcalendar#opt-renderHeader
            return '<div class="row w-100 m-0 m-md-2"><div mbsc-calendar-nav class="cal-header-nav p-0 order-md-1 order-1 col-6 col-md-2"></div>' +
                '<div class="cal-header-picker order-md-2 order-3 col-12 col-md-5 col-lg-5 p-0 text-align-center">' +
                '<label>Year<input mbsc-segmented type="radio" name="view" value="year" class="md-view-change"></label>' +
                '<label>Month<input mbsc-segmented type="radio" name="view" value="month" class="md-view-change"></label>' +
                '<label>Week<input mbsc-segmented type="radio" name="view" value="week" class="md-view-change"></label>' +
                '<label>Day<input mbsc-segmented type="radio" name="view" value="day" class="md-view-change" checked></label>' +
                '<label>Agenda<input mbsc-segmented type="radio" name="view" value="agenda" class="md-view-change"></label>' +
                '</div>' +
                '<div class="d-flex flex-row col-6 p-0 col-md-3 order-md-3 order-2 ml-auto justify-content-end">' +
                '<div mbsc-calendar-prev class="cal-header-prev"></div>' +
                '<div mbsc-calendar-today class="cal-header-today"></div>' +
                '<div mbsc-calendar-next class="cal-header-next"></div>' +
                '</div>';
        },
        onEventClick: function (args) {
            oldEvent = $.extend({}, args.event);
            tempEvent = args.event;

            const d = new Date();
            let diff = d.getTimezoneOffset()*60*1000*-1;

            if (!popup.isVisible()) {
                if(new Date(new Date(tempEvent.start).getTime() - diff) < new Date() || new Date(new Date(tempEvent.end).getTime() - diff) < new Date()) {
                    createEditPopup(args, false);
                } else {
                    createEditPopup(args);
                }
            }
        },
        onEventCreate: function (args, inst) {
            if (hasOverlap(args, inst)) {
                mobiscroll.toast({
                    message: 'Make sure not to double book'
                });
                return false;
            }
        },
        onEventCreated: function (args) {
            popup.close();

            // store temporary event
            tempEvent = args.event;
            createAddPopup(args.target);
        },
        onEventDelete: function () {
            let savePayloadData = {};
            savePayloadData["id"] = tempEvent.id;
            savePayloadData["title"] = tempEvent.title;
            savePayloadData["description"] = tempEvent.description;
            savePayloadData["startTime"] = tempEvent.start;
            savePayloadData["endTime"] = tempEvent.end;
            savePayloadData["colorCode"] = tempEvent.color;
            savePayloadData["patientId"] = tempEvent.patientId;
            savePayloadData["doctorId"] = tempEvent.doctorId;
            savePayloadData["active"] = false;
            $.ajax({
                url: globalURL + "appointment/delete",
                type: "POST",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify(savePayloadData),
            })
            .done(function(response) {
                try {
                    let data = response;
                    if(data.isError){
                        addToast(true, "Error", data.msg);
                        return false;
                    } else {
                        // addToast(false, "Success", "Appointment deleted successfully!")
                    }
                } catch(err){
                    addToast(true, "Error", "Some unknown error occurred. Unable to delete an appointment!")
                    return false;
                }
            })
            .fail(function (jqXHR, textStatus, errorThrown){
                addToast(true, "Error", "Some unknown error occurred. Unable to delete an appointment!");
                return false;
            });
            calendar.removeEvent(tempEvent);
        },
        onEventUpdate: function (args, inst) {
            if (hasOverlap(args, inst)) {
                mobiscroll.toast({
                    message: 'Make sure not to double book'
                });
                return false;
            }
        },
        onEventUpdated: function (args, inst) {
            if (hasOverlap(args, inst)) {
                mobiscroll.toast({
                    message: 'Make sure not to double book'
                });
                return false;
            }
        },
        onEventCreateFailed: function (event, inst) {
            // More info about onEventCreateFailed: https://docs.mobiscroll.com/5-21-2/eventcalendar#event-onEventCreateFailed
            if (event.invalid.type == "lunch") {
                mobiscroll.toast({
                    message: "Can't create this task on lunch break.",
                });
            }
        },
        onEventUpdateFailed: function (event, inst) {
            // More info about onEventUpdateFailed: https://docs.mobiscroll.com/5-21-2/eventcalendar#event-onEventUpdateFailed
            if (event.invalid.type == "lunch") {
                mobiscroll.toast({
                    message: "Can't schedule this task on lunch break.",
                });
            }
        },
        clickToCreate: editable,
        // clickToCreate: 'double',
        dragToCreate: editable,
        dragToMove: false,
        dragToResize: false
    }).mobiscroll('getInst');

    let payloadData = {};
    if(type == "Patient") {
        payloadData["patientId"] = id;
    } else if(type == "Doctor") {
        payloadData["doctorId"] = id;
    }
    $.ajax({
        url: globalURL + "appointment/fetchAppointmentsBy"+type,
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(payloadData),
    })
        .done(function(response) {
            try {
                let data = response;
                if(data.isError){
                    addToast(true, "Error", data.msg);
                    // window.location.href="./index.html";
                } else {
                    let responseDataList = [];
                    const d = new Date();
                    let diff = 2*d.getTimezoneOffset()*60*1000*-1;
                    for(let i = 0; i < data.data.length; i++) {
                        let responseData = {};
                        responseData["id"] = data.data[i].id
                        responseData["title"] = data.data[i].title
                        responseData["description"] = data.data[i].description
                        // responseData["start"] = new Date(new Date(data.data[i].startTime).getTime() + diff).toJSON()
                        // responseData["end"] = new Date(new Date(data.data[i].endTime).getTime() + diff).toJSON()
                        // responseData["start"] = new Date(data.data[i].startTime).toJSON()
                        // responseData["end"] = new Date(data.data[i].endTime).toJSON()
                        responseData["start"] = new Date(new Date(data.data[i].startTime).getTime() + new Date(data.data[i].startTime).getTimezoneOffset()*60*1000*-1).toJSON();
                        responseData["end"] = new Date(new Date(data.data[i].endTime).getTime() + new Date(data.data[i].endTime).getTimezoneOffset()*60*1000*-1).toJSON();
                        responseData["color"] = data.data[i].colorCode
                        responseData["doctorId"] = data.data[i].doctorId;
                        responseData["patientId"] = data.data[i].patientId;
                        responseDataList.push(responseData)
                    }
                    calendar.setEvents(responseDataList);
                    // addToast(false, "Success", "Data fetched successfully!")
                }
            } catch(err){
                addToast(true, "Error", "Some unknown error occurred. Unable to fetch the appointments!")
                // window.location.href="./index.html";
            }
        })
        .fail(function (jqXHR, textStatus, errorThrown){
            addToast(true, "Error", "Some unknown error occurred. Unable to fetch the appointments!");
        });

    $('.md-view-change').change(function (ev) {
        switch (ev.target.value) {
            case 'year':
                calendar.setOptions({
                    view: {                      // More info about view: https://docs.mobiscroll.com/5-21-2/eventcalendar#opt-view
                        calendar: { type: 'year', labels: true }
                    }
                })
                break;
            case 'month':
                calendar.setOptions({
                    view: {                      // More info about view: https://docs.mobiscroll.com/5-21-2/eventcalendar#opt-view
                        calendar: { type: 'month', labels: true }
                    }
                })
                break;
            case 'week':
                calendar.setOptions({
                    view: {                      // More info about view: https://docs.mobiscroll.com/5-21-2/eventcalendar#opt-view
                        schedule: { type: 'week' }
                    }
                })
                break;
            case 'day':
                calendar.setOptions({
                    view: {                      // More info about view: https://docs.mobiscroll.com/5-21-2/eventcalendar#opt-view
                        schedule: { type: 'day' }
                    }
                })
                break;
            case 'agenda':
                calendar.setOptions({
                    view: {                      // More info about view: https://docs.mobiscroll.com/5-21-2/eventcalendar#opt-view
                        calendar: { type: 'week' },
                        agenda: { type: 'week' }
                    }
                })
                break;
        }
    });

    let popup = $('#demo-add-popup').mobiscroll().popup({
        display: 'bottom',
        contentPadding: false,
        fullScreen: true,
        onClose: function () {
            if (deleteEvent) {
                calendar.removeEvent(tempEvent);
            } else if (restoreEvent) {
                calendar.updateEvent(oldEvent);
            }
        },
        responsive: {
            medium: {
                display: 'anchored',
                width: 400,
                fullScreen: false,
                touchUi: false
            }
        }
    }).mobiscroll('getInst');

    $title.on('input', function (ev) {
        // update current event's title
        tempEvent.title = ev.target.value;
    });

    $description.on('change', function (ev) {
        // update current event's title
        tempEvent.description = ev.target.value;
    });

    let range = $('#event-date').mobiscroll().datepicker({
        controls: ['datetime'],
        select: 'range',
        startInput: '#start-input',
        endInput: '#end-input',
        showRangeLabels: false,
        touchUi: true,
        responsive: datePickerResponsive,
        // maxRange: 3600000,
        invalid: [
            {
                recurring: {
                    repeat: "weekly",
                    weekDays: "SA,SU", // More info about weekDays: https://docs.mobiscroll.com/5-21-2/eventcalendar#opt-weekDays
                },
            }
        ],
        onChange: function (args) {
            let date = args.value;

            // update event's start date
            tempEvent.start = date[0];
            tempEvent.end = date[1];
        }
    }).mobiscroll('getInst');

    $deleteButton.on('click', function () {
        let savePayloadData = {};
        savePayloadData["id"] = tempEvent.id;
        savePayloadData["title"] = tempEvent.title;
        savePayloadData["description"] = tempEvent.description;
        savePayloadData["startTime"] = tempEvent.start;
        savePayloadData["endTime"] = tempEvent.end;
        savePayloadData["colorCode"] = tempEvent.color;
        savePayloadData["patientId"] = tempEvent.patientId;
        savePayloadData["doctorId"] = tempEvent.doctorId;
        savePayloadData["active"] = false;
        $.ajax({
            url: globalURL + "appointment/delete",
            type: "POST",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(savePayloadData),
        })
        .done(function(response) {
            try {
                let data = response;
                if(data.isError){
                    addToast(true, "Error", data.msg);
                    return false;
                } else {
                    // addToast(false, "Success", "Appointment deleted successfully!")
                }
            } catch(err){
                addToast(true, "Error", "Some unknown error occurred. Unable to delete an appointment!")
                return false;
            }
        })
        .fail(function (jqXHR, textStatus, errorThrown){
            addToast(true, "Error", "Some unknown error occurred. Unable to delete an appointment!");
            return false;
        });

        // delete current event on button click
        calendar.removeEvent(tempEvent);

        // save a local reference to the deleted event
        // let deletedEvent = tempEvent;

        popup.close();

        // mobiscroll.snackbar({
        //     button: {
        //         action: function () {
        //             calendar.addEvent(deletedEvent);
        //         },
        //         text: 'Undo'
        //     },
        //     message: 'Event deleted'
        // });
    });

    colorPicker = $('#demo-event-color').mobiscroll().popup({
        display: 'bottom',
        contentPadding: false,
        showArrow: false,
        showOverlay: false,
        buttons: [
            'cancel',
            {
                text: 'Set',
                keyCode: 'enter',
                handler: function (ev) {
                    setSelectedColor();
                },
                cssClass: 'mbsc-popup-button-primary'
            }
        ],
        responsive: {
            medium: {
                display: 'anchored',
                anchor: $('#event-color-cont')[0],
                buttons: {},
            }
        }
    }).mobiscroll('getInst');

    function selectColor(color, setColor) {
        $('.crud-color-c').removeClass('selected');
        $('.crud-color-c[data-value="' + color + '"]').addClass('selected');
        if (setColor) {
            $color.css('background', color || '');
        }
    }

    function setSelectedColor() {
        tempEvent.color = tempColor;
        $color.css('background', tempColor);
        colorPicker.close();
    }

    $('#event-color-picker').on('click', function () {
        selectColor(tempEvent.color || '');
        colorPicker.open();
    });


    $('.crud-color-c').on('click', function (ev) {
        let $elm = $(ev.currentTarget);

        tempColor = $elm.data('value');
        selectColor(tempColor);

        if (!colorPicker.s.buttons.length) {
            setSelectedColor();
        }
    });


    const myModalEl = document.getElementById('exampleModal')
    if(myModalEl != null) {
        myModalEl.addEventListener('hidden.bs.modal', event => {
            calendar.destroy();
        })
    }
}