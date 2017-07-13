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

    <title>Stocks</title>

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

        h2 {
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

<div class="row" align="center" style="padding-top: 3%;
            padding-left: 34%;">
    <div class="col-lg-6" align="center">
        <div class="input-group">
            <form class="navbar-form navbar-left" role="search" action="/search" method="post">
                <div class="form-group">
                    <input type="text" class="form-control" name="pattern" placeholder="Ticker">
                </div>
                <button type="submit" class="btn btn-primary">Find stock</button>
            </form>
            </span>
        </div>
    </div>
</div>

<c:if test="${error ne null}">
    <div class="row" style="padding-top:0px; " align="center">
        <h2>${error} </h2>
        <h2>Try again</h2>
    </div>
</c:if>

<c:if test="${order ne null}">
    <div class="row" align="center" style="margin-top: 3%">
        <form action="/orders">
            <button class="btn btn-lg btn-primary btn-block" type="submit" style="width: 20%; align-self: center">Order
                has been placed
            </button>
        </form>
    </div>
</c:if>

<c:if test="${stock ne null}">
<div class="row" style="padding-top:0px; padding-left:8%">
    <div class="col-md-4" style="width:60%; text-align: center; align-content: center">
        <h2>${stock.ticker} current price: ${stock.price}</h2>
        <img src=https://www.google.com/finance/getchart?q=${stock.ticker}&p=1Y&i=86400" width="auto">
    </div>
    <div class="col-lg-4" style="width:25%">
        <div class="dropdown" style="padding-top: 20%">
            <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown"
                    aria-haspopup="true" aria-expanded="true" style="width:150px" name="orderType">
                Order type
                <span class="caret"></span>
            </button>
            <ul class="dropdown-menu" aria-labelledby="dropdownMenu1" style="width:150px">
                <li><a href="#" data-value="buy">Buy</a></li>
                <li><a href="#" data-value="sell">Sell</a></li>
            </ul>
        </div>
        <div class="input-group">
            <form class="navbar-form navbar-top" role="search" action="/placeOrder" method="post">
                <div class="form-group">
                    <input type="text" class="form-control" name="amount" placeholder="Amount">
                    <input type="text" class="form-control" name="price" placeholder="Price">
                    <input type="hidden" class="form-control" name="orderType" id="type" required autofocus>
                    <input type="hidden" class="form-control" value="${stock.ticker}" name="ticker" required
                           autofocus>
                </div>
                <button type="submit" class="btn btn-primary">Place order</button>
            </form>
            </span>
        </div>
    </div>
    </c:if>
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
