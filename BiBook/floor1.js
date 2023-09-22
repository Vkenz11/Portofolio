let floor1 = document.getElementById("floor1")

floor1.addEventListener("submit", (e) => {
    e.preventDefault()

    let roomfloor1 = document.getElementsByName("roomfloor1")

    let error = document.getElementById("error")
    error.innerHTML=""

    if(!roomfloor1[0].checked && ! roomfloor1[1].checked && ! roomfloor1[2].checked
        && ! roomfloor1[3].checked && ! roomfloor1[4].checked && ! roomfloor1[5].checked
        && ! roomfloor1[6].checked && ! roomfloor1[7].checked && ! roomfloor1[8].checked
        && ! roomfloor1[9].checked && ! roomfloor1[10].checked){
        error.innerHTML = "You must select room"
        return
    }
    
    window.location.href="./FacilityPage.html"
    
})