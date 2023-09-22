let floor16 = document.getElementById("floor16")

floor16.addEventListener("submit", (e) => {
    e.preventDefault()

    let roomfloor = document.getElementsByName("roomfloor16")

    let error = document.getElementById("error")
    error.innerHTML=""

    if(!roomfloor[0].checked && ! roomfloor[1].checked && ! roomfloor[2].checked
        && ! roomfloor[3].checked && ! roomfloor[4].checked && ! roomfloor[5].checked
        && ! roomfloor[6].checked && ! roomfloor[7].checked && ! roomfloor[8].checked
        && ! roomfloor[9].checked && ! roomfloor[10].checked){
        error.innerHTML = "You must select room"
        return
    }
    
    window.location.href="./FacilityPage.html"
    
})