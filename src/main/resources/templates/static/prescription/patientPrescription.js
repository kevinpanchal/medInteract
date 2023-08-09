let dName;
let myData;
let mergedData = [];

$.when(
    $.ajax({
        url: globalURL + "prescription/fetch/" + getCookie("id"),
        type: "GET",
        dataType: "json"
    }),
    $.ajax({
        url: globalURL + "doctor/fetch/" + getCookie("id"),
        type: "GET",
        dataType: "json"
    })
).done(function (response1, response2) {
    let response = response1.concat(response2);
    let prescriptionData = response[0].data;
    let doctorData = response[3].data;

    // Join prescriptionTime and doctorName data based on matching keys
    if (prescriptionData && doctorData) {
        for (let i = 0; i < prescriptionData.length; i++) {
            let match = doctorData.find(obj => obj.id === prescriptionData[i].doctorId);
            if (typeof match !== "undefined") {
                let dateTime = new Date(prescriptionData[i].prescriptionTime)
                mergedData.push({
                    prescriptionTime: dateTime.toLocaleString(),
                    doctorName: match.doctorName,
                    prescriptionId: prescriptionData[i].prescriptionId,
                    doctorEmail: match.doctorEmail,
                    doctorMobileNumber: match.doctorMobileNumber,
                    doctorAddressStreet: match.doctorAddressStreet
                });
            }
        }
    }


    // Define columns
    let columns = [
        { data: 'doctorName' },
        { data: 'prescriptionTime' },
        {
            data: 'prescriptionId',
            render: function (data) {
                return '<button id = "' + data + '" type="button" class="view btn btn-outline-primary" data-bs-toggle="modal" data-bs-target="#prescriptionModal">View</button>';
            }
        }
    ];

    // Initialize DataTable with mergedData and columns definitions
    try {
        $('#prescriptionTable').DataTable({
            data: mergedData,
            columns: columns
        });
    } catch (err) {
        addToast(true, "Error", "Some unknown error occurred. Pls try again later!")
    }
}).fail(function (jqXHR, textStatus, errorThrown) {
    addToast(true, "Error", "Some unknown error occurred. Pls try again later!")
});


$(document).on('click', '.view', function () {
    let buttonId = $(this).attr("id");
    let row = $(this).closest("tr");
    dName = row.find("td:eq(0)").text();
    let time = row.find("td:eq(1)").text();
    const { doctorEmail: email } = mergedData.find((data) => data.doctorName === dName);
    const { doctorMobileNumber: number } = mergedData.find((data) => data.doctorName === dName);
    const { doctorAddressStreet: address } = mergedData.find((data) => data.doctorName === dName);
    $('#doctor-name').empty();
    $('#details').empty(); // Added this to clear #details

    $.ajax({
        type: "get",
        url: globalURL + "prescription/fetch/" + getCookie('id'),
        dataType: "JSON",
        success: function (response) {
            for (let i = 0; i < response.data.length; i++) {
                const prescription = response.data[i];

                if (buttonId === response.data[i].id.toString()) {
                    let doctorHeader = `<h1 class="modal-title fs-3" id="exampleModalLabel">${dName}</h1><button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>`
                    $(doctorHeader).appendTo('#doctor-name');

                    let header = `<h2 class="fs-6"><i class="bi bi-envelope"></i>&nbsp${email}</h2>
                        <h2 class="fs-6"><i class="bi bi-telephone"></i>&nbsp${number}</h2>
                        <h2 class="fs-6"><i class="bi bi-pin-map-fill"></i>&nbsp${address}</h2><br>
                        <h2 class="fs-5">Prescription</h2>
                        `;
                    $(header).appendTo('#details');

                    let table = `<div class="table-responsive"><table class="table table-striped table-bordered"><thead><tr><th scope="col">Name</th><th scope="col">Amount</th><th scope="col">Morning</th><th scope="col">Afternoon</th><th scope="col">Evening</th><th scope="col">Notes</th></tr></thead><tbody>`;

                    for (let j = 0; j < prescription.medicines.length; j++) {
                        const medicines = prescription.medicines[j];
                        let medicineInfo = `<tr><td>${medicines.medicineName}</td><td>${medicines.medicineAmount}</td>`
                        if (medicines.morning) {
                            medicineInfo += `<td><i class="bi bi-check"></i></td>`;
                        } else {
                            medicineInfo += `<td><i class="bi bi-x"></i></td>`
                        }
                        if (medicines.afternoon) {
                            medicineInfo += `<td><i class="bi bi-check"></i></td>`;
                        } else {
                            medicineInfo += `<td><i class="bi bi-x"></i></td>`
                        }
                        if (medicines.evening) {
                            medicineInfo += `<td><i class="bi bi-check"></i></td>`;
                        } else {
                            medicineInfo += `<td><i class="bi bi-x"></i></td>`
                        }

                        medicineInfo += `<td>${medicines.additionalNotes}</td></tr>`;
                        table += medicineInfo;
                    }
                    table += '</tbody></table></div>';
                    $(table).appendTo('#details');
                    let footer = `<br><h3 class="fs-6"><i class="bi bi-calendar-date"></i>&nbsp${time}</h3>`;
                    $(footer).appendTo('#details')
                }
            }
        }
    });
});