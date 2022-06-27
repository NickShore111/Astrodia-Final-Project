const updateForm = document.getElementById("update-form");
const REGEX_DATE = new RegExp('(0?[1-9])|([1-3]\\d{1})[/](0?[1-9]|[1-3][0-9])[/](\\d{4})');
const REGEX_TIME = new RegExp('((1[0-2]|0?[1-9]):([0-5][0-9]) ?([AaPp][Mm]$))');
//const REGEX_FLIGHTCODE = new RegExp('(([A-Z]{3,4})\\d{1,3}\\s([A-Z]{1}\\d{1})-([A-Z]{1}\\d{1}))');
const REGEX_NUM = new RegExp('^\\d*$');
const API_URL = "http://localhost:8080/astrodia/api/flights";

var spacelinerParams = [];
var shuttleParams = [];
var departureRegionParams = [];
var departurePortParams = [];
var arrivalRegionParams = [];
var arrivalPortParams = [];
var URLParams;

/**
 * Criteria Selection URL Builder, triggers new Flight Selection
 * population after change
 */
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

        const new_url = new URL(`${API_URL}?${URLParams}`);
        console.log(new_url.href);

        fetchSelection(new_url).then(flights => {
            buildDOMWithResults(flights);
        });
    })
/**
 * Resets all Update Form validations and error messages
 */
function resetForm() {
    $("input, select").removeClass("is-valid");
    $("input, select").removeClass("is-invalid");
    $(".error-msg").hide();
}

/**
 * Runs all form validation checks
 * @returns {boolean}
 */
function validateUpdateForm() {
    if (validateDepartureDate() && validateArrivalDate()) {
        let validateDates = validateDates();
    }

    if (
        validateAvailableSeats()
        && validatePricePerSeat()
        && validateSpaceliner()
        && validateShuttle()
        && validateDeparturePort()
        && validateDeparturePad()
        && validateArrivalPort()
        && validateArrivalPad()
        && validateDepartureTime()
        && validateArrivalTime()
        && validateDates) {
            return true;
        } else return false;
}

/**
 * Perform Departure and Arrival Date validations
 * @returns {boolean}
 */
function validateDates() {
    var updateDepartureDate = updateForm.departureDate.value.trim();
    var updateArrivalDate = updateForm.arrivalDate.value.trim();

    if (new Date(updateDepartureDate) > new Date(updateArrivalDate)){
        $("#departureDate").addClass("is-invalid").removeClass("is-valid");
        $("#arrivalDate").addClass("is-invalid").removeClass("is-valid");
        $("#dep-date-error-msg").html("Departure cannot be before Arrival.");
        $("#arr-date-error-msg").html("Departure cannot be before Arrival.");
        $("#dep-date-error-msg").show();
        $("#arr-date-error-msg").show();
        return false;
    } else {
        $("#departureDate").removeClass("is-invalid").addClass("is-valid");
        $("#arrivalDate").removeClass("is-invalid").addClass("is-valid");
        $("#dep-date-error-msg").hide();
        $("#arr-date-error-msg").hide();
        return true;
    }
}

/**
 * Arrival port validation
 * @returns {boolean}
 */
function validateArrivalPort() {
    var updateArrivalPort = updateForm.arrivalPort.value;

    if(updateArrivalPort) {
        $("#arrivalPort").addClass("is-valid").removeClass("is-invalid");
        return true;
    } else {
        $("#arrivalPort").addClass("is-invalid").removeClass("is-valid");
        return false;
    }
}

/**
 * Departure Port validation
 * @returns {boolean}
 */
function validateDeparturePort() {
    var updateDeparturePort = updateForm.departurePort.value;

    if(updateDeparturePort) {
        $("#departurePort").addClass("is-valid").removeClass("is-invalid");
        return true;
    } else {
        $("#departurePort").addClass("is-invalid").removeClass("is-valid");
        return false;
    }
}

/**
 * Spaceliner validation
 * @returns {boolean}
 */
function validateSpaceliner() {
    var updateSpaceliner = updateForm.spaceliner.value;

    if(updateSpaceliner) {
        $("#spaceliner").addClass("is-valid").removeClass("is-invalid");
        return true;
    } else {
        $("#spaceliner").addClass("is-invalid").removeClass("is-valid");
        return false;
    }
}

/**
 * Available Seat validation
 * @returns {boolean}
 */
