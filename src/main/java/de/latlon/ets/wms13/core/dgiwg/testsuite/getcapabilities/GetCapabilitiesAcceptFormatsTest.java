package de.latlon.ets.wms13.core.dgiwg.testsuite.getcapabilities;

import static de.latlon.ets.wms13.core.domain.DGIWGWMS.GET_CAPABILITIES;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactoryConfigurationException;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import de.latlon.ets.wms13.core.TestRunArg;
import de.latlon.ets.wms13.core.util.ServiceMetadataUtils;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Tests if the AcceptFormats values are resolvable.
 * 
 * @author <a href="mailto:ombeline.le-gall@cesta.fr">Ombeline Le Gall</a>
 */

public class GetCapabilitiesAcceptFormatsTest extends AbstractBaseGetCapabilitiesFixture{

	@DataProvider(name = "acceptFormats")
    public Object[][] parseAcceptFormats( ITestContext testContext )
                    throws XPathFactoryConfigurationException, XPathExpressionException, SAXException, IOException {
		
		String value = (testContext.getSuite().getParameter(TestRunArg.WMS.toString())).toLowerCase();
		
		Map<String, List<String>> params = splitQuery(new URL(value));
		
		List<String> acceptFormats = new ArrayList<>();
		
		if(params==null) {
			acceptFormats.add("text/xml");
		}else if(!params.containsKey("ACCEPTFORMATS") ) {
			acceptFormats.add("text/xml");
		}else {
			String p = params.get("ACCEPTFORMATS").get(0);
			acceptFormats = Arrays.asList(p.split("\\s*,\\s*"));
			
			if(!acceptFormats.contains("text/xml"))
					acceptFormats.add("text/xml");
		}

		String docMimeType = getResourceType(testContext).split(";")[0];
/*
		URL wmsurl = new URL(value);
		URLConnection urlconn = wmsurl.openConnection();
		HttpURLConnection con = (HttpURLConnection) urlconn;
		con.connect();
		String docMimeType =  con.getHeaderField("Content-Type");
*/		
		List<String> supportedFormats = ServiceMetadataUtils.parseSupportedFormats( wmsCapabilities, GET_CAPABILITIES );

    	String desiredMimeType = "text/xml";
    	for(String search : acceptFormats) {
    		if(supportedFormats.contains(search.toLowerCase())) {
    			desiredMimeType = search;
    			break;
    		}
    	}
		
        Object[][] checkFormat = new Object[][]{ {docMimeType, desiredMimeType} };
       
		return checkFormat;
	}
	
	@Test(description = "DGIWG - Web Map Service 1.3 Profile, Requirement 22", dataProvider = "acceptFormats")
    public void wmsCapabilitiesFormatIsAccepted(String actual, String expected) {
    	assertEquals(actual, expected);
	}

	  /**
	   * Retrieves parameters and their value(s) of request.
	   * @param url nc
	   * @throws UnsupportedEncodingException nc
	   * @return query_pairs nc
	   */
	public static Map<String, List<String>> splitQuery(URL url) throws UnsupportedEncodingException {
		
		  final Map<String, List<String>> query_pairs = new LinkedHashMap<String, List<String>>();
		  try {
			  final String[] pairs = url.getQuery().split("&");
			  for (String pair : pairs) {
			    final int idx = pair.indexOf("=");
			    String key_un = idx > 0 ? URLDecoder.decode(pair.substring(0, idx), "UTF-8") : pair;
			    final String key = key_un.toUpperCase();
			    if (!query_pairs.containsKey(key)) {
			      query_pairs.put(key, new LinkedList<String>());
			    }
			    final String value = idx > 0 && pair.length() > idx + 1 ? URLDecoder.decode(pair.substring(idx + 1), "UTF-8") : null;
			    query_pairs.get(key).add(value);
			  }
		  }
		  catch(Exception e) {
			  return null;
		  }
		  return query_pairs;
		}

	/**
	   * Retrieves MIME type of the resource pointed by the URL.
	   * @param testContext nc
	   * @return mimeType nc
	   */
	public String getResourceType(ITestContext testContext) {
		String wmsRef = testContext.getSuite().getParameter(TestRunArg.WMS.toString());
	    URL wmsurl = null;
		try {
			wmsurl = new URL(wmsRef);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
	    String mimeType = null;
		try {
			mimeType = wmsurl.openConnection().getContentType();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mimeType;
	}

}
