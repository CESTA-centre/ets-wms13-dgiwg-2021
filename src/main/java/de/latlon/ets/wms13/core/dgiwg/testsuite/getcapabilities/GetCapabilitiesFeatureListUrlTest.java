package de.latlon.ets.wms13.core.dgiwg.testsuite.getcapabilities;

import static de.latlon.ets.core.assertion.ETSAssert.assertQualifiedName;
import static de.latlon.ets.core.assertion.ETSAssert.assertUriIsResolvable;
import static de.latlon.ets.core.assertion.ETSAssert.assertUrl;
import static org.testng.Assert.assertTrue;

import javax.xml.namespace.QName;
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

import de.latlon.ets.wms13.core.domain.DGIWGWMS;
import de.latlon.ets.wms13.core.domain.WmsNamespaces;

/**
 * Tests if the FeatureListUrls are resolvable.
 * 
 * @author <a href="mailto:goltz@lat-lon.de">Lyn Goltz</a>
 */
public class GetCapabilitiesFeatureListUrlTest extends AbstractBaseGetCapabilitiesFixture {

	@DataProvider(name = "featureListUrls")
	public Object[][] parseFeatureListUrlNodes(ITestContext testContext)
			throws XPathFactoryConfigurationException, XPathExpressionException {
		if (this.wmsCapabilities == null)
			initBaseFixture(testContext);
		NodeList featureListUrlNodes = parseFeatureListUrlNodes(wmsCapabilities);
		Object[][] featureListUrls = new Object[featureListUrlNodes.getLength()][];
		for (int featureListUrlNodeIndex = 0; featureListUrlNodeIndex < featureListUrlNodes
				.getLength(); featureListUrlNodeIndex++) {
			Node featureListUrlNode = featureListUrlNodes.item(featureListUrlNodeIndex);
			String featureListUrl = (String) createXPath().evaluate("//@xlink:href", featureListUrlNode,
					XPathConstants.STRING);
			featureListUrls[featureListUrlNodeIndex] = new Object[] { featureListUrl };
		}
		return featureListUrls;
	}
	
	@DataProvider(name = "featureListUrlNumber")
	public Object[][] getFeatureListUrlNodesNumber(ITestContext testContext)
			throws XPathFactoryConfigurationException, XPathExpressionException {
		if (this.wmsCapabilities == null)
			initBaseFixture(testContext);
		NodeList featureListUrlNodes = getFeatureListUrlNodes(wmsCapabilities);
		int nbNodes = featureListUrlNodes.getLength();
		Object[][] featureListUrls = {{nbNodes}};
		return featureListUrls;
	}
	

	@Test(groups = "If <FeatureListURL> element is present, its value shall be a URL to allow access to a list of features available in a particular layer.\n"
			+ "NOTE :  This URL will relate to the source system and may not be resolvable on all connected/unconnected systems or applications. This requirement is conditional on the <FeatureListURL> being relevant to the generated service. ", description = "Checks if FeatureListURL exists.", dataProvider = "featureListUrlNumber")
	public void wmsCapabilitiesFeatureListUrlExists(int featureListUrlnumber)
			throws XPathExpressionException, XPathFactoryConfigurationException {
		if(featureListUrlnumber>0)
			assertTrue(featureListUrlnumber>0);
		else
			throw new SkipException("Skipping FeatureListURL test because no such tag in document.");
	}

	@Test(groups = "If <FeatureListURL> element is present, its value shall be a URL to allow access to a list of features available in a particular layer.\n"
			+ "NOTE :  This URL will relate to the source system and may not be resolvable on all connected/unconnected systems or applications. This requirement is conditional on the <FeatureListURL> being relevant to the generated service. ", description = "Checks if URL of FeatureListURL is resolvable.", dataProvider = "featureListUrls", dependsOnMethods = "wmsCapabilitiesFeatureListUrlExists")
	public void wmsCapabilitiesFeatureListUrlIsResolvable(String featureListUrl)
			throws XPathExpressionException, XPathFactoryConfigurationException {
		
		assertUrl(featureListUrl);
		assertUriIsResolvable(featureListUrl);
	}

	private NodeList parseFeatureListUrlNodes(Document entity)
			throws XPathFactoryConfigurationException, XPathExpressionException {
		String xPathFeatureListUrls = "//wms:Layer/wms:FeatureListURL/wms:OnlineResource";
		XPath xpath = createXPath();
		return (NodeList) xpath.evaluate(xPathFeatureListUrls, entity, XPathConstants.NODESET);
	}

	private NodeList getFeatureListUrlNodes(Document entity)
			throws XPathFactoryConfigurationException, XPathExpressionException {
		String xPathFeatureListUrls = "//wms:Layer/wms:FeatureListURL";
		XPath xpath = createXPath();
		return (NodeList) xpath.evaluate(xPathFeatureListUrls, entity, XPathConstants.NODESET);
	}

	private XPath createXPath() throws XPathFactoryConfigurationException {
		XPathFactory factory = XPathFactory.newInstance(XPathConstants.DOM_OBJECT_MODEL);
		XPath xpath = factory.newXPath();
		xpath.setNamespaceContext(NS_BINDINGS);
		return xpath;
	}

}