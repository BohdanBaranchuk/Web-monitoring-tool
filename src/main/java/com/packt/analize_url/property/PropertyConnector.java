package com.packt.analize_url.property;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.packt.analize_url.domain.URLConfig;
import com.packt.analize_url.domain.configpart.ResponseLength;
import com.packt.analize_url.domain.configpart.ResponseTime;


public class PropertyConnector {

	InputStream inputStream;
	
	// base directory with properties
	// "urls" - default directory with property files
	String namePath;
	
	public PropertyConnector(String namePath) {
		this.namePath = namePath;
	}
	

	public List<URLConfig> getAllConfigurations() throws Exception {
		URL url = getClass().getClassLoader().getResource(namePath);
		return scanDirectory(new File(url.getPath()));
	}
 
	private URLConfig getPropValues(String propFileName) throws IOException {
		
		URLConfig urlConfig = new URLConfig();
		
		try {
			Properties prop = new Properties();

			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
			
			urlConfig.setUrlId(prop.getProperty("id"));
			
			if(prop.getProperty("run").equals("Y"))
				urlConfig.setMonitored(true);
			else 
				urlConfig.setMonitored(false);
			
			urlConfig.setMonitoringPeriod(Integer.parseInt(prop.getProperty("period")));
			urlConfig.setResponseCode(Integer.parseInt(prop.getProperty("code")));
			urlConfig.setResponseFindSubstring(prop.getProperty("substring"));
			
			ResponseLength responseLength = new ResponseLength();
			responseLength.setMaxLength(Integer.parseInt(prop.getProperty("maxsize")));
			responseLength.setMinLength(Integer.parseInt(prop.getProperty("minsize")));
			urlConfig.setResponseLength(responseLength);
			
			ResponseTime responseTime = new ResponseTime();
			responseTime.setTimeForCritical(Integer.parseInt(prop.getProperty("timecritic")));
			responseTime.setTimeForOK(Integer.parseInt(prop.getProperty("timeok")));
			responseTime.setTimeForWarning(Integer.parseInt(prop.getProperty("timewarning")));
			urlConfig.setResposeTime(responseTime);
			
			urlConfig.setUrl(prop.getProperty("url"));

		} catch (Exception ex) {
			ex.printStackTrace();
			urlConfig = null;
		} finally {
			inputStream.close();
		}
		return urlConfig;
	}


	// fill list of the URLs config
	public List<URLConfig> scanDirectory (File dir) throws Exception{
		List<URLConfig> urls = new ArrayList<URLConfig>();
		
	    File[] files = dir.listFiles();
	    urls.clear();

	    for (File file : files) {
	    	URLConfig urlConfig = getPropValues(namePath+"/"+file.getName());
	    	if(urlConfig != null) {
	    		urls.add(urlConfig);
	    		//System.out.println(urlConfig);
	    	}
	    }
	    return urls;
	} 


}
