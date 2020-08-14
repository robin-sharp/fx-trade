package com.trade.entity.cassandra;

import com.trade.entity.Party;
import com.trade.security.http.HttpSecurityContext;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.trade.JUnitTag.UNIT_TEST;
import static com.trade.entity.EntityServerTestData.PARTY_EXTERNAL_PRINCIPAL;
import static com.trade.entity.EntityServerTestData.PARTY_SUPPORT_PRINCIPAL;
import static com.trade.entity.PartyTestData.*;
import static com.trade.security.SecurityTestData.PRINCIPAL_NAME;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag(UNIT_TEST)
@ExtendWith(MockitoExtension.class)
public class CassandraPartyServiceTest {

	@InjectMocks
	private CassandraPartyService cassandraPartyService;

	@Mock
	private CassandraPartyRepository cassandraPartyRepository;

	@Mock
	private HttpSecurityContext httpSecurityContext;

	@Test
	public void testSaveParty() {
		cassandraPartyService.save(PARTY);
		verify(cassandraPartyRepository).save(PARTY);
	}

	@Test
	public void saveAllParties() {
		List<Party> parties = Arrays.asList(NEW_PARTY(), NEW_PARTY());
		cassandraPartyService.saveAll(parties);
		verify(cassandraPartyRepository).saveAll(parties);
	}

	@Test
	public void deleteParty() {
		UUID partyId = UUID.randomUUID();
		cassandraPartyService.delete(partyId);
		verify(cassandraPartyRepository).deleteById(partyId);
	}

	@Test
	public void TestGetPartyById() {
		UUID partyId = UUID.randomUUID();
		cassandraPartyService.getById(partyId);
		verify(cassandraPartyRepository).findById(partyId);
	}

	@Test
	public void TestGetPartyByParty() {
		when(httpSecurityContext.getHttpPrincipal()).thenReturn(PARTY_SUPPORT_PRINCIPAL);
		cassandraPartyService.getByPartyCode(PARTY_CODE);
		verify(cassandraPartyRepository).findByPartyCode(PARTY_CODE);
	}

	@Test
	public void TestGetPartyByPartyCodeAndUser() {
		when(httpSecurityContext.getHttpPrincipal()).thenReturn(PARTY_EXTERNAL_PRINCIPAL);
		cassandraPartyService.getByPartyCode(PARTY_CODE);
		verify(cassandraPartyRepository).findByPartyCodeAndUser(PARTY_CODE, PRINCIPAL_NAME);
	}

	@Test
	public void testGetAllPartys() {
		cassandraPartyService.getAll();
		verify(cassandraPartyRepository).findAll();
	}
}
