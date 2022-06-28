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
const flights = document.getElementsByClassName("flight");

/**
 * Closes Flight Detail overlay
 * @event {Onclick<close-overlay>}
 */
document.querySelector(".overlay-close").addEventListener("click", hideOverlay);
$(window).click((event)=> {
    var overlay = document.getElementById("flight-detail-overlay");
    if (event.target.isSameNode(overlay)) {
        return;
    }
    hideOverlay();
})
/**
 * Triggers overlay generation of selected flight details and outline selected in table flight
 * @event {Onclick<GenerateOverlayDOM>}
 */
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

/**
 * Dynamically renders flight overlay DOM with provided flight as parameter
 * @param flight
 * @returns {Promise<void>}
 */
async function generateOverlayForFlight(flight) {
const url = "http://localhost:8080/astrodia/api/flights/".concat(flight.id);
//console.log(url);
const response = await fetch(url);
    if (!response.ok) {
    console.log(response);
    }
    const f = await response.json();
//    console.log(f);


const LOGOS = {
    "SPX": "SpaceX_logo.png",
    "BLO": "Blue_Origin_logo.png",
    "VGN": "Virgin_Galactic_logo.jpg"
}
    let flightSpaceliner = f.shuttle.spaceliner.id;
    let logo_asset_url = "/assets/logo/".concat(LOGOS[flightSpaceliner]);
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
    let logo = document.createElement("img");
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

/**
 * Display overlay
 */
function showOverlay() {
  document.getElementById("flight-detail-overlay").style.display = "block";
}

/**
 * Hide overlay
 */
function hideOverlay() {
  document.getElementById("flight-detail-overlay").style.display = "none";
  if (document.querySelector(".selected")) {
    document.querySelector(".selected").classList.remove("selected");
  }
}

/**
 * Toggles between departure and return flight lists
 * @type {NodeListOf<Element>}
 */
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
