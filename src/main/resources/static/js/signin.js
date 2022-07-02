const email = document.querySelector("#email");
const password = document.querySelector("#password");
const form = document.querySelector("#loginForm");
const REGEX_EMAIL = new RegExp('^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$');
$(function () {
    $("#submitBtn").click(function (e) {
        if(!REGEX_EMAIL.test(email.value)) {
            e.preventDefault();
            $("#emailInvalid").show();
        }
    })
})
form.addEventListener("keyup", ()=> {
    const formBtn = document.querySelector("#submitBtn")
    if (email.value &&
    password.value) {
    formBtn.disabled = false;}
    else {formBtn.disabled = true;}
})