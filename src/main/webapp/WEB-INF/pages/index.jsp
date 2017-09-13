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

    <title>ProStock</title>

    <link href="https://getbootstrap.com/docs/3.3/dist/css/bootstrap.min.css" rel="stylesheet">


    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>


    <link href="https://getbootstrap.com/docs/3.3/examples/carousel/carousel.css" rel="stylesheet">

    <style>
        html, body {
            width: 100%;
            height: 100%;
            margin: 0px;
            padding: 0px;
            overflow-x: hidden;
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

<div id="myCarousel" class="carousel slide" data-ride="carousel">
    <ol class="carousel-indicators">
        <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
        <li data-target="#myCarousel" data-slide-to="1"></li>
        <li data-target="#myCarousel" data-slide-to="2"></li>
    </ol>
    <div class="carousel-inner" role="listbox">
        <div class="item active">
            <img class="first-slide"
                 src="http://toursfera.com/wp-content/uploads/2015/01/Another_Costa_Rica_night_sky_includes_volcano_OC1920x1278-s1920x1278-410769.jpg"
                 alt="First slide" height="200">
            <div class="container">
                <div class="carousel-caption">
                    <h1>Start trade with your demo account</h1>
                    <p>You have 3000 USD and can check your trading skill in stock market. </p>
                    <p><a class="btn btn-lg btn-primary" href="/stocks" role="button">Find stock</a></p>
                </div>
            </div>
        </div>
        <div class="item">
            <img class="second-slide" src="http://healthyconclusions.com/wp-content/uploads/2016/04/slide04.jpg" alt="Second slide">
            <div class="container">
                <div class="carousel-caption">
                    <h1>There is rule of margin call</h1>
                    <p>If your balance plus all current results of all opened positions fell to 300 USD, all your
                        positions and orders will be closed automatically. </p>
                    <p><a class="btn btn-lg btn-primary" href="/positions" role="button">See your positions</a></p>
                </div>
            </div>
        </div>
        <div class="item">
            <img class="third-slide" src="http://asianblisstours.com/wp-content/uploads/2017/02/vietnamin_kiertomatka_main.jpg" alt="Third slide">
            <div class="container">
                <div class="carousel-caption">
                    <h1>Good luck!</h1>
                    <p>Remember, trading - 80% of knowledge and 20% of luck.</p>
                    <p><a class="btn btn-lg btn-primary" href="/stocks" role="button">Go!</a></p>
                </div>
            </div>
        </div>
    </div>
    <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
        <span class="sr-only">Previous</span>
    </a>
    <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
        <span class="sr-only">Next</span>
    </a>
</div>

<div class="container marketing">
    <div class="row">
        <div class="col-lg-4">
            <img class="img-circle"
                 src="https://g.foolcdn.com/editorial/images/213819/stock-market-crash-on-board-getty_large.jpg"
                 alt="Generic placeholder image" width="140" height="140">
            <h2>Investing in Stocks</h2>
            <p>Investing in the right stocks to capitalize on trends can potentially lead to gains in your portfolio as
                new companies or industries become increasingly popular.</p>
            <p><a class="btn btn-default" href="/stocks" role="button">Start trading &raquo;</a></p>
        </div>
        <div class="col-lg-4">
            <img class="img-circle" src="https://cdn01.vulcanpost.com/wp-uploads/2017/04/141016-stockmarket-stock.jpg"
                 alt="Generic placeholder image" width="140" height="140">
            <h2>Investing for Income</h2>
            <p>Some stocks pay dividends on their earnings, which work similarly to bonds or CDs in that you receive
                money directly based on the performance of the stock.</p>
            <p><a class="btn btn-default" href="/stocks" role="button">Become trader &raquo;</a></p>
        </div>
        <div class="col-lg-4">
            <img class="img-circle"
                 src="http://www.sharesinv.com/wp-content/uploads/articles/buy-hold-sell-for-2014-stock-picks.jpg"
                 alt="Generic placeholder image" width="140" height="140">
            <h2>Building a Portfolio</h2>
            <p>Stocks allow greater control over your investments by letting you quickly change positions to react to
                market changes.</p>
            <p><a class="btn btn-default" href="/stocks" role="button">Build portfolio &raquo;</a></p>
        </div>
    </div>

    <footer>
        <p align="center">&copy; 2017 seleznov2211@gmail.com </p>
    </footer>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"><\/script>')</script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>
</html>
