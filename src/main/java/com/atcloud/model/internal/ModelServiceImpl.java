/**
 * 
 */
package com.atcloud.model.internal;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.UUID;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.atcloud.model.ATCloudDataModelException;
import com.atcloud.model.Aircraft;
import com.atcloud.model.DefaultSchemaFactorySourceLocatorImpl;
import com.atcloud.model.FEM;
import com.atcloud.model.Federate;
import com.atcloud.model.FlightPlan;
import com.atcloud.model.FlightPlanStatusEnumType;
import com.atcloud.model.FlightRoute;
import com.atcloud.model.License;
import com.atcloud.model.ModelService;
import com.atcloud.model.Name;
import com.atcloud.model.Organization;
import com.atcloud.model.Role;
import com.atcloud.model.Scenario;
import com.atcloud.model.SchemaFactorySourceLocator;
import com.atcloud.model.Simulation;
import com.atcloud.model.USAddressType;
import com.atcloud.model.User;

/**
 * 
 * @author <a href=mailto:support@atcloud.com>support</a>
 * @version $Revision: $
 */
public class ModelServiceImpl implements ModelService {

	private Schema schema = null;
	private JAXBContext ctx = null;

	private Marshaller marshaller = null;
	private Unmarshaller unmarshaller = null;
	private String xsdLocation = "/";

	private SchemaFactorySourceLocator schemaFactorySourceLocator = null;
	private ClassLoader cl = null;

	/**
	 * 
	 */
	private static final Logger LOG = LoggerFactory
			.getLogger(ModelServiceImpl.class.getName());

