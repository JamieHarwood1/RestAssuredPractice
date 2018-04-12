import configuration.ExampleConfig;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "example/src/test/resources",
		glue = "stepDefs",
		format = "pretty"
)
@Component
@ContextConfiguration(
		classes = ExampleConfig.class,
		loader = SpringBootContextLoader.class
)
public class TestRunner
{
}