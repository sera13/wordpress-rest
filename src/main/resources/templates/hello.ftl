
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Hello ${name}!</title>
    <link href="/css/main.css" rel="stylesheet">
     <link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css"
            rel="stylesheet">
</head>
<body>


    <div class="container">
     <h2 class="hello-title">Hello ${name}!</h2>

<button type="button" class="btn btn-primary" onclick="jsonservice.display()">Primary</button>

<form method="get" action="http://localhost:8082">

<input type="submit" value="getArticles"></form>

  <form action="/hello" method="get">
            <p>Message: <input type="text" name="name"></p>
            <p>
                <input type="submit" value="Submit">
                <input type="reset" value="Reset">
            </p>
        </form>



<button type="button" class="btn btn-link">Link</button>
<div>


<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <script src="/js/main.js"></script>
</body>
</html>
