/**
 * 
 */
package com.atcloud.model;

import java.net.URISyntaxException;

/**
 * 
 * @author <a href=mailto:support@atcloud.com>support</a>
 * @version $Revision: $
 */
public class DefaultSchemaFactorySourceLocatorImpl implements
		SchemaFactorySourceLocator {

	private ClassLoader classLoader;

	/**
	 * 
	 * @param cl
	 * @throws ATCloudException
	 */
	public DefaultSchemaFactorySourceLocatorImpl(final ClassLoader cl)
			throws ATCloudDataModelException {
		if (cl == null) {
			throw new ATCloudDataModelException(
					"Classloader passed to DefaultSchemaFactorySourceLocatorImpl ctor was null.");
		}
		this.classLoader = cl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.atcloud.commons.SchemaFactorySourceLocator#getURL(java.lang.String)
	 */
	@Override
	public String getURL(String sourceName) throws ATCloudDataModelException {

		if ((sourceName == null) || (sourceName.length() == 0)) {
			throw new ATCloudDataModelException("Source name was null or empty.");
		}

		if (classLoader == null) {
			throw new ATCloudDataModelException(
					"Classloader in DefaultSchemaFactorySourceLocatorImpl was null.");
		}

		try {
			return classLoader.getResource("/" + sourceName).toURI().toString();
		} catch (URISyntaxException e) {
			throw new ATCloudDataModelException(e);
		}
	}

}
