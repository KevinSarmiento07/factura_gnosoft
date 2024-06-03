<%--
  Created by IntelliJ IDEA.
  User: kevin
  Date: 31/5/2024
  Time: 15:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="es">
<%@include file="../plantillas/head.jsp" %>
<body>

<%@include file="../plantillas/header.jsp" %>
<div class="container my-5 content">
    <h1 class="text-center">Listado de productos</h1>

    <a class="btn btn-primary my-4" href="/productos/crear">Crear producto</a>

    <table class="table my-3">
        <thead>
            <tr>
                <th>#</th>
                <th>Nombre</th>
                <th>Valor</th>
                <th>Eliminar</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="producto" items="${productos}">
            <tr id="row-${producto.id}">
                <td>${producto.id}</td>
                <td>${producto.nombre}</td>
                <td>${producto.valor}</td>
                <td><button class="btn btn-danger" onclick="eliminarProducto(${producto.id})"><i class="bi bi-trash3"></i></button> </td>
            </tr>
        </c:forEach>

        </tbody>
    </table>
</div>


<%@include file="../plantillas/footer.jsp" %>
</body>
<script type="text/javascript" src="../../recursos/funcionesProd.js" ></script>
</html>
