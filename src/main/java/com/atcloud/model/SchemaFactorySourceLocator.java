/**
 * 
 */
package com.atcloud.model;

import javax.xml.transform.Source;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

/**
 * 
 * @author <a href=mailto:support@atcloud.com>support</a>
 * @version $Revision: $
 */
public interface SchemaFactorySourceLocator {

	/**
	 * Returns a URL to be used to instantiate a {@link Source} to be used by the
	 * {@link SchemaFactory#newInstance(String)} in the construction of a
	 * {@link Schema}
	 * 
	 * @return URL of the sourceName
	 */
	String getURL(final String sourceName) throws ATCloudDataModelException;
}
