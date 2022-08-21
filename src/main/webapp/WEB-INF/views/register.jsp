<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "t" tagdir="/WEB-INF/tags" %>

<title>Sign Up • Chit Chat</title>
<t:base>
<div class="container g-5 d-flex vh-100 w-md-50 w-lg-32 justify-content-center mt-5 mt-sm-3 flex-column fade-in">
	<h6 class="display-6 styled-heading">Join the Conversation</h6>
	<form:form action="/register" method="post" modelAttribute="newUser" class="styled-text">
		<form:input type="hidden" path="registered" value="1"></form:input>
		<div class="mb-3">
			<form:label for="username" path="username" class="form-label">Username:</form:label>
			<small><form:errors path="username" class="text-danger"/></small>
			<form:input type="text" class="form-control" path="username" />
		</div>
		<div class="mb-3">
			<form:label for="nickname" path="nickname" class="form-label">Nickname:</form:label>
			<small><form:errors path="nickname" class="text-danger"/></small>
			<form:input type="text" class="form-control" path="nickname" />
		</div>
		<div class="mb-3">
			<form:label for="email" path="email" class="form-label">Email:</form:label>
			<small><form:errors path="email" class="text-danger" /></small>
			<form:input type="text" class="form-control" path="email" />
		</div>
		<div class="mb-3">
			<form:label for="password" path="password" class="form-label">Password:</form:label>
			<small><form:errors path="password" class="text-danger" /></small>
			<form:input type="password" class="form-control" path="password" />
		</div>
		<div class="mb-3">
			<form:label for="confirm" path="confirm" class="form-label">Confirm:</form:label>
			<small><form:errors path="confirm" class="text-danger" /></small>
			<form:input type="password" class="form-control" path="confirm" />
		</div>
		<button type="submit" class="btn btn-light btn-lg mt-2 styled-button">Register</button>
	</form:form>	
	<p class="styled-text">Already have an account? <a href="/accounts/login">Sign In</a></p>
</div>
</t:base>
	
