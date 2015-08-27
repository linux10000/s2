package br.com.s2.mercadorias.helper;

import java.io.ByteArrayInputStream;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LoggerFactory {
	
	private static LoggerFactory instancia;
	
	public static LoggerFactory getInstancia(){
		if ( instancia == null )
			instancia = new LoggerFactory();
		
		return instancia;
	}

	private static StringBuilder parametros() throws Exception{
		
		StringBuilder sb = new StringBuilder();
		//sb.append("handlers = java.util.logging.ConsoleHandler").append(System.getProperty("line.separator"));
		sb.append("handlers = java.util.logging.ConsoleHandler, java.util.logging.FileHandler").append(System.getProperty("line.separator"));
		
		//esse vale para todo o tomcat
		sb.append(".level= INFO").append(System.getProperty("line.separator"));
		
		//File Handler Options
		sb.append("java.util.logging.FileHandler.level = FINEST").append(System.getProperty("line.separator"));
		//sb.append("java.util.logging.FileHandler.filter = integradorerpiisugar.%g.log").append(System.getProperty("line.separator"));
		sb.append("java.util.logging.FileHandler.formatter = java.util.logging.SimpleFormatter").append(System.getProperty("line.separator"));
		sb.append("java.util.logging.FileHandler.limit = 5000000").append(System.getProperty("line.separator"));
		sb.append("java.util.logging.FileHandler.count = 100").append(System.getProperty("line.separator"));
		sb.append("java.util.logging.FileHandler.append = TRUE").append(System.getProperty("line.separator"));
//		sb.append("java.util.logging.FileHandler.directory = ${catalina.base}/logs").append(System.getProperty("line.separator"));
//		sb.append("java.util.logging.FileHandler.pattern = /integradorerpiisugar.%g.log").append(System.getProperty("line.separator"));
		sb.append("java.util.logging.FileHandler.pattern = %t/integradorerpiisugar.%g.log").append(System.getProperty("line.separator"));

		//Console Handler Options
		sb.append("java.util.logging.ConsoleHandler.level = FINEST").append(System.getProperty("line.separator"));
		//sb.append("java.util.logging.ConsoleHandler.filter = integradorerpiisugar.%g.log").append(System.getProperty("line.separator"));
		//sb.append("java.util.logging.FileHandler.formatter = java.util.logging.SimpleFormatter").append(System.getProperty("line.separator"));
					
		
		return sb;
	}
	
	public static Logger getLogger(Class<?> classe) {
		Logger l = null;
		try {
			carregarParametros();
			
			l = Logger.getLogger(classe.getName());
			
			for (Handler h :l.getParent().getHandlers()) {
				h.setLevel(Level.parse("FINEST"));
			}
			
			l.setLevel(Level.parse("FINEST"));
		} catch (Exception e) {
			System.out.println("erro no metodo getLogger no LogFactory");
			System.out.println(e);
			e.printStackTrace();
		}
		
		return l;
	}
	
	public static void carregarParametros() throws Exception{
		LogManager.getLogManager().readConfiguration(new ByteArrayInputStream(parametros().toString().getBytes()));
	}
}
