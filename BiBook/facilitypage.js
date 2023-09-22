let register = document.getElementById("facility")

register.addEventListener("submit", (e) => {
    e.preventDefault()

    let mic = document.getElementsByName("mic")
    let cam = document.getElementsByName("cam")
    let error = document.getElementById("error")
    error.innerHTML=""

    if(!mic[0].checked && !cam[0].checked) {
        error.innerHTML = "Pick at least one"
        return
    }

    alert("Submit Successful")
    window.location.href="./RoomActivePage.html"
    
})