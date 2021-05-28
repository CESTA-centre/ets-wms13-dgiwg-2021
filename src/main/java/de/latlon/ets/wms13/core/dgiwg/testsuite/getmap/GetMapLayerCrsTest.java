package de.latlon.ets.wms13.core.dgiwg.testsuite.getmap;

import static de.latlon.ets.core.assertion.ETSAssert.assertContentType;
import static de.latlon.ets.core.assertion.ETSAssert.assertStatusCode;
import static de.latlon.ets.wms13.core.domain.DGIWGWMS.BBOX_PARAM;
import static de.latlon.ets.wms13.core.domain.DGIWGWMS.CRS_PARAM;
import static de.latlon.ets.wms13.core.domain.DGIWGWMS.FORMAT_PARAM;
import static de.latlon.ets.wms13.core.domain.DGIWGWMS.GET_MAP;
import static de.latlon.ets.wms13.core.domain.DGIWGWMS.IMAGE_PNG;
import static de.latlon.ets.wms13.core.domain.DGIWGWMS.LAYERS_PARAM;
import static de.latlon.ets.wms13.core.domain.ProtocolBinding.GET;
import static de.latlon.ets.wms13.core.util.ServiceMetadataUtils.getOperationEndpoint;
import static org.testng.Assert.assertTrue;

import java.net.URI;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactoryConfigurationException;

import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.sun.jersey.api.client.ClientResponse;

import de.latlon.ets.core.error.ErrorMessage;
import de.latlon.ets.core.error.ErrorMessageKey;
import de.latlon.ets.wms13.core.crs.CrsMatcher;
import de.latlon.ets.wms13.core.domain.BoundingBox;
import de.latlon.ets.wms13.core.domain.LayerInfo;

/**
 * Tests if the Layer is requestable with the required CRS.
 * 
 * @author <a href="mailto:stenger@lat-lon.de">Dirk Stenger</a>
 */
public class GetMapLayerCrsTest extends BaseGetMapFixture {

    private static final CrsMatcher CRS_MATCHER = new CrsMatcher();

    private static final String REQUEST_FORMAT = IMAGE_PNG;

    @DataProvider(name = "layerNodes")
    public Object[][] parseLayerNodes( ITestContext testContext )
                    throws XPathFactoryConfigurationException, XPathExpressionException {
        Object[][] layers = new Object[layerInfo.size()][];
        for ( int layerIndex = 0; layerIndex < layerInfo.size(); layerIndex++ ) {
            LayerInfo layer = layerInfo.get( layerIndex );
            layers[layerIndex] = new Object[] { layer };
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
    		+ "Each WMS service instance does not have to provide its data in all these CRSs. "}, description = "Cehcks if GetMap response is in CRS84.", dataProvider = "layerNodes")
    public
                    void wmsGetMapLayerCrs_Mandatory_CRS_84_Supported( LayerInfo layer ) {
        String crs = "CRS:84";
        String bbox = findBboxOrSkipTestIfCrsIsNotSupported( layer, crs );
        String layerName = layer.getLayerName();

        this.reqEntity.addKvp( LAYERS_PARAM, layerName );
        this.reqEntity.addKvp( CRS_PARAM, crs );
        this.reqEntity.addKvp( BBOX_PARAM, bbox );
        this.reqEntity.addKvp( FORMAT_PARAM, REQUEST_FORMAT );

        URI endpoint = getOperationEndpoint( this.wmsCapabilities, GET_MAP, GET );
        ClientResponse rsp = wmsClient.submitRequest( this.reqEntity, endpoint );

        storeResponseImage( rsp, "Requirement5", "Mandatory_CRS_84_Supported_By_Layer_" + layerName, REQUEST_FORMAT );

        assertTrue( rsp.hasEntity(), ErrorMessage.get( ErrorMessageKey.MISSING_XML_ENTITY ) );
        assertStatusCode( rsp.getStatus(), 200 );
        assertContentType( rsp.getHeaders(), REQUEST_FORMAT );
    }

    @Test(groups= {"A  WMS server shall support the following coordinate reference systems, as a minimum: \n"
    		+ "• CRS:84 WGS84 geographic longitude, then latitude, expressed in decimal degrees \n"
    		+ "• EPSG:4326 WGS84 geographic latitude, then longitude, expressed in decimal degrees \n"
    		+ "• EPSG:3395 World Mercator projection Among the following Coordinate Reference Systems, the service shall support all those which validity zone overlaps data published by the service: \n"
    		+ "• UTM projections over WGS84 (north zones)…  EPSG:32601 to EPSG:32660 \n"
    		+ "• UTM projections over  WGS84 (south zones)…  EPSG:32701 to EPSG:32760\n"
    		+ "• UPS projection over WGS84 (north zone)…  EPSG: 5041 UPS projection over WGS84 (south zone)…  EPSG: 5042 \n"
    		+ "Each WMS service instance does not have to provide its data in all these CRSs. "}, description = "Cehcks if GetMap response is in EPSG4326.", dataProvider = "layerNodes")
    public
                    void wmsGetMapLayerCrs_Mandatory_EPSG_4326_Supported( LayerInfo layer ) {
        String crs = "EPSG:4326";
        String bbox = findBboxOrSkipTestIfCrsIsNotSupported( layer, crs );
        String layerName = layer.getLayerName();

        this.reqEntity.addKvp( LAYERS_PARAM, layerName );
        this.reqEntity.addKvp( CRS_PARAM, crs );
        this.reqEntity.addKvp( BBOX_PARAM, bbox );
        this.reqEntity.addKvp( FORMAT_PARAM, REQUEST_FORMAT );

        URI endpoint = getOperationEndpoint( this.wmsCapabilities, GET_MAP, GET );
        ClientResponse rsp = wmsClient.submitRequest( this.reqEntity, endpoint );

        storeResponseImage( rsp, "Requirement5", "Mandatory_EPSG_4326_Supported_By_Layer_" + layerName, REQUEST_FORMAT );

        assertTrue( rsp.hasEntity(), ErrorMessage.get( ErrorMessageKey.MISSING_XML_ENTITY ) );
        assertStatusCode( rsp.getStatus(), 200 );
        assertContentType( rsp.getHeaders(), REQUEST_FORMAT );
    }

