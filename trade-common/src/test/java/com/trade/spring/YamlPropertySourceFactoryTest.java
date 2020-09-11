package com.trade.spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class YamlPropertySourceFactoryTest {

	private final String applicationYmlFile = "yaml/config/application.yml";
	private final String roleYmlFile = "yaml/config/role-application.yml";

	private final String applicationYmlPath = "yaml/config/";

	@BeforeEach
	public void beforeEach() {
		System.setProperty("spring.profiles.active", "");
	}

	@Test
	public void testLoadPropertiesFromApplicationYmlFile() throws Exception {

		ClassPathResource resource = new ClassPathResource(applicationYmlFile);
		assertTrue(resource.isFile());

		YamlPropertySourceFactory yamlPropertySourceFactory = new YamlPropertySourceFactory();
		assertEquals(applicationYmlFile, yamlPropertySourceFactory.getPath(resource));

		checkApplicationYml(yamlPropertySourceFactory.loadYamlIntoProperties(resource));
	}

	@Test
	public void testLoadPropertiesFromRoleYmlFile() throws Exception {

		ClassPathResource resource = new ClassPathResource(roleYmlFile);
		assertTrue(resource.isFile());

		YamlPropertySourceFactory yamlPropertySourceFactory = new YamlPropertySourceFactory();
		assertEquals(roleYmlFile, yamlPropertySourceFactory.getPath(resource));

		checkRoleYml(yamlPropertySourceFactory.loadYamlIntoProperties(resource));
	}

	@Test
	public void testLoadPropertySourceFromApplicationYmlFile() throws Exception {

		EncodedResource resource = new EncodedResource(new ClassPathResource(applicationYmlFile));

		YamlPropertySourceFactory yamlPropertySourceFactory = new YamlPropertySourceFactory();
		PropertySource<?> ps = yamlPropertySourceFactory.createPropertySource("", resource);
		assertTrue(ps.getSource() instanceof Properties);
		checkApplicationYml((Properties) ps.getSource());
	}

	@Test
	public void testLoadPropertySourceFromRoleYmlFile() throws Exception {

		EncodedResource resource = new EncodedResource(new ClassPathResource(roleYmlFile));

		YamlPropertySourceFactory yamlPropertySourceFactory = new YamlPropertySourceFactory();
		PropertySource<?> ps = yamlPropertySourceFactory.createPropertySource("", resource);
		assertTrue(ps.getSource() instanceof Properties);
		checkRoleYml((Properties) ps.getSource());
	}

	@Test
	public void testLoadPropertySourceFromApplicationYmlPath() throws Exception {

		EncodedResource resource = new EncodedResource(new ClassPathResource(applicationYmlPath));

		//By default if there are no active roles then only the application properties are loaded
		YamlPropertySourceFactory yamlPropertySourceFactory = new YamlPropertySourceFactory();
		PropertySource<?> ps = yamlPropertySourceFactory.createPropertySource("", resource);
		assertTrue(ps.getSource() instanceof Properties);
		checkApplicationYml((Properties) ps.getSource());
	}

	@Test
	public void testLoadPropertySourceFromApplicationYmlPathWithActiveProfile() throws Exception {

		System.setProperty("spring.profiles.active", "role");

		EncodedResource resource = new EncodedResource(new ClassPathResource(applicationYmlPath));

		YamlPropertySourceFactory yamlPropertySourceFactory = new YamlPropertySourceFactory();
		PropertySource<?> ps = yamlPropertySourceFactory.createPropertySource("", resource);
		assertTrue(ps.getSource() instanceof Properties);
		checkOverWrittenApplicationYml((Properties) ps.getSource());
	}

	void checkApplicationYml(Properties p) {
		assertEquals("value-0", p.get("key-0"));
		assertEquals("value-1-0", p.get("key-1.key-1-0"));
		assertEquals("value-1-1", p.get("key-1.key-1-1"));
		assertEquals("value-3-0-0", p.get("key-2.key-2-0.key-3-0-0"));
		assertEquals("value-3-0-1", p.get("key-2.key-2-0.key-3-0-1"));
		assertEquals("value-3-1-0", p.get("key-2.key-2-1.key-3-1-0"));
		assertEquals("value-3-1-1", p.get("key-2.key-2-1.key-3-1-1"));
		assertEquals("value-3", p.get("key-3"));
	}

	void checkOverWrittenApplicationYml(Properties p) {
		checkRoleYml(p);

		assertEquals("value-3", p.get("key-3"));
	}

	void checkRoleYml(Properties p) {
		assertEquals("role-0", p.get("key-0"));
		assertEquals("role-1-0", p.get("key-1.key-1-0"));
		assertEquals("role-1-1", p.get("key-1.key-1-1"));
		assertEquals("role-3-0-0", p.get("key-2.key-2-0.key-3-0-0"));
		assertEquals("role-3-0-1", p.get("key-2.key-2-0.key-3-0-1"));
		assertEquals("role-3-1-0", p.get("key-2.key-2-1.key-3-1-0"));
		assertEquals("role-3-1-1", p.get("key-2.key-2-1.key-3-1-1"));
	}
}