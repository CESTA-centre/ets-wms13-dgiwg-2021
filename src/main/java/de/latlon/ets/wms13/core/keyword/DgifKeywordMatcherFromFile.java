package de.latlon.ets.wms13.core.keyword;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import de.latlon.ets.core.util.TestSuiteLogger;

public class DgifKeywordMatcherFromFile implements DgifKeywordMatcher {

	private static final String KEYWORD_FILE = "dgif.keywords";

    private List<String> dgifKeywords;

    @Override
    public boolean containsAtLeastOneDgifKeyword( List<String> keywordsToCheck ) {
        parseKeywords();
        for ( String keyword : keywordsToCheck ) {
            if ( dgifKeywords.contains( keyword ) )
                return true;
        }
        return false;
    }

    private void parseKeywords() {
        if ( dgifKeywords == null ) {
            InputStream resource = DgifKeywordMatcherFromFile.class.getResourceAsStream( KEYWORD_FILE );
            dgifKeywords = parseKeywordsFromStream( resource );
        }
    }

    private List<String> parseKeywordsFromStream( InputStream resource ) {
        List<String> keywords = new ArrayList<String>();
        if ( resource != null ) {
            try ( BufferedReader br = new BufferedReader( new InputStreamReader( resource, "UTF-8" ) ) ) {
                String line;
                while ( ( line = br.readLine() ) != null ) {
                    String keyword = line.trim();
                    if ( !keyword.isEmpty() )
                        keywords.add( keyword );
                }
            } catch ( IOException e ) {
                TestSuiteLogger.log( Level.WARNING, "Keywords file " + KEYWORD_FILE + " could not be parsed.", e );
            }
        } else {
            TestSuiteLogger.log( Level.WARNING, "Could not find keywords file '" + KEYWORD_FILE + "'." );
        }
        return keywords;
    }

}
