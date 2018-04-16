package stepDefs;

import client.ClientWrapper;
import configuration.ExampleConfig;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import org.json.JSONObject;
import org.junit.Assert;
import org.skyscreamer.jsonassert.JSONAssert;
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

	@And("^Proxy is (.*):(\\d+)$")
	public void proxyIs(final String host, final int port) throws Throwable
	{
		clientWrapper.setProxyHost(host);
		clientWrapper.setProxyPort(port);
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

		JSONAssert.assertEquals(expectedBody, clientWrapper.getResponseBody(), false);
	}

	@Then("^Response code is (.*)$")
	public void responseCodeIsCode(final String expectedCode) throws Throwable
	{
		Assert.assertEquals(expectedCode, String.valueOf(clientWrapper.getResponseCode()));
	}

	@And("^Response body (.*) attribute is (.*)$")
	public void responseBodyAttributeIs(final String attributeName, final String attributeValuePath) throws Throwable
	{
		final String expectedValue = new String(Files.readAllBytes(Paths.get(exampleProperties.getBodyPath() +
																			 attributeValuePath)));
		final String actualValue = new JSONObject(clientWrapper.getResponseBody()).getString(attributeName);
		JSONAssert.assertEquals(expectedValue, actualValue, false);
	}
}
