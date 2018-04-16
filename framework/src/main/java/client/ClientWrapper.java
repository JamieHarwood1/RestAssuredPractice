package client;

import io.restassured.http.Headers;
import io.restassured.response.ResponseOptions;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

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

	private String proxyHost;
	private int proxyPort;

	public void initialise()
	{
		requestUrl = null;
		requestType = null;
		requestBody = null;
		requestHeaders = new Headers();

		proxyHost = null;
		proxyPort = -1;

		responseCode = -1;
		responseBody = null;
	}

	public void makeRequest()
	{
		final RequestSpecification request;

		final ResponseOptions response;

		if (Objects.isNull(proxyHost) || proxyPort == -1)
		{
			// No proxy
			request = given().headers(requestHeaders);
		}
		else
		{
			// Use proxy
			request = given().proxy(proxyHost, proxyPort)
							 .headers(requestHeaders);
		}

		switch (requestType)
		{
			case "GET":
			{
				response = request.get(requestUrl)
								  .thenReturn();
				break;
			}
			case "POST":
			{
				response = request.body(requestBody)
								  .post(requestUrl)
								  .thenReturn();
				break;
			}
			case "PUT":
			{
				response = request.body(requestBody)
								  .put(requestUrl)
								  .thenReturn();
				break;
			}
			case "DELETE":
			{
				response = request.body(requestBody)
								  .delete(requestUrl)
								  .thenReturn();
				break;
			}
			default:
			{
				throw new IllegalArgumentException("There is no test case for requestSpec request type: " +
												   requestType);
			}
		}

		responseCode = response.statusCode();

		responseBody = response.getBody()
							   .prettyPrint();
	}
}
