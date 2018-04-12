package stepDefs;

import client.ClientWrapper;
import configuration.ExampleConfig;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import properties.ExampleProperties;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
@ContextConfiguration(
		classes = ExampleConfig.class,
		loader = SpringBootContextLoader.class
)
public class ExampleStepDefs
{
	private final ClientWrapper clientWrapper;
	private final ExampleProperties exampleProperties;

	@Autowired
	public ExampleStepDefs(final ClientWrapper clientWrapper, final ExampleProperties exampleProperties)
	{
		this.clientWrapper = clientWrapper;
		this.exampleProperties = exampleProperties;
	}

	@Given("^Client wrapper is initialised$")
	public void clientWrapperIsInitialised()
	{
		clientWrapper.initialise();
	}

	@Given("^Request URL is (.*)$")
	public void urlIsUrl(final String url) throws Throwable
	{
		clientWrapper.setRequestUrl(url);
	}

	@Given("^Request type is (.*)$")
	public void requestTypeIsType(final String type) throws Throwable
	{
		clientWrapper.setRequestType(type);
	}

	@Given("^Request body is (.*)$")
	public void requestBodyIsBody(final String bodyPath) throws Throwable
	{
		final String body = new String(Files.readAllBytes(Paths.get(exampleProperties.getBodyPath() + bodyPath)));
		clientWrapper.setRequestBody(body);
	}

	@Given("^Request headers is (.*)$")
	public void requestHeadersIsHeaders(final String headerPath) throws Throwable
	{
		final Map<String, String> headerValues = JsonPath.from(new File(exampleProperties.getHeaderPath() + headerPath))
														 .getMap("headers");

		final List<Header> headerList = new LinkedList<>();
		headerValues.keySet()
					.forEach(key -> headerList.add(new Header(key, headerValues.get(key))));

		final Headers headers = new Headers(headerList);
		clientWrapper.setRequestHeaders(headers);
	}

	@When("^User makes request$")
	public void userMakesRequest() throws Throwable
	{
		clientWrapper.makeRequest();
	}

	@Then("^Response body is (.*)$")
	public void responseBodyIsBody(final String bodyPath) throws Throwable
	{
		final String expectedBody = new String(Files.readAllBytes(Paths.get(exampleProperties.getBodyPath() +
																			bodyPath)));
		Assert.assertEquals(expectedBody, clientWrapper.getResponseBody());
	}

	@Then("^Response code is (.*)$")
	public void responseCodeIsCode(final String expectedCode) throws Throwable
	{
		Assert.assertEquals(expectedCode, String.valueOf(clientWrapper.getResponseCode()));
	}
}
