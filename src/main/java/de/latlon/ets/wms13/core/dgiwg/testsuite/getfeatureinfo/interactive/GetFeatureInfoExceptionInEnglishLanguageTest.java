package de.latlon.ets.wms13.core.dgiwg.testsuite.getfeatureinfo.interactive;

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
public class GetFeatureInfoExceptionInEnglishLanguageTest {

    @Test(groups= {"A WMS server shall provide the service exceptions in the English language.  Exception text content may also be provided in additional languages, but English must always be included."}, description = "Asks the user if the presented request returns an exception that contains English language.")
    public void getFeatureInfoExceptionInEnglishLanguage( ITestContext context )
                    throws XPathExpressionException, XPathFactoryConfigurationException {
        if ( context == null )
            throw new SkipException( "Context is null!" );
        Object attribute = context.getSuite().getAttribute( SuiteAttribute.INTERACTIVE_TEST_RESULT.getName() );
        if ( attribute == null )
            throw new SkipException( "Missing testresult!" );

        InteractiveTestResult interactiveTestResult = (InteractiveTestResult) attribute;
        boolean getFeatureInfoExceptopmResponseInEnglishLanguage = interactiveTestResult.isGetFeatureInfoExceptionInEnglishLanguage();
        assertTrue( getFeatureInfoExceptopmResponseInEnglishLanguage,
                    "Content of the GetFeatureInfo exception does not include English language." );
    }

}