<%--
  Created by IntelliJ IDEA.
  User: kevin
  Date: 31/5/2024
  Time: 22:19
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
    <h1 class="text-center">Crear un producto</h1>

    <form method="post" action="/productos/crear">
        <div class="mb-3">
            <label for="nombre" class="form-label">Nombre del producto</label>
            <input type="text" class="form-control" id="nombre" name="nombre" required>
        </div>
        <div class="mb-3">
            <label for="valor" class="form-label">Valor</label>
            <input type="number" step="0.01"  class="form-control" id="valor" name="valor" required>
        </div>

        <button type="submit" class="btn btn-primary btn-lg">Guardar</button>
    </form>

</div>

<%@include file="../plantillas/footer.jsp" %>
</body>
<script type="text/javascript" src="../../recursos/funcionesProd.js" ></script>
</html>
