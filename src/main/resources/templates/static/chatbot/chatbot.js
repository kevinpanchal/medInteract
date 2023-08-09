var sendForm = document.querySelector('#chatform'),
    textInput = document.querySelector('.chatbox'),
    chatList = document.querySelector('.chatlist'),
    userBubble = document.querySelectorAll('.userInput'),
    botBubble = document.querySelectorAll('.bot__output'),
    animateBotBubble = document.querySelectorAll('.bot__input--animation'),
    overview = document.querySelector('.chatbot__overview'),
    hasCorrectInput,
    imgLoader = false,
    animationCounter = 1,
    animationBubbleDelay = 600,
    input,
    previousInput,
    isReaction = false,
    unkwnCommReaction = "I didn't quite get that.",
    chatbotButton = document.querySelector(".submit-button"),
    bodyElement = document.body;


bodyElement.addEventListener("click", function(event){
  if (event.target.classList.contains('viewDoctors')) {
    createBubble("view doctors")
  }
});

bodyElement.addEventListener("click", function(event){
  if (event.target.classList.contains('createappointment')) {
    createBubble("create appointment")
  }
});



bodyElement.addEventListener("click", function(event){
  if (event.target.classList.contains('emergencyInfo')) {
    createBubble("emergency contact")
  }
});

  sendForm.onkeydown = function(e){
  if(e.keyCode == 13){
    e.preventDefault();

    //No mix ups with upper and lowercases
    var input = textInput.value.toLowerCase();

    //Empty textarea fix
    if(input.length > 0) {
      createBubble(input)
    }
  }
};

sendForm.addEventListener('submit', function(e) {
  //so form doesnt submit page (no page refresh)
  e.preventDefault();

  //No mix ups with upper and lowercases
  var input = textInput.value.toLowerCase();

  //Empty textarea fix
  if(input.length > 0) {
    createBubble(input)
  }
})

var createBubble = function(input) {
  //create input bubble
  var chatBubble = document.createElement('li');
  chatBubble.classList.add('userInput');

  //adds input of textarea to chatbubble list item
  chatBubble.innerHTML = input;

  //adds chatBubble to chatlist
  chatList.appendChild(chatBubble)

  checkInput(input);
}

var checkInput = async function(input) {
  hasCorrectInput = false;
  isReaction = false;
const textVal = input.toString();
    //Is a word of the input also in possibleInput object?
    if(possibleInput.hasOwnProperty(input)){
      hasCorrectInput = true;
      await botResponse(textVal);
      if(textVal != "help") {
        await botResponse("help");
      }
    }
    //When input is not in possibleInput
    if(hasCorrectInput == false){
      unknownCommand(unkwnCommReaction);
      hasCorrectInput = true;
    }
  }

async function botResponse(textVal) {
  //sets previous input to that what was called
  // previousInput = input;
  //create response bubble
  let userBubble = document.createElement('li');
  userBubble.classList.add('bot__output');
  userBubble.innerHTML = await possibleInput[textVal]();
  //add list item to chatlist
  // chatList.appendChild(userBubble) //adds chatBubble to chatlist

  // reset text area input
  textInput.value = "";
}

function unknownCommand(unkwnCommReaction) {
  // animationCounter = 1;
  //create response bubble
  var failedResponse = document.createElement('li');

  failedResponse.classList.add('bot__output');
  failedResponse.classList.add('bot__output--failed');

  //Add text to failedResponse
  failedResponse.innerHTML = unkwnCommReaction; //adds input of textarea to chatbubble list item

  //add list item to chatlist
  chatList.appendChild(failedResponse) //adds chatBubble to chatlist

  animateBotOutput();
  console.log(130)
  // reset text area input
  textInput.value = "";
  console.log(133)

  //Sets chatlist scroll to bottom
  chatList.scrollTop = chatList.scrollHeight;

  animationCounter = 1;
}



function emergencyResponse() {

  var response = document.createElement('li');
  response.classList.add('bot__output');

  const websiteUrl = "911.novascotia.ca";

  const infoText = "Call 911 when someone’s health, safety or property is threatened and help is needed right away.";

  response.innerHTML = `<p><a href="${websiteUrl}">${websiteUrl}</a><p>${infoText}</p>`;

  chatList.appendChild(response);

  animateBotOutput();


  //Sets chatlist scroll to bottom
  setTimeout(function(){
    chatList.scrollTop = chatList.scrollHeight;
  }, 0)
}


