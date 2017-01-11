<html>
<html>
<head>
    <meta name="layout" content="public"/>
    <title>Home Page</title>

    <asset:link rel="icon" href="favicon.ico" type="image/x-ico" />
</head>
<body>

<div id="content" role="main">
    <section class="row colset-2-its">
        <h1>Welcome ${name}!</h1>
        <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
        </g:if>

        <p>There are ${vehicleTotal} vehicles in the database.</p>

        <g:form action="updateName" style="margin: 0 auto; width:320px">
            <g:textField name="name" value="" />
            <g:submitButton name="Update name" />
        </g:form>

    </section>
</div>

</body>
</html>
