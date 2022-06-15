document.getElementById("signInNavLink").addEventListener("click", (event) => {
  document.getElementById("signInPopup").style.display = "block";
  event.stopPropagation();
});
$(window).click(function () {
  document.getElementById("signInPopup").style.display = "none";
});