package de.latlon.ets.wms13.core.dgiwg.testsuite.getcapabilities;

import static de.latlon.ets.core.assertion.ETSAssert.assertUriIsResolvable;
import static de.latlon.ets.core.assertion.ETSAssert.assertUrl;
import static org.testng.Assert.assertTrue;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactoryConfigurationException;

import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Tests if the DataUrls are resolvable.
 * 
 * @author <a href="mailto:goltz@lat-lon.de">Lyn Goltz</a>
 */
public class GetCapabilitiesDataUrlTest extends AbstractBaseGetCapabilitiesFixture {

    @DataProvider(name = "dataUrls")
    public Object[][] parseDataUrls( ITestContext testContext )
                    throws XPathFactoryConfigurationException, XPathExpressionException {
        if ( this.wmsCapabilities == null )
            initBaseFixture( testContext );
        NodeList dataUrlNodes = parseDataUrlNodes( wmsCapabilities );
        Object[][] dataUrls = new Object[dataUrlNodes.getLength()][];
        for ( int dataUrlNodeIndex = 0; dataUrlNodeIndex < dataUrlNodes.getLength(); dataUrlNodeIndex++ ) {
            Node dataUrlNode = dataUrlNodes.item( dataUrlNodeIndex );
            String dataUrl = (String) createXPath().evaluate( "//@xlink:href", dataUrlNode, XPathConstants.STRING );
            dataUrls[dataUrlNodeIndex] = new Object[] { dataUrl };
        }
        return dataUrls;
    }
    
    @DataProvider(name = "DataUrlNumber")
	public Object[][] getFeatureListUrlNodesNumber(ITestContext testContext)
			throws XPathFactoryConfigurationException, XPathExpressionException {
		if (this.wmsCapabilities == null)
			initBaseFixture(testContext);
		NodeList dataUrlsNodes = getDataUrlNodes(wmsCapabilities);
		int nbNodes = dataUrlsNodes.getLength();
		Object[][] dataUrls = {{nbNodes}};
		return dataUrls;
	}
	

    @Test(groups="If <DataListURL> element is present, its value shall be a URL to allow access to a list of features available in a particular layer.\n"
    		+ "NOTE :  This URL will relate to the source system and may not be resolvable on all connected/unconnected systems or applications. This requirement is conditional on the <DataListURL> being relevant to the generated service. ", description = "Checks if DataListURL is present in document.", dataProvider = "DataUrlNumber")
	public void wmsCapabilitiesDataListUrlExists(int dataUrlsnumber)
			throws XPathExpressionException, XPathFactoryConfigurationException {
		if(dataUrlsnumber>0)
			assertTrue(dataUrlsnumber>0);
		else
			throw new SkipException("Skipping DataListURL test because no such tag in document.");
	}
    

    @Test(groups="If <DataListURL> element is present, its value shall be a URL to allow access to a list of features available in a particular layer.\n"
    		+ "NOTE :  This URL will relate to the source system and may not be resolvable on all connected/unconnected systems or applications. This requirement is conditional on the <DataListURL> being relevant to the generated service. ", description = "Checks if URL of DataListURL is resolvable.", dataProvider = "dataUrls", dependsOnMethods="wmsCapabilitiesDataListUrlExists")
    public
                    void wmsCapabilitiesDataUrlIsResolvable( String dataUrl )
                                    throws XPathExpressionException, XPathFactoryConfigurationException {
        assertUrl( dataUrl );
        assertUriIsResolvable( dataUrl );
    }

    private NodeList parseDataUrlNodes( Document entity )
                    throws XPathFactoryConfigurationException, XPathExpressionException {
        String xPath = "//wms:Layer/wms:DataURL/wms:OnlineResource";
        XPath xpath = createXPath();
        return (NodeList) xpath.evaluate( xPath, entity, XPathConstants.NODESET );
    }
    
    private NodeList getDataUrlNodes(Document entity)
			throws XPathFactoryConfigurationException, XPathExpressionException {
		String xPathFeatureListUrls = "//wms:Layer/wms:DataURL";
		XPath xpath = createXPath();
		return (NodeList) xpath.evaluate(xPathFeatureListUrls, entity, XPathConstants.NODESET);
	}

    private XPath createXPath()
                    throws XPathFactoryConfigurationException {
        XPathFactory factory = XPathFactory.newInstance( XPathConstants.DOM_OBJECT_MODEL );
        XPath xpath = factory.newXPath();
        xpath.setNamespaceContext( NS_BINDINGS );
        return xpath;
    }

}