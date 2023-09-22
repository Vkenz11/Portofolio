let register = document.getElementById("registerform")

register.addEventListener("submit", (e) => {
    e.preventDefault()

    let email = document.getElementById("email")
    let confirm = document.getElementById("confirm")
    let username = document.getElementById("username")
    let password = document.getElementById("password")
    let gender = document.getElementsByName("gender")
    let tos = document.getElementById("tos")
    let error = document.getElementById("error")
    error.innerHTML=""

    // must have '@' and ".com"
    if (!emailValidate(email.value)){
        error.innerHTML = "Wrong email format"
        return
    }

    if (email.value != confirm.value){
        error.innerHTML = "Email is different"
        return
    }

    if (username.value.length < 3 || username.value.length > 20){
        error.innerHTML = "Username is invalid (3-20 characters)"
        return
    }

    if (!passwordValidate(password.value)){
        error.innerHTML = "Password must have number and letter (> 7 characters)"
        return
    }

    if(!gender[0].checked && !gender[1].checked){
        error.innerHTML = "You must select a gender"
        return
    }

    if(!tos.checked){
        error.innerHTML = "You must agree ToS"
        return
    }

    alert("Register successful.. Back to Home page")
    window.location.href="./Main Page.html"
    
})

function emailValidate(email){
    return email.indexOf('@') != -1 && email.endsWith('.com')
}

function passwordValidate(password){
    let length = password.length

    let char = false
    let num = false
    let long = false

    if(length > 7){
        long = true
    }

    for (let i = 0; i < length; i++) {
        let ascii = password.charCodeAt(i)

        if(!isNaN(password[i])){
            num = true   
        }

        if((ascii >= 65 && ascii <= 90) || (ascii >= 97 && ascii <= 122)){
            char = true
        }
    }

    if(char && num && long){
        return true
    }   
}