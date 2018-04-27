<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${status!}</title>
</head>
<body>
<h1>${status!} > ${message!}</h1>
<#if desc??>
<h2>${desc}</h2>
</#if>
timestamp: ${timestamp?string("yyyy-MM-dd HH:mm:ss")!} <br/>
error: ${error!} <br/>
path: ${path!} <br/>

</body>
</html>