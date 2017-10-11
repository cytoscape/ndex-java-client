/**
 * Copyright (c) 2013, 2016, The Regents of the University of California, The Cytoscape Consortium
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */
package org.ndexbio.rest.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import org.cxio.aspects.datamodels.EdgesElement;
import org.cxio.aspects.datamodels.NetworkAttributesElement;
import org.cxio.aspects.datamodels.NodeAttributesElement;
import org.cxio.aspects.datamodels.NodesElement;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.ndexbio.model.exceptions.NdexException;
import org.ndexbio.model.exceptions.ObjectNotFoundException;
import org.ndexbio.model.object.CXSimplePathQuery;
import org.ndexbio.model.object.Group;
import org.ndexbio.model.object.NdexStatus;
import org.ndexbio.model.object.NetworkSearchResult;
import org.ndexbio.model.object.SimpleQuery;
import org.ndexbio.model.object.SolrSearchResult;
import org.ndexbio.model.object.Task;


public class NdexRestClientTest {
	
	private NdexRestClient client;
	private NdexRestClientModelAccessLayer ndex;
    private static String _username = "cj1";
    private static String _password = "aaa";
    private static String _route = "dev.ndexbio.org";
		
    @Rule
    public ExpectedException thrown1= ExpectedException.none();
    
