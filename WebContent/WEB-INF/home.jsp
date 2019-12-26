<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<script src="js/FileUpload.js"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<title>Welcome to the home page of the subtitle</title>
</head>
<body>

<h1>Home</h1>
<p><c:if test="${!empty message }">${message }</c:if> </p>
<form action="homeServlet" method="post" class="form-example" enctype="multipart/form-data">
  <div class="form-example">
    <label for="fichier">File to send : </label>
    <input type="file" name="fichier" id="fichier" onchange="handleFiles(this.files)" />
  </div>
  <div class="form-example">
    <input type="submit" value="Create">
  </div>
</form>
</body>
</html>

