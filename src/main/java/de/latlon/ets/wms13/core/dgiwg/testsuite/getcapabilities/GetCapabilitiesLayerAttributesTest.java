package de.latlon.ets.wms13.core.dgiwg.testsuite.getcapabilities;

import static de.latlon.ets.core.assertion.ETSAssert.assertXPath;
import static de.latlon.ets.wms13.core.util.ServiceMetadataUtils.parseRequestableLayerNodes;
import static org.testng.Assert.assertTrue;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactoryConfigurationException;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Tests if the Layer contains the expected attributes.
 * 
 * @author <a href="mailto:goltz@lat-lon.de">Lyn Goltz</a>
 */
public class GetCapabilitiesLayerAttributesTest extends AbstractBaseGetCapabilitiesFixture {

    @DataProvider(name = "layerNodes")
    public Object[][] parseLayerNodes( ITestContext testContext )
                    throws XPathFactoryConfigurationException, XPathExpressionException {
        if ( this.wmsCapabilities == null )
            initBaseFixture( testContext );
        NodeList layerNodes = parseRequestableLayerNodes( wmsCapabilities );
        Object[][] layers = new Object[layerNodes.getLength()][];
        for ( int layerNodeIndex = 0; layerNodeIndex < layerNodes.getLength(); layerNodeIndex++ ) {
            Node layerNode = layerNodes.item( layerNodeIndex );
            String name = (String) createXPath().evaluate( "wms:Name", layerNode, XPathConstants.STRING );
            String title = (String) createXPath().evaluate( "wms:Title", layerNode, XPathConstants.STRING );
            layers[layerNodeIndex] = new Object[] { layerNode, name, title };
        }
        return layers;
    }

    @Test(groups="A WMS server shall provide the Layer Attributes with following restrictions on their values : noSubsets (0, false), fixedWidth (0), FixedHeight (0).", description = "Checks if noSubsets, fixedWidth and FixedHeight are present.", dataProvider = "layerNodes")
    public
                    void wmsCapabilitiesLayerAttributesExists( Node layerNode, String name, String title )
                                    throws XPathExpressionException, XPathFactoryConfigurationException {
        String attributes = "@noSubsets and @fixedWidth and @fixedHeight";
        assertXPath( attributes, layerNode, NS_BINDINGS );
    }
    
    @Test(groups="A WMS server shall provide the Layer Attributes with following restrictions on their values : noSubsets (0, false), fixedWidth (0), FixedHeight (0).", description = "Checks if noSubsets, fixedWidth and FixedHeight have valid restrictions.", dataProvider = "layerNodes", dependsOnMethods="wmsCapabilitiesLayerAttributesExists")
    public
                    void validAttributesValues( Node layerNode, String name, String title )
                                    throws XPathExpressionException, XPathFactoryConfigurationException {
        
        String noSubsetsValue = (String) createXPath().evaluate( "@noSubsets", layerNode, XPathConstants.STRING );
        String fixedWidthValue = (String) createXPath().evaluate( "@fixedWidth", layerNode, XPathConstants.STRING );
        String fixedHeightValue = (String) createXPath().evaluate( "@fixedHeight", layerNode, XPathConstants.STRING );
        
        assertTrue( noSubsetsValue.equals("0"), "noSubsetsValue equals " + noSubsetsValue + " whereas it shall be 0.");
        assertTrue( fixedWidthValue.equals("0"), "fixedWidthValue equals " + fixedWidthValue + " whereas it shall be 0.");
        assertTrue( fixedHeightValue.equals("0"), "fixedHeightValue equals " + fixedHeightValue + " whereas it shall be 0.");
        
    }

    private XPath createXPath()
                    throws XPathFactoryConfigurationException {
        XPathFactory factory = XPathFactory.newInstance( XPathConstants.DOM_OBJECT_MODEL );
        XPath xpath = factory.newXPath();
        xpath.setNamespaceContext( NS_BINDINGS );
        return xpath;
    }

}