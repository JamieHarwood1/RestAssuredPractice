package configuration;

import client.ClientWrapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import properties.ExampleProperties;


@Configuration
@EnableConfigurationProperties(ExampleProperties.class)
public class ExampleConfig
{
	@Bean
	public ClientWrapper clientWrapper()
	{
		return new ClientWrapper();
	}
}
