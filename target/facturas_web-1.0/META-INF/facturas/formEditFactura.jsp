<%--
  Created by IntelliJ IDEA.
  User: kevin
  Date: 2/6/2024
  Time: 09:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="es">
<%@include file="../plantillas/head.jsp" %>
<head>
    <title>Title</title>
</head>
<body>
<%@include file="../plantillas/header.jsp" %>
<div class="container my-5 content">
    <h1 class="text-center">Editar Factura</h1>

    <form onsubmit="handlerUpdate(event, ${factura.id})" >
        <div class="mb-3">
            <label for="nombreCliente" class="form-label">Nombre del Cliente</label>
            <input type="text" class="form-control" id="nombreCliente" name="nombreCliente" value="${factura.nombreCliente}" readonly required>
        </div>
        <div class="row g-3 align-items-center">
            <div class="col-6 mb-3">
                <label for="fecha" class="form-label">Fecha</label>
                <input type="date" class="form-control" id="fecha" name="fecha" value="${factura.fecha}" required>
            </div>
            <div class="col-6 mb-3">
                <label for="iva" class="form-label">Iva %</label>
                <input type="number" step="0.01" class="form-control" id="iva" name="iva" value="${factura.iva}" onchange="handlerIva()" required>
            </div>
            <div class="col-6">
                <label for="select-producto" class="form-label">Productos</label>
                <select class="form-select" aria-label="Default select example" id="select-producto" onchange="addProducto()">
                    <option selected>Selecciona una opción</option>
                    <c:forEach var="producto" items="${productos}">
                        <option id="producto-id" data-nombre="${producto.nombre}" data-valor="${producto.valor}" value="${producto.id}" >${producto.nombre}</option>
                    </c:forEach>


                </select>
            </div>

        </div>
        <table class="table my-5">
            <thead>
            <tr>
                <th scope="col">Nombre del producto</th>
                <th scope="col">Precio</th>
                <th >Cantidad</th>
                <th scope="col">Valor</th>
                <th scope="col">Eliminar</th>
            </tr>
            </thead>
            <tbody id="factura-table-body">

            </tbody>
        </table>


        <input type="hidden" id="productosId" name="productosId">
        <input type="hidden" id="productosCantidad" name="productosCantidad">
        <input type="hidden" id="subtotal" name="subtotal">
        <input type="hidden" id="total" name="total">


        <button type="submit" class="btn btn-primary my-5">Guardar</button>
    </form>

</div>

<%@include file="../plantillas/footer.jsp" %>
</body>

<script type="text/javascript" src="../../recursos/funcionesFact.js" ></script>
<script>
    var pathname = window.location.pathname;
    if(pathname === "/facturas/editar"){
        <c:forEach var="item" items="${factura.items}">
        productosId.push(Number(${item.producto.id}));
        productosNombre.push("${item.producto.nombre}");
        productosCantidad.push(Number(${item.cantidad}));
        productosValor.push(Number(${item.producto.valor}));
        </c:forEach>
        addRow();
    }
</script>
</html>
