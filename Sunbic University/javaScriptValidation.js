let form = document.getElementById("registerform")

form.addEventListener("submit", (e) => {
  e.preventDefault()

  let firstname = document.getElementById("firstname")
  let lastname = document.getElementById("lastname")
  var gender = document.getElementById("gender")
  let phone = document.getElementById("phonenumber")
  let email = document.getElementById("email")
  let parentfirstname = document.getElementById("parentfirstname")
  let parentlastname = document.getElementById("parentlastname")
  let parentphone = document.getElementById("parentnumber")
  var course = document.getElementById("course")
  let date = document.getElementById("datetime")
  let toc = document.getElementById("tos")
  error.innerHTML = ""

  // firstname (length 3 - 50)
  if(firstname.value.length < 3 || firstname.value.length > 50){
    error.innerHTML = "First name is invalid (3-50 characters)"
    return
  }

 // lastname (length 3 - 50)
  if(lastname.value.length < 3 || lastname.value.length > 50){
    error.innerHTML = "Last name is invalid (3-50 characters)"
    return
  }

  // gender
  if(gender.value == "ChooseGender"){
    error.innerHTML = "Please choose a gender"
    return
  }

  // phone number (5-13 characters) (numbers only)
  if(!validPhone(phone.value)){
    error.innerHTML = "Phone number is invalid (5 - 13 characters)(numbers only)"
    return
  }

  // email: "@" and ".com"
  if(!validEmail(email.value)){
    error.innerHTML = "Email is invalid"
    return
  }

  // parentfirstname (length 3 - 50)
  if(parentfirstname.value.length < 3 || parentfirstname.value.length > 50){
    error.innerHTML = "Parent's/Guardian's First name is invalid (2-50 characters)"
    return
  }
  
  // parentlastname (length 3 - 50)
  if(parentlastname.value.length < 3 || parentlastname.value.length > 50){
    error.innerHTML = "Parent's/Guardian's Last name is invalid (2-50 characters)"
    return
  }

  // parent phone number
  if(!validPhone(parentphone.value)){
    error.innerHTML = "Parent's/Guardian's Phone Number is Invalid"
    return
  }

  // choose course
  if(course.value == "ChooseCourse"){
    error.innerHTML = "Please choose a course"
    return
  }

  // date birth
  if(!date.value){
    error.innerHTML = "Fill the date birth"
    return
  }

  // Terms and Conditions
  if(!toc.checked){
    error.innerHTML = "You must agree to the Terms of Service"
    return
  }

  let confirmResult = false;
  confirmResult = confirm("Are you sure you want to submit?")
  if(confirmResult == true){
      alert("Register Successful")
      window.location.href="./Main page.html"
  }
  else{
      alert("Register Cancelled")
      window.location.href="./form page.html"
  }

})

function validPhone(phone){
  let length = phone.length
  let isNum = true
  let isLength = false

  if(length > 5 && length < 13){
    isLength = true
  }

  for (let i = 0; i < length; i++) {
    let charCode = phone.charCodeAt(i)

    if((charCode >= 65 && charCode <= 90) || (charCode >= 97 && charCode <= 122)){
      isNum = false
    }
    
  }

  if(isLength && isNum){
    return true
  }
}

function validEmail(email){
  return email.indexOf('@') != -1 && email.endsWith('.com')
}