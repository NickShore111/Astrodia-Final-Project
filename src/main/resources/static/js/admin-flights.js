$(function () {
    $(".datepicker").datepicker();
  });
// trigger after document content has loaded
const url = "http://localhost:8080/astrodia/api/flights";
const formCheckInputs = document.querySelectorAll(".form-check-input");

var spacelinerParams = [];
var shuttleParams = [];

var departureRegionParams = [];
var departurePortParams = [];

var arrivalRegionParams = [];
var arrivalPortParams = [];

// build parameters lists
for (input of formCheckInputs) {
    input.addEventListener("change", (event) => {
    var URLParams = new URLSearchParams();
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

        buildURLParams('spaceliner', spacelinerParams);
        buildURLParams('shuttle', shuttleParams);
        buildURLParams('departureRegion', departureRegionParams);
        buildURLParams('departurePort', departurePortParams);
        buildURLParams('arrivalRegion', arrivalRegionParams);
        buildURLParams('arrivalPort', arrivalPortParams);

        const new_url = new URL(`${url}?${URLParams}`);
        console.log(new_url.href);

        fetchSelectionResults(new_url);

function buildURLParams(key, paramsList) {
    for (param of paramsList) {
        URLParams.append(key, param);
    }
}
//            const new_url = new URL(`${url}?${params}`);
//            console.log(new_url.href);
//            fetch("http://localhost:8080/astrodia/api/flights")
//                .then(response => response.text()).then(data => console.log(data));
})
}
async function fetchSelectionResults(url) {
    const response = await fetch(url)
        .then(response => response.text()).then(data => console.log(data));
//    return
}
function setParamsList(paramsList, event) {
    if (event.target.checked) {
        paramsList.push(event.target.value);
    } else {
        remove(event.target.value, paramsList);
    }
    console.log(paramsList);
}
function remove(element, array) {
    for (let i = 0; i < array.length; i++) {
        if (array[i] == element) {
             array.splice(i,1);
        }
    }
}



//window.addEventListener("click", () => {
//    fetch("http://localhost:8080/astrodia/api/flights")
//        .then(response => response.text()).then(data => console.log(data));
//})

