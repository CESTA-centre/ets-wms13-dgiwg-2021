package de.latlon.ets.wms13.core.dgiwg.testsuite.getcapabilities;

import static de.latlon.ets.core.assertion.ETSAssert.assertXPath;

import javax.xml.soap.SOAPException;

import org.testng.annotations.Test;

public class GetCapabilitiesAbstractExists extends AbstractBaseGetCapabilitiesFixture{

    @Test(groups= {"A WMS server shall provide an \"Abstract\" at the service level, in the in the GetCapabilities response document."}, description = "Checks if Abstract exists.")
    public void wmsCapabilitiesAbstractExists()
                    throws SOAPException {
        String xPathXml = "//wms:WMS_Capabilities/wms:Service/wms:Abstract/text() != ''";
        assertXPath( xPathXml, wmsCapabilities, NS_BINDINGS );
    }

}
