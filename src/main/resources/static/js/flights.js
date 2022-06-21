// Start: flights.html flight details overlay
const flights = document.getElementsByClassName("flight");
document.getElementById("overlay-close").addEventListener("click", hideOverlay);
$(window).click(hideOverlay);

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
function generateOverlayForFlight(flight) {
//const flight = document.querySelector(".selected");
//const logo = document.querySelector("#flight-spaceliner-logo");
//const overlayLogoParent = document.querySelector("#overlay-flight-spaceliner-logo");
//
//document.querySelector("#overlay-departure-arrival-time")
//    .innerHTML = document.querySelector("#departure-arrival-time").innerHTML;
//    console.log(document.querySelector("#departure-arrival-time").innerHTML);
//var img = document.createElement("img");
//    img.src = logo.src;
//    img.alt = logo.alt;
//    img.style = logo.style;
//
//overlayLogoParent.appendChild(img);

showOverlay();
}
function showOverlay() {
  document.getElementById("flight-detail-overlay").style.display = "block";
}
function hideOverlay() {
  document.getElementById("flight-detail-overlay").style.display = "none";
  document.querySelector(".selected").classList.remove("selected");
}

// End: flights.html flight details overlay
