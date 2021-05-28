package de.latlon.ets.wms13.core.dgiwg.testsuite.getmap;

import static de.latlon.ets.core.assertion.ETSAssert.assertStatusCode;
import static de.latlon.ets.wms13.core.domain.DGIWGWMS.GET_MAP;
import static de.latlon.ets.wms13.core.domain.DGIWGWMS.TRANSPARENT_PARAM;
import static org.testng.Assert.assertTrue;

import java.net.URI;

import javax.xml.soap.SOAPException;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.sun.jersey.api.client.ClientResponse;

import de.latlon.ets.core.error.ErrorMessage;
import de.latlon.ets.core.error.ErrorMessageKey;
import de.latlon.ets.wms13.core.domain.ProtocolBinding;
import de.latlon.ets.wms13.core.util.ServiceMetadataUtils;



public class GetMapKVPRequestSupported extends BaseGetMapFixture{

	
	@Test(groups="A WMS server shall support HTTP GET for all operations provided by the server, with parameters encoded in KVP.", description="Checks if KVP GetMap request is supported.")
	public void getMapKVPrequestIsSupported(ITestContext testContext) throws SOAPException {

		URI endpoint = ServiceMetadataUtils.getOperationEndpoint( this.wmsCapabilities, GET_MAP, ProtocolBinding.GET );

        this.reqEntity.addKvp( TRANSPARENT_PARAM, "FALSE" );
        ClientResponse rsp = wmsClient.submitRequest( this.reqEntity, endpoint );


        assertTrue( rsp.hasEntity(), ErrorMessage.get( ErrorMessageKey.MISSING_XML_ENTITY ) );
        assertStatusCode( rsp.getStatus(), 200 );
	}

}
