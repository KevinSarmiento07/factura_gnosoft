function eliminarProducto(id){
    fetch('/productos?id=' + id, {
        method: 'DELETE',
    }).then((res) =>{
        let message;
        let icon;
        if(res.status == 204){
            message = "Producto Eliminado";
            icon = "success";
            document.getElementById("row-" +id).remove();
        }else{
            message = "El producto no existe en la base de datos";
            icon = "error";
        }
        Swal.fire({
            position: "center",
            icon: icon,
            title: message,
            showConfirmButton: false,
            timer: 1500
        });
    });
}

window.onload = function() {
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.has('success') && urlParams.get('success') === 'true') {
        Swal.fire({
            position: "center",
            icon: "success",
            title: "Producto guardado correctamente",
            showConfirmButton: false,
            timer: 1500
        });
    }
};