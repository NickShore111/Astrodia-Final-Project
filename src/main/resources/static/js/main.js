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
      const multiPortform = document.getElementById("multiPortForm");
      let datepickers = document
        .querySelectorAll(".datepicker")
        .forEach((datepicker) => loadDatepickerDepature("#" + datepicker.id));
      // changes shuttle2 departing from to shuttle 1 going to on change
      multiPortform.arrivalPort_1.addEventListener("change", () => {
        multiPortform.departurePort_2.value = form.arrivalPort_1.value;
      });
      // create adding and removing flights functionality
      multiPortAddFlightBtn();
    }
  });

  function multiPortAddFlightBtn() {
  const addAnotherFlight = document.querySelectorAll("#add_flight");
  let shuttleCount = document.querySelectorAll("#search-inputs").length;
        for (const btn of addAnotherFlight) {
            btn.addEventListener("click", () => {
                console.log(shuttleCount);
                console.log(multiPortForm.lastElementChild);
                var newFieldInputs = document.createElement("div");
                newFieldInputs.className = "row g-2 justify-content-center my-1";
                newFieldInputs.id = "search-inputs";
                newFieldInputs.innerHTML = addFlightToMultiportHTML;

                multiPortForm.insertBefore(newFieldInputs, multiPortForm.children[shuttleCount]);
                console.log(multiPortForm.children[shuttleCount-1]);

            })
        }
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
