
# DGIWG – Web Map Service 1.3 Profile Conformance Test Suite
# STANAG 6523 Ed.1 revision WMS Harmonized profile

## Scope

This executable test suite (ETS) verifies the conformance of the implementation under 
test (IUT) with respect to harmonized DGIWG – Web Map Service 1.3 Profile from STANAG 6523 Ed.1 revision.
Conformance testing is a kind of "black box" testing that examines the externally 
visible characteristics or behaviors of the IUT while disregarding any implementation details.


## What is tested

  - All requirements described in Harmonized DGIWG – Web Map Service 1.3 Profile, STANAG 6523 Ed.1 revision.


## What is not tested

  - WMS 1.3 Standard. As the DGIWG profile complements the WMS standard, the [WMS 1.3](https://portal.ogc.org/files/?artifact_id=14416) test suite must first be run to check the conformity of the service (can be found on [GitHub](https://github.com/opengeospatial/ets-wms13)).


## Test requirements

The documents listed below stipulate requirements that must be satisfied by a 
conforming implementation.

1. STANAG 6523 Ed.1 revision of [DGIWG 112 – Defence Profile of OGC's Web Map Service 1.3 - Revision (14-013r2)](https://portal.dgiwg.org/files/68226)
2. [Web Map Server Implementation Specification, Version 1.3.0 (06-042)](http://portal.opengeospatial.org/files/?artifact_id=14416)

If any of the following preconditions are not satisfied then all tests in the 
suite will be marked as skipped.

1. WMS capabilities document must be available.


## Test suite structure

The test suite definition file (testng.xml) is located in the root package, 
`de.latlon.ets.wms13.dgiwg`. A group corresponds to a &lt;test&gt; element, each 
of which includes a set of test classes that contain the actual test methods. 
The general structure of the test suite is shown in Table 1.

<table>
  <caption>Table 1 - Test suite structure</caption>
  <thead>
    <tr style="text-align: left; background-color: LightCyan">
      <th>Group</th>
      <th>Test classes</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>Preconditions</td>
      <td>de.latlon.ets.wms13.core.dgiwg.testsuite.Prerequisites</td>
    </tr>
    <tr>
      <td>DGIWG - Web Map Service 1.3 Harmonized Profile, Requirement 2</td>
      <td>
        A WMS server shall support HTTP GET for all operations provided by the server, with parameters encoded in KVP.
        <ul>
          <li>dde.latlon.ets.wms13.core.dgiwg.testsuite.getcapabilities.GetCapabilitiesKVPRequestSupported</li>
          <li>de.latlon.ets.wms13.core.dgiwg.testsuite.getfeatureinfo.GetFeatureInfoKVPRequestSupported</li>
          <li>de.latlon.ets.wms13.core.dgiwg.testsuite.getmap.GetMapKVPRequestSupported</li>
        </ul>
      </td>
    </tr>
    <tr>
      <td>DGIWG - Web Map Service 1.3 Harmonized Profile, Requirement 3</td>
      <td>
        A WMS server shall provide the metadata content in response to a "GetCapabilities" or “GetFeatureInfo” (if supported) request in the English language.  Metadata content may also be provided in additional languages, but English must always be included.
        <ul>
          <li>de.latlon.ets.wms13.core.dgiwg.testsuite.getcapabilities.interactive.GetCapabilitiesInEnglishLanguageTest</li>      <li>de.latlon.ets.wms13.core.dgiwg.testsuite.getfeatureinfo.interactive.GetFeatureInfoInEnglishLanguageTest</li>
        </ul>
      </td>
    </tr>
    <tr>
      <td>DGIWG - Web Map Service 1.3 Harmonized Profile, Requirement 4</td>
      <td>
        A WMTS server shall provide tiles in at least one of the following raster formats  : 
• image/png (Portable Network Graphics) 
• image/gif (Graphics Interchange Format) 
• image/jpeg (Joint Photographics Expert Group).
        <ul>
          <li>de.latlon.ets.wms13.core.dgiwg.testsuite.getmap.GetMapOutputFormatTest</li>      
        </ul>
      </td>
    </tr>
    <tr>
      <td>DGIWG - Web Map Service 1.3 Harmonized Profile, Requirement 5</td>
      <td>
        A  WMS server shall support the following coordinate reference systems, as a minimum: 
• CRS:84 WGS84 geographic longitude, then latitude, expressed in decimal degrees 
• EPSG:4326 WGS84 geographic latitude, then longitude, expressed in decimal degrees 
• EPSG:3395 World Mercator projection Among the following Coordinate Reference Systems, the service shall support all those which validity zone overlaps data published by the service: 
• UTM projections over WGS84 (north zones)…  EPSG:32601 to EPSG:32660 
• UTM projections over  WGS84 (south zones)…  EPSG:32701 to EPSG:32760
• UPS projection over WGS84 (north zone)…  EPSG: 5041 UPS projection over WGS84 (south zone)…  EPSG: 5042 
Each WMS service instance does not have to provide its data in all these CRSs. 
        <ul>
          <li>de.latlon.ets.wms13.core.dgiwg.testsuite.getcapabilities.GetCapabilitiesLayerCrsTest</li>
          <li>de.latlon.ets.wms13.core.dgiwg.testsuite.getmap.GetMapLayerCrsTest</li>
        </ul>
      </td>
    </tr>
    <tr>
      <td>DGIWG - Web Map Service 1.3 Harmonized Profile, Requirement 6</td>
      <td>
      A WMS server shall provide the service exceptions in the English language.  Exception text content may also be provided in additional languages, but English must always be included.
      <ul>
          <li>de.latlon.ets.wms13.core.dgiwg.testsuite.getfeatureinfo.interactive.GetFeatureInfoExceptionInEnglishLanguageTest</li>      
          <li>de.latlon.ets.wms13.core.dgiwg.testsuite.getmap.interactive.GetMapExceptionInEnglishLanguageTest</li>
          <li>de.latlon.ets.wms13.core.dgiwg.testsuite.getcapabilities.interactive.GetCapabilitiesExceptionInEnglishLanguageTest</li>
        </ul>
      </td>
    </tr>
    <tr>
      <td>DGIWG - Web Map Service 1.3 Harmonized Profile, Requirement 7</td>
      <td>
        A WMS server shall provide an "Abstract" at the service level, in the in the GetCapabilities response document.
        <ul>
          <li>de.latlon.ets.wms13.core.dgiwg.testsuite.getcapabilities.GetCapabilitiesAbstractExists</li>      
        </ul>
      </td>
    </tr>
    <tr>
      <td>DGIWG - Web Map Service 1.3 Harmonized Profile, Requirement 8</td>
      <td>
        If a WMS server is providing services to a coalition mission federated network, in support of operations or an exercise, it shall provide "ContactInformation", "AccessConstraints" and "KeywordList" elements. The provision of these metadata elements are optional for a WMS server which is providing services across one single non-mission network.
        <ul>
          <li>de.latlon.ets.wms13.core.dgiwg.testsuite.getcapabilities.GetCapabilitiesContentTest</li>      
        </ul>
      </td>
    </tr>
    <tr>
      <td>DGIWG - Web Map Service 1.3 Harmonized Profile, Requirement 9</td>
      <td>
        A WMS server SHALL use the AccessContraints  element to hold the classification information for this web service instance."
        <ul>
          <li>de.latlon.ets.wms13.core.dgiwg.testsuite.getcapabilities.GetCapabilitiesAccessConstraintTest</li>      
        </ul>
      </td>
    </tr>
    <tr>
      <td>DGIWG - Web Map Service 1.3 Harmonized Profile, Requirement 10</td>
      <td>
        If a WMS server is providing services to a coalition mission federated network, in support of operations or an exercise, it shall include the following information in the abstract element of the service metadata: "This service implements the WMS 1.3 STANAG 6523 Ed.2 profile". The provision of these metadata elements are optional for a WMS server which is providing services across one single non-mission network.
        <ul>
          <li>de.latlon.ets.wms13.core.dgiwg.testsuite.getcapabilities.GetCapabilitiesAbstractTest</li>      
        </ul>
      </td>
    </tr>
    <tr>
      <td>DGIWG - Web Map Service 1.3 Harmonized Profile, Requirement 11</td>
      <td>
        If a WMS server is providing services to a coalition mission federated network, in support of operations or an exercise, it shall provide a minimum keyword list, based on ISO 19115 Topic Categories. It's recommended to provide additional details based on the DGIF groups.
The provision of these keywords elements are optional for a WMS server which is providing services across one single non-mission network.
        <ul>
          <li>de.latlon.ets.wms13.core.dgiwg.testsuite.getcapabilities.GetCapabilitiesKeywordTest</li>      
        </ul>
      </td>
    </tr>
    <tr>
      <td>DGIWG - Web Map Service 1.3 Harmonized Profile, Requirement 12</td>
      <td>
        A WMS server shall always provide at least one style element and that style shall be advertised even if it's only the default style. 
        <ul>
          <li>de.latlon.ets.wms13.core.dgiwg.testsuite.getcapabilities.GetCapabilitiesLayerStyleTest</li>      
        </ul>
      </td>
    </tr>
	<tr>
      <td>DGIWG - Web Map Service 1.3 Harmonized Profile, Requirement 13</td>
      <td>
        Each layer's style shall have an associated legend (using the legendURL element) if the data being provisioned is symbolized/portrayed (i.e. not imagery).
        <ul>
          <li>de.latlon.ets.wms13.core.dgiwg.testsuite.getcapabilities.GetCapabilitiesLegendExists</li>      
        </ul>
      </td>
    </tr>
    <tr>
      <td>DGIWG - Web Map Service 1.3 Harmonized Profile, Requirement 14</td>
      <td>
        Legends shall be available as an image in at least one of the following formats: PNG (image/png), GIF (image/gif) or JPEG (image/jpeg).
        <ul>
          <li>de.latlon.ets.wms13.core.dgiwg.testsuite.getcapabilities.GetCapabilitiesLegendFormatTest</li>      
        </ul>
      </td>
    </tr>
    <tr>
      <td>DGIWG - Web Map Service 1.3 Harmonized Profile, Requirement 15</td>
      <td>
        If legend is present (see req 13), the LegendURL element shall contain a URL to allow access to an image of the legend. 
Note : This URL will relate to the source system and may not be resolvable on all connected/unconnected systems or applications. This requirement is conditional on the LegendURL being relevant to the generated service.
        <ul>
          <li>de.latlon.ets.wms13.core.dgiwg.testsuite.getcapabilities.GetCapabilitiesLegendUrlTest</li>      
        </ul>
      </td>
    </tr>
    <tr>
      <td>DGIWG - Web Map Service 1.3 Harmonized Profile, Requirement 16</td>
      <td>
        When scale denominators are both specified, the MinScaleDenominator value shall always be less than or equal to the MaxScaleDenominator value.
        <ul>
          <li>de.latlon.ets.wms13.core.dgiwg.testsuite.getcapabilities.GetCapabilitiesLayerScaleDenominatorsTest</li>      
        </ul>
      </td>
    </tr>
    <tr>
      <td>DGIWG - Web Map Service 1.3 Harmonized Profile, Requirement 17</td>
      <td>
        If FeatureListURL element is present, its value shall be a URL to allow access to a list of features available in a particular layer.
NOTE :  This URL will relate to the source system and may not be resolvable on all connected/unconnected systems or applications. This requirement is conditional on the FeatureListURL being relevant to the generated service. 
        <ul>
          <li>de.latlon.ets.wms13.core.dgiwg.testsuite.getcapabilities.GetCapabilitiesFeatureListUrlTest</li>      
        </ul>
      </td>
    </tr>
    <tr>
      <td>DGIWG - Web Map Service 1.3 Harmonized Profile, Requirement 18</td>
      <td>
        If DataListURL element is present, its value shall be a URL to allow access to a list of features available in a particular layer.
NOTE :  This URL will relate to the source system and may not be resolvable on all connected/unconnected systems or applications. This requirement is conditional on the DataListURL being relevant to the generated service. 
        <ul>
          <li>de.latlon.ets.wms13.core.dgiwg.testsuite.getcapabilities.GetCapabilitiesDataUrlTest</li>      
        </ul>
      </td>
    </tr>
    <tr>
      <td>DGIWG - Web Map Service 1.3 Harmonized Profile, Requirement 19</td>
      <td>
        A WMS server shall provide the Layer Attributes with following restrictions on their values : noSubsets (0, false), fixedWidth (0), FixedHeight (0).
        <ul>
          <li>de.latlon.ets.wms13.core.dgiwg.testsuite.getcapabilities.GetCapabilitiesLayerAttributesTest</li>      
        </ul>
      </td>
    </tr>
    <tr>
      <td>DGIWG - Web Map Service 1.3 Harmonized Profile, Requirement 20</td>
      <td>
        A WMS server shall support the INIMAGE EXCEPTIONS.
        <ul>
          <li>de.latlon.ets.wms13.core.dgiwg.testsuite.getmap.GetMapInImageExceptionsTest</li>      
        </ul>
      </td>
    </tr>
    <tr>
      <td>DGIWG - Web Map Service 1.3 Harmonized Profile, Requirement 21</td>
      <td>
        A WMS server shall support the BLANK EXCEPTIONS.
        <ul>
          <li>de.latlon.ets.wms13.core.dgiwg.testsuite.getmap.GetMapBlankExceptionsTest</li>      
        </ul>
      </td>
    </tr>
    <tr>
      <td>DGIWG - Web Map Service 1.3 Harmonized Profile, Requirement 22</td>
      <td>
        If AcceptFormats and AcceptLanguages are provided in the GetCapabilities operation, they shall be used as specified in Section 7.3 of [OGC WSCommon 2.0, 2010].
        --> Not tested yet.
      </td>
    </tr>
    <tr>
      <td>DGIWG - Web Map Service 1.3 Harmonized Profile, Requirement 23</td>
      <td>
        If a WMS server offers its functionality via the SOAP protocol, it shall do so in compliance with the Messaging Service SIP [NCIA TR/2012/SPW008000/30, 2012] which defines general requirements that apply to all services in the NNEC environment that make use of SOAP. 
        --> Not tested yet.
      </td>
    </tr>
    <tr>
      <td>DGIWG - Web Map Service 1.3 Harmonized Profile, Requirement 24</td>
      <td>
        If the WMS server responds with an exceptionfor a request containing duplicated parameters with conflicting values, it shall be valid according to the schema provided in Appendix E.2 of the WMS Standard [OGC WMS 1.3, 2006], and SHALL use the exception code “DuplicatedParameterInRequest” as value of the attribute /ogc:ServiceExceptionReport/ogc:ServiceException/@code
        --> Not tested yet.
      </td>
    </tr>
        
  </tbody>
</table>

The Javadoc documentation provides more detailed information about the test 
methods that constitute the suite.


## Test run arguments

The test run arguments are summarized in Table 2. The _Obligation_ descriptor can 
have the following values: M (mandatory), O (optional), or C (conditional).

<table>
  <caption>Table 2 -Test run arguments</caption>
  <thead>
    <tr style="text-align: left; background-color: LightCyan">
      <th>Name</th>
      <th>Value domain</th>
      <th>Obligation</th>
  	  <th>Description</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>wms</td>
      <td>URI</td>
      <td>M</td>
	  <td>A URI that refers to the implementation under test or metadata about it.
      Ampersand '&amp;' characters must be followed with 'amp;' expression.</td>
    </tr>
	<tr>
      <td>vector</td>
      <td>Boolean</td>
      <td>M</td>
      <td>Controls if tests targeting layers which base on vector data are executed.</td>
    </tr>
  </tbody>
</table>


## Credits
This test suite is an adaptation of [ets-wms13-dgiwg](https://github.com/opengeospatial/ets-wms13-dgiwg).




##  License

[Apache License, Version 2.0](http://opensource.org/licenses/Apache-2.0 "Apache License")
