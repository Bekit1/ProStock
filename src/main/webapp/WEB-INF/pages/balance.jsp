<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon"
          href="https://is5-ssl.mzstatic.com/image/thumb/Purple127/v4/51/bc/6e/51bc6ebc-59f4-ab31-0cb7-3aba903715d4/source/256x256bb.jpg">

    <title>Balance</title>

    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">

    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

    <link href="https://getbootstrap.com/examples/carousel/carousel.css" rel="stylesheet">
    <style>
        html, body {
            width: 100%;
            height: 100%;
            margin: 0px;
            padding: 0px;
            overflow-x: hidden;
        }

        td.header {
            background-color: black;
            color: white;
            text-align: center;
        }

        td {
            text-align: center;
            font-family: Helvetica Neue;
        }
    </style>
</head>

<body>
<div class="navbar-wrapper">
    <div class="container">
        <nav class="navbar navbar-inverse navbar-static-top">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                            aria-expanded="false" aria-controls="navbar">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="/">ProStock</a>
                </div>
                <div id="navbar" class="navbar-collapse collapse">
                    <ul class="nav navbar-nav">
                        <li class="active"><a href="/">Home</a></li>
                        <li><a href="/stocks">Stocks</a></li>
                        <li><a href="/orders">Orders</a></li>
                        <li><a href="/positions">Positions</a></li>
                        <li><a href="/balance">Balance</a></li>
                        <li><a href="/logout">Log out</a></li>
                    </ul>
                </div>
            </div>
        </nav>
    </div>
</div>

<div>
    <img src="https://i1.sndcdn.com/visuals-000208777921-W6FHcI-original.jpg"
         style="padding: 0;
    display: block;
    margin: 0 auto;
    max-height: 100%;
    max-width: 100%;">
</div>

<table class="table table-striped" style="margin-top: 3%; width:30%" border="1" align="center">
    <thead>
    <tr>
        <td width="40%" class="header"><b>Current balance</b></td>
        <td><b>${balance.stringBalance}</b></td>
    </tr>
    </thead>
</table>

<table class="table table-striped" style="margin-top: 3%; width:80%" border="1" align="center">
    <thead>
    <tr>
        <td class="header"><b>Execute date</b></td>
        <td class="header"><b>Stock</b></td>
        <td class="header"><b>Amount</b></td>
        <td class="header"><b>Type</b></td>
        <td class="header"><b>Result</b></td>
    </tr>
    </thead>
    <c:forEach items="${positions}" var="position">
        <tr style="padding-top: 0px">
            <td>${position.closed}</td>
            <td>${position.stock}</td>
            <td>${position.amount}</td>
            <td>${position.type}</td>
            <td>${position.stringResult}</td>
        </tr>
    </c:forEach>
</table>
<div align="center">
    <nav aria-label="Page navigation">
        <ul class="pagination">
            <c:if test="${byClosedPositionsPages ne null}">
                <c:forEach var="i" begin="1" end="${byClosedPositionsPages}">
                    <li><a href="/balance/?page=<c:out value="${i - 1}"/>"><c:out value="${i}"/></a></li>
                </c:forEach>
            </c:if>
        </ul>
    </nav>
</div>

<footer class="navbar-fixed-bottom">
    <p align="center">&copy; 2017 seleznov2211@gmail.com </p>
</footer>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script>window.jQuery || document.write('<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"><\/script>')</script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type='text/javascript'
        src='https://mdbootstrap.com/wp-content/themes/mdbootstrap4/js/compiled.min.js?ver=4.3.2'></script>
<script>
    $(".dropdown-menu li a").click(function () {
        $(this).parents(".dropdown").find('.btn').html($(this).text() + ' <span class="caret"></span>');
        $(this).parents(".dropdown").find('.btn').val($(this).data('value'));
        document.getElementById("type").value = $(this).data('value');
    });
</script>
</body>
</html>