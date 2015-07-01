package com.atcloud.model;

import java.io.ByteArrayOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

/**
 * 
 * 
 * @author <a href=mailto:support@atcloud.com>support</a>
 * @version $Revision: $
 */
public class ModelBase {

	public ModelBase() {

	}

	/**
	 * Marshals the object into well formed XML
	 * 
	 * @return String representing the object as XML
	 */
	public String toXml() {

		ByteArrayOutputStream baos = null;
		try {
			JAXBContext ctx = JAXBContext.newInstance(this.getClass());
			Marshaller marshaller = ctx.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					Boolean.TRUE);
			baos = new ByteArrayOutputStream();
			marshaller.marshal(this, baos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return baos.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		return toXml();
	}

}
