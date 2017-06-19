package com.packt.analize_url.domain.repository.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PreDestroy;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.packt.analize_url.domain.URLConfig;
import com.packt.analize_url.domain.configpart.ResponseLength;
import com.packt.analize_url.domain.configpart.ResponseTime;
import com.packt.analize_url.domain.repository.URLConfigRepository;
import com.packt.analize_url.hibernate.dao.URLConfigDAO;
import com.packt.analize_url.hibernate.dao.URLConfigDAOImpl;
import com.packt.analize_url.jdbc.BaseConnector;


@Repository
public class JDBCMySqlConfigRepository implements URLConfigRepository{

	private List<URLConfig> listOfURLConfigs = new ArrayList<URLConfig>();
	
	// JDBC connect to the database and operations with it
	BaseConnector baseConnector;
	
	// table in the database to save URL
	String TableName = "urlconfig";
	
	// columns of the table in the database
	String columnID = "id";
	String columnUrl = "url";
	String columnRun = "run";
	String columnPeriod = "period";
	String columnCode = "code";
	String columnTimeOK = "timeok";
	String columnTimeCritic = "timecritic";
	String columnTimeWarn = "timewarn";
	String columnSubstring = "substring";
	String columnMinSize = "minsize";
	String columnMaxSize = "maxsize";


	public JDBCMySqlConfigRepository() {
		
		baseConnector = new BaseConnector("jdbc:mysql://localhost/testdb?","postgres","Admin123");
		
		fillList();
	}

	public List<URLConfig> getAllURLConfigs() {
		return listOfURLConfigs;
	}

	public URLConfig getURLConfigById(String urlConfigID) {
		URLConfig urlConfigById = null;
		for (URLConfig urlConfig : listOfURLConfigs) {
			if (urlConfig != null && urlConfig.getUrlId() != null && urlConfig.getUrlId().equals(urlConfigID)) {
				urlConfigById = urlConfig;
				break;
			}
		}
		if (urlConfigById == null) {
			throw new IllegalArgumentException("No urlConfig found with the urlConfig id: " + urlConfigID);
		}
		return urlConfigById;
	}

	public void addURLConfig(URLConfig urlConfig) {
		
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO " + TableName + " values(");
		query.append(urlConfig.getUrlId() + ",");
		
		if(urlConfig.isMonitored())
			query.append("'Y',");
		else
			query.append("'N',");
		
		query.append(urlConfig.getMonitoringPeriod() + ",");
		query.append(urlConfig.getResponseCode() + ",");
		query.append("'" + urlConfig.getResponseFindSubstring() + "',");
		query.append(urlConfig.getResponseLength().getMaxLength() + ",");
		query.append(urlConfig.getResponseLength().getMinLength() + ",");
		query.append(urlConfig.getResposeTime().getTimeForCritical() + ",");
		query.append(urlConfig.getResposeTime().getTimeForOK() + ",");
		query.append(urlConfig.getResposeTime().getTimeForWarning() + ",");
		query.append("'" + urlConfig.getUrl() + "')");
		
		System.out.println(query);
		
		boolean error = false;
		try {
			baseConnector.executeIUD(query.toString());
		} catch (Exception ex) {
			ex.printStackTrace();
			error = true;
		} finally {
			baseConnector.releaseResourses();
		}
		if(!error)
			fillList();
		
	}
	
	public void deleteURLConfig(String urlConfigID) {

		StringBuilder query = new StringBuilder();
		query.append("DELETE from " + TableName + " where id = " + urlConfigID);
		
		
		boolean error = false;
		try {
			baseConnector.executeIUD(query.toString());
		} catch (Exception ex) {
			ex.printStackTrace();
			error = true;
		} finally {
			baseConnector.releaseResourses();
		}
		
		if(!error)
			fillList();
	}
	
	public void updateURLConfig(URLConfig urlConfig) {
		
		StringBuilder query = new StringBuilder();
		query.append("UPDATE " + TableName + " SET ");

		if(urlConfig.isMonitored())
			query.append(columnRun + " = 'Y', ");
		else
			query.append(columnRun + " = 'N', ");
		
		query.append(columnPeriod + " = " + urlConfig.getMonitoringPeriod() + ", ");
		query.append(columnCode + " = " + urlConfig.getResponseCode() + ", ");
		query.append(columnSubstring + " = '" + urlConfig.getResponseFindSubstring() + "', ");
		query.append(columnMaxSize + " = " + urlConfig.getResponseLength().getMaxLength() + ", ");
		query.append(columnMinSize + " = " + urlConfig.getResponseLength().getMinLength() + ", ");
		query.append(columnTimeCritic + " = " + urlConfig.getResposeTime().getTimeForCritical() + ", ");
		query.append(columnTimeOK + " = " + urlConfig.getResposeTime().getTimeForOK() + ", ");
		query.append(columnTimeWarn + " = " + urlConfig.getResposeTime().getTimeForWarning() + ", ");
		query.append(columnUrl + " = '" + urlConfig.getUrl() + "' WHERE id = " + urlConfig.getUrlId());
		
		boolean error = false;
		try {
			baseConnector.executeIUD(query.toString());
		} catch (Exception ex) {
			ex.printStackTrace();
			error = true;
		} finally {
			baseConnector.releaseResourses();
		}
		if(!error)
			fillList();
		
	}
	
	// read configs of the URLs from the database and save result in the local copy
	private void fillList() {
		
		List<URLConfig> newlistOfURLConfigs = new ArrayList<URLConfig>();
		
		boolean error = false;
		try {
			ResultSet rs = baseConnector.executeSelect("SELECT * FROM " + TableName);
			
			while (rs.next()) {
				URLConfig readURLConf = new URLConfig();
				
				readURLConf.setUrlId(rs.getString(columnID));
				readURLConf.setUrl(rs.getString(columnUrl));
				readURLConf.setMonitoringPeriod(rs.getInt(columnPeriod));
				
				ResponseTime responseTime = new ResponseTime();
				responseTime.setTimeForOK(rs.getInt(columnTimeOK));
				responseTime.setTimeForWarning(rs.getInt(columnTimeWarn));
				responseTime.setTimeForCritical(rs.getInt(columnTimeCritic));	
				readURLConf.setResposeTime(responseTime );
				
				readURLConf.setResponseCode(rs.getInt(columnCode));
				
				ResponseLength responseLength = new ResponseLength();
				responseLength.setMinLength(rs.getInt(columnMinSize));
				responseLength.setMaxLength(rs.getInt(columnMaxSize));
				readURLConf.setResponseLength(responseLength);
				
				readURLConf.setResponseFindSubstring(rs.getString(columnSubstring));
				readURLConf.setMonitored(rs.getBoolean(columnRun));
				
				newlistOfURLConfigs.add(readURLConf);
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			error = true;
		} finally {
			baseConnector.releaseResourses();
		}
		
		if(!error) {
			listOfURLConfigs = newlistOfURLConfigs;
		}
	}

}
