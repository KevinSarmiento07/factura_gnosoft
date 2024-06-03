<%--
  Created by IntelliJ IDEA.
  User: kevin
  Date: 1/6/2024
  Time: 14:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="es">
<%@include file="../plantillas/head.jsp" %>
<body>

<%@include file="../plantillas/header.jsp" %>
<div class="container my-5 content">
    <h1 class="text-center">Listado de facturas</h1>

    <a class="btn btn-primary my-4" href="/facturas/crear">Nueva Factura</a>

    <table class="table my-3">
        <thead>
        <tr>
            <th>#</th>
            <th>Nombre del Cliente</th>
            <th>Fecha</th>
            <th>Iva</th>
            <th>Subtotal</th>
            <th>Total</th>
            <th>Detalles</th>
            <th>Eliminar</th>
            <th>Editar</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="factura" items="${facturas}">
            <tr id="row-${factura.id}">
                <td>${factura.id}</td>
                <td>${factura.nombreCliente}</td>
                <td>${factura.fecha}</td>
                <td>${factura.iva}</td>
                <td>${factura.subtotal}</td>
                <td>${factura.total}</td>
                <td><button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#detalle-${factura.id}"><i class="bi bi-card-list"></i></button></td>
                <td><button class="btn btn-danger" onclick="eliminarFactura(${factura.id})"><i class="bi bi-trash3"></i></button> </td>
                <td><a class="btn btn-info"  href="/facturas/editar?id=${factura.id}"><i class="bi bi-pencil"></i></a> </td>
            </tr>
        </c:forEach>

        </tbody>
    </table>
</div>



<c:forEach var="factura" items="${facturas}">
    <div class="modal fade" id="detalle-${factura.id}" tabindex="-1" aria-labelledby="detalle-${factura.id}" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered modal-lg">
            <div class="modal-content">
                <div class="modal-header text-center">
                    <h1 class="modal-title fs-5 text-center" style="width: -webkit-fill-available;" >DETALLE DE LA FACTURA</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <table class="table my-3">
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>Nombre del Producto</th>
                            <th>Precio por unidad</th>
                            <th>Cantidad</th>
                            <th>Valor</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="item" items="${factura.items}">
                            <tr id="row-${item.id}">
                                <td>${item.id}</td>
                                <td>${item.producto.nombre}</td>
                                <td>${item.producto.valor}</td>
                                <td>${item.cantidad}</td>
                                <td>${item.valor}</td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td colspan="4" class="fw-bold">Subtotal</td>
                            <td class="fw-bold">${factura.subtotal}</td>
                        </tr>
                        <tr>
                            <td colspan="4" class="fw-bold">Iva</td>
                            <td class="fw-bold">${factura.iva}</td>
                        </tr>
                        <tr>
                            <td colspan="4" class="fw-bold">Total</td>
                            <td class="fw-bold">${factura.total}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</c:forEach>





<%@include file="../plantillas/footer.jsp" %>
</body>
<script type="text/javascript" src="../../recursos/funcionesFact.js" ></script>
</html>
