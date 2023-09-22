let register = document.getElementById("bookingfloor")

let bookingfloorpage
let floorresult
let htmlresult

register.addEventListener("submit", (e) => {
    e.preventDefault()

    let floor = document.getElementsByName("floor")
    let error = document.getElementById("error")
    error.innerHTML=""

    if(!floor[0].checked && ! floor[1].checked && ! floor[2].checked
        && ! floor[3].checked && ! floor[4].checked && ! floor[5].checked
        && ! floor[6].checked && ! floor[7].checked && ! floor[8].checked
        && ! floor[9].checked && ! floor[10].checked && ! floor[11].checked
        && ! floor[12].checked && ! floor[13].checked && ! floor[14].checked
        && ! floor[15].checked && ! floor[16].checked && ! floor[17].checked
        && ! floor[18].checked){
        error.innerHTML = "You must select floor"
        return
    }

    let temp
    for (i = 0; i < floor.length; i++) {
        if(floor[i].checked)
            temp = i;
    }


    temp = temp + 1

    let html1 = "./Room"
    let html2 = ".html"

    let htmltemp = html1.concat(temp)
    let htmltarget = htmltemp.concat(html2)

    bookingfloorpage = {
        floorresult : temp,
        htmlresult : htmltarget
    }

    // alert("Submit Successful")
    window.location.href=htmltarget
    
})

module.exports = {bookingfloorpage}
