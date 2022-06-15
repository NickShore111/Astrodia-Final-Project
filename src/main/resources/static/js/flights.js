// Start: flights.html flight details overlay
const flights = document.getElementsByClassName("listview-flight-details");

for (const flight of flights) {
  flight.addEventListener("click", () => {
    on();
    if (!flight.classList.contains("flight-selected")) {
      flight.className = flight.className.concat(" flight-selected");
      // TODO: run method createFlightInlay using flight details
      // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
      for (const otherFlight of flights) {
        if (!otherFlight.isSameNode(flight)) {
          otherFlight.classList.remove("flight-selected");
        }
      }
    }
  });
}

var overlayX = document.getElementById("overlay-close");
overlayX.addEventListener("click", off);

function on() {
  document.getElementById("flight-detail-overlay").style.display = "block";
}

function off() {
  document.getElementById("flight-detail-overlay").style.display = "none";
  document.querySelector(".flight-selected").classList.remove("flight-selected");

}

// End: flights.html flight details overlay
