$( function() {
  var dateFormat = "mm/dd/yyyy",
    from = $("#departureDate")
      .datepicker({
        defaultDate: "+2d",
        minDate: 0,
        maxDate: "+3M",
        showAnim: "fadeIn",
      })
      .on("change", function () {
        to.datepicker("option", "minDate", getDate(this));
      }),
    to = $("#arrivalDate")
      .datepicker({
        defaultDate: "+1w",
        minDate: +1,
        showAnim: "fadeIn",
      })
      .on("change", function () {
        from.datepicker("option", "maxDate", getDate(this));
      });

  function getDate(element) {
    var date;
    try {
      date = $.datepicker.parseDate(dateFormat, element.value);
    } catch (error) {
      date = null;
    }
    return date;
  }
});
// Start: flights.html flight details overlay
const flights = document.getElementsByClassName("flight");
document.querySelector(".overlay-close").addEventListener("click", hideOverlay);
$(window).click((event)=> {
    var overlay = document.getElementById("flight-detail-overlay");
    if (event.target.isSameNode(overlay)) {
        return;
    }
    hideOverlay();
})

// Select and Unselect flight behavior
for (const flight of flights) {
  flight.addEventListener("click", (event) => {
    event.stopPropagation();

    for (let selectedFlight of flights) {
        if (!flight.isSameNode(selectedFlight)) {
            selectedFlight.classList.remove("selected");
        } else {
            selectedFlight.classList.add("selected");
            generateOverlayForFlight(flight);
        }
    }
  })
}
async function generateOverlayForFlight(flight) {
const url = "http://localhost:8080/astrodia/api/flights/".concat(flight.id);
//console.log(url);
const response = await fetch(url);
    if (!response.ok) {
//    console.log(response);
    }
    const f = await response.json();
//    console.log(f);

//  Modify DOM with flight details
const logos = {
    "SPX": "SpaceX_logo.png",
    "BLO": "Blue_Origin_logo.png",
    "VGN": "Virgin_Galactic_logo.jpg"
}
    var flightSpaceliner = f.shuttle.spaceliner.id;
    var logo_asset_url = "/assets/logo/".concat(logos[flightSpaceliner]);
    document.getElementById("flight-code").innerHTML = f.flightCode;
    document.getElementById("flight-launch").innerHTML = `
        <h5>Departing<h5>
        <h6>
        ${f.departing}<br>
        ${f.launchPad.port.region.name}<br>
        ${f.launchPad.port.name} in ${f.launchPad.port.location}<br>
        Pad: ${f.launchPad.id}
        </h6>
        `;
    document.getElementById("flight-landing").innerHTML = `
        <h5>Landing<h5>
        <h6>
        ${f.arriving}<br>
        ${f.arrivalPad.port.region.name}<br>
        ${f.arrivalPad.port.name} in ${f.arrivalPad.port.location}<br>
        Pad: ${f.arrivalPad.id}
        </h6>
        `;
    var logo = document.createElement("img");
    logo.src = logo_asset_url;
    logo.alt = `${f.shuttle.spaceliner.name} logo`;
    logo.style.height = "10px";
    if (flightSpaceliner == "VGN") {
        logo.style.height = "40px";
    }
    document.getElementById("aboard").innerHTML = `Shuttle: ${f.shuttle.name}`;
    document.getElementById("aboard").appendChild(logo);


showOverlay();
}
function showOverlay() {
  document.getElementById("flight-detail-overlay").style.display = "block";
}
function hideOverlay() {
  document.getElementById("flight-detail-overlay").style.display = "none";
  if (document.querySelector(".selected")) {
    document.querySelector(".selected").classList.remove("selected");
  }
}
// toggle between Departure and Return flights
const toggleBtns = document.querySelectorAll(".toggle-flight")
for (let btn of toggleBtns) {
    btn.addEventListener("click",(event) => {
        const departureFlights = document.querySelector("#departure-flights");
        const returnFlights = document.querySelector("#return-flights");
        const toggleDepartures = document.querySelector("#toggle-departures");
        const toggleReturns = document.querySelector("#toggle-returns");
        if (event.target.id.includes("departures")) {
            departureFlights.style.display = "block";
            returnFlights.style.display = "none";
            toggleDepartures.disabled= true;
            toggleReturns.disabled= false;
        } else {  // user selected to show return flights
            departureFlights.style.display = "none";
            returnFlights.style.display = "block";
            toggleDepartures.disabled= false;
            toggleReturns.disabled= true;
        }
    })
}
// End: flights.html flight details overlay
