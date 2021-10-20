
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Hello!</title>
    <link href="/css/main.css" rel="stylesheet">
     <link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css"
            rel="stylesheet">
</head>
<body>


    <div class="container">
     <h2 class="hello-title">Hello something</h2>

  <form action="/index" method="get">
  <input type="hidden" id="taxonomies" name="name" value="taxonomies">
            <p>
                <input type="submit" value="Create Taxonomy Files">
            </p>
        </form>

          <form action="/index" method="get">
            <input type="hidden" id="article" name="name" value="article">

                    <p>
                        <input type="submit" value="Create Article Json File">
                    </p>
                </form>

<#if value??>
        <p>
        ${value}
        </p>
        </#if>
<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <script src="/js/main.js"></script>
</body>
</html>
