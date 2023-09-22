let register = document.getElementById("registerform")

register.addEventListener("submit", (e) => {
    e.preventDefault()

    let NIM = document.getElementById("nim")
    let password = document.getElementById("password")
    error.innerHTML=""

    // must have '@' and ".com"
    if (!validNIM(NIM.value)){
        error.innerHTML = "NIM must be between 8 - 10 characters"
        return
    }

    if (!passwordValidate(password.value)){
        error.innerHTML = "Password must have number and letter (> 7 characters)"
        return
    }

    alert("Login Successful")
    window.location.href="./BiBookMainPage.html"
    
})

function validNIM(NIM){
    let length = NIM.length
    let isNum = true
    let isLength = false
  
    if(length >= 8 && length <= 10){
      isLength = true
    }
  
    for (let i = 0; i < length; i++) {
      let charCode = NIM.charCodeAt(i)
  
      if((charCode >= 65 && charCode <= 90) || (charCode >= 97 && charCode <= 122)){
        isNum = false
      }
      
    }
  
    if(isLength && isNum){
      return true
    }
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