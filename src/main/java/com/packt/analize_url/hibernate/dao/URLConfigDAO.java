package com.packt.analize_url.hibernate.dao;

import org.hibernate.Session;

import com.packt.analize_url.domain.*;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public interface URLConfigDAO {

	// operation with session
    public Session openCurrentSession();
    public Session openCurrentSessionWithTransaction();
    public void closeCurrentSession();
    public void closeCurrentSessionWithTransaction();
    public void closeCurrentSessionWithRollback();

    public void begintransactionCurrentSession();
    public void clear();
    public void flush();
    public void commitCurrentSession();
    public void rollback();

    // operation with URLConfig
    public void addURLConfig(URLConfig urlConfig) throws SQLException;
    public void saveOrUpdateURLConfig(URLConfig urlConfig) throws SQLException;
    public void updateURLConfig(URLConfig urlConfig) throws SQLException;
    public void replicateURLConfig(URLConfig urlConfig) throws SQLException;
    public void deleteURLConfig(URLConfig urlConfig) throws SQLException;
    public void deleteURLConfigById(String id) throws SQLException;

    public URLConfig getProductById(String id) throws SQLException;
    // criteria
    public List<URLConfig> getAllURLConfigs_criteria() throws SQLException;
}
