$(function () {
    $(".datepicker").datepicker();
  });
// trigger after document content has loaded
document.addEventListener("DOMContentLoaded", function(){
    const url = "http://localhost:8080/astrodia/api/flights";
    const formCheckInputs = document.querySelectorAll(".form-check-input");

    var spacelinerParams = [];
    var regionParams = [];
    var portParams = [];

    // build parameters lists
    for (input of formCheckInputs) {
        input.addEventListener("change", (event) => {
        var URLParams = new URLSearchParams();

            if (event.target.parentElement.classList.contains("spacelinerSelection")) {
                setParamsList(spacelinerParams, event);
            } else if (event.target.parentElement.classList.contains("regionSelection")) {
                setParamsList(regionParams, event);
            } else (
                setParamsList(portParams, event)
            );

            buildURLParams('spaceliner', spacelinerParams);
            buildURLParams('region', regionParams);
            buildURLParams('port', portParams);

            const new_url = new URL(`${url}?${URLParams}`);
            console.log(new_url.href);

            fetch(new_url);
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
    console.log(paramsList);
}
function remove(element, array) {
    for (let i = 0; i < array.length; i++) {
        if (array[i] == element) {
             array.splice(i,1);
        }
    }
}

//            const new_url = new URL(`${url}?${params}`);
//            console.log(new_url.href);
//             fetch("http://localhost:8080/astrodia/api/flights")
//                    .then(response => response.text()).then(data => console.log(data));
        })
    }
});



//window.addEventListener("click", () => {
//    fetch("http://localhost:8080/astrodia/api/flights")
//        .then(response => response.text()).then(data => console.log(data));
//})

