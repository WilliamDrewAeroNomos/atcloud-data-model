/**
 * 
 */
package com.atcloud.model;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * 
 * @author <a href=mailto:support@atcloud.com>support</a>
 * @version $Revision: $
 */

public interface ModelService {

	/**
	 * @throws
	 * @throws ATCloudDataModelException
	 * 
	 */
	void start() throws ATCloudDataModelException;

	/**
	 * 
	 */
	void stop();

	/**
	 * @return the marshaller
	 */
	Marshaller getMarshaller();

	/**
	 * @return the unmarshaller
	 */
	Unmarshaller getUnmarshaller();

	/**
	 * 
	 * @param jaxbElement
	 * @throws ATCloudDataModelException
	 */
	void validateObject(final Object jaxbElement)
			throws ATCloudDataModelException;

	/**
	 * 
	 * @return
	 */
	User createUser();

	/**
	 * @param marshaller
	 *          the marshaller to set
	 */
	void setMarshaller(final Marshaller marshaller);

	/**
	 * @param unmarshaller
	 *          the unmarshaller to set
	 */
	void setUnmarshaller(final Unmarshaller unmarshaller);

	/**
	 * 
	 * @return
	 */
	Role createRole();

	/**
	 * Creates a standard version of a {@link FlightPlan} with a globally unique
	 * ID and current date stamps in the Date fields.
	 * 
	 * @return {@link FlightPlan} with default values, unique ID and current
	 *         timestamp in the Date fields.
	 */
	FlightPlan createFlightPlan();

	/**
	 * 
	 * @return
	 */
	Aircraft createAircraft();

	/**
	 * 
	 * @return
	 */
	FlightRoute createFlightRoute(final String name);

	/**
	 * 
	 * @return
	 */
	Scenario createScenario(final String name);

	/**
	 * 
	 * @return
	 */
	Simulation createSimulation(final String name);

	/**
	 * 
	 * @return
	 */
	License createLicense();

	/**
	 * @param name
	 * @param description
	 * @return
	 */
	Federate createFederate(String name, String description);

	/**
	 * 
	 * @param xsdLocation
	 */
	void setXsdLocation(String xsdLocation);

	/**
	 * 
	 * @return
	 */
	String getXsdLocation();

	/**
	 * 
	 * @param schemaFactorySourceLocator
	 */
	void setSchemaFactorySourceLocator(
			SchemaFactorySourceLocator schemaFactorySourceLocator);

	/**
	 * 
	 * @return
	 */
	SchemaFactorySourceLocator getSchemaFactorySourceLocator();

	/**
	 * @return
	 */
	Organization createOrganization();

	/**
	 * 
	 * @param name
	 * @return
	 */
	FEM createFEM(final String name);

	/**
	 * Unmarshals an XML document into an object.
	 * 
	 * @param objectAsXml
	 * @return
	 * @throws JAXBException
	 */
	Object getObjectFromXML(final String objectAsXml) throws JAXBException;
}
