const forms = document.querySelectorAll(".form-container");
window.onload = function () {
    for (let form of forms) {
        console.log(form.id);
        let depDateId = "#"+form.id.concat("-departureDate");
        let arrDateId = "#"+form.id.concat("-arrivalDate");

        loadDatepickers(depDateId, arrDateId);
    }
}
const loadDatepickers = (depId, arrId) => {
  var dateFormat = "mm/dd/yyyy",
    from = $(depId)
      .datepicker({
        defaultDate: "+2d",
        minDate: 0,
        maxDate: "+3M",
        showAnim: "fadeIn",
      })
      .on("change", function () {
        to.datepicker("option", "minDate", getDate(this));
      }),
    to = $(arrId)
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
$(function () {
    $(".searchBy-tab").click(function (event) {
        if (!event.target.classList.contains("focus")) {
            $("#region-tab").toggleClass("focus");
            $("#port-tab").toggleClass("focus");

            $(".port-content").toggle();
            $(".region-content").toggle();
        }
    })
})

