<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="/view/login/process" method="post">
		<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }" />
		<p>
			<input type="text" id="username" name="username" />
		</p>
		<p>
			<input type="password" id="password" name="password" />
		</p>
		<button type="submit" class="btn">Log in</button>
	</form>
</body>
</html>