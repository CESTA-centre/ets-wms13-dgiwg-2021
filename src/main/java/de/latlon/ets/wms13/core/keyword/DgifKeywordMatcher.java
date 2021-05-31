package de.latlon.ets.wms13.core.keyword;

import java.util.List;

public interface DgifKeywordMatcher {
    /**
     * Checks if the passed list of keywords contains at least one keyword from DGIF register. The check is
     * case-sensitive!
     * 
     * @param keywordsToCheck
     *            the keywords to check, may be empty but never <code>null</code>
     * @return <code>true</code> if the passed list contains at least one keyword from DGIF register, <code>false</code>
     *         if not or the passed list is empty
     */
    boolean containsAtLeastOneDgifKeyword( List<String> keywordsToCheck );

}
