package de.latlon.ets.wms13.core.util.interactive;

import static de.latlon.ets.wms13.core.domain.DGIWGWMS.GET_FEATURE_INFO;
import static de.latlon.ets.wms13.core.domain.DGIWGWMS.BBOX_PARAM;
import static de.latlon.ets.wms13.core.domain.DGIWGWMS.GET_CAPABILITIES;
import static de.latlon.ets.wms13.core.domain.DGIWGWMS.GET_MAP;
import static de.latlon.ets.wms13.core.domain.DGIWGWMS.LAYERS_PARAM;
import static de.latlon.ets.wms13.core.domain.DGIWGWMS.QUERY_LAYERS_PARAM;
import static de.latlon.ets.wms13.core.domain.ProtocolBinding.GET;
import static de.latlon.ets.wms13.core.util.ServiceMetadataUtils.getOperationEndpoint;
import static de.latlon.ets.wms13.core.util.ServiceMetadataUtils.parseLayerInfo;
import static de.latlon.ets.wms13.core.util.request.WmsRequestBuilder.buildGetFeatureInfoRequest;
import static de.latlon.ets.wms13.core.util.request.WmsRequestBuilder.buildGetMapRequest;
import static org.testng.Assert.assertNotNull;
import static de.latlon.ets.wms13.core.util.request.WmsRequestBuilder.buildGetCapabilitiesRequest;

import java.net.URI;
import java.util.List;

import javax.ws.rs.core.UriBuilder;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;

import de.latlon.ets.core.util.URIUtils;
import de.latlon.ets.wms13.core.client.WmsKvpRequest;
import de.latlon.ets.wms13.core.domain.DGIWGWMS;
import de.latlon.ets.wms13.core.domain.LayerInfo;
import de.latlon.ets.wms13.core.util.request.WmsRequestBuilder;

/**
 * Contains methods useful for interactive ctl tests.
 * 
 * @author <a href="mailto:goltz@lat-lon.de">Lyn Goltz</a>
 */
public final class InteractiveTestUtils {

    private static final String UNKNOWN_LAYER_FOR_TESTING = "UNKNOWN_LAYER_FOR_TESTING";
    private static final String NOT_FOUND = " | ! No suitable request found ";

    private InteractiveTestUtils() {
    }

    /**
     * Creates a GetFeatureInfo request.
     * 
     * @param wmsCapabilitiesUrl
     *            the url of the WMS capabilities, never <code>null</code>
     * @return a GetFeatureInfo request, never <code>null</code>
     */
    public static String retrieveGetFeatureInfoRequest( String wmsCapabilitiesUrl ) {
        Document wmsCapabilities = readCapabilities( wmsCapabilitiesUrl );
        URI getFeatureInfoEndpoint = getOperationEndpoint( wmsCapabilities, GET_FEATURE_INFO, GET );
        List<LayerInfo> layerInfos = parseLayerInfo( wmsCapabilities );

        WmsKvpRequest getFeatureInfoRequest = WmsRequestBuilder.buildGetFeatureInfoRequest( wmsCapabilities, layerInfos );
        
        if(getFeatureInfoRequest!=null){
			if(getFeatureInfoRequest.getKvpValue(NOT_FOUND)!=null){
				return (getFeatureInfoRequest.getKvpValue(NOT_FOUND)+ NOT_FOUND);
			}else {
				return createUri(getFeatureInfoEndpoint, getFeatureInfoRequest);
			}
		}else {
			return ("GetFeatureInfo operation is not supported by the server !");
		}
        
    }

    /**
     * Creates a GetFeatureInfo request with unsupported layer.
     * 
     * @param wmsCapabilitiesUrl
     *            the url of the WMS capabilities, never <code>null</code>
     * @return a GetFeatureInfo request with unsupported layer, never <code>null</code>
     */
    public static String retrieveInvalidGetFeatureInfoRequest( String wmsCapabilitiesUrl ) {
        Document wmsCapabilities = readCapabilities( wmsCapabilitiesUrl );
        URI getFeatureInfoEndpoint = getOperationEndpoint( wmsCapabilities, GET_FEATURE_INFO, GET );
        List<LayerInfo> layerInfos = parseLayerInfo( wmsCapabilities );

        WmsKvpRequest getFeatureInfoRequest = buildGetFeatureInfoRequest( wmsCapabilities, layerInfos );
               
        if(getFeatureInfoRequest!=null){
			if(getFeatureInfoRequest.getKvpValue(NOT_FOUND)!=null){
				return (getFeatureInfoRequest.getKvpValue(NOT_FOUND)+ NOT_FOUND);
			}else {
				getFeatureInfoRequest.addKvp( LAYERS_PARAM, UNKNOWN_LAYER_FOR_TESTING );
		        getFeatureInfoRequest.addKvp( QUERY_LAYERS_PARAM, UNKNOWN_LAYER_FOR_TESTING );
		        return createUri( getFeatureInfoEndpoint, getFeatureInfoRequest );
			}
		}else {
			return ("GetFeatureInfo operation is not supported by the server !");
		}
    }

