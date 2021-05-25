package de.latlon.ets.wms13.core.dgiwg.testsuite.getcapabilities.interactive;

import static org.testng.Assert.assertTrue;

import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactoryConfigurationException;

import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.Test;

import de.latlon.ets.wms13.core.domain.InteractiveTestResult;
import de.latlon.ets.wms13.core.domain.SuiteAttribute;

public class GetTitleInLocaleLanguageTest {
	
	public void titleInLocaleLanguage(ITestContext testContext) {
		System.out.println("!!!! hello");
        Object attribute = testContext.getSuite().getAttribute( SuiteAttribute.INTERACTIVE_TEST_RESULT.getName() );
        if ( attribute == null )
            throw new SkipException( "Missing testresult!" );

        InteractiveTestResult interactiveTestResult = (InteractiveTestResult) attribute;
        boolean getTitleInLocaleLanguage = interactiveTestResult.isGetTitleInLocaleLanguage();
        System.out.println("!!!! isInLocaleLanguage = " + getTitleInLocaleLanguage);
        assertTrue( getTitleInLocaleLanguage,
                "Content of the GetCapabilities response is not in user's language." );
                
	}
	
}
