package com.packt.analize_url.domain.property;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.packt.analize_url.domain.URLConfig;
import com.packt.analize_url.property.PropertyConnector;

public class ProperyConnectorTest {
	
	PropertyConnector propertyConnector;

	@Before
	public void setup() {
		propertyConnector = new PropertyConnector("urls");
	}
	
	@Test
	public void testRead() {
		try {
			Assert.assertEquals(propertyConnector.getAllConfigurations().size(), 2);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
