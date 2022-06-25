$(function () {
    $(".datepicker").datepicker();
    populateAndShowUpdateFlightForm();

    $("#updateBtn").click((e) => {
        e.preventDefault();
        const isValid = validateUpdateForm();

    })
    $("#cancelBtn").click((e)=>{
        e.preventDefault();
        $("#update-flight-overlay").hide();
    })
    $("#resetBtn").click((e)=>{
        e.preventDefault();
//        console.log(document.getElementById("formFlightId").value);
        updateForm.flightCode.readOnly = true;
        var flightId = document.getElementById("formFlightId").value;

        setFormFields(flightId);
        showAllDropdownOptions();
    })
    $("#edit-flightCode").click(function() {
        updateForm.flightCode.readOnly = false;
    })
    $("#update-form").change(function() {
        updateFlightCode();
    })
});
const updateForm = document.getElementById("update-form");

function validateUpdateForm() {

}
function updateFlightCode() {
    var form = document.getElementById("update-form")
    var dayOfYear = getDepartureDayOfYear(form.departureDate.value);
    var spacelinerId = form.spaceliner.value;
    var depPad = form.departurePad.value;
    var arrPad = form.arrivalPad.value;
    var newFlightCode = `${spacelinerId}${dayOfYear} ${depPad}-${arrPad}`;

    document.getElementById("flightCode").value = newFlightCode;
}
function getDepartureDayOfYear(date) {
    var now = new Date(date);
    var start = new Date(now.getFullYear(), 0, 0);
    var diff = (now - start) + ((start.getTimezoneOffset() - now.getTimezoneOffset()) * 60 * 1000);
    var oneDay = 1000 * 60 * 60 * 24;
    var day = Math.floor(diff / oneDay);
    return day;
}
function setFormFields(flightId) {

        fetchFlight(flightId).then((f)=> {
        console.log(f);
        document.getElementById("update-flight-title").innerHTML = `Updating: ${f.flightCode}`
        document.getElementById("formFlightId").value = f.id;
        updateForm.flightCode.value = f.flightCode;
        updateForm.spaceliner.value = f.shuttle.spaceliner.id;
        updateForm.shuttle.value = f.shuttle.id;
        updateForm.pricePerSeat.value = f.pricePerSeat;
        updateForm.availableSeats.value = f.seatsAvailable;
        updateForm.availableSeats.max = f.shuttle.passengerCapacity;

        updateForm.departurePort.value = f.launchPad.port.id;
        updateForm.departurePad.value = f.launchPad.id;
        updateForm.departureDate.value = getDate(f.departing);
        updateForm.departureTime.value = getTime(f.departing);

        updateForm.arrivalPort.value = f.arrivalPad.port.id;
        updateForm.arrivalPad.value = f.arrivalPad.id;
        updateForm.arrivalDate.value = getDate(f.arriving);
        updateForm.arrivalTime.value = getTime(f.arriving);

//      Custom dropdown event listeners
        shuttleDropdownResponseToSpaceliner();
        spacelinerDropdownResponseToShuttle();
        departurePadDropdownResponseToPort();
        departurePortDropdownResponseToPad();
        arrivalPadDropdownResponseToPort();
        arrivalPortDropdownResponseToPad();

        }).catch(console.error);
}
const populateAndShowUpdateFlightForm = function() {
    $(".admin-flight-tr").click(function(e) {
        const flightId = e.target.parentElement.id;
        setFormFields(flightId);
        $("#update-flight-overlay").show();
    })
}
function showAllDropdownOptions() {
    var dropdowns = document.querySelectorAll(".update-dropdown");
    dropdowns.forEach((element, idx) => {
        for (let i = 0; i < element.options.length; i++) {
            element.options[i].hidden = false;
        }
    })
}
function arrivalPadDropdownResponseToPort() {
    $("#arrivalPort").change((e) => {
        var portId = e.target.value;
        const ddlArrivalPads = document.getElementById("arrivalPad");

        for (let i = 0; i < ddlArrivalPads.options.length; i++) {
            if (ddlArrivalPads.options[i].getAttribute('data-arr-pad-port-id') != portId) {
                ddlArrivalPads.options[i].hidden = true;
                ddlArrivalPads.value = "";
            } else { ddlArrivalPads.options[i].hidden = false; }
        }
    })
}
function arrivalPortDropdownResponseToPad() {
    $("#arrivalPad").change((e) => {
        var arrivalPadPortId = $('option:selected', "#arrivalPad").attr('data-arr-pad-port-id');
        const ddlArrivalPorts = document.getElementById("arrivalPort");
        ddlArrivalPorts.value = arrivalPadPortId
    })
}
function departurePadDropdownResponseToPort() {
    $("#departurePort").change((e) => {
        var portId = e.target.value;
        const ddlDeparturePads = document.getElementById("departurePad");

        for (let i = 0; i < ddlDeparturePads.options.length; i++) {
            if (ddlDeparturePads.options[i].getAttribute('data-dep-pad-port-id') != portId) {
                ddlDeparturePads.options[i].hidden = true;
                ddlDeparturePads.value = "";
            } else { ddlDeparturePads.options[i].hidden = false; }
        }
    })
}
function departurePortDropdownResponseToPad() {
    $("#departurePad").change((e) => {
        var departurePadPortId = $('option:selected', "#departurePad").attr('data-dep-pad-port-id');
        const ddlDeparturePorts = document.getElementById("departurePort");
        ddlDeparturePorts.value = departurePadPortId
    })
}
function spacelinerDropdownResponseToShuttle() {
    $("#shuttle").change((e) => {
        var shuttleSpacelinerId = $('option:selected', "#shuttle").attr('data-spaceliner-id');
        const ddlSpaceliners = document.getElementById("spaceliner");
        ddlSpaceliners.value = shuttleSpacelinerId;

//        set max seating availability to new shuttle max
        updateForm.availableSeats.max = $('option:selected', "#shuttle").attr('data-shuttle-maxseating');;
    })
}
function shuttleDropdownResponseToSpaceliner() {
    $("#spaceliner").change((e) => {

        var selectedSpacelinerId = e.target.value;
//        console.log(selectedSpacelinerId);

        const ddlShuttles = document.getElementById("shuttle");

        for (let i = 0; i < ddlShuttles.options.length; i++) {
//            console.log(ddlShuttleList.options[i].getAttribute('data-spaceliner-id'));
            if (ddlShuttles.options[i].getAttribute('data-spaceliner-id') != selectedSpacelinerId) {
                ddlShuttles.options[i].hidden = true;
                ddlShuttles.value = "";
//                console.log(ddlShuttles.options[i].getAttribute('data-spaceliner-id'));
            } else { ddlShuttles.options[i].hidden = false; }
        }
//        $('option:selected', "#shuttle").attr('data-spaceliner-id');

    })
};
function getTime(datetime) {
    const newDatetime = new Date(datetime);
//    console.log(newDatetime.toLocaleString().split(",")[1]);
    return newDatetime.toLocaleString().split(",")[1];
}
function getDate(datetime) {
    const newDatetime = new Date(datetime);
//    console.log(newDatetime.toLocaleString().split(",")[0]);
    return newDatetime.toLocaleString().split(",")[0];
};

