let floor6 = document.getElementById("floor6")

floor6.addEventListener("submit", (e) => {
    e.preventDefault()

    let roomfloor = document.getElementsByName("roomfloor6")

    let error = document.getElementById("error")
    error.innerHTML=""

    if(!roomfloor[0].checked && ! roomfloor[1].checked && ! roomfloor[2].checked
        && ! roomfloor[3].checked ){
        error.innerHTML = "You must select room"
        return
    }
    
    window.location.href="./FacilityPage.html"
    
})