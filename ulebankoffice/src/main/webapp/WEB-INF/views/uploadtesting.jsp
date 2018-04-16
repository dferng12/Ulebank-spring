<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>
<%
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
%>
<html>

<body>
<h1>Spring MVC multi files upload example</h1>

 <form action="/uploadMulti" method="post" 
 enctype="multipart/form-data">
        <input type="file" name="myFile">
        <input type="submit" value="Submit">
 </form>

</body>
</html>