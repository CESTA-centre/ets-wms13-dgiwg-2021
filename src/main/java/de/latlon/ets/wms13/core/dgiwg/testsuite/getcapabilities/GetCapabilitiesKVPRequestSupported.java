package de.latlon.ets.wms13.core.dgiwg.testsuite.getcapabilities;

import static de.latlon.ets.core.assertion.ETSAssert.assertStatusCode;
import static de.latlon.ets.wms13.core.domain.DGIWGWMS.GET_CAPABILITIES;
import static de.latlon.ets.wms13.core.domain.DGIWGWMS.INFO_FORMAT_PARAM;
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

public class GetCapabilitiesKVPRequestSupported extends AbstractBaseGetCapabilitiesFixture{

	@Test(groups="A WMS server shall support HTTP GET for all operations provided by the server, with parameters encoded in KVP.", description="Checks if KVP GetFeatureInfo request is supported.")
	public void getFeatureInfoKVPrequestIsSupported(ITestContext testContext) throws SOAPException {
		URI endpoint = ServiceMetadataUtils.getOperationEndpoint( this.wmsCapabilities, GET_CAPABILITIES,
                ProtocolBinding.GET );
		this.reqEntity.addKvp( INFO_FORMAT_PARAM, "text/xml" );
        ClientResponse rsp = wmsClient.submitRequest( this.reqEntity, endpoint );

        assertTrue( rsp.hasEntity(), ErrorMessage.get( ErrorMessageKey.MISSING_XML_ENTITY ) );
        assertStatusCode( rsp.getStatus(), 200 );
	}
	
}