function responseText(e) {

  var response = document.createElement('li');

  response.classList.add('bot__output');

  //Adds whatever is given to responseText() to response bubble
  response.innerHTML = e;

  chatList.appendChild(response);

  animateBotOutput();


  //Sets chatlist scroll to bottom
  setTimeout(function(){
    chatList.scrollTop = chatList.scrollHeight;
  }, 0)
}

function responseButtons(name,otherElement,id,customClassName) {
  var list = document.createElement('li');
  list.classList.add('bot__output');

  var button = document.createElement('button');
  button.classList.add('btn-primary', 'btn', customClassName)
  button.id= id+"_"+name
  button.innerHTML = name

  list.appendChild(button)
  if(otherElement!=null)
    list.appendChild(otherElement)
  chatList.appendChild(list);

  animateBotOutput();


  //Sets chatlist scroll to bottom
  setTimeout(function(){
    chatList.scrollTop = chatList.scrollHeight;
  }, 0)
}



//change to SCSS loop
function animateBotOutput() {
  chatList.lastElementChild.style.animationDelay= (animationCounter * animationBubbleDelay)+"ms";
  animationCounter++;
  chatList.lastElementChild.style.animationPlayState = "running";
}

function commandReset(e){
  animationCounter = 1;
  previousInput = Object.keys(possibleInput)[e];
}


var possibleInput = {

  "hi" : function(){
    responseText("Hello, How can i help you?");
    commandReset(2);
    return
  },
  "viewPatients":async function () {
    let url = "http://localhost:6969/patient/fetchAll";
    let responseData = await fetch(url)
    const data= await responseData.json();
    for (let i = 0; i < data.data.length; i++) {
      responseButtons(data.data[i].patientName.toString(), null, data.data[i].id,"calendar");
      commandReset(2);
    }
    return;
  }
    ,
  "my appointments": function(){
    $("#show").click();
    let name = getCookie("name")
    $("#modalTitleDoctorName").html(name);


    if (getCookie("type") == "patient" || getCookie("type") == "doctor") {
      loadAppointments(getCookie("type").charAt(0).toUpperCase() + getCookie("type").slice(1), getCookie("id"), true);
    }
  commandReset(2);
  return
}
  ,
  "emergency contact" : function (){
    emergencyResponse()
    commandReset(2)
    return
  },

  "create appointment" : async function () {


    const apiUrl = "http://localhost:6969/appointment/fetchDoctorNamesByAppointments";

    const jsonData = {
      "id": getCookie("id")
    };

    let responseData = await fetch(apiUrl, {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(jsonData)
    })
    const data= await responseData.json();
    const doctorData = data.data

          for (let i = 0; i < doctorData.length; i++) {
           responseButtons(doctorData[i].doctorName,null,doctorData[i].id,"calendar")
            animateBotOutput();
            commandReset(2);
          }
    return


  },


  "help" : function(){
    let resp = returnOptions();
    responseText(resp);
    commandReset(2);
    return
  },

  "view doctors" : async function () {
    let url = "http://localhost:6969/doctor/fetchAll";
    let responseData = await fetch(url)
    const data= await responseData.json();

    for (let i = 0; i < data.data.length; i++) {
      responseButtons(data.data[i].doctorName.toString(), null, data.data[i].id,"calendar");
      commandReset(2);

    }
    return
  }




}

function returnOptions(){
  let resp = "";
  if (getCookie("type") == "patient") {
    resp += "<input class=\"btn btn-primary createappointment\" type=\"button\" id=\"createappointment\" value=\"Create Appointment\">\n";
  }
  resp += "<input class=\"btn btn-primary myBooking\" type=\"button\" id=\"myBooking\" value=\"My Appointments\">\n";
  if (getCookie("type") == "patient") {
    resp += "<input class=\"btn btn-primary viewDoctors\" id=\"viewDoctors\" type=\"button\" value=\"View Doctors\">\n";
  }
  resp += "<input class=\"btn btn-primary emergencyInfo\" id=\"emergencyInfo\" type=\"button\" value=\"Emergency Contact\">";
  return resp;
}



