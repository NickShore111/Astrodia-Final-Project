$(function () {
    $(".datepicker").datepicker();
  });
// fire once document content loaded
document.addEventListener("DOMContentLoaded", function(){
    const allFlights = document.querySelectorAll(".admin-flight-tr");
//        console.log(allFlights);
        const spaceliners = document.querySelectorAll(".spaceliners input");
        console.log(spaceliners);
});


window.addEventListener("click", () => {
    fetch("http://localhost:8080/astrodia/api/flights")
        .then(response => response.text()).then(data => console.log(data));

})

