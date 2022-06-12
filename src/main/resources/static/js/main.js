import {
  roundtripInputHTML,
  multiportInputHTML,
  onewayInputHTML,
  addFlightToMultiportHTML,
} from "./formFlightsHTML.js";

const loadDatepickerDepature = (departId) => {
  $(function () {
    $(departId).datepicker();
  });
};
const loadDatepickerReturnAndArrival = () => {
  var dateFormat = "mm/dd/yy",
    from = $("#departDate")
      .datepicker({
        defaultDate: "+2d",
        minDate: 0,
        maxDate: "+3M",
        showAnim: "fadeIn",
      })
      .on("change", function () {
        to.datepicker("option", "minDate", getDate(this));
      }),
    to = $("#returnDate")
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
// Initial load datepicker object
$(document).ready(function () {
  loadDatepickerReturnAndArrival();
});
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
  inputFeildsContainer.innerHTML = roundtripInputHTML;
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
      inputFeildsContainer.innerHTML = roundtripInputHTML;
      loadDatepickerReturnAndArrival();
    } else if (document.getElementById("one-way").classList.contains("focus")) {
      inputFeildsContainer.innerHTML = onewayInputHTML;
      loadDatepickerDepature("#departDate");
    } else {
    // selecting multi-port adds event listeners
      inputFeildsContainer.innerHTML = multiportInputHTML;
      const multiportForm = document.getElementById("multiportForm");

      let datepickers = document
        .querySelectorAll(".datepicker")
        .forEach((datepicker) => loadDatepickerDepature("#" + datepicker.id));

      addNextShuttleDepartureAutofillChangeEvent(multiportForm.arrivalPort_1);
      // create adding and removing flights functionality
      multiPortAddFlightBtnEvent();
    }
  });


  function addNextShuttleDepartureAutofillChangeEvent(input_arrival) {
    const multiportForm = document.getElementById("multiportForm");
        let lastIndex = Number.parseInt(input_arrival.name.length-1);
        let shuttleNum = input_arrival.name[lastIndex];

        let nextShuttleNum = Number.parseInt(shuttleNum) + 1;
        let targetDepartureId = "departurePort_" + nextShuttleNum;
        var targetDepartureNode = document.getElementById(targetDepartureId);
        input_arrival.addEventListener("change", () => {
        targetDepartureNode.value = input_arrival.value;
  });
  }

  function multiPortAddFlightBtnEvent() {
    document.querySelector("#add-flight-btn").addEventListener("click", () => {
        var shuttleCount = document.querySelectorAll(".search-inputs").length;
        const addFlightBtnRow = document.getElementById("add-flight-btn-row");

        // Create the input fields with container.id = search-inputs
        var newFieldInputs = document.createElement("div");
        newFieldInputs.className = "row g-2 justify-content-center my-1 search-inputs";
        newFieldInputs.id = "multiport-shuttle" + shuttleCount + 1;
        newFieldInputs.innerHTML = `
            <div class="row p-0">
               <div class="col offset-1"><h6 id="shuttle-number">Shuttle ${shuttleCount+1}</h6></div>
            </div> ${addFlightToMultiportHTML}`;

        // create Remove field element
        var removeFieldsBtn = document.createElement("div");
        removeFieldsBtn.className = "row offset-9 p-2 d-inline fw-bold text-primary";
        removeFieldsBtn.id = "remove-multiport-inputs";
        removeFieldsBtn.innerHTML = "Remove";
        // insert new input fields
        multiportForm.insertBefore(newFieldInputs, addFlightBtnRow);
        // insert remove fields element
        multiportForm.insertBefore(removeFieldsBtn, newFieldInputs);

    })
  }
}
// clear signin popup window with click outside element
document.getElementById("signInNavLink").addEventListener("click", (event) => {
  document.getElementById("signInPopup").style.display = "block";
  event.stopPropagation();
});
$(window).click(function () {
  document.getElementById("signInPopup").style.display = "none";
});

// End: main-section-form-choice-tabs onclick select element
// END:  index.html vanilla Javascript