    /**
     * Creates a GetMap request with unsupported layer.
     * 
     * @param wmsCapabilitiesUrl
     *            the url of the WMS capabilities, never <code>null</code>
     * @return a GetMap request with unsupported layer, never <code>null</code>
     */
    public static String retrieveInvalidGetMapRequest( String wmsCapabilitiesUrl ) {
        Document wmsCapabilities = readCapabilities( wmsCapabilitiesUrl );
        URI getMapEndpoint = getOperationEndpoint( wmsCapabilities, GET_MAP, GET );
        List<LayerInfo> layerInfos = parseLayerInfo( wmsCapabilities );

        WmsKvpRequest getMapRequest = buildGetMapRequest( wmsCapabilities, layerInfos );
        
        if(getMapRequest!=null){
			if(getMapRequest.getKvpValue(NOT_FOUND)!=null){
				return (getMapRequest.getKvpValue(NOT_FOUND)+ NOT_FOUND);
			}else {
				getMapRequest.addKvp( LAYERS_PARAM, UNKNOWN_LAYER_FOR_TESTING );
		        return createUri( getMapEndpoint, getMapRequest );
			}
		}else {
			return ("GetMap operation is not supported by the server !");
		}

    }
    
    /**
     * Creates a GetCapabilities request with unsupported layer.
     * 
     * @param wmsCapabilitiesUrl
     *            the url of the WMS capabilities, never <code>null</code>
     * @return a GetCapabilities request with unsupported layer, never <code>null</code>
     */
    public static String retrieveInvalidGetCapabilitiesRequest( String wmsCapabilitiesUrl ) {
    	Document wmsCapabilities = readCapabilities( wmsCapabilitiesUrl );
        URI getCapabilitiesEndpoint = getOperationEndpoint( wmsCapabilities, GET_CAPABILITIES, GET );
        List<LayerInfo> layerInfos = parseLayerInfo( wmsCapabilities );

        WmsKvpRequest getCapabilitiesRequest = buildGetCapabilitiesRequest( wmsCapabilities, layerInfos );
        String dummy_bbox = "dummy";
        getCapabilitiesRequest.addKvp(BBOX_PARAM, dummy_bbox);
        return createUri( getCapabilitiesEndpoint, getCapabilitiesRequest );
    }
    
    
    
    /**
     * Get title of GetCapabilities document.
     * 
     * @param wmsCapabilitiesUrl
     *            the url of the WMS capabilities, never <code>null</code>
     * @return a the title of capabilities document.
     */
    public static String retrieveTitleInLocaleLanguage( String wmsCapabilitiesUrl ) {
    	
    	//System.out.println("!!!! wmsCapabilitiesUrl = " + wmsCapabilitiesUrl);
    	//Document wmsCapabilities = readCapabilities( wmsCapabilitiesUrl );
    	
        //List<String> sentence = parseCapabilityTitles( wmsCapabilities );	
        //System.out.println("!!!! LA ok" + sentence);
        //return sentence.get(0);
    	return null;
    }

    private static String createUri( URI getFeatureInfoEndpoint, WmsKvpRequest getFeatureInfoRequest ) {
        String queryString = getFeatureInfoRequest.asQueryString();
        URI requestURI = UriBuilder.fromUri( getFeatureInfoEndpoint ).replaceQuery( queryString ).build();
        return requestURI.toString();
    }

    private static Document readCapabilities( String wmsCapabilitiesUrl ) {
        URI wmsURI = URI.create( wmsCapabilitiesUrl );
        Document doc = null;
        try {
            doc = URIUtils.resolveURIAsDocument( wmsURI );
            if ( !doc.getDocumentElement().getLocalName().equals( DGIWGWMS.WMS_CAPABILITIES ) ) {
                throw new RuntimeException( "Did not receive WMS capabilities document: "
                                            + doc.getDocumentElement().getNodeName() );
            }
        } catch ( Exception ex ) {
            throw new RuntimeException( "Failed to parse resource located at " + wmsURI, ex );
        }
        return doc;
    }

}