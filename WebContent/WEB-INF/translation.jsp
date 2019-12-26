<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.ocsubtitles.beans.SubtitleTranslateBean"%>
<link href="css/global.css" rel="stylesheet" type="text/css">
<script src="js/UpdateSubtitle.js"></script>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Update subtitles</title>
</head>
<body>
<button class="overlayButton" onclick="topFunction()" id="topButton" title="Go to top">Top</button>
<!-- <button class ="overlayButton" onclick="update()" id="updateButton" title="Update">Update</button> -->
<c:set var="number" value="0" scope="page" />
<c:if test="${translatedSub !=null}">

 <form action="" method="post" class="form-example">
<c:forEach var="transSub" items="${translatedSub}">  
<c:set var="number" value="${number+1 }" scope="page" /> 
  <div class="form-update">
    <label class="labelUpdate">Subtitle number : </label>
<%--     <label class="constValue" name="number<c:out value="${number}"/>"><c:out value="${transSub.number}"/></label> --%>
    <input type="text" disabled name="number<c:out value="${number}"/>" value="<c:out value="${transSub.number}"/>">
  </div>
  <div class="form-update">
    <label class="labelUpdate">Start time : </label>
    <label class="constValue"><c:out value="${transSub.start}"/></label>
  </div>
    <div class="form-update">
    <label class="labelUpdate">End time : </label>
    <label class="constValue"><c:out value="${transSub.end}"/></label>
  </div>
    <div class="form-update">
    <label class="labelUpdate">Original text : </label>
    <label class="constValue"><c:out value="${transSub.text}"/></label>
  </div>
   <div class="form-update">
    <label class="labelUpdate">Translation : </label>
    <input type="text" name="translation<c:out value="${number}"/>" value="<c:out value="${transSub.translation}"/>">
  </div>
  <br>

</c:forEach>
<div class="overlayButton" id="updateButton">
    <input type="submit" value="Update" class="overlayButton"  id="updateButton">
  </div>
  </form> 
</c:if>

</body>
</html>