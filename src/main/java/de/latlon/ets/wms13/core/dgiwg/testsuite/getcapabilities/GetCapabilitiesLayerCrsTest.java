package de.latlon.ets.wms13.core.dgiwg.testsuite.getcapabilities;

import static de.latlon.ets.wms13.core.util.ServiceMetadataUtils.parseRequestableLayerNodes;
import static org.testng.Assert.assertNotNull;

import java.util.List;

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

import de.latlon.ets.wms13.core.crs.CrsMatcher;
import de.latlon.ets.wms13.core.domain.BoundingBox;
import de.latlon.ets.wms13.core.util.ServiceMetadataUtils;

/**
 * Tests if the Layer supports the required CRS.
 * 
 * @author <a href="mailto:goltz@lat-lon.de">Lyn Goltz</a>
 */
public class GetCapabilitiesLayerCrsTest extends AbstractBaseGetCapabilitiesFixture {

    private static final CrsMatcher CRS_MATCHER = new CrsMatcher();

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

    @Test(groups= {"A  WMS server shall support the following coordinate reference systems, as a minimum: \n"
    		+ "• CRS:84 WGS84 geographic longitude, then latitude, expressed in decimal degrees \n"
    		+ "• EPSG:4326 WGS84 geographic latitude, then longitude, expressed in decimal degrees \n"
    		+ "• EPSG:3395 World Mercator projection Among the following Coordinate Reference Systems, the service shall support all those which validity zone overlaps data published by the service: \n"
    		+ "• UTM projections over WGS84 (north zones)…  EPSG:32601 to EPSG:32660 \n"
    		+ "• UTM projections over  WGS84 (south zones)…  EPSG:32701 to EPSG:32760\n"
    		+ "• UPS projection over WGS84 (north zone)…  EPSG: 5041 UPS projection over WGS84 (south zone)…  EPSG: 5042 \n"
    		+ "Each WMS service instance does not have to provide its data in all these CRSs. "}, description = "Checks CRS:84", dataProvider = "layerNodes")
    public
                    void wmsCapabilitiesLayerCrs_Mandatory_CRS_84_Supported( Node layerNode, String name, String title )
                                    throws XPathExpressionException, XPathFactoryConfigurationException {
        assertCrs( layerNode, "CRS:84" );
    }

    @Test(groups= {"A  WMS server shall support the following coordinate reference systems, as a minimum: \n"
    		+ "• CRS:84 WGS84 geographic longitude, then latitude, expressed in decimal degrees \n"
    		+ "• EPSG:4326 WGS84 geographic latitude, then longitude, expressed in decimal degrees \n"
    		+ "• EPSG:3395 World Mercator projection Among the following Coordinate Reference Systems, the service shall support all those which validity zone overlaps data published by the service: \n"
    		+ "• UTM projections over WGS84 (north zones)…  EPSG:32601 to EPSG:32660 \n"
    		+ "• UTM projections over  WGS84 (south zones)…  EPSG:32701 to EPSG:32760\n"
    		+ "• UPS projection over WGS84 (north zone)…  EPSG: 5041 UPS projection over WGS84 (south zone)…  EPSG: 5042 \n"
    		+ "Each WMS service instance does not have to provide its data in all these CRSs. "}, description = "Checks EPSG:4326", dataProvider = "layerNodes")
    public
                    void wmsCapabilitiesLayerCrs_Mandatory_EPSG_4326_Supported( Node layerNode, String name,
                                                                                String title )
                                    throws XPathExpressionException, XPathFactoryConfigurationException {
        assertCrs( layerNode, "EPSG:4326" );
    }

    @Test(groups= {"A  WMS server shall support the following coordinate reference systems, as a minimum: \n"
    		+ "• CRS:84 WGS84 geographic longitude, then latitude, expressed in decimal degrees \n"
    		+ "• EPSG:4326 WGS84 geographic latitude, then longitude, expressed in decimal degrees \n"
    		+ "• EPSG:3395 World Mercator projection Among the following Coordinate Reference Systems, the service shall support all those which validity zone overlaps data published by the service: \n"
    		+ "• UTM projections over WGS84 (north zones)…  EPSG:32601 to EPSG:32660 \n"
    		+ "• UTM projections over  WGS84 (south zones)…  EPSG:32701 to EPSG:32760\n"
    		+ "• UPS projection over WGS84 (north zone)…  EPSG: 5041 UPS projection over WGS84 (south zone)…  EPSG: 5042 \n"
    		+ "Each WMS service instance does not have to provide its data in all these CRSs. "}, description = "Checks EPSG:3395", dataProvider = "layerNodes")
    public
                    void wmsCapabilitiesLayerCrs_Mandatory_EPSG_3395_Supported( Node layerNode, String name,
                                                                                String title )
                                    throws XPathExpressionException, XPathFactoryConfigurationException {
        assertCrs( layerNode, "EPSG:3395" );
    }

    @Test(groups= {"A  WMS server shall support the following coordinate reference systems, as a minimum: \n"
    		+ "• CRS:84 WGS84 geographic longitude, then latitude, expressed in decimal degrees \n"
    		+ "• EPSG:4326 WGS84 geographic latitude, then longitude, expressed in decimal degrees \n"
    		+ "• EPSG:3395 World Mercator projection Among the following Coordinate Reference Systems, the service shall support all those which validity zone overlaps data published by the service: \n"
    		+ "• UTM projections over WGS84 (north zones)…  EPSG:32601 to EPSG:32660 \n"
    		+ "• UTM projections over  WGS84 (south zones)…  EPSG:32701 to EPSG:32760\n"
    		+ "• UPS projection over WGS84 (north zone)…  EPSG: 5041 UPS projection over WGS84 (south zone)…  EPSG: 5042 \n"
    		+ "Each WMS service instance does not have to provide its data in all these CRSs. "}, description = "Checks the support of CRS codes when boundingbox overlap.", dataProvider = "layerNodes")
    public
                    void wmsCapabilitiesLayerCrs_Conditional_Supported( Node layerNode, String name, String title )
                                    throws XPathExpressionException, XPathFactoryConfigurationException {
        BoundingBox geographicBoundingBox = ServiceMetadataUtils.parseGeographicBoundingBox( layerNode );
        List<String> conditionalExpectedCrs = CRS_MATCHER.retrieveOverlappingCrs( geographicBoundingBox );

        for ( String expectedCrs : conditionalExpectedCrs ) {
            assertCrs( layerNode, expectedCrs );
        }
    }

    private void assertCrs( Node layerNode, String crs )
                    throws XPathExpressionException, XPathFactoryConfigurationException {
        String xPath = String.format( "ancestor-or-self::wms:Layer/wms:CRS[text() = '%s']", crs );
        Node crsNode = (Node) createXPath().evaluate( xPath, layerNode, XPathConstants.NODE );
        assertNotNull( crsNode, "CRS " + crs + " is not supported." );
    }

    private XPath createXPath()
                    throws XPathFactoryConfigurationException {
        XPathFactory factory = XPathFactory.newInstance( XPathConstants.DOM_OBJECT_MODEL );
        XPath xpath = factory.newXPath();
        xpath.setNamespaceContext( NS_BINDINGS );
        return xpath;
    }

}