function validateAvailableSeats() {
    var updateSeats = parseInt(updateForm.availableSeats.value);

    if(updateSeats > updateForm.availableSeats.max
        || !REGEX_NUM.test(updateSeats)) {
        $("#seats-error-msg").html("Enter value from 0 to ".concat(updateForm.availableSeats.max));
        $("#availableSeats").addClass("is-invalid").removeClass("is-valid");
        $("#seats-error-msg").show();
        return false;
    } else {
        $("#availableSeats").removeClass("is-invalid").addClass("is-valid")
        $("#seats-error-msg").hide();
        return true;
    }
}

/**
 * Price Per Seat validation
 * @returns {boolean}
 */
function validatePricePerSeat() {
    var updatePrice = updateForm.pricePerSeat.value;

    if(!REGEX_NUM.test(updatePrice)) {
        $("#pricePerSeat").addClass("is-invalid").removeClass("is-valid");
        $("#price-error-msg").show();
        return false;
    } else {
        $("#pricePerSeat").addClass("is-valid").removeClass("is-invalid");
        $("#price-error-msg").hide();
        return true;
    }
}

/**
 * Shuttle validation
 * @returns {boolean}
 */
function validateShuttle() {
    var updateShuttle = updateForm.shuttle.value;

    if(!updateShuttle) {
        $("#shuttle").addClass("is-invalid").removeClass("is-valid");
        $("#shuttle-error-msg").show();
        return false;
    } else {
        $("#shuttle").removeClass("is-invalid").addClass("is-valid");
        $("#shuttle-error-msg").hide();
        return true;
    }
}

/**
 * Departure Port validation
 * @returns {boolean}
 */
function validateDeparturePad() {
    var updateDeparturePad = updateForm.departurePad.value;

    if(updateDeparturePad == "") {
        $("#departurePad").addClass("is-invalid").removeClass("is-valid");
        $("#dep-pad-error-msg").show();
        return false;
    } else {
        $("#departurePad").removeClass("is-invalid").addClass("is-valid");
        $("#dep-pad-error-msg").hide();
        return true;
    }
}

/**
 * Arrival Pad validation
 * @returns {boolean}
 */
function validateArrivalPad() {
    var updateArrivalPad = updateForm.arrivalPad.value;

    if(updateArrivalPad == "") {
        $("#arrivalPad").addClass("is-invalid").removeClass("is-valid");
        $("#arr-pad-error-msg").show();
        return false;
    } else {
        $("#arrivalPad").removeClass("is-invalid").addClass("is-valid");
        $("#arr-pad-error-msg").hide();
        return true;
    }
}

/**
 * Departure Date validation
 * @returns {boolean}
 */
function validateDepartureDate() {
    var updateDepartureDate = updateForm.departureDate.value.trim();

    if (!REGEX_DATE.test(updateDepartureDate)) {
        $("#departureDate").addClass("is-invalid").removeClass("is-valid");
        $("#departureDate").html("Invalid Date format.");
        $("#dep-date-error-msg").show();
        return false;
    } else {

        $("#departureDate").removeClass("is-invalid").addClass("is-valid");
        $("#dep-date-error-msg").hide();
        return true;
    }
}

/**
 * Departure Time validation
 * @returns {boolean}
 */
function validateDepartureTime() {
    var updateDepartureTime = updateForm.departureTime.value.trim();

    if (!REGEX_TIME.test(updateDepartureTime)) {
        $("#departureTime").addClass("is-invalid").removeClass("is-valid");
        $("#dep-time-error-msg").show();
        return false;
    } else {
        $("#departureTime").removeClass("is-invalid").addClass("is-valid");
        $("#dep-time-error-msg").hide();
        return true;
    }
}

/**
 * Arrival Date validation
 * @returns {boolean}
 */
function validateArrivalDate() {
    var updateArrivalDate = updateForm.arrivalDate.value.trim();

    if (!REGEX_DATE.test(updateArrivalDate)) {
        $("#arrivalDate").addClass("is-invalid").removeClass("is-valid");
        $("#arrivalDate").html("Invalid Date format.");
        $("#arr-date-error-msg").show();
        return false;
    } else {
        $("#arrivalDate").removeClass("is-invalid").addClass("is-valid");
        $("#arr-date-error-msg").hide();
        return true;
    }
}

/**
 * Arrival Time validation
 * @returns {boolean}
 */
