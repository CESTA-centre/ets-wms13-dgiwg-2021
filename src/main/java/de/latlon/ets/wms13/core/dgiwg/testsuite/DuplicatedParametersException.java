package de.latlon.ets.wms13.core.dgiwg.testsuite;

import static de.latlon.ets.core.assertion.ETSAssert.assertQualifiedName;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.latlon.ets.core.util.TestSuiteLogger;
import de.latlon.ets.wms13.core.dgiwg.testsuite.getcapabilities.AbstractBaseGetCapabilitiesFixture;
import de.latlon.ets.wms13.core.domain.DGIWGWMS;
import de.latlon.ets.wms13.core.domain.WmsNamespaces;

public class DuplicatedParametersException extends AbstractBaseGetCapabilitiesFixture{


	@DataProvider(name = "codeName")
    public Object[][] parseExceptionCodeName(ITestContext testContext) throws IOException{
		String actualCode = null;
		actualCode = getExceptionCode();
		
		Object[][] checkCode = new Object[][]{ {actualCode} };
        System.out.println("!!! Values = " + actualCode);
       
		return checkCode;
	}
	
	@Test(description = "DGIWG - Web Map Service 1.3 Profile, Requirement 24")
    public void schemaIsWellUsed() {
		//Checks if doc follows the good schema when there is a duplicated parameter in the request.
		boolean flag = true;
		String docTag = wmsCapabilities.getDocumentElement().getTagName().toLowerCase();
		if(!(docTag.contains("exception"))){
			flag = false;
		}
		if(flag)
			assertQualifiedName( wmsCapabilities.getDocumentElement(), new QName( WmsNamespaces.OGC, DGIWGWMS.OGC_EXCEPTION) );
		else
			throw new SkipException("Skipping DuplicatedParameterInRequest because not an exception : " + docTag + " document.");

    }
	
	@Test(description = "DGIWG - Web Map Service 1.3 Profile, Requirement 24", dataProvider = "codeName", dependsOnMethods="schemaIsWellUsed")
    public void exceptionCodeIsValid(String actual) {
		if(actual != null)
		{
			assertEquals(actual, "DuplicatedParameterInRequest", "Invalid exception code value ");
		}else {
			fail("No exception code found.");
		}
    }

	/**
	   * Gets exception code if there is no match between requested languages and supported ones by the server.
	   * @return code.get(0) nc
   */
	public String getExceptionCode() {
	  List<String> code = new ArrayList<>();
      String expr = "ogc:ServiceExceptionReport/ogc:ServiceException/@code";
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

	
}
