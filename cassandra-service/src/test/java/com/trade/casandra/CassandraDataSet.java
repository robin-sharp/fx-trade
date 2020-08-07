package com.trade.casandra;

import lombok.extern.slf4j.Slf4j;
import org.cassandraunit.dataset.CQLDataSet;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * Used to build a CqlDataSet from separate files.
 */
@Slf4j
public class CassandraDataSet implements CQLDataSet {

	private final List<String> csqlStatements;
	private final boolean keyspaceCreation;
	private final boolean keyspaceDeletion;
	private final String keyspaceName;

	public CassandraDataSet(List<String> csqlStatements, boolean keyspaceCreation, boolean keyspaceDeletion, String keyspaceName) {
		this.csqlStatements = csqlStatements;
		this.keyspaceCreation = keyspaceCreation;
		this.keyspaceDeletion = keyspaceDeletion;
		this.keyspaceName = keyspaceName;
	}

	public static CassandraDataSetBuilder builder() {
		return new CassandraDataSetBuilder();
	}

	@Override
	public List<String> getCQLStatements() {
		return csqlStatements;
	}

	@Override
	public String getKeyspaceName() {
		return keyspaceName;
	}

	@Override
	public boolean isKeyspaceCreation() {
		return keyspaceCreation;
	}

	@Override
	public boolean isKeyspaceDeletion() {
		return keyspaceDeletion;
	}

	public static class CassandraDataSetBuilder {

		private String directory;
		private List<String> csqlStatements = new ArrayList<>();
		private boolean keyspaceCreation = true;
		private boolean keyspaceDeletion = true;
		private String keyspaceName;

		public CassandraDataSetBuilder() {
		}

		public CassandraDataSet build() {
			log.info("Building cql {} {} {}", keyspaceName, keyspaceCreation, keyspaceDeletion);
			return new CassandraDataSet(csqlStatements, keyspaceCreation, keyspaceDeletion, keyspaceName);
		}

		public CassandraDataSetBuilder withKeyspaceCreation(boolean keyspaceCreation) {
			this.keyspaceCreation = keyspaceCreation;
			return this;
		}

		public CassandraDataSetBuilder withKeyspaceDeletion(boolean keyspaceDeletion) {
			this.keyspaceDeletion = keyspaceDeletion;
			return this;
		}

		public CassandraDataSetBuilder withKeyspaceName(String keyspaceName) {
			this.keyspaceName = keyspaceName;
			return this;
		}

		public CassandraDataSetBuilder withDirectory(String directory) {
			this.directory = directory;
			return this;
		}

		public CassandraDataSetBuilder withFile(String relativeFileName) {
			csqlStatements.addAll(readFile(relativeFileName));
			return this;
		}

		private List<String> readFile(String fileName) {
			try {
				log.info("Reading cql file {} {}", directory, fileName);
				final String relativeFileName = directory != null ? directory + fileName : fileName;
				final URI fileUri = getClass().getClassLoader().getResource(relativeFileName).toURI();
				final String content = Files.lines(Paths.get(fileUri)).collect(Collectors.joining(System.lineSeparator()));
				List<String> cql = Arrays.asList(content.replace("\r\n", "\n").split("(?<=;)")).
						stream().filter(c -> c.trim().length() > 0 && !c.trim().startsWith("//")).collect(Collectors.toList());
				log.info("Read cql commands {}", cql);
				return cql;
			} catch (Exception e) {
				throw new IllegalStateException(format("Failed to read directory=%s fileName=%s", directory, fileName), e);
			}
		}
	}
}