	@Before
	public void setUp() throws Exception {
		
        client = new NdexRestClient(_username, _password, _route);
		ndex = new NdexRestClientModelAccessLayer(client);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testGetStatus() throws IOException, NdexException {
		NdexStatus s = ndex.getServerStatus(true);
		assertNotNull ( s.getProperties().get("Build"));
		assertEquals(((String)s.getProperties().get("ServerVersion")).substring(0,1), "2");
		s = ndex.getServerStatus(false);
		assertNull((Object)s.getProperties().get("ImporterExporters"));
	}

	@Test 
	public void testGetGroupOperations() throws IllegalStateException, Exception {
		Group newGroup = new Group();
		String testGroupName = "CJ's Test Group Created From Java Client";
		String testGroupDesc = "Don't Modifiy this group";
		newGroup.setGroupName(testGroupName);
		newGroup.setDescription(testGroupDesc);
		
		UUID groupId= ndex.createGroup(newGroup);
		
		Group g = ndex.getGroup(groupId);
		assertEquals(g.getGroupName(), testGroupName);
		assertEquals(g.getExternalId(), groupId);
		assertEquals(g.getDescription(), testGroupDesc);
		
		String testString = "SpecialStringOnlyForABCDEFTesting";
		g.setDescription(testString);
		ndex.updateGroup(g);
		g = ndex.getGroup(groupId);
		assertEquals(g.getDescription(), testString);
		SimpleQuery query = new SimpleQuery();
		query.setSearchString(testString);
		Thread.sleep(3000);
		SolrSearchResult<Group> r = ndex.findGroups(query, 0, 10);
		assertEquals(r.getNumFound(), 1);
		ndex.deleteGroup(groupId);
        thrown1.expect(ObjectNotFoundException.class);
        ndex.getGroup(groupId);
	}
	
	@Test 
	public void testTaskOperators () throws IOException, NdexException {
		UUID taskId = UUID.fromString("ff254008-adfa-11e7-9b0a-06832d634f41");
		Task t = ndex.getTask(taskId);
		assertEquals(t.getExternalId(), taskId);
		
	}
	
    @Test
    public void testCreateCXNetwork() throws IllegalStateException, Exception {
    	
   /* 	NetworkSearchResult r = 
    			ndex.findNetworks("", null, 0, 1000);
    	System.out.println(r);
    	CXSimplePathQuery query = new CXSimplePathQuery ();
    	query.setSearchDepth(1);
    	query.setSearchString("CALC");
    	query.setEdgeLimit(10);
    	Set<String> foo = new TreeSet<>();
    	
    	foo.add(NodesElement.ASPECT_NAME);
    	foo.add(EdgesElement.ASPECT_NAME);
    	foo.add(NetworkAttributesElement.ASPECT_NAME);
    	foo.add(NodeAttributesElement.ASPECT_NAME);
    	query.setAspects(foo);
    	InputStream in0 = ndex.getNeighborhoodAsCXStream("981a352d-8e20-11e5-89b1-f66787c00b17", query);
    	printInputStream(in0);
    	in0.close(); */
    	
   //	InputStream in = ndex.getNetworkAsCXStream("ad78abd0-6e00-11e5-978e-0251251672f9");
 /*   	MetaDataCollection md = CXMetaDataManager.getInstance().createCXMataDataTemplate();  
    	List<String > l = new ArrayList<>(md.size());
    	for (MetaDataElement e : md)
    		l.add(e.getName());
    	InputStream in = ndex.getNetworkAspects("65308e68-8191-11e5-b7f9-0251251672f9", l);
    	printInputStream(in);
    	in.close();  
    	
        in = ndex.getNetworkAspectElements("6dffd124-7cd8-11e5-8e3a-96da26a8cd91", "nodes",10);
    	printInputStream(in);
    	in.close(); 
    	*/
        /*    FileInputStream s = new FileInputStream ( "/Users/chenjing/Downloads/small-corpus-test.cx");
   			
            ndex.updateCXNetwork(UUID.fromString("6e1554f5-7cd8-11e5-8e3a-96da26a8cd91"), s);
            System.out.println("network updated." ) ;
           // s.close(); */
    }

    private static String printInputStream(InputStream is) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

	}

    
    public  void testResteasy() throws UnsupportedEncodingException, IOException
    {
/*          ResteasyProviderFactory factory =
                   ResteasyProviderFactory.getInstance();

          // this line is only needed if you run this as a java console app.
         //  in tomcat and jboss initialization should work without this
          ResteasyProviderFactory.pushContext(javax.ws.rs.ext.Providers.class, factory);


             ResteasyClient client =null;
             Response r = null;
          try {


                 ResteasyClientBuilder resteasyClientBuilder = new
                             ResteasyClientBuilder().providerFactory(factory);

                  client = resteasyClientBuilder.build();
             //     client.register(new BasicAuthentication(_username,_password));

                  // insert the url of the webservice here
               ResteasyWebTarget target = client.target("http://localhost:8080/ndexbio-rest/network/asCX");
               target.register(new BasicAuthentication(_username,_password));
                MultipartFormDataOutput mdo = new MultipartFormDataOutput();

                mdo.addFormData("file1", new FileInputStream(new File(
                       //       "/Users/chenjing/Downloads/5a81ae28-679e-11e5-aba2-2e70fd96076e.cx"
                                "/Users/chenjing/working/cx/ligand.cx"
                                )),
                                MediaType.APPLICATION_OCTET_STREAM_TYPE);

                GenericEntity<MultipartFormDataOutput> entity = new GenericEntity<MultipartFormDataOutput>(mdo) {};

                //Upload File
                r = target.request().post(       Entity.entity(entity, MediaType.MULTIPART_FORM_DATA_TYPE));

                // Read File Response
                String  input =  r.readEntity(String.class);



          System.out.println("DONE'" + input);

         } catch (Exception e) {

                e.printStackTrace();
         }
          finally
          {
                 if (r != null) r.close();
                 if (client != null) client.close();
          }
          */
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
//		boolean b = ndex.checkCredential();
		
//		Assert.assertTrue(b);
		
		
		
		/*
		Network n = ndex.getNetwork("f717cacf-7fbf-11e4-a6f2-90b11c72aefa");
	
		if (n != null)
			System.out.println("foo");
			// example of search.
		List<NetworkSummary> s = ndex.findNetworks("*", true, "Support", 0,3);
		System.out.println(s.get(0).getName());
		
		// example of get server status.
		NdexStatus status = ndex.getServerStatus();
		System.out.println(status.getNetworkCount());*/
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
