// Start: flights.html flight details overlay
const flights = document.getElementsByClassName("listview-flight-details");

for (const flight of flights) {
  flight.addEventListener("click", () => {

    if (!flight.classList.contains("flight-selected")) {
      flight.className = flight.className.concat(" flight-selected");
      generateOverlay();
      showOverlay();

      for (const otherFlight of flights) {
        if (!otherFlight.isSameNode(flight)) {
          otherFlight.classList.remove("flight-selected");
        }
      }
    }
  });
}
function generateOverlay() {
const flight = document.querySelector(".flight-selected");
const logo = document.querySelector("#flight-spaceliner-logo");
const overlayLogoParent = document.querySelector("#overlay-flight-spaceliner-logo");

document.querySelector("#overlay-departure-arrival-time")
    .innerHTML = document.querySelector("#departure-arrival-time").innerHTML;
    console.log(document.querySelector("#departure-arrival-time").innerHTML);
var img = document.createElement("img");
    img.src = logo.src;
    img.alt = logo.alt;
    img.style = logo.style;

overlayLogoParent.appendChild(img);
}
var overlayX = document.getElementById("overlay-close");
overlayX.addEventListener("click", off);

function showOverlay() {
  document.getElementById("flight-detail-overlay").style.display = "block";
}

function off() {
  document.getElementById("flight-detail-overlay").style.display = "none";
  document.querySelector(".flight-selected").classList.remove("flight-selected");

}

// End: flights.html flight details overlay
