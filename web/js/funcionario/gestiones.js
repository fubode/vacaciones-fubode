$(document).ready(function () {
    document.getElementById('gestiones').onclick = mostrarGestiones;    
});
function mostrarGestiones(){
    var x = document.getElementById("tablaGestiones");
    var div = document.getElementById('gestiones');
    if (x.style.display === "none") {
        x.style.display = "block";
        div.classList.replace("fa-plus", "fa-minus");
        console.log('mostrar')
    } else {
        console.log('oculto')
        div.classList.replace("fa-minus", "fa-plus");
        x.style.display = "none";
    }
}