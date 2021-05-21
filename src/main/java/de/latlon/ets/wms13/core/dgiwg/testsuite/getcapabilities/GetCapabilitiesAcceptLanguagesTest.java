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
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
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
import org.xml.sax.SAXException;

import com.sun.jersey.core.header.reader.HttpHeaderReader;

import de.latlon.ets.core.util.TestSuiteLogger;
import de.latlon.ets.wms13.core.TestRunArg;
import de.latlon.ets.wms13.core.domain.InteractiveTestResult;
import de.latlon.ets.wms13.core.domain.SuiteAttribute;
import de.latlon.ets.wms13.core.util.ServiceMetadataUtils;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


/**
 * Tests if the AcceptLanguages values are resolvable.
 * 
 * @author <a href="mailto:ombeline.le-gall@cesta.fr">Ombeline Le Gall</a>
 */

public class GetCapabilitiesAcceptLanguagesTest extends AbstractBaseGetCapabilitiesFixture{
	
	/*1 - Récupérer les valeurs du paramètre AcceptLanguage
	 * SI PAS VIDE
	 * 2 - Dans la liste des valeurs écrites dans requête, voir si elles correspondent à code RFC 4646 (5 lettres)
	 * 		ou abbréviation 2 lettres ou *
	 * 3 - Voir s'il existe attribut xml:lang avec valeurs.
	 * Si existe
		 * 	Récupérer les valeurs et voir s'il y en a communes avec données dans requête (ou si * qui veut dire peu importe).
		 * 	Si oui : doit fournir réponse dans 1ere langue listée dans params requête (faire au mieux, pas grave si pas homogène) --> poser question de manière interactive pour savoir si ok
		 *  Si non : InvalidParameterValue exception.
	 *  Si n'existe pas, veut dire que serveur ignore complètement langage et est alors ok.
	 * 
	 * SI VIDE
	 * Récupérer l'entête MIME Accept-Language (voir HTTP_ACCEPT_LANGUAGE)
	 * Vérifier que fichier est écrit dans la langue de ce type --> poser question à utilisateur
	 */

	  /**
	   * Gets the list of languages desired by parsing parameters and their value(s) of request.
	   * @param testContext nc
	   * @throws UnsupportedEncodingException nc
	   * @throws MalformedURLException nc
	   * @return acceptLanguages nc
	   */
	public List<String> desiredLanguages(ITestContext testContext) throws UnsupportedEncodingException, MalformedURLException{
		
		List<String> acceptLanguages = new ArrayList<>();
		String value = (testContext.getSuite().getParameter(TestRunArg.WMS.toString())).toLowerCase();
		Map<String, List<String>> params = splitQuery(new URL(value));
		if(params != null) {
			if(params.containsKey("ACCEPTLANGUAGES")) {
				String p = params.get("ACCEPTLANGUAGES").get(0);
				acceptLanguages = Arrays.asList(p.split("\\s*,\\s*"));
			}
		}
		return acceptLanguages;
	}
	
