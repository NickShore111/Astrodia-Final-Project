$(function () {
    $(".datepicker").datepicker();
  });

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
        tr.innerHTML = `
            <th scope="row">${flight.flightCode}</th>
            <th class="fs-6 fw-normal">${flight.launchPad.port.id}</th>
            <th class="fs-6 fw-normal">${flight.departing}</th>
            <th class="fs-6 fw-normal">${flight.arrivalPad.port.id}</th>
            <th class="fs-6 fw-normal">${flight.arriving}</th>
            <th class="fs-6 fw-normal">${flight.shuttle.name}</th>
            <td class="fs-6 fw-normal"><a href="/flights/delete/${flight.flightCode}">Delete</a></td>`;
        tableBody.appendChild(tr);
    });
}