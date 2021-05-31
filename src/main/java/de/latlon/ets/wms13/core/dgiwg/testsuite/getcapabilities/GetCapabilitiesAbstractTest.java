package de.latlon.ets.wms13.core.dgiwg.testsuite.getcapabilities;

import static de.latlon.ets.wms13.core.domain.DGIWGWMS.GET_CAPABILITIES;
import static org.testng.Assert.assertTrue;

import java.net.URI;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactoryConfigurationException;

import org.testng.annotations.Test;
import org.w3c.dom.Document;

import com.sun.jersey.api.client.ClientResponse;

import de.latlon.ets.wms13.core.domain.ProtocolBinding;
import de.latlon.ets.wms13.core.util.ServiceMetadataUtils;

/**
 * Tests if the capabilities contains a valid value for Abstract.
 * 
 * @author <a href="mailto:goltz@lat-lon.de">Lyn Goltz</a>
 */
public class GetCapabilitiesAbstractTest extends AbstractBaseGetCapabilitiesFixture {

    private static final String EXPECTED_ABSTRACT = "This service implements the WMS 1.3 STANAG 6523 Ed.2 profile";

    @Test(groups="If a WMS server is providing services to a coalition mission federated network, in support of operations or an exercise, it shall include the following information in the <abstract> element of the service metadata: \"This service implements the WMS 1.3 STANAG 6523 Ed.2 profile\". The provision of these metadata elements are optional for a WMS server which is providing services across one single non-mission network.", description = "Checks if Abstract content corresponds to specific string.")
    public void wmsCapabilitiesAbstractContainsProfile()
                    throws XPathFactoryConfigurationException, XPathExpressionException {
        URI endpoint = ServiceMetadataUtils.getOperationEndpoint( this.wmsCapabilities, GET_CAPABILITIES,
                                                                  ProtocolBinding.GET );
        ClientResponse rsp = wmsClient.submitRequest( this.reqEntity, endpoint );

        String abstractValue = parseAbstract( rsp );
        assertTrue( abstractValue.contains( EXPECTED_ABSTRACT ), "Abstract is not valid, must contain the string '"
                                                                + EXPECTED_ABSTRACT + " but is '" + abstractValue + "'" );
    }

    private String parseAbstract( ClientResponse rsp )
                    throws XPathFactoryConfigurationException, XPathExpressionException {
        String xPathAbstract = "//wms:WMS_Capabilities/wms:Service/wms:Abstract";
        XPathFactory factory = XPathFactory.newInstance( XPathConstants.DOM_OBJECT_MODEL );
        XPath xpath = factory.newXPath();
        xpath.setNamespaceContext( NS_BINDINGS );
        return (String) xpath.evaluate( xPathAbstract, rsp.getEntity( Document.class ), XPathConstants.STRING );
    }

}