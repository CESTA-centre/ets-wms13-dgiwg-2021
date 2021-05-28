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
 * @author <a href="mailto:goltz@lat-lon.de">Lyn Goltz</a>
 */
public class GetCapabilitiesInEnglishLanguageTest {

	@Test(groups= {"DGIWG Requirement 3 : A WMS server shall provide the metadata content \n"
    		+ "				in response to a \"GetCapabilities\" or “GetFeatureInfo” (if supported) request \n"
    		+ "				in the English language. Metadata content may also be provided in additional \n"
    		+ "				languages, but English must always be included."}, description = "Ask user if GetCapabilties response is in English.")
    public void wmsCapabilitiesInEnglishLanguage( ITestContext context )
                    throws XPathExpressionException, XPathFactoryConfigurationException {
        if ( context == null )
            throw new SkipException( "Context is null!" );
        Object attribute = context.getSuite().getAttribute( SuiteAttribute.INTERACTIVE_TEST_RESULT.getName() );
        if ( attribute == null )
            throw new SkipException( "Missing testresult!" );

        InteractiveTestResult interactiveTestResult = (InteractiveTestResult) attribute;
        boolean capabilitiesInEnglishLanguage = interactiveTestResult.isCapabilitiesInEnglishLanguage();
        assertTrue( capabilitiesInEnglishLanguage, "Content of the capabilities are not in English language." );
    }

}