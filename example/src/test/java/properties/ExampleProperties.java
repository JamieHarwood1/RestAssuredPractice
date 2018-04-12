package properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "example")
public class ExampleProperties
{
	private String headerPath;
	private String bodyPath;
}
