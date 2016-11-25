package domain.dao;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import domain.model.Gate;

public class DAOGate {
	private static SessionFactory sessionFactory;
	private static Session session;
	
	private static void before(){
		
		sessionFactory  = new Configuration().configure("/resources/hibernate.cfg.xml").buildSessionFactory();
		session= sessionFactory.openSession();
		
	}
	
	private static void after(){
		
		session.close();
		sessionFactory.close();
		
	}
	
	
	public static boolean insertGate(Gate gate){
		try{
			before();
			session.getTransaction().begin();
			session.save(gate);
			session.getTransaction().commit();
			after();
			
		}catch(Exception e){
			session.getTransaction().rollback();
			after();
			return false;
		}
		
		return true;
		
	}
	
	public static boolean deleteGate(Gate gate){
		try{
			before();
			session.delete(gate);
			after();
			
		}catch(Exception e){
			session.getTransaction().rollback();
			after();
			return false;
		}
		
		return true;
	}

	public static ArrayList<Gate> loadAllGatesFromTerminal(int terminalId) {
		
		return null;
	}

}
