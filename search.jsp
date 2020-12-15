<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
	<title>Books Store Application</title>
</head>
<body>
<div align="center">
<%@ include file="menu.jsp" %>

<form   method="post"action="search" >
    	<p>
	    	<label for="id">id_book : </label>
	    	<input type="text" name="id" id="id" value="2" required/>
    	</p>
    	<input type="submit" />
</form>
    

        <table border="1" cellpadding="5">
            <caption>List of Books</caption>
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Author</th>
                <th>Price</th>
                <th>Actions</th>
            </tr>
             <c:forEach var="listsearchedBook" items="${listsearchedBook}">
                <tr>
                    <td><c:out value="${listsearchedBook.id}" /></td>
                    <td><c:out value="${listsearchedBook.title}" /></td>
                    <td><c:out value="${listsearchedBook.author}" /></td>
                    <td><c:out value="${listsearchedBook.price}" /></td>
                     <td>
                    	<a href="edit?id=<c:out value='${book.id}' />">Edit</a>
                    	&nbsp;&nbsp;&nbsp;&nbsp;
                    	<a href="delete?id=<c:out value='${book.id}' />">Delete</a>                    	
                    </td>
                   
                </tr>
            </c:forEach>
        </table>
    </div>	
         
</body>
</html>