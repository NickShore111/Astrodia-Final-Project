import {
  roundtripInputHTML,
  multiportInputHTML,
  onewayInputHTML,
  addFlightToMultiportHTML,
} from "./formFlightsHTML.js";

const loadDatepicker = (datepickerSelector) => {
  $(function () {
    $(datepickerSelector).datepicker();
  });
};
// Load datepickers for RoundTrip
const loadDatepickerReturnAndArrival = () => {
  var dateFormat = "mm/dd/yy",
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
};

// ==============================================================
// START: index.html vanilla Javascript
const addFocus = function (element) {
  if (!element.classList.contains("focus")) {
    element.className = element.className.concat(" focus");
  }
};
const removeFocus = function (element, elements) {
  for (let others of elements) {
    if (!others.isSameNode(element)) {
      others.classList.remove("focus");
    }
  }
};
const inputFeildsContainer = document.querySelector(".input-fields-container");

window.onload = () => {
  //  inputFeildsContainer.innerHTML = roundtripInputHTML;
  loadDatepickerReturnAndArrival();
};
// Start: main-section-form-box-tabs onlick focus element
const formTabs = document.getElementsByClassName("main-section-tab");

for (const formTab of formTabs) {
  formTab.addEventListener("click", () => {
    addFocus(formTab);
    removeFocus(formTab, formTabs);
  });
}
// End: main-section-form-box-tabs onclick underline element
// Start: load form for Flights, Stays, Reviews

// Start: main-section-form-choice-tabs onclick focus element and populate input fields
const choiceTabs = document.querySelectorAll(
  ".main-section-form-choice-tabs h6"
);

for (const choiceTab of choiceTabs) {
  choiceTab.addEventListener("click", () => {
    addFocus(choiceTab);
    removeFocus(choiceTab, choiceTabs);
// Populate innerHTML from formFlightsHTML.js imports
// and reload Jquery datepicker object after selecting travel choice
    if (document.getElementById("roundtrip").classList.contains("focus")) {
//      inputFeildsContainer.innerHTML = roundtripInputHTML;
      loadDatepickerReturnAndArrival();
    } else if (document.getElementById("one-way").classList.contains("focus")) {
      inputFeildsContainer.innerHTML = onewayInputHTML;
      loadDatepicker(".departureDate");
    } else {
    // selecting multi-port adds event listeners
      inputFeildsContainer.innerHTML = multiportInputHTML;
      const multiportForm = document.getElementById("multiportForm");

      let datepickers = document
        .querySelectorAll(".datepicker")
        .forEach((datepicker) => loadDatepicker("#" + datepicker.id));

      addNextShuttleDepartureAutofillChangeEvent("arrivalPort_1");
      // create adding and removing flights functionality
      multiPortAddFlightBtnEvent();
    }
  });


  function addNextShuttleDepartureAutofillChangeEvent(targetElementId) {
    const multiportForm = document.getElementById("multiportForm");
    let shuttleNum = targetElementId[targetElementId.length - 1];
    let nextShuttleNum = Number.parseInt(shuttleNum) + 1;
    let targetDepartureId = "departurePort_" + nextShuttleNum;

    document.getElementById(targetElementId).addEventListener("change", () => {
        let targetDepartureNode = document.getElementById(targetDepartureId);
        targetDepartureNode.value = document.getElementById(targetElementId).value;
    });
  }

  function multiPortAddFlightBtnEvent() {
    document.querySelector("#add-flight-btn").addEventListener("click", () => {
        var shuttleCount = Number.parseInt(document.querySelectorAll(".search-inputs").length);
        const addFlightBtnRow = document.getElementById("add-flight-btn-row");
        let newShuttleCount = shuttleCount + 1;
        // Create the input fields with container.id = search-inputs
        var newFieldInputs = document.createElement("div");
        newFieldInputs.className = "row g-2 justify-content-center my-1 search-inputs";
        newFieldInputs.id = "multiport-shuttle" + newShuttleCount;
        newFieldInputs.innerHTML = `
            <div class="row p-0">
               <div class="col offset-1"><h6 id="shuttle-number">Shuttle ${newShuttleCount}</h6></div>
            </div> ${addFlightToMultiportHTML}`;

        // create Remove field element
        var removeFieldsBtn = document.createElement("div");
        removeFieldsBtn.className = "row offset-9 p-2 d-inline fw-bold text-primary";
        removeFieldsBtn.id = "remove-multiport-inputs";
        removeFieldsBtn.innerHTML = "Remove";
        // insert new input fields
        multiportForm.insertBefore(newFieldInputs, addFlightBtnRow);
        // update name and id for new newFieldInputs
        let newDeparture = document.getElementById("newDeparturePort");
        let newArrival = document.getElementById("newArrivalPort");
        let newDatepicker = document.getElementById("newDepartureDatepicker");
        newDeparture.id = "departurePort_" + newShuttleCount;
        newDeparture.name = "departurePort_" + newShuttleCount;
        newArrival.id = "arrivalPort_" + newShuttleCount;
        newArrival.name= "arrivalPort_" + newShuttleCount;
        newDatepicker.id = "departureDate_" + newShuttleCount;

        // insert remove fields element
        multiportForm.insertBefore(removeFieldsBtn, newFieldInputs);
        let targetElementId = "arrivalPort_" + shuttleCount;


        addNextShuttleDepartureAutofillChangeEvent(targetElementId);
    })
  }
}
// End: main-section-form-choice-tabs onclick select element
// Start: submit form action button
const searchBtn = document.getElementById("searchBtn");
searchBtn.onclick = () => {console.log("button clicked")};
searchBtn.addEventListener("click", (event) => {
    const searchForm = document.getElementById("searchForm");
    console.log(searchForm);
})
// END:  index.html vanilla Javascript
