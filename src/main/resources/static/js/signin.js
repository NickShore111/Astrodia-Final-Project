const email = document.querySelector("#email");
const password = document.querySelector("#password");
const form = document.querySelector("#loginForm");

form.addEventListener("keyup", ()=> {
    const formBtn = document.querySelector("#submitBtn")
    if (email.value &&
    password.value) {
    formBtn.disabled = false;}
    else {formBtn.disabled = true;}
})