    @Test(groups= {"A  WMS server shall support the following coordinate reference systems, as a minimum: \n"
    		+ "• CRS:84 WGS84 geographic longitude, then latitude, expressed in decimal degrees \n"
    		+ "• EPSG:4326 WGS84 geographic latitude, then longitude, expressed in decimal degrees \n"
    		+ "• EPSG:3395 World Mercator projection Among the following Coordinate Reference Systems, the service shall support all those which validity zone overlaps data published by the service: \n"
    		+ "• UTM projections over WGS84 (north zones)…  EPSG:32601 to EPSG:32660 \n"
    		+ "• UTM projections over  WGS84 (south zones)…  EPSG:32701 to EPSG:32760\n"
    		+ "• UPS projection over WGS84 (north zone)…  EPSG: 5041 UPS projection over WGS84 (south zone)…  EPSG: 5042 \n"
    		+ "Each WMS service instance does not have to provide its data in all these CRSs. "}, description = "Cehcks if GetMap response is in EPSG3395.", dataProvider = "layerNodes")
    public
                    void wmsGetMapLayerCrs_Mandatory_EPSG_3395_Supported( LayerInfo layer ) {
        String crs = "EPSG:3395";
        String bbox = findBboxOrSkipTestIfCrsIsNotSupported( layer, crs );
        String layerName = layer.getLayerName();

        this.reqEntity.addKvp( LAYERS_PARAM, layerName );
        this.reqEntity.addKvp( CRS_PARAM, crs );
        this.reqEntity.addKvp( BBOX_PARAM, bbox );
        this.reqEntity.addKvp( FORMAT_PARAM, REQUEST_FORMAT );

        URI endpoint = getOperationEndpoint( this.wmsCapabilities, GET_MAP, GET );
        ClientResponse rsp = wmsClient.submitRequest( this.reqEntity, endpoint );

        storeResponseImage( rsp, "Requirement5", "Mandatory_EPSG_3395_Supported_By_Layer_" + layerName, REQUEST_FORMAT );

        assertTrue( rsp.hasEntity(), ErrorMessage.get( ErrorMessageKey.MISSING_XML_ENTITY ) );
        assertStatusCode( rsp.getStatus(), 200 );
        assertContentType( rsp.getHeaders(), REQUEST_FORMAT );
    }

    @Test(groups= {"A  WMS server shall support the following coordinate reference systems, as a minimum: \n"
    		+ "• CRS:84 WGS84 geographic longitude, then latitude, expressed in decimal degrees \n"
    		+ "• EPSG:4326 WGS84 geographic latitude, then longitude, expressed in decimal degrees \n"
    		+ "• EPSG:3395 World Mercator projection Among the following Coordinate Reference Systems, the service shall support all those which validity zone overlaps data published by the service: \n"
    		+ "• UTM projections over WGS84 (north zones)…  EPSG:32601 to EPSG:32660 \n"
    		+ "• UTM projections over  WGS84 (south zones)…  EPSG:32701 to EPSG:32760\n"
    		+ "• UPS projection over WGS84 (north zone)…  EPSG: 5041 UPS projection over WGS84 (south zone)…  EPSG: 5042 \n"
    		+ "Each WMS service instance does not have to provide its data in all these CRSs. "}, description = "Cehcks if GetMap response is in good CRS when overlapping.", dataProvider = "layerNodes")
    public
                    void wmsGetMapLayerCrs_Conditional_Supported( LayerInfo layer ) {
        List<String> conditionalExpectedCrs = CRS_MATCHER.retrieveOverlappingCrs( layer.getGeographicBbox() );

        for ( String crs : conditionalExpectedCrs ) {
            String bbox = findBboxOrSkipTestIfCrsIsNotSupported( layer, crs );
            String layerName = layer.getLayerName();

            this.reqEntity.addKvp( LAYERS_PARAM, layerName );
            this.reqEntity.addKvp( CRS_PARAM, crs );
            this.reqEntity.addKvp( BBOX_PARAM, bbox );
            this.reqEntity.addKvp( FORMAT_PARAM, REQUEST_FORMAT );

            URI endpoint = getOperationEndpoint( this.wmsCapabilities, GET_MAP, GET );
            ClientResponse rsp = wmsClient.submitRequest( this.reqEntity, endpoint );

            storeResponseImage( rsp, "Requirement5", "Conditional_" + crs.replace( ":", "_" ) + "_Supported_By_Layer_"
                                                     + layerName, REQUEST_FORMAT );

            assertTrue( rsp.hasEntity(), ErrorMessage.get( ErrorMessageKey.MISSING_XML_ENTITY ) );
            assertStatusCode( rsp.getStatus(), 200 );
            assertContentType( rsp.getHeaders(), REQUEST_FORMAT );
        }
    }

    private String findBboxOrSkipTestIfCrsIsNotSupported( LayerInfo layer, String crs ) {
        for ( BoundingBox bbox : layer.getBboxes() ) {
            if ( crs.equals( bbox.getCrs() ) )
                return bbox.getBboxAsString();
        }
        throw new SkipException( "Layer " + layer.getLayerName() + " does not support CRS " + crs
                                 + ", tests are skipped!" );
    }

}