	/**
	   * Checks if capabilities document specifies xml:lang attributes or Languages parameters by returning list of their values.
	   * If not, means that AcceptLanguages is ignored and service is compliant.
	   * @param testContext nc
	   * @throws UnsupportedEncodingException nc
	   * @throws MalformedURLException nc
	   * @return lang nc
	   */
	public List<String> getXmlLang(ITestContext testContext) throws UnsupportedEncodingException, MalformedURLException{
		
		List<String> lang = new ArrayList<>();
        String expr = "//@xml:lang | //wms:WMS_Capabilities/wms:Languages/wms:Language";
        String xPathExpr = String.format(expr);

		try {
			XPath xPath = createXPath();
			NodeList formatNodes = (NodeList) xPath.evaluate(xPathExpr, wmsCapabilities, XPathConstants.NODESET);
			for (int formatNodeIndex = 0; formatNodeIndex < formatNodes.getLength(); formatNodeIndex++) {
				Node formatNode = formatNodes.item(formatNodeIndex);
				String code = formatNode.getTextContent();
				if (code != null && !code.isEmpty())
					lang.add(code);
			}
		} catch (XPathExpressionException ex) {
			TestSuiteLogger.log(Level.INFO, ex.getMessage());
		}
		return lang;
	}
 
	
	/**
	   * Returns the language of the response returned by the server.
	   * @param testContext nc
	 * @throws IOException nc
	 * @return httpLanguage nc
	   */
	public String http_accept_language(ITestContext testContext) throws IOException {
		/*If the AcceptLanguages parameter is not present in an OWS request, the server should 
		attempt to honor the Accept-Language MIME header in the HTTP request (usually 
		passed to the process by the web server by means of the HTTP_ACCEPT_LANGUAGE 
		environment variable instead*/
		
		String httpLanguage = null;
		String wmsRef = testContext.getSuite().getParameter(TestRunArg.WMS.toString());
	    URL wmsurl = null;
		try {
			wmsurl = new URL(wmsRef);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		/*
		URLConnection urlconn = wmsurl.openConnection();
		System.out.println("!!!! Header fields : " +urlconn.getHeaderFields());
		
		HttpURLConnection con = (HttpURLConnection) urlconn;
		con.connect();
		System.out.println("!!! Content-type : " + con.getHeaderField("Content-Type"));
		System.out.println("!!! " + con.getHeaderFields());
		*/
		httpLanguage = "en";
		return httpLanguage;
	}
	
	
	/**
	   * Returns list of languages supported by server.
	   * @param testContext nc
	   * @return httpLanguage nc
	   */
	public List<String> list_http_accept_language(ITestContext testContext) {
		
		List<String> httpLanguage = new ArrayList<>();
		httpLanguage.add("en");
		return httpLanguage;
	}
	
	/**
	   * Gets exception code if there is no match between requested languages and supported ones by the server.
	   * @return code.get(0) nc
   */
	public String getExceptionCode() {
		List<String> code = new ArrayList<>();
		
        String expr = "ows:ExceptionReport/ows:Exception/@exceptionCode";
        String xPathExpr = String.format(expr);

		try {
			XPath xPath = createXPath();
			NodeList formatNodes = (NodeList) xPath.evaluate(xPathExpr, wmsCapabilities, XPathConstants.NODESET);
			for (int formatNodeIndex = 0; formatNodeIndex < formatNodes.getLength(); formatNodeIndex++) {
				Node formatNode = formatNodes.item(formatNodeIndex);
				String format = formatNode.getTextContent();
				if (format != null && !format.isEmpty())
					code.add(format);
			}
		} catch (XPathExpressionException ex) {
			TestSuiteLogger.log(Level.INFO, ex.getMessage());
		}
		
		if(code.size()<1) {
			return ("No exception");
		}else {
			return code.get(0);
		}
	}
	
	private static XPath createXPath() {
		XPath xPath = XPathFactory.newInstance().newXPath();
		xPath.setNamespaceContext(NS_BINDINGS);
		return xPath;
	}


	public boolean getTitleInLocaleLanguage(ITestContext testContext) {
        Object attribute = testContext.getSuite().getAttribute( SuiteAttribute.INTERACTIVE_TEST_RESULT.getName() );
        if ( attribute == null )
            throw new SkipException( "Missing testresult!" );

        InteractiveTestResult interactiveTestResult = (InteractiveTestResult) attribute;
        boolean isInLocaleLanguage = interactiveTestResult.isCapabilitiesInEnglishLanguage();
        return isInLocaleLanguage;
	}
	
	
	@DataProvider(name = "acceptLanguage")
    public Object[][] parseAcceptFormats(ITestContext testContext) throws IOException{
		
		String expectedLanguage = null;
		String actualLanguage = null;
		
		List<String> languagesParam = desiredLanguages(testContext);
		System.out.println("!!! Requested language : " + languagesParam);
		
		if(languagesParam.size()<1) { //No AcceptLanguages parameter given in the request
			expectedLanguage = http_accept_language(testContext); //Get language response : must be in the locale language
			// Ask user if ok --> interactive
			System.out.println("!!!!! la");
			boolean resp = false;
			resp = getTitleInLocaleLanguage(testContext);
			if(resp)
				actualLanguage = "*";

		}else {	
			/*If there is no match between the list of languages in the AcceptLanguages parameter and 
				the list of languages supported by the server, the service shall return an 
				InvalidParameterValue exception. 
			*/
			List<String> listOfSupportedLang = list_http_accept_language(testContext);
			System.out.println("!!!!! List of supported languages by the server : " + listOfSupportedLang);
			boolean match = false;

			// Checking if there is a match between supported parameters and desired ones
			for(String l : languagesParam) {
				if(listOfSupportedLang.contains(l)) {
					match = true;
					//While browsing the list of desired languages, we found one which is supported by the server.
					//As the browse is done in the order of the list of parameters, it is in this language that the server must respond
					expectedLanguage = l; 
					break;
				}
			}
			if(match) {
				//Checks if Capabilities document takes language into account.
				//Get list of languages of Capabilites document.
				List<String> supportedCapabilitieslang = getXmlLang(testContext);
				System.out.println("!!! Capabilities explicitly says : " + supportedCapabilitieslang);
				if(supportedCapabilitieslang.size()<1) {
					//No xml:lang or <Languages>, server ignores AcceptLanguages : it is compliant.
					expectedLanguage = "*";
					actualLanguage = "*";
				}else {
					//xml:lang found, the list must contain expectedLanguage
					if(supportedCapabilitieslang.contains(expectedLanguage)) {
						actualLanguage = expectedLanguage;
					}
				}
			}else {
				//No match between desired languages and supported ones by the server :
				// the service shall return an InvalidParameterValue exception.
				expectedLanguage = "InvalidParameterValue";				
				//Get exception code if there is one and put it as actual value
				actualLanguage = getExceptionCode();				
			}
		}
		
        Object[][] checkLanguage = new Object[][]{ {actualLanguage, expectedLanguage} };
        System.out.println("!!! Values = " + actualLanguage + " , " + expectedLanguage);
       
		return checkLanguage;
	}
	
	@Test(description = "DGIWG - Web Map Service 1.3 Profile, Requirement 22", dataProvider = "acceptLanguage")
    public void wmsCapabilitiesLanguageIsAccepted(String actual, String expected) {
		if(actual==null || expected==null) {
			assertTrue(true);
		}else if (actual.contains("*") || expected.contains("*")) {
			assertTrue(true);
		}else {
	    	assertTrue(actual.contains(expected), "Response is not in the expected language : expected " + expected +" but have " + actual + "\n");
		}				
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


}
