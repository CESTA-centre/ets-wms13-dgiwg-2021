package de.latlon.ets.wms13.core.dgiwg.testsuite.getcapabilities;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactoryConfigurationException;

import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.latlon.ets.wms13.core.keyword.DgifKeywordMatcher;
import de.latlon.ets.wms13.core.keyword.DgifKeywordMatcherFromFile;

/**
 * Tests if the service contains at least one expected keywords.
 * 
 * @author <a href="mailto:goltz@lat-lon.de">Lyn Goltz</a>
 */
public class GetCapabilitiesKeywordTest extends AbstractBaseGetCapabilitiesFixture {

    private static final DgifKeywordMatcher DGIF_KEYWORD_MATCHER = new DgifKeywordMatcherFromFile();

    @Test(groups="If a WMS server is providing services to a coalition mission federated network, in support of operations or an exercise, it shall provide a minimum keyword list, based on ISO 19115 Topic Categories. It's recommended to provide additional details based on the DGIF groups.\n"
    		+ "The provision of these keywords elements are optional for a WMS server which is providing services across one single non-mission network.", description = "Checks if Keyword contains at least one keyword in the list of DGIF groups.")
    public void wmsCapabilitiesContainsKeywordFromDGIFRegister()
                    throws XPathFactoryConfigurationException, XPathExpressionException {
        List<String> keywords = parseKeywords( wmsCapabilities );
        boolean atLeastOneKeywordIsFromDgif = DGIF_KEYWORD_MATCHER.containsAtLeastOneDgifKeyword( keywords );

        assertTrue( atLeastOneKeywordIsFromDgif,
                    "Invalid keywords, expected is at least one keyword from ISO 19115 Topic Categories (DGIF groups), but is " + keywords );
    }

    private List<String> parseKeywords( Document wmsCapabilities )
                    throws XPathFactoryConfigurationException, XPathExpressionException {
        String xPathExpr = "//wms:WMS_Capabilities/wms:Service/wms:KeywordList/wms:Keyword";
        NodeList keywordNodes = (NodeList) createXPath().evaluate( xPathExpr, wmsCapabilities, XPathConstants.NODESET );
        List<String> keywords = new ArrayList<String>();
        for ( int keywordNodeIndex = 0; keywordNodeIndex < keywordNodes.getLength(); keywordNodeIndex++ ) {
            Node keywordNode = keywordNodes.item( keywordNodeIndex );
            String keyword = keywordNode.getTextContent();
            if ( keyword != null )
                keywords.add( keyword.trim() );
        }
        return keywords;
    }

    private XPath createXPath()
                    throws XPathFactoryConfigurationException {
        XPathFactory factory = XPathFactory.newInstance( XPathConstants.DOM_OBJECT_MODEL );
        XPath xpath = factory.newXPath();
        xpath.setNamespaceContext( NS_BINDINGS );
        return xpath;
    }

}