	public ModelServiceImpl() throws ATCloudDataModelException {

		cl = this.getClass().getClassLoader();

		if (null == cl) {
			throw new ATCloudDataModelException(
					"Error retrieving schema resources from ModelService bundle. "
							+ "this.getClass().getClassLoader() returned null.");
		}

		setSchemaFactorySourceLocator(new DefaultSchemaFactorySourceLocatorImpl(cl));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.atcloud.model.ModelService#start()
	 */
	@Override
	public void start() throws ATCloudDataModelException {

		LOG.debug("Starting model service...");

		try {

			schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
					.newSchema(
							new Source[] {
									new StreamSource(schemaFactorySourceLocator
											.getURL("common-types.xsd")),
									new StreamSource(schemaFactorySourceLocator
											.getURL("configuration.xsd")),
									new StreamSource(schemaFactorySourceLocator
											.getURL("flight.xsd")),
									new StreamSource(schemaFactorySourceLocator
											.getURL("scenario.xsd")),
									new StreamSource(schemaFactorySourceLocator
											.getURL("airport.xsd")),
									new StreamSource(schemaFactorySourceLocator
											.getURL("airspace.xsd")),
									new StreamSource(schemaFactorySourceLocator
											.getURL("user.xsd")) });

			// schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
			// .newSchema(
			// new Source[] {
			// new StreamSource(xsdLocation + "/common-types.xsd"),
			// new StreamSource(xsdLocation + "/configuration.xsd"),
			// new StreamSource(xsdLocation + "/flight.xsd"),
			// new StreamSource(xsdLocation + "/scenario.xsd"),
			// new StreamSource(xsdLocation + "/airport.xsd"),
			// new StreamSource(xsdLocation + "/airspace.xsd"),
			// new StreamSource(xsdLocation + "/user.xsd") });

			// schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
			// .newSchema(
			// new Source[] {
			// new StreamSource(cl.getResource("/common-types.xsd").toURI()
			// .toString()),
			// new StreamSource(cl.getResource("/configuration.xsd").toURI()
			// .toString()),
			// new StreamSource(cl.getResource("/flight.xsd").toURI()
			// .toString()),
			// new StreamSource(cl.getResource("/scenario.xsd").toURI()
			// .toString()),
			// new StreamSource(cl.getResource("/airport.xsd").toURI()
			// .toString()),
			// new StreamSource(cl.getResource("/airspace.xsd").toURI()
			// .toString()),
			// new StreamSource(cl.getResource("/user.xsd").toURI()
			// .toString()) });

			ctx = JAXBContext.newInstance("com.atcloud.model", cl);

			marshaller = ctx.createMarshaller();

			marshaller.setSchema(schema);

			unmarshaller = ctx.createUnmarshaller();

			unmarshaller.setSchema(schema);

		} catch (SAXException e) {
			throw new ATCloudDataModelException(e.getLocalizedMessage(), e);

		} catch (JAXBException e) {
			throw new ATCloudDataModelException(e.getLocalizedMessage(), e);
		}

		LOG.info("Started model service.");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.atcloud.model.ModelService#stop()
	 */
	@Override
	public void stop() {

		LOG.debug("Stopping model service...");

		LOG.info("Stopped model service.");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.atcloud.model.ModelService#validateObject(java.lang.Object)
	 */
	public void validateObject(final Object jaxbElement)
			throws ATCloudDataModelException {

		/**
		 * Check the validity of the object with the marshaller
		 */

		if (marshaller == null) {
			throw new ATCloudDataModelException(
					"Marshaller is null. Unable to validate object.");
		}

		try {
			marshaller.marshal(jaxbElement, new DefaultHandler());
		} catch (Exception e2) {
			throw new ATCloudDataModelException(e2);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.atcloud.model.ModelService#getObjectFromXML(java.lang.String)
	 */
	@Override
	public Object getObjectFromXML(final String objectAsXml) throws JAXBException {
		return getUnmarshaller().unmarshal(new StringReader(objectAsXml));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.atcloud.model.ModelService#createFlightPlan()
	 */
	public FlightPlan createFlightPlan() {

		FlightPlan flightPlan = new FlightPlan();

		flightPlan.setFlightPlanID(UUID.randomUUID().toString());

		flightPlan.setActive(true);

		Calendar calendar = Calendar.getInstance();

		flightPlan.setDateCreated(calendar);

		flightPlan.setFlightPlanStatus(FlightPlanStatusEnumType.FILED);

		return flightPlan;
	}

	/**
	 * 
	 * @return
	 */
	public FlightRoute createFlightRoute(final String name) {

		FlightRoute flightRoute = new FlightRoute();

		flightRoute.setName(name);
		flightRoute.setFlightRouteID(UUID.randomUUID().toString());

		return flightRoute;
	}

	/**
	 * 
	 * @return
	 */
	public Scenario createScenario(final String name) {

		Scenario scenario = new Scenario();

		scenario.setScenarioID(UUID.randomUUID().toString());
		scenario.setName(name);

		return scenario;
	}

	/**
	 * 
	 * @return
	 */
	public Simulation createSimulation(final String name) {

		Simulation simulation = new Simulation();

		simulation.setSimulationID(UUID.randomUUID().toString());
		simulation.setName(name);

		return simulation;
	}

	/**
	 * 
	 * @return
	 */
	public Aircraft createAircraft() {

		Aircraft aircraft = new Aircraft();

		aircraft.setAircraftID(UUID.randomUUID().toString());

		Calendar calendar = Calendar.getInstance();

		aircraft.setDateCreated(calendar);

		return aircraft;
	}

	/**
	 * 
	 * @return
	 */
	public User createUser() {

		User user = new User();

		user.setUserId(UUID.randomUUID().toString());
		Name name = new Name();

		user.setName(name);

		user.setActive(true);

		Calendar calendar = Calendar.getInstance();

		user.setDateActivated(calendar);
		user.setDateCreated(calendar);

		return user;
	}

	/**
	 * 
	 * @return
	 */
	public Role createRole() {

		Role role = new Role();

		Calendar calendar = Calendar.getInstance();

		role.setDate(calendar);
		role.setDescription("Default role");
		role.setName("Default");

		return role;
	}

	/**
	 * @return the marshaller
	 */
	public Marshaller getMarshaller() {
		return marshaller;
	}

	/**
	 * @param marshaller
	 *          the marshaller to set
	 */
	public void setMarshaller(final Marshaller marshaller) {
		this.marshaller = marshaller;
	}

	/**
	 * @return the unmarshaller
	 */
	public Unmarshaller getUnmarshaller() {
		return unmarshaller;
	}

	/**
	 * @param unmarshaller
	 *          the unmarshaller to set
	 */
	public void setUnmarshaller(final Unmarshaller unmarshaller) {
		this.unmarshaller = unmarshaller;
	}

	/**
	 * @return the xsdLocation
	 */
	@Override
	public String getXsdLocation() {
		return xsdLocation;
	}

	/**
	 * @param xsdLocation
	 *          the xsdLocation to set
	 */
	@Override
	public void setXsdLocation(String xsdLocation) {
		this.xsdLocation = xsdLocation;
	}

	@Override
	public SchemaFactorySourceLocator getSchemaFactorySourceLocator() {
		return schemaFactorySourceLocator;
	}

	@Override
	public void setSchemaFactorySourceLocator(
			SchemaFactorySourceLocator schemaFactorySourceLocator) {
		this.schemaFactorySourceLocator = schemaFactorySourceLocator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.atcloud.model.ModelService#createLicense()
	 */
	@Override
	public License createLicense() {

		License license = new License();
		license.setActive(false);
		license.setDateCreated(Calendar.getInstance());
		license.setLicenseID(UUID.randomUUID().toString());
		license.setInUse(false);

		return license;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.atcloud.model.ModelService#createFederate()
	 */
	@Override
	public Federate createFederate(final String name, final String description) {
		Federate fed = new Federate();
		fed.setName(name);
		fed.setDescription(description);
		return fed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.atcloud.model.ModelService#createOrganization()
	 */
	@Override
	public Organization createOrganization() {

		Organization org = new Organization();

		org.setOrgID(UUID.randomUUID().toString());

		USAddressType usAddr = new USAddressType();
		usAddr.setCity("");
		usAddr.setCountry("");
		usAddr.setHouseNumber("");
		usAddr.setState("");
		usAddr.setStreet1("");
		usAddr.setUnit("");
		usAddr.setZip(new BigDecimal(0));

		org.setAddress(usAddr);

		org.setCellPhoneNumber("");
		org.setDateActivated(null);
		org.setDateCreated(Calendar.getInstance());
		org.setEmail("");
		org.setOfficePhoneNumber("");
		org.setOrgName("");

		return org;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.atcloud.model.ModelService#createFEM()
	 */
	@Override
	public FEM createFEM(final String name) {

		FEM fem = new FEM();

		fem.setFemID(UUID.randomUUID().toString());
		fem.setName(name);
		fem.setLogicalStrtTimeMSecs(Calendar.getInstance().getTimeInMillis());
		fem.setAutoStart(true);
		fem.setDefDurStrtupPrtclMSecs(10000L);
		fem.setFederationExecutionMSecs(3600000L); // duration of 1 hour
		fem.setJoinFederationMSecs(10000L);
		fem.setRegisterPublicationMSecs(10000L);
		fem.setRegisterSubscriptionMSecs(10000L);
		fem.setRegisterToRunMSecs(10000L);
		fem.setWaitForStartMSecs(10000L);
		fem.setWaitTimeAfterTermMSecs(1L); // recycles within 1 ms

		return fem;
	}
}
