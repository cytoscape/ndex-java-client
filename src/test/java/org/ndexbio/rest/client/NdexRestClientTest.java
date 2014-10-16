package org.ndexbio.rest.client;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ndexbio.model.object.NdexStatus;
import org.ndexbio.model.object.network.NetworkSummary;


public class NdexRestClientTest {
	
	private NdexRestClient client;
	private NdexRestClientModelAccessLayer ndex;
		
	@Before
	public void setUp() throws Exception {
//		client = new NdexRestClient("Support", "probably-insecure2"); //("dexterpratt", "insecure");
		client = new NdexRestClient("cjtest", "1234", 
				"http://localhost:8080/ndexbio-rest",
				"http://localhost:8080/AuthenticationService/AuthenticationService", AuthenticationType.SAML); 
		ndex = new NdexRestClientModelAccessLayer(client);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAuthentication() throws Exception {
		
	/*	NetworkSummary networksummary = mal.getNetworkSummaryById("d8c5b86a-1997-11e4-8f64-90b11c72aefa");
		
		System.out.println(networksummary);
		
		//Network n0 = mal.getNeighborhood("d750c790-199e-11e4-86bd-90b11c72aefa","YGR218W", 1);
		PropertyGraphNetwork n0 = mal.getNeighborhoodAsPropertyGraph("d750c790-199e-11e4-86bd-90b11c72aefa","YGR218W", 1);
		System.out.println(n0);
		
		Network network = mal.getEdges("f6ecda26-18fc-11e4-8590-90b11c72aefa", 0, 12);
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writeValueAsString(network));
		
		PropertyGraphNetwork pn = 
		mal.getPropertyGraphNetwork("d9ed6aa1-1364-11e4-8b0d-90b11c72aefa", 0,12);
		for ( PropertyGraphNode n : pn.getNodes().values()) {
			System.out.println ("node id: "+ n.getId());
			for (NdexProperty p : n.getProperties()) {
				System.out.println("\t" + p.getPredicateString() + ": " + p.getValue());
			}
			
		}

		for (PropertyGraphEdge e : pn.getEdges()) {
			System.out.println("Edge:" + e.getSubjectId() + "->" + e.getPredicate() + "->" + e.getObjectId());
		}
		System.out.println(pn);
		
		int i = 0;
		for ( NdexProperty p : pn.getProperties()) {
			if ( p.getPredicateString().equals(PropertyGraphNetwork.uuid) ) {
				break;
			}
			i++;
		}
		pn.getProperties().remove(i);
		
		NdexProperty pname = new NdexProperty();
		pname.setPredicateString(PropertyGraphNetwork.name);
		pname.setValue("my test network1");
		pn.getProperties().add(pname);
		
		NetworkSummary summary = mal.insertPropertyGraphNetwork(pn);
		
        System.out.println(summary);
*/		
		boolean b = ndex.checkCredential();
		
		Assert.assertTrue(b);
	
		// example of search.
		List<NetworkSummary> s = ndex.findNetworks("*", "Support", 0,3);
		System.out.println(s.get(0).getName());
		
		// example of get server status.
		NdexStatus status = ndex.getServerStatus();
		System.out.println(status.getNetworkCount());
	}

	
	
/*	
	@Test
	public void testApi() throws Exception {
		JsonNode response = client.get("/networks/api", "");
		Iterator<JsonNode> elements = response.elements();
		while (elements.hasNext()){
			JsonNode resource = elements.next();
			System.out.println(resource.get("requestType") + "  " + resource.get("path"));
			System.out.println("   implemented by: " + resource.get("methodName") + "(" + resource.get("parameterTypes") + ")");
            String consumes = resource.get("consumes").textValue();
            if (null != consumes){
            	System.out.println("   consumes: " + consumes);
            }
			//System.out.println(response.toString());
			
		}
		
		
	}
	*/

	/*
	@Test
	public void testStatus() throws Exception {
		JsonNode status = client.get("/networks/status", "");
		System.out.println(status.get("networkCount") + " networks");
		System.out.println(status.get("userCount") + " users");
		System.out.println(status.get("groupCount") + " groups");
	}
	*/
/*	
	@Test
	public void testFindNetworksByName() throws Exception {
		List<Network> networks = mal.findNetworksByText("BEL", "contains", 10, 0);
		System.out.println("\n______\nTesting Finding Networks by text contains BEL:");
		for(Network network : networks){
			System.out.println(network.getName() + "  (edge count = " + network.getEdgeCount() + ")");	
		}
	}
	
	@Test
	public void testFindNetworksByProperty() throws Exception {
		List<Network> networks = mal.findNetworksByProperty("Format", "BEL_DOCUMENT", "=", 10);
		System.out.println("\n______\nTesting Finding Networks by Property Format = BEL_DOCUMENT:");
		for(Network network : networks){
			System.out.println(network.getName() + "  (edge count = " + network.getEdgeCount() + ")");	
		}
	}

	@Test
	public void testFindTermsInNetworkByNamespace() throws Exception {
		List<Network> networks = mal.findNetworksByProperty("Format", "BEL_DOCUMENT", "=", 10);
		for(Network network : networks){
			System.out.println("\n______\n" + network.getName() + "  id = " + network.getId() + "\nTerms:");
			
			List<BaseTerm> baseTerms = mal.findBaseTermsInNetworkByNamespace("HGNC", network.getId());
			for (BaseTerm baseTerm : baseTerms){
				System.out.println(" " + baseTerm.getName() + "\t  id = " + baseTerm.getId());
			}
			
		}
		
	}
*/

}