function validateArrivalTime() {
    var updateArrivalTime = updateForm.arrivalTime.value.trim();

    if (!REGEX_TIME.test(updateArrivalTime)) {
        $("#arrivalTime").addClass("is-invalid").removeClass("is-valid");
        $("#arr-time-error-msg").show();
        return false;
    } else {
        $("#arrivalTime").removeClass("is-invalid").addClass("is-valid");
        $("#arr-time-error-msg").hide();
        return true;
    }
}

/**
 * Update Flight Code according to flight detail changes
 * (SpacelinerID)(Day of the year departure) (DeparturePadID)-(ArrivalPadID)
 */
function updateFlightCode() {
    var dayOfYear = getDepartureDayOfYear(updateForm.departureDate.value);
    var spacelinerId = updateForm.spaceliner.value;
    var depPad = updateForm.departurePad.value;
    var arrPad = updateForm.arrivalPad.value;
    var newFlightCode = `${spacelinerId}${dayOfYear} ${depPad}-${arrPad}`;

    document.getElementById("flightCode").value = newFlightCode;
}

/**
 * Returns day of the year Flight departure Date used in Flight Code assembly
 * @param date
 * @returns {number}
 */
function getDepartureDayOfYear(date) {
    var now = new Date(date);
    var start = new Date(now.getFullYear(), 0, 0);
    var diff = (now - start) + ((start.getTimezoneOffset() - now.getTimezoneOffset()) * 60 * 1000);
    var oneDay = 1000 * 60 * 60 * 24;
    var day = Math.floor(diff / oneDay);
    return day;
}

/**
 * Assemble Update Form fields according to selected flight parameters
 * after fetching flight by ID
 * @param flightId
 */
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

/**
 * Trigger event for flight selection, required to reload after
 * each flight selection population
 */
const loadFlightSelectionFormTriggerEvent = function() {
    $(".admin-flight-tr").click(function(e) {
        const flightId = e.target.parentElement.id;
        setFormFields(flightId);
        $("#update-flight-overlay").show();
    })
}

/**
 * Helper function used to during form reset
 */
function showAllDropdownOptions() {
    var dropdowns = document.querySelectorAll(".update-dropdown");
    dropdowns.forEach((element, idx) => {
        for (let i = 0; i < element.options.length; i++) {
            element.options[i].hidden = false;
        }
    })
}

/**
 * Customize Arrival Pad options according to selected Arrival Port
 */
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

/**
 * Customize Arrival Port options according to selected Arrival Pad
 */
function arrivalPortDropdownResponseToPad() {
    $("#arrivalPad").change((e) => {
        var arrivalPadPortId = $('option:selected', "#arrivalPad").attr('data-arr-pad-port-id');
        const ddlArrivalPorts = document.getElementById("arrivalPort");
        ddlArrivalPorts.value = arrivalPadPortId
    })
}

/**
 * Customize Departure Pad options according to selected Departure Port
 */
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

/**
 * Customize Departure Port options according to selected Departure Pad
 */
function departurePortDropdownResponseToPad() {
    $("#departurePad").change((e) => {
        var departurePadPortId = $('option:selected', "#departurePad").attr('data-dep-pad-port-id');
        const ddlDeparturePorts = document.getElementById("departurePort");
        ddlDeparturePorts.value = departurePadPortId
    })
}

/**
 * Customize Spaceliner options according to selected Shuttle
 */
function spacelinerDropdownResponseToShuttle() {
    $("#shuttle").change((e) => {
        var shuttleSpacelinerId = $('option:selected', "#shuttle").attr('data-spaceliner-id');
        const ddlSpaceliners = document.getElementById("spaceliner");
        ddlSpaceliners.value = shuttleSpacelinerId;

//        set max seating availability to new shuttle max
        updateForm.availableSeats.max = $('option:selected', "#shuttle").attr('data-shuttle-maxseating');;
    })
}

/**
 * Customize Shuttle options according to selected Spaceliner
 */
function shuttleDropdownResponseToSpaceliner() {
    $("#spaceliner").change((e) => {
        var selectedSpacelinerId = e.target.value;
        const ddlShuttles = document.getElementById("shuttle");

        for (let i = 0; i < ddlShuttles.options.length; i++) {
//            console.log(ddlShuttleList.options[i].getAttribute('data-spaceliner-id'));
            if (ddlShuttles.options[i].getAttribute('data-spaceliner-id') != selectedSpacelinerId) {
                ddlShuttles.options[i].hidden = true;
                ddlShuttles.value = "";
            } else { ddlShuttles.options[i].hidden = false; }
        }
//        $('option:selected', "#shuttle").attr('data-spaceliner-id');
    })
}

