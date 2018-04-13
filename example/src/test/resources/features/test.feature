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
		And Request body is requestBody.json
		When User makes request
		Then Response code is 200

	Scenario: HttpBin returns a 200 when making PUT request
		Given Request URL is http://httpbin.org/put
		And Request type is PUT
		And Request body is requestBody.json
		When User makes request
		Then Response code is 200

	Scenario: HttpBin returns a 200 when making DELETE request
		Given Request URL is http://httpbin.org/delete
		And Request type is DELETE
		And Request body is requestBody.json
		When User makes request
		Then Response code is 200
		
	Scenario: Verify response body of POST request
		Given Request URL is http://httpbin.org/post
		And Request type is POST
		And Request body is requestBody.json
		When User makes request
		Then Response code is 200
		And Response body is responseBody.json