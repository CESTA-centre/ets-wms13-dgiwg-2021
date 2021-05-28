package de.latlon.ets.wms13.core.dgiwg.testsuite.getcapabilities;

import static de.latlon.ets.core.assertion.ETSAssert.assertXPath;
import static de.latlon.ets.wms13.core.util.ServiceMetadataUtils.parseRequestableLayerNodes;
import static org.testng.Assert.assertNotNull;

import javax.xml.soap.SOAPException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactoryConfigurationException;

import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.latlon.ets.wms13.core.domain.SuiteAttribute;

/**
 * Tests LegendURL element of a capabilities document.
 * 
 * @author <a href="mailto:stenger@lat-lon.de">Dirk Stenger</a>
 */
public class GetCapabilitiesLegendExists extends AbstractBaseGetCapabilitiesFixture {

	@DataProvider(name = "layerNodes")
	public Object[][] parseLayerNodes(ITestContext testContext)
			throws XPathFactoryConfigurationException, XPathExpressionException {
		if (this.wmsCapabilities == null)
			initBaseFixture(testContext);
		NodeList layerNodes = parseRequestableLayerNodes(wmsCapabilities);
		Object[][] layers = new Object[layerNodes.getLength()][];
		for (int layerNodeIndex = 0; layerNodeIndex < layerNodes.getLength(); layerNodeIndex++) {
			Node layerNode = layerNodes.item(layerNodeIndex);
			String name = (String) createXPath().evaluate("wms:Name", layerNode, XPathConstants.STRING);
			String title = (String) createXPath().evaluate("wms:Title", layerNode, XPathConstants.STRING);
			layers[layerNodeIndex] = new Object[] { layerNode, name, title };
		}
		return layers;
	}

	@Test(groups =
			"Each layer's style shall have an associated legend (using the <legendURL >element) if the data being provisioned is symbolized/portrayed (i.e. not imagery).", description = "Checks if legend exists.", dataProvider = "layerNodes")
	public void wmsCapabilitiesLayersLegendExits(ITestContext testContext, Node layerNode, String name,
			String title) throws SOAPException, XPathFactoryConfigurationException, XPathExpressionException {
		skipIfNoVectorData(testContext);
		Node formatNode = (Node) createXPath().evaluate(
				"ancestor-or-self::wms:Layer/wms:Style/wms:LegendURL", layerNode, XPathConstants.NODE);
		assertNotNull(formatNode,
				"Style/LegendURL element for layer (name: " + name + ", title: " + title + ") is missing.");
	}

	private XPath createXPath() throws XPathFactoryConfigurationException {
		XPathFactory factory = XPathFactory.newInstance(XPathConstants.DOM_OBJECT_MODEL);
		XPath xpath = factory.newXPath();
		xpath.setNamespaceContext(NS_BINDINGS);
		return xpath;
	}

	private void skipIfNoVectorData(ITestContext testContext) {
		boolean isVector = (boolean) testContext.getSuite().getAttribute(SuiteAttribute.IS_VECTOR.getName());
		if (!isVector)
			throw new SkipException("WMS does not contain vector data layers, tests are skipped!");
	}

}
