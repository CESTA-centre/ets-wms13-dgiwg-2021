package de.latlon.ets.wms13.core.dgiwg.testsuite.getcapabilities;

import static de.latlon.ets.core.assertion.ETSAssert.assertXPath;

import javax.xml.soap.SOAPException;

import org.testng.annotations.Test;

/**
 * Tests if the capabilities provides all mandatory and optional service metadata elements.
 * 
 * @author <a href="mailto:goltz@lat-lon.de">Lyn Goltz</a>
 */
public class GetCapabilitiesContentTest extends AbstractBaseGetCapabilitiesFixture {

    @Test(groups="If a WMS server is providing services to a coalition mission federated network, in support of operations or an exercise, it shall provide \"ContactInformation\", \"AccessConstraints\" and \"KeywordList\" elements. The provision of these metadata elements are optional for a WMS server which is providing services across one single non-mission network.", description = "Checks if KeywordList is provided.")
    public void wmsCapabilitiesKeywordListExists()
                    throws SOAPException {
        String xPathXml = "//wms:WMS_Capabilities/wms:Service/wms:KeywordList != ''";
        assertXPath( xPathXml, wmsCapabilities, NS_BINDINGS );
    }

    @Test(groups="If a WMS server is providing services to a coalition mission federated network, in support of operations or an exercise, it shall provide \"ContactInformation\", \"AccessConstraints\" and \"KeywordList\" elements. The provision of these metadata elements are optional for a WMS server which is providing services across one single non-mission network.", description = "Checks if ContactInformation is provided.")
    public void wmsCapabilitiesContactInformationExists()
                    throws SOAPException {
        String xPathXml = "//wms:WMS_Capabilities/wms:Service/wms:ContactInformation != ''";
        assertXPath( xPathXml, wmsCapabilities, NS_BINDINGS );
    }


    @Test(groups="If a WMS server is providing services to a coalition mission federated network, in support of operations or an exercise, it shall provide \"ContactInformation\", \"AccessConstraints\" and \"KeywordList\" elements. The provision of these metadata elements are optional for a WMS server which is providing services across one single non-mission network.", description = "Checks if AccessConstraints is provided.")
    public void wmsCapabilitiesAccessConstraintsExists()
                    throws SOAPException {
        String xPathXml = "//wms:WMS_Capabilities/wms:Service/wms:AccessConstraints/text() != ''";
        assertXPath( xPathXml, wmsCapabilities, NS_BINDINGS );
    }

}