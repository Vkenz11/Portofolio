let register = document.getElementById("bookingpage")

let bookingpageresult
let dateresult
let timeresult
let durationresult


register.addEventListener("submit", (e) => {
    e.preventDefault()

    let date = document.getElementById("datetime")
    let time = document.getElementById("timehour")
    let duration = document.getElementById("lang")
    let error = document.getElementById("error")
    error.innerHTML=""

    if(!date.value) {
        error.innerHTML = "Input Date"
        return
    }

    if(!time.value){
        error.innerHTML = "Input Time"
        return
    }
    
    if(duration.value == 1){
        error.innerHTML = "Choose Duration"
        return
    }

    bookingpageresult = {
        dateresult : date,
        timeresult : time,
        durationresult : duration
    }

    alert("Submit Successful")
    window.location.href="./BookingFloorPage.html"
    
})

module.exports = {bookingpageresult}