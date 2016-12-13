<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ME-E-E-E-E-AL!!!!</title>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h2>Here could be your advertisement</h2>
<table border="1">
    <thead>
    <tr>
        <th width="100">ID</th>
        <th width="180">DateTime</th>
        <th width="180">Description</th>
        <th width="120">Calories</th>
    </tr>
    </thead>
    <c:forEach var="meal" items="${meals}">
        <tr>
            <tr style="color:${meal.isExceed()?"red":"green"}">
            <td><c:out value="${meal.getId()}"/> </td>
            <td>
                <c:out value="${meal.getDate()}"/> <c:out value="${meal.getTime()}"/>
            </td>
            <td>
                <c:out value="${meal.getDescription()}" />
            </td>
            <td>
                <c:out value="${meal.getCalories()}" />
            </td>
        <td><a href="meals?action=edit&id=<c:out value="${meal.id}"/>">Update</a></td>
        <td><a href="meals?action=delete&id=<c:out value="${meal.id}"/>">Delete</a></td>
        </tr>
    </c:forEach>
</table>
<h2>Here you can create MEAL entry</h2>
    <form name="test" method="post" action='meals'>
        DateTime(): <input type="text" name="datetime" size="10">
        Description:<input type="text" name="descr" size="20">
        Calories:<input type="text" name="cals" size="20">
        <p><input type="submit" value="Отправить">
    </form>

</body>
</html>