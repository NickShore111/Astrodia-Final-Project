const loadDatepicker = (datepickerSelector) => {
    $(datepickerSelector).datepicker();
};

const loadDatepickers = () => {
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
};

// ==============================================================
// START: home.html vanilla Javascript
// helper functions
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
const displayForm = function (showFormId) {
    const forms = document.querySelectorAll(".form-container");
    for (let form of forms) {
        if (form.id != showFormId) {
            form.style.display = "none";
        } else {
            form.style.display = "block";
        }
        loadDatepickers();
    }
}
const inputFeildsContainer = document.querySelector(".input-fields-container");

window.onload = () => {
  loadDatepickers();
};
// Start: main-section-form-box-tabs onclick focus element
// change form by user selection (region, port, spaceliner)
const formTabs = document.querySelectorAll(".search-by-tab")
for (const formTab of formTabs) {
  formTab.addEventListener("click", (event) => {
    addFocus(formTab);
    removeFocus(formTab, formTabs);
    let showFormId = event.target.id.split("-")[0] + "-form-container";

    displayForm(showFormId);

  });
}
// End: main-section-form-box-tabs onclick underline element
// Start: main-section-form-choice-tabs onclick focus element and populate input fields
const choiceTabs = document.querySelectorAll(".form-choice");

for (const choiceTab of choiceTabs) {
  choiceTab.addEventListener("click", (event) => {
    addFocus(choiceTab);
    removeFocus(choiceTab, choiceTabs);

// Modify search inputs
    if (event.target.id == "roundtrip") {
        console.log("roundtrip selected");
    } else {
        console.log("oneway selected");
    }
    })
}
// END:  index.html vanilla Javascript
