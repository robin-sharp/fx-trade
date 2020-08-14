package com.trade.entity;

import com.trade.security.http.HttpSecurePrincipal;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class EntityServerTestData {

	public final static String WEBSERVICE = "party-client";

	public final static String PRINCIPAL_NAME = "robin.sharp";

	public final static Set<String> PARTY_SUPPORT_ROLES = new LinkedHashSet<>(Arrays.asList("Party-Support-Save-Role", "Party-Support-Find-Role", "Party-Support-Delete-Role"));
	public final static HttpSecurePrincipal PARTY_SUPPORT_PRINCIPAL = new HttpSecurePrincipal(WEBSERVICE, PRINCIPAL_NAME, PARTY_SUPPORT_ROLES);

	public final static Set<String> PARTY_INTERNAL_ROLES = new LinkedHashSet<>(Arrays.asList("Party-Internal-Save-Role", "Party-Internal-Find-Role"));
	public final static HttpSecurePrincipal PARTY_INTERNAL_PRINCIPAL = new HttpSecurePrincipal(WEBSERVICE, PRINCIPAL_NAME, PARTY_INTERNAL_ROLES);

	public final static Set<String> PARTY_EXTERNAL_ROLES = new LinkedHashSet<>(Arrays.asList("Party-External-Save-Role", "Party-External-Find-Role"));
	public final static HttpSecurePrincipal PARTY_EXTERNAL_PRINCIPAL = new HttpSecurePrincipal(WEBSERVICE, PRINCIPAL_NAME, PARTY_EXTERNAL_ROLES);

}
