Feature: Make requests to an endpoint
	Background:
		Given Client wrapper is initialised

	Scenario: Google returns a 200 when given URL
		Given Request URL is https://www.google.co.uk
		And Request type is GET
		When User makes request
		Then Response code is 200

	Scenario: HttpBin returns a 200 when making POST request
		Given Request URL is http://httpbin.org/post
		And Request type is POST
		And Request body is testBody.json
		When User makes request
		Then Response code is 200

	Scenario: HttpBin returns a 200 when making PUT request
		Given Request URL is http://httpbin.org/put
		And Request type is PUT
		And Request body is testBody.json
		When User makes request
		Then Response code is 200

	Scenario: HttpBin returns a 200 when making DELETE request
		Given Request URL is http://httpbin.org/delete
		And Request type is DELETE
		And Request body is testBody.json
		When User makes request
		Then Response code is 200
		
	Scenario: Verify response body of POST request
		Given Request URL is http://httpbin.org/post
		And Request type is POST
		And Request body is testBody.json
		When User makes request
		Then Response code is 200
		#TODO: Extract body from actual body
		And Response body is testBody.json

	Scenario: Verify headers
		Given Request URL is http://httpbin.org/headers
		And Request type is GET
		And Request headers is testHeaders.json
		When User makes request
		Then Response code is 200
		#TODO: Headers in different location
		And Response body is testHeaders.json