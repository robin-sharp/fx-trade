package com.trade.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Class to get Spring to handle .yml files like .properties files, using standard @PropertySource annotation
 * <p>
 * If the Resource path ends with a "/" it will be treated as a directory and the
 * <directory></directory>/application.yml will be loaded. In addition if the spring.profiles.active system property
 * is set, then <directory></directory>/<profile>-application.yml will be loaded for each active profile and
 * overwrite the application.yml property values.
 * <p>
 * If the Resource path does not end with a "/"  the PropertySource will try to load from the Resource path value.
 * <p>
 * For example:-
 *
 * @ConfigurationProperties(prefix = "yml")
 * @PropertySource(value = "classpath:/client-config/", factory = YamlPropertySourceFactory.class)
 */
@Slf4j
public class YamlPropertySourceFactory extends DefaultPropertySourceFactory {

	@Override
	public PropertySource<?> createPropertySource(String name, EncodedResource encodedResource) throws IOException {
		if (getPath(encodedResource.getResource()).endsWith("/")) {
			return createYamlPropertySourceUsingProfile(encodedResource.getResource());
		} else {
			return createYamlPropertySource(encodedResource.getResource());
		}
	}

	PropertySource<?> createYamlPropertySource(Resource resource) throws IOException {
		final Properties properties = loadYamlIntoProperties(resource);
		return new PropertiesPropertySource(resource.getFilename(), properties);
	}

	PropertySource<?> createYamlPropertySourceUsingProfile(Resource resource) throws IOException {
		final String yamlPath = getPath(resource);
		final List<Resource> resources = new ArrayList<>();
		final String defaultYamlPathName = yamlPath + "application.yml";
		log.info("Loading defaultYamlPathName={}", defaultYamlPathName);

		resources.add(new ClassPathResource(defaultYamlPathName));
		resources.addAll(loadProfileResources(yamlPath));

		Properties properties = loadYamlIntoProperties(resources.toArray(new Resource[resources.size()]));
		return new PropertiesPropertySource(yamlPath, properties);
	}

	List<Resource> loadProfileResources(String yamlPath) {
		final List<Resource> resources = new ArrayList<>();
		final String activeProfile = System.getProperty("spring.profiles.active");
		final List<String> activeProfiles = new ArrayList<>(Arrays.asList(activeProfile != null ? activeProfile.split(",") : new String[0]));
		for (String profile : activeProfiles) {
			final String profileYamlPathName = yamlPath + profile + "-application.yml";
			final ClassPathResource yamlResource = new ClassPathResource(profileYamlPathName);
			if (yamlResource.exists()) {
				log.info("Loading profileYamlPathName={}", profileYamlPathName);
				resources.add(new ClassPathResource(profileYamlPathName));
			} else {
				log.info("Failed to load profileYamlPathName={}", profileYamlPathName);
			}
		}
		return resources;
	}

	String getPath(Resource resource) {
		if (resource instanceof ClassPathResource) {
			return ((ClassPathResource) resource).getPath();
		}
		return resource.getFilename();
	}

	Properties loadYamlIntoProperties(Resource... resources) throws FileNotFoundException {
		try {
			final YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
			factory.setResources(resources);
			factory.afterPropertiesSet();
			return factory.getObject();
		} catch (IllegalStateException e) {
			Throwable cause = e.getCause();
			if (cause instanceof FileNotFoundException)
				throw (FileNotFoundException) e.getCause();
			throw e;
		}
	}
}