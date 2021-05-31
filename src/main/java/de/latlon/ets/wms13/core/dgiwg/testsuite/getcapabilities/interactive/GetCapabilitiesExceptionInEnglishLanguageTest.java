package de.latlon.ets.wms13.core.dgiwg.testsuite.getcapabilities.interactive;

import static org.testng.Assert.assertTrue;

import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactoryConfigurationException;

import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.Test;

import de.latlon.ets.wms13.core.domain.InteractiveTestResult;
import de.latlon.ets.wms13.core.domain.SuiteAttribute;

/**
 * Checks the result of the interactive test for the language of the metadata content.
 * 
 * @author <a href="mailto:cesta.github@cesta.fr">CESTA Centre</a>
 */
public class GetCapabilitiesExceptionInEnglishLanguageTest {
	 @Test(groups= {"A WMS server shall provide the service exceptions in the English language.  Exception text content may also be provided in additional languages, but English must always be included."}, description = "Asks the user if the presented request returns an exception that contains English language.")
	    public void getCapabilitiesExceptionInEnglishLanguage( ITestContext context )
	                    throws XPathExpressionException, XPathFactoryConfigurationException {
	        if ( context == null )
	            throw new SkipException( "Context is null!" );
	        Object attribute = context.getSuite().getAttribute( SuiteAttribute.INTERACTIVE_TEST_RESULT.getName() );
	        if ( attribute == null )
	            throw new SkipException( "Missing testresult!" );

	        InteractiveTestResult interactiveTestResult = (InteractiveTestResult) attribute;
	        boolean getCapabilitiesExceptopmResponseInEnglishLanguage = interactiveTestResult.isGetCapabilitiesExceptionInEnglishLanguage();
	        assertTrue( getCapabilitiesExceptopmResponseInEnglishLanguage,
	                    "Content of the GetCapabilities exception does not include English language." );
	    }

}
