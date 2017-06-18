package com.packt.analize_url.hibernate.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.*;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;

import com.packt.analize_url.domain.URLConfig;
import com.packt.analize_url.hibernate.util.HibernateUtil;

@Service
public class URLConfigDAOImpl implements URLConfigDAO{
	
    //the current active session
    private Session session;

    //the active SessionFactory
    private SessionFactory sessionFactory;


    //Just get the current session
	public Session openCurrentSession() {
        sessionFactory = HibernateUtil.getSessionAnnotationFactory();
        session = sessionFactory.getCurrentSession();
        return session;
	}

    // Get the current session and begin the transaction
	public Session openCurrentSessionWithTransaction() {
        sessionFactory = HibernateUtil.getSessionAnnotationFactory();
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        return session;
	}

    //Just close the current session.
	public void closeCurrentSession() {
		HibernateUtil.getSessionAnnotationFactory().close();
	}

    //Commit the current session and close it.
	public void closeCurrentSessionWithTransaction() {
        session.getTransaction().commit();
        HibernateUtil.getSessionAnnotationFactory().close();
	}

    // Rollback and close the current session.
	public void closeCurrentSessionWithRollback() {
        session.getTransaction().rollback();
        HibernateUtil.getSessionAnnotationFactory().close();
	}

    // Just begin the transaction.
	public void begintransactionCurrentSession() {
		session.beginTransaction();
	}

    //Clear the session. Remove all managed entities from L1 cache.
	public void clear() {
		session.clear();
	}
	
    //Flush the session.
	public void flush() {
		session.flush();
	}

    //Commit the session and begin the transaction again.
	public void commitCurrentSession() {
        session.getTransaction().commit();
        sessionFactory = HibernateUtil.getSessionAnnotationFactory();
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
	}

    //Rollback the session and begin the transaction again.
	public void rollback() {
        session.getTransaction().rollback();
        sessionFactory = HibernateUtil.getSessionAnnotationFactory();
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
	}
	
	
    //Persist the instance.
	public void addURLConfig(URLConfig urlConfig) throws SQLException {
		session.save(urlConfig);
	}

    // Either save(Object) or update(Object) the given instance.
	public void saveOrUpdateURLConfig(URLConfig urlConfig) throws SQLException {
		clear();
		session.saveOrUpdate(urlConfig);
	}
	
    // Update the persistent instance with the identifier of the given detached instance.
	public void updateURLConfig(URLConfig urlConfig) throws SQLException {
		session.update(urlConfig);
	}

    //Persist the state of the given detached instance, reusing the current identifier value with the ReplicationMode OVERWRITE.
	public void replicateURLConfig(URLConfig urlConfig) throws SQLException {
		session.replicate(urlConfig, ReplicationMode.OVERWRITE);
	}

    //Remove a persistent instance from the dataStore.
	public void deleteURLConfig(URLConfig urlConfig) throws SQLException {
		session.delete(urlConfig);
	}

    //Remove a persistent instance from the dataStore using its id.
	public void deleteURLConfigById(String id) throws SQLException {
		URLConfig urlConfig = null;
		urlConfig = (URLConfig) session.get(URLConfig.class,id);
        session.delete(urlConfig);
	}

    // Get the urlConfig from database using explicit Id.
	public URLConfig getProductById(String id) throws SQLException {
		URLConfig urlConfig=null;
		urlConfig = (URLConfig) session.load(URLConfig.class,id);
        return urlConfig;
	}
	
    // Get all URLConfigs from the database using Criteria.
	public List<URLConfig> getAllURLConfigs_criteria() throws SQLException {
        List<URLConfig> urlConfigs = new ArrayList<URLConfig>();
        urlConfigs = session.createCriteria(URLConfig.class).list();
        return urlConfigs;
	}

}
