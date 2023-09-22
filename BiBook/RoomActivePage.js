let form = document.getElementById("dataroom")
const {bookingfloorpage} = require('./bookingfloorpage.js');
const {bookingpageresult} = require('./bookingpage.js');

form.addEventListener("submit", (e) => {
    e.preventDefault()

    let date = bookingpageresult.dateresult
    let time = bookingpageresult.timeresult
    let floor = bookingfloorpage.floorresult

    let dateid = document.getElementById("date")
    let timeid = document.getElementById("time")
    let floorid = document.getElementById("floor")

    dateid.innerHTML = date
    timeid.innerHTML = time
    floorid.innerHTML = floor
    
})