async function fetchFlight(id) {
const url = "http://localhost:8080/astrodia/api/flights/".concat(id);
//console.log(url);
const response = await fetch(url);
    if (!response.ok) {
    console.log(response);
    }
    const f = await response.json();
//    console.log(f);
    return f;
}
const url = "http://localhost:8080/astrodia/api/flights";
const formCheckInputs = document.querySelectorAll(".form-check-input");
const datepickers = document.querySelectorAll(".datepicker");

var spacelinerParams = [];
var shuttleParams = [];
var departureRegionParams = [];
var departurePortParams = [];
var arrivalRegionParams = [];
var arrivalPortParams = [];
var URLParams;

// build parameters lists using URLSearchParams()
document.getElementById("search-parameters")
    .addEventListener("change", (event) => {
    URLParams = new URLSearchParams();
//    build params from checkboxes
        let checkBoxClassList = event.target.parentElement.classList;
        switch(checkBoxClassList[checkBoxClassList.length - 1]) {
            case "spacelinerSelection":
                setParamsList(spacelinerParams, event);
                break;
            case "shuttleSelection":
                setParamsList(shuttleParams, event);
                break;
            case "departureRegionSelection":
                setParamsList(departureRegionParams, event);
                break;
            case "departurePortSelection":
                setParamsList(departurePortParams, event);
                break;
            case "arrivalRegionSelection":
                setParamsList(arrivalRegionParams, event);
                break;
            case "arrivalPortSelection":
                setParamsList(arrivalPortParams, event);
                break;
        }
//        TODO: Using dates for parameters in dynamic query
//     get dates if valid length
//        var departureDate = document.getElementById('departureDate').value;
//        var arrivalDate = document.getElementById('arrivalDate').value;
//        if (departureDate.length == 10) {
//            URLParams.append('departing', departureDate);
//        }
//        if (arrivalDate.length == 10) {
//            URLParams.append('arriving', arrivalDate);
//        }
        buildURLParams('spaceliner', spacelinerParams);
        buildURLParams('shuttle', shuttleParams);
        buildURLParams('departureRegion', departureRegionParams);
        buildURLParams('departurePort', departurePortParams);
        buildURLParams('arrivalRegion', arrivalRegionParams);
        buildURLParams('arrivalPort', arrivalPortParams);

        const new_url = new URL(`${url}?${URLParams}`);
        console.log(new_url.href);

        fetchSelection(new_url).then(flights => {
            buildDOMWithResults(flights);
        });
})

function buildURLParams(key, paramsList) {
    for (param of paramsList) {
        URLParams.append(key, param);
    }
}
function setParamsList(paramsList, event) {
    if (event.target.checked) {
        paramsList.push(event.target.value);
    } else {
        remove(event.target.value, paramsList);
    }
//    console.log(paramsList);
}
function remove(element, array) {
    for (let i = 0; i < array.length; i++) {
        if (array[i] == element) {
             array.splice(i,1);
        }
    }
}
function displayError(response) {
    const tableBody = document.querySelector("tbody");
    tableBody.innerHTML = "";
    var error = document.createElement("tr")
    error.innerHTML = `<h3>${response.status} Sorry, there was an issue with your request...</h3>`;
    error.className = "text-center";
    tableBody.appendChild(error);
}
function displayNoResults() {
    const tableBody = document.querySelector("tbody");
    tableBody.innerHTML = "";
    var noResults = document.createElement("tr")
    noResults.innerHTML = "<h3>Sorry, no results found...</h3>";
    noResults.className = "text-center";
    tableBody.appendChild(noResults);
}
async function fetchSelection(url) {
    const response = await fetch(url);
    console.log(response);

    if (!response.ok) {
        displayError(response);
    }
    const flights = await response.json();
    return flights;
}
async function buildDOMWithResults(flights) {
    const tableBody = document.querySelector("tbody");
    tableBody.innerHTML = "";
//    console.log(flights[0]);
//    console.log(flights);
    if (flights.length == 0) {
        displayNoResults();
    }
    flights.forEach((key, idx) => {
        var flight = flights[idx];
//        console.log(`Index:${idx}: ${flights[idx]}`);
        var tr = document.createElement("tr");
        tr.className = "admin-flight-tr"
        tr.id = flight.id;
        tr.innerHTML = `
            <th scope="row">${flight.flightCode}</th>
            <th class="fs-6 fw-normal">${flight.launchPad.port.id}</th>
            <th class="fs-6 fw-normal">${flight.departing}</th>
            <th class="fs-6 fw-normal">${flight.arrivalPad.port.id}</th>
            <th class="fs-6 fw-normal">${flight.arriving}</th>
            <th class="fs-6 fw-normal">${flight.shuttle.name}</th>
            <td class="fs-6 fw-normal"><a href="/flights/delete/${flight.id}">Delete</a></td>`;
        tableBody.appendChild(tr);
    });

     populateAndShowUpdateFlightForm();
}
