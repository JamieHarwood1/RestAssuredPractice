Feature: Make requests to an endpoint

	Scenario: Google returns a 200 when given URL
		When User makes GET request to https://www.google.co.uk
		Then Response code is 200

	Scenario: Google returns a 200 when given IP address
		When User makes GET request to 8.8.8.8
		Then Response code is 200

	Scenario: HttpBin returns a 200 when making POST request
		When User makes POST request to httpbin.org/post
		Then Response code is 200

	Scenario: HttpBin returns a 200 when making PUT request
		When User makes PUT request to httpbin.org/put
		Then Response code is 200

	Scenario: HttpBin returns a 200 when making DELETE request
		When User makes delete request to httpbin.org/delete
		Then Response code is 200
		
	Scenario: Verify response body of POST request
		When User makes POST request to httpbin.org/post with body testBody.json
		Then Response code is 200 and body is testBody.json

	Scenario: Verify headers
		When User makes GET request to httpbin.org/headers with headers testHeaders.json
		Then Response code is 200 and body is testHeaders.json