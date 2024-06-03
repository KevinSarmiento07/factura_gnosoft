function eliminarFactura(id){
    console.log(id);
    console.log("Eliminar Factura");
    fetch('/facturas?id=' + id, {
        method: 'DELETE',
    }).then((res) =>{
        let message;
        let icon;
        if(res.status == 204){
            message = "Factura Eliminada";
            icon = "success";
            document.getElementById("row-" +id).remove();
            document.getElementById("detalle-" +id).remove();
        }else{
            message = "La factura no existe no existe en la base de datos";
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

function handlerUpdate(e, id){
    e.preventDefault();
    console.log(e);
    console.log(id);
    document.getElementById('productosId').value = productosId;
    document.getElementById('productosCantidad').value = productosCantidad;
    let formData = new FormData();
    formData.append("id", id);
    formData.append("nombreCliente",document.getElementById("nombreCliente").value);
    formData.append("iva",document.getElementById("iva").value);
    formData.append("total",document.getElementById("total").value);
    formData.append("fecha",document.getElementById("fecha").value);
    formData.append("subtotal",document.getElementById("subtotal").value);
    formData.append("productosId",document.getElementById("productosId").value);
    formData.append("productosCantidad",document.getElementById("productosCantidad").value);
    fetch("/facturas/editar", {
        method: "PUT",
        body: formData,

    }).then((res) =>{
        console.log("todo salio bien");
        if(res.status === 200){

            Swal.fire({
                position: "center",
                icon: "success",
                title: "Producto guardado correctamente",
                showConfirmButton: false,
                timer: 1500
            });
            window.location.href = "/facturas";
        }
    })
}

let productosId = [];
let productosNombre = [];
let productosValor = [];
let productosCantidad = [];
function addProducto(){
    console.log("Add Producto");

    let select = document.getElementById("select-producto");
    let selectOption = select.options[select.selectedIndex];

    let productoNombre = selectOption.getAttribute('data-nombre');
    let productoValor = selectOption.getAttribute('data-valor');
    let productoId = select.value;

    if(document.getElementById("factura-row-" +productoId) === null){
        console.log("entro en if")
        productosId.push(Number(productoId));
        productosNombre.push(productoNombre);
        productosValor.push(Number(productoValor));
        productosCantidad.push(1);
        addRow();
    }else{
        console.log("entro en false");
        console.log(productosId);
        console.log(productoId)
        let position = productosId.indexOf(Number(productoId));
        productosCantidad[position]++;
        addRow();
    }

    select.selectedIndex = 0;
}

function addRow(){
    const tableBody = document.getElementById("factura-table-body");
    tableBody.innerHTML = '';
    let subtotal = 0;
    const ivaInput = document.getElementById('iva').value;
    let total = 0;
    for(let i = 0; i < productosNombre.length; i++){
        subtotal += productosValor[i]*productosCantidad[i];
        let valorIva = (productosValor[i]*productosCantidad[i]*ivaInput)/100;
        total += valorIva + (productosValor[i]*productosCantidad[i]);

        const newRow = document.createElement("tr");
        newRow.id="factura-row-"+productosId[i];

        const cellNombre = document.createElement("td");
        cellNombre.id="factura-cell-nombre-" + productosId[i];
        cellNombre.textContent = productosNombre[i];

        const cellPrecio = document.createElement("td");
        cellPrecio.id="factura-cell-precio-" + productosId[i];
        cellPrecio.textContent = productosValor[i];

        const cellCantidad = document.createElement("td");
        cellCantidad.id = "factura-cell-cantidad-" + productosId[i];
        const inputCantidad = document.createElement("input");
        inputCantidad.type = "number";
        inputCantidad.value = productosCantidad[i];
        inputCantidad.className ="form-control";
        inputCantidad.id="factura-cell-input-cantidad-" + productosId[i];
        inputCantidad.readOnly = true;
        cellCantidad.appendChild(inputCantidad);


        const cellTotalCantidad = document.createElement("td");
        cellTotalCantidad.id="factura-cell-totalcantidad-" + productosId[i];
        cellTotalCantidad.textContent = productosValor[i]*productosCantidad[i];


        const cellEliminar = document.createElement("td");
        cellEliminar.id = "factura-cell-eliminar-" + productosId[i];
        const buttonEliminar = document.createElement("button");
        buttonEliminar.className ="btn btn-danger";
        buttonEliminar.type = "button";
        buttonEliminar.id="factura-cell-button-eliminar-" + productosId[i];
        buttonEliminar.textContent = "Eliminar producto";
        buttonEliminar.onclick = function() {
            eliminarFila(productosId[i]);
        };
        cellEliminar.appendChild(buttonEliminar);


        newRow.appendChild(cellNombre);
        newRow.appendChild(cellPrecio);
        newRow.appendChild(cellCantidad);
        newRow.appendChild(cellTotalCantidad);
        newRow.appendChild(cellEliminar);
        tableBody.appendChild(newRow);
    }

    if(productosId.length > 0){
        addInformationTable(tableBody,subtotal,ivaInput,total);
    }


}

function addInformationTable(tableBody,subtotal,ivaInput,total){
    const rowSubtotal = document.createElement("tr");
    rowSubtotal.id="factura-row-subtotal";

    const cellSubtotal = document.createElement("td");
    cellSubtotal.colSpan = 4;
    cellSubtotal.className="fw-bold";
    cellSubtotal.id="factura-cell-subtotal";
    cellSubtotal.textContent = "Subtotal";

    const cellSubTotalValue = document.createElement("td");
    cellSubTotalValue.id="factura-cell-iva";
    cellSubTotalValue.className="fw-bold";
    cellSubTotalValue.textContent = subtotal.toFixed(2);

    rowSubtotal.appendChild(cellSubtotal);
    rowSubtotal.appendChild(cellSubTotalValue);
    tableBody.appendChild(rowSubtotal);

    document.getElementById('subtotal').value = subtotal.toFixed(2);
    document.getElementById('total').value = total.toFixed(2);

    const rowIva = document.createElement("tr");
    rowIva.id="factura-row-iva";

    const cellIva = document.createElement("td");
    cellIva.colSpan = 4;
    cellIva.className="fw-bold";
    cellIva.id="factura-cell-iva";
    cellIva.textContent = "Iva";

    const cellIvaValue = document.createElement("td");
    cellIvaValue.id="factura-cell-iva";
    cellIvaValue.className="fw-bold";
    cellIvaValue.textContent = ivaInput;

    rowIva.appendChild(cellIva);
    rowIva.appendChild(cellIvaValue);

    tableBody.appendChild(rowIva);


    const rowTotal = document.createElement("tr");
    rowTotal.id="factura-row-total";

    const cellTotal = document.createElement("td");
    cellTotal.colSpan = 4;
    cellTotal.className="fw-bold";
    cellTotal.id="factura-cell-total";
    cellTotal.textContent = "Total";

    const cellTotalValue = document.createElement("td");
    cellTotalValue.id="factura-cell-total";
    cellTotalValue.className="fw-bold";
    cellTotalValue.textContent = total.toFixed(2);

    rowTotal.appendChild(cellTotal);
    rowTotal.appendChild(cellTotalValue);

    tableBody.appendChild(rowTotal);
}

function handlerIva(){
    console.log('hola');
    addRow();
}

function eliminarFila(id){
    console.log('Eliminar fila');
    let position = productosId.indexOf(id);
    console.log(position);
    productosId.splice(position, 1);
    productosValor.splice(position,1);
    productosCantidad.splice(position,1);
    productosNombre.splice(position,1);

    addRow();
    console.log(id);
}

function handlerSubmit() {
    document.getElementById('productosId').value = productosId;
    document.getElementById('productosCantidad').value = productosCantidad;
};


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


