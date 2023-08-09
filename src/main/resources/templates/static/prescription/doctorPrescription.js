let patientId;
let data = [];
let index = 0;


$(document).ready(function () {
  $(".add-item").click(function (e) {
    e.preventDefault();
    index++;
    $("#show-item").append(`
            <ul class="list-group">
              <li class="list-group-item ${index}">
                  <div class="row">
                      <div class="col-md-4 mb-3">
                          <input type="text" name="medicineName" class="form-control" 
                          placeholder="Medicine Name" 
                          onchange="handleChange(this.name,this.value,${index})"
                          required>
                      </div>
                      <div class="col-md-2 mb-3">
                          <input type="number" name="medicineAmount" class="form-control" placeholder="Amount" onchange="handleChange(this.name,this.value,${index})" required>
                      </div>
                      <div class="col-md-2 mb-3">
                          <input class="form-check-input" type="checkbox" onchange="handleChange(this.name,this.checked,${index})"
                          name="morning" defaultchecked=false value="" id="defaultCheck1">
                          <label class="form-check-label" for="defaultCheck1">
                              Morning
                          </label>
                      </div>
                      <div class="col-md-2 mb-3">
                          <input class="form-check-input" onchange="handleChange(this.name,this.checked,${index})"
                          type="checkbox" name="afternoon" defaultchecked=false value="" id="defaultCheck2">
                          <label class="form-check-label" for="defaultCheck2">
                              Afternoon
                          </label>
                      </div>
                      <div class="col-md-2 mb-3">
                          <input class="form-check-input" type="checkbox" name="evening" defaultchecked=false onchange="handleChange(this.name,this.checked,${index})"
                          value="" id="defaultCheck3">
                          <label class="form-check-label" for="defaultCheck3">
                              Evening
                          </label>
                      </div>
                      <div class="col-md-10 mb-3">
                        <input type="text" name="additionalNotes" class="form-control" onchange="handleChange(this.name,this.value,${index})" placeholder="Additional Notes">
                      </div>
                      <div class="col-md-2 mb-3 d-flex justify-content-end">
                        <button class="btn btn-danger remove-item d-grid">Remove</button>
                      </div>
                    </div>
                  </li>
                </ul>
        `);
  });

  $(document).on('click', '.remove-item', function (e) {
    e.preventDefault();
    let row_item = $(this).parent().parent().parent();
    let remove_index = row_item[0].className.substring(row_item[0].className.indexOf("item") + 5);
    // data.slice(remove_index, 1);
    data[remove_index] = {}
    // index--;
    $(row_item).remove();
  });

  $.ajax({
    url: globalURL + "patient/patientAppointment/" + getCookie('id'),
    type: "GET",
    success: function (response) {
      for (let index = 0; index < response.data.length; index++) {
        let patientEmail = response.data[index].patientEmail;
        $("#patient-email").append(new Option(patientEmail));
      }
    },
    error: function (xhr, status, error) {
      addToast(true, "Error", "Some unknown error occurred!");
    }
  });
  $("#patient-email").on("change", function () {
    var selectedEmail = $(this).children("option:selected").val();
    $.ajax({
      url: globalURL + "patient/patientAppointment/" + getCookie('id'),
      type: "GET",
      success: function (response) {
        for (let index = 0; index < response.data.length; index++) {
          let patientEmail = response.data[index].patientEmail;
          if (patientEmail === selectedEmail) {
            patientId = response.data[index].id;
            $("#patient-name").attr("placeholder", response.data[index].patientName);
          }
        }
      },
      error: function (xhr, status, error) {
        addToast(true, "Error", "Some unknown error occurred!");
      }
    });
  });

});

$("form").submit(function (e) {
  let dataa = {};
  e.preventDefault();
  const id = patientId;
  var currentdate = new Date();

  function padNum(num) {
    return num < 10 ? "0" + num : num;
  }

  var year = currentdate.getFullYear();
  var month = padNum(currentdate.getMonth() + 1);
  var day = padNum(currentdate.getDate());
  var hours = padNum(currentdate.getHours());
  var minutes = padNum(currentdate.getMinutes());
  var seconds = padNum(currentdate.getSeconds());
  var milliseconds = padNum(currentdate.getMilliseconds()) + "00";

  var formattedDate = year + "-" + month + "-" + day + "T" + hours + ":" + minutes + ":" + seconds + "." + milliseconds;
  let redirectURL = "";

  dataa["patientId"] = patientId;
  dataa["doctorId"] = getCookie('id');
  dataa["prescriptionTime"] = formattedDate;
  dataa["medicines"] = data.filter(value => Object.keys(value).length !== 0);;

  $.ajax({
    type: "POST",
    url: globalURL + "prescription/addPrescription",
    data: JSON.stringify(dataa),
    dataType: "json",
    contentType: "application/json"
  }).done(function (dataa) {
    if (data.isError) {
      addToast(true, "Error", data.msg);
    } else {
      addToast(false, "Hurray", data.msg);
    }
    window.location.href = redirectURL;
  })
    .fail(function (jqXHR, textStatus, errorThrown) {
      addToast(true, "Error", "Some unknown error occurred. Pls try again later!")
    });
});

function handleChange(name, value, index) {
  const oldata = [...data];
  const updatedElement = { ...oldata[index], [name]: value };
  const updatedData = [
    ...oldata.slice(0, index),
    updatedElement,
    ...oldata.slice(index + 1)
  ];
  data = updatedData;
}

$('.clear-form').click(function () {
  $(this).closest('form')[0].reset();
});