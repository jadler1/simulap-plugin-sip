//© Copyright 2018 Hewlett Packard Enterprise Development LP
//Licensed under Apache License version 2.0: http://www.apache.org/licenses/LICENSE-2.0
package org.apache.jmeter.protocol.sip.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.hp.simulap.sip.headers.Header;
import com.hp.simulap.sip.headers.HeaderList;
import com.hp.simulap.sip.headers.ObjectFactory;

public class SipDico {

	@SuppressWarnings("unchecked")
	public static List<Header> loadDico() {
		
		List<Header> readList = null;
		String dictionaryPath = System.getProperty("simulap.sip.dictionary.path", "/etc/opt/OC/hpocsip/");

		//1. We need to create JAXContext instance
		JAXBContext jaxbContext;
		JAXBElement<HeaderList> unmarshalledObject = null;
		try {
			jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
			//2. Use JAXBContext instance to create the Unmarshaller.
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

			//3. Use the Unmarshaller to unmarshal the XML document to get an instance of JAXBElement.
			String xmlFileName = dictionaryPath+"/SipHeadersGUIDictionary.xml";
			unmarshalledObject =
			(JAXBElement<HeaderList>)unmarshaller.unmarshal(
					new FileReader(xmlFileName ));
			//4. Get the instance of the required JAXB Root Class from the JAXBElement.
			HeaderList listHeadersObj = unmarshalledObject.getValue();
			//Obtaining all the required data from the JAXB Root class instance.
			readList = new ArrayList<Header>();
			for ( Header item : listHeadersObj.getHeaderelem()){
				readList.add(item);
			}
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return readList;
	}

}
