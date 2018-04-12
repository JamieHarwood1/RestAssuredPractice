package client;

import io.restassured.http.Headers;
import io.restassured.response.ResponseOptions;
import lombok.Getter;
import lombok.Setter;

import static io.restassured.RestAssured.given;

@Getter
@Setter
public class ClientWrapper
{
	private String requestUrl;
	private String requestType;
	private String requestBody;
	private Headers requestHeaders;

	private int responseCode;
	private String responseBody;

	public void initialise()
	{
		requestUrl = null;
		requestType = null;
		requestBody = null;
		requestHeaders = new Headers();

		responseCode = -1;
		responseBody = null;
	}

	public void makeRequest()
	{
		final ResponseOptions response;

		switch (requestType)
		{
			case "GET":
			{
				response = given().headers(requestHeaders)
								  .get(requestUrl)
								  .thenReturn();
				break;
			}
			case "POST":
			{
				response = given().headers(requestHeaders)
								  .body(requestBody)
								  .post(requestUrl)
								  .thenReturn();
				break;
			}
			case "PUT":
			{
				response = given().headers(requestHeaders)
								  .body(requestBody)
								  .put(requestUrl)
								  .thenReturn();
				break;
			}
			case "DELETE":
			{
				response = given().headers(requestHeaders)
								  .body(requestBody)
								  .delete(requestUrl)
								  .thenReturn();
				break;
			}
			default:
			{
				throw new IllegalArgumentException("There is no test case for given request type: " + requestType);
			}
		}

		responseCode = response.statusCode();

		responseBody = response.getBody()
							   .prettyPrint();
	}
}
