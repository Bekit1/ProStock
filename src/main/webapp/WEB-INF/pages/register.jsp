<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon"
          href="https://is5-ssl.mzstatic.com/image/thumb/Purple127/v4/51/bc/6e/51bc6ebc-59f4-ab31-0cb7-3aba903715d4/source/256x256bb.jpg">

    <title>Register</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
    <link href="http://getbootstrap.com/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="http://getbootstrap.com/examples/signin/signin.css" rel="stylesheet">

    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

    <style>
        .container {
            width: 300px;
        }
    </style>
</head>
<body>
<div class="container" align="center">
    <c:url value="/newuser" var="regUrl"/>
    <form action="${regUrl}" method="POST">
        Login:<br/><input type="text" name="login" class="form-control" placeholder="Login" required autofocus><br/>
        Password:<br/><input type="password" name="password" class="form-control" placeholder="Password" required
                             autofocus><br/>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Send</button>
        <c:if test="${exists ne null}">
            <p>User already exists!</p>
        </c:if>
    </form>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script>window.jQuery || document.write('<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"><\/script>')</script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type='text/javascript'
        src='https://mdbootstrap.com/wp-content/themes/mdbootstrap4/js/compiled.min.js?ver=4.3.2'></script>

</body>
</html>
