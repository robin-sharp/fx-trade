package com.trade.cassandra;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Configuration
@Validated
public abstract class CassandraConfiguration extends AbstractCassandraConfiguration {

	@NotEmpty
	private String contactPoints;

	@Min(1024)
	@Max(49151)
	private int port;

	@NotEmpty
	private String localDataCenter;

	@NotEmpty
	private String keyspaceName;

	private String sessionName;

	@NotNull
	private SchemaAction schemaAction;

	@NotNull
	private String entityBasePackages;

//	public CassandraConfiguration(String contactPoints, int port, String localDataCenter,
//								  String keyspaceName, SchemaAction schemaAction, String entityBasePackages,
//								  String sessionName) {
//		this.contactPoints = contactPoints;
//		this.port = port;
//		this.localDataCenter = localDataCenter;
//		this.keyspaceName = keyspaceName;
//		this.schemaAction = schemaAction != null ? schemaAction : SchemaAction.CREATE_IF_NOT_EXISTS;
//		this.entityBasePackages = entityBasePackages;
//		this.sessionName = sessionName;
//	}

	@Override
	protected String getKeyspaceName() {
		return keyspaceName;
	}

	public void setKeyspaceName(String keyspaceName) {
		this.keyspaceName = keyspaceName;
	}

	@Override
	protected String getContactPoints() {
		return contactPoints;
	}

	public void setContactPoints(String contactPoints) {
		this.contactPoints = contactPoints;
	}

	@Override
	protected int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	@Override
	protected String getLocalDataCenter() {
		return localDataCenter;
	}

	public void setLocalDataCenter(String localDataCenter) {
		this.localDataCenter = localDataCenter;
	}

	@Override
	public SchemaAction getSchemaAction() {
		return SchemaAction.CREATE_IF_NOT_EXISTS;
	}

	public void setSchemaAction(SchemaAction schemaAction) {
		this.schemaAction = schemaAction;
	}

	@Override
	public String getSessionName() {
		return sessionName != null ? sessionName : keyspaceName;
	}

	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}

	@Override
	public String[] getEntityBasePackages() {
		return entityBasePackages.split(",");
	}

	public void setEntityBasePackages(String entityBasePackages) {
		this.entityBasePackages = entityBasePackages;
	}
}
