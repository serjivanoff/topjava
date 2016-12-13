<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edition</title>
</head>
<body>
<form name="test" method="post" action='meals'>
    ID: <input value="${meal.id}" name="mealid" type="text" size="5" readonly>
    DateTime(YYYY-MM-DD HH:MM:SS): <input value="${meal.getDate()}" type="text" name="datetime" size="10">
    Description:<input value="${meal.getDescription()}" type="text" name="descr" size="20">
    Calories:<input value="${meal.getCalories()}" type="text" name="cals" size="20">
    <p><input  type="submit" value="Отправить">
</form>
<a href="meals">Back</a>
</body>
</html>
