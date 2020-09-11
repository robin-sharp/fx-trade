package com.trade.casandra;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.trade.JUnitTag.UNIT_TEST;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag(UNIT_TEST)
public class CassandraDataSetTest {

	@Test
	public void testCassandraBuilder() {
		CassandraDataSet dataSet = CassandraDataSet.builder().
				withKeyspaceCreation(true).
				withKeyspaceDeletion(true).
				withKeyspaceName("test").
				withFile("cassandra/test-dataset1.cql").
				withFile("cassandra/test-dataset2.cql").
				build();

		assertEquals(true, dataSet.isKeyspaceCreation());
		assertEquals(true, dataSet.isKeyspaceDeletion());
		assertEquals("test", dataSet.getKeyspaceName());

		assertEquals(6, dataSet.getCQLStatements().size());

		assertEquals("line-1.1\nline-1.2\nline-1.3;", dataSet.getCQLStatements().get(0).trim());
		assertEquals("line-1.4;", dataSet.getCQLStatements().get(1).trim());
		assertEquals("line-1.5;", dataSet.getCQLStatements().get(2).trim());

		assertEquals("line-2.1\nline-2.2\nline-2.3;", dataSet.getCQLStatements().get(3).trim());
		assertEquals("line-2.4;", dataSet.getCQLStatements().get(4).trim());
		assertEquals("line-2.5;", dataSet.getCQLStatements().get(5).trim());
	}
}