/**
 * Return formatted Time String from input
 * @param datetime
 * @returns {string}
 */
function getTime(datetime) {
    const newDatetime = new Date(datetime);
//    console.log(newDatetime.toLocaleString().split(",")[1]);
    let t1 = newDatetime.toLocaleString().split(",")[1].trim();
    let meridiem = t1.split(" ")[1]
    let t2 = t1.split(" ")[0].slice(0,-3);
    return t2.concat(" "+meridiem);
}

/**
 * Return formatted Date String from input
 * @param datetime
 * @returns {string}
 */
function getDate(datetime) {
    const newDatetime = new Date(datetime);
//    console.log(newDatetime.toLocaleString().split(",")[0]);
    return newDatetime.toLocaleString().split(",")[0];
}

/**
 * Fetch flight by id and return JSON object used to set Update Form fields
 * @param id
 * @returns {Promise<any>}
 */
async function fetchFlight(id) {
let new_url = API_URL.concat(id);
//console.log(new_url);
const response = await fetch(new_url);
    if (!response.ok) {
    console.log(response);
    }
    const f = await response.json();
//    console.log(f);
    return f;
}

/**
 * Helper function adds Query parameters to URLParams object
 * @param key
 * @param paramsList
 */
function buildURLParams(key, paramsList) {
    for (param of paramsList) {
        URLParams.append(key, param);
    }
}

/**
 * Helper Function creates list of Parameters used to build URL query parameters
 * @param paramsList
 * @param event
 */
function setParamsList(paramsList, event) {
    if (event.target.checked) {
        paramsList.push(event.target.value);
    } else {
        remove(event.target.value, paramsList);
    }
//    console.log(paramsList);
}

/**
 * Helper Function used to remove parameter from ParamsList when unselected
 * @param element
 * @param array
 */
function remove(element, array) {
    for (let i = 0; i < array.length; i++) {
        if (array[i] == element) {
             array.splice(i,1);
        }
    }
}

/**
 * Used to display Error on page in case of Flight Fetch failure
 * @param response
 */
function displayError(response) {
    const tableBody = document.querySelector("tbody");
    tableBody.innerHTML = "";
    var error = document.createElement("tr")
    error.innerHTML = `<h3>${response.status} Sorry, there was an issue with your request...</h3>`;
    error.className = "text-center";
    tableBody.appendChild(error);
}

/**
 * Used to display No Results message in case of 0 results found
 */
function displayNoResults() {
    const tableBody = document.querySelector("tbody");
    tableBody.innerHTML = "";
    var noResults = document.createElement("tr")
    noResults.innerHTML = "<h3>Sorry, no results found...</h3>";
    noResults.className = "text-center";
    tableBody.appendChild(noResults);
}

/**
 * Used to fetch selection of flight based on parameterized URL query
 * @param url
 * @returns {Promise<any>}
 */
async function fetchSelection(url) {
    const response = await fetch(url);
    console.log(response);

    if (!response.ok) {
        displayError(response);
    }
    const flights = await response.json();
    return flights;
}

/**
 * Builds out new table of flights based on dynamically created selection criteria
 * @param flights
 * @returns {Promise<void>}
 */
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
     loadFlightSelectionFormTriggerEvent();
}

/**
 * Jquery Update Form control flow
 */
$(function () {
    $(".error-msg").hide();
    $(".datepicker").datepicker();
    loadFlightSelectionFormTriggerEvent();

    $("#updateForm").submit(function (e) {
        if (!validateUpdateForm()) {
            e.preventDefault();
        }
    })
    $("#updateBtn").click((e) => {
        e.preventDefault();
        validateUpdateForm();
    })
    $("#cancelBtn").click((e)=>{
        e.preventDefault();
        resetForm();
        $("#update-flight-overlay").hide();
    })
    $("#resetBtn").click((e)=>{
        e.preventDefault();
        var flightId = document.getElementById("formFlightId").value;
        setFormFields(flightId);
        resetForm();
        showAllDropdownOptions();
    })
    $("#update-form").change(function() {
        updateFlightCode();
    })
});
