const email = document.querySelector("#email");
const firstName = document.querySelector("#firstName");
const lastName = document.querySelector("#lastName");
const password = document.querySelector("#password");
const form = document.querySelector("#signUpForm");
const matchingPassword = document.querySelector("#matchingPassword");

form.addEventListener("keyup", ()=> {
    const formBtn = document.querySelector("#submitBtn")
    if (email.value &&
    firstName.value &&
    lastName.value &&
    password.value &&
    matchingPassword.value) {
    formBtn.disabled = false;}
    else {formBtn.disabled = true;}
})