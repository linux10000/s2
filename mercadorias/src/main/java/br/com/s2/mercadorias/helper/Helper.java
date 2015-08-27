package br.com.s2.mercadorias.helper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;

import org.hibernate.exception.ConstraintViolationException;

import br.com.s2.mercadorias.exception.ParametroJsonNaoEncontradoException;
import br.com.s2.mercadorias.exception.S2Exception;
import br.com.s2.mercadorias.pojo.GenericPojo;

public class Helper {

	private static Helper instancia;
	
	
	public Helper(){
	}
	
	public static Helper getInstancia(){
		if ( instancia == null )
			instancia = new Helper();
		
		return instancia;
	}
	
	private static String mostraExceptionRollback(Throwable ex){
		Throwable r = ex;
		
		try {
			if (ex.getCause() instanceof PersistenceException){
				if ( ex.getCause().getCause() instanceof ConstraintViolationException){
					r = ex.getCause().getCause().getCause();
				}
				else if ( ex.getCause() instanceof OptimisticLockException){
					try {
						GenericPojo pojo = (GenericPojo) ((OptimisticLockException)ex.getCause()).getEntity();
						return 
								"Registro alterado em outra estação: " + 
						pojo.getClass().getSimpleName() + 
						" -- ID: " + 
						String.valueOf(((BigInteger)pojo.getChave()).intValue());
					} catch (Exception e) {
						return "Registro alteradao em outra estação!";
					}
				}
			}
			else
				r = ex.getCause();
		}
		catch (Exception e){
			r = ex;
		}
		
		StringWriter sw = new StringWriter();  
        PrintWriter pw = new PrintWriter (sw);  
        r.printStackTrace(pw); 
        
        return sw.toString();
	}
	
	public static String mostraException(Throwable ex){
				
		if ( ex instanceof S2Exception){
			return ex.getMessage();
		}
		else if ( ex instanceof RollbackException){
			return mostraExceptionRollback(ex);
		}
		else {
			StringWriter sw = new StringWriter();  
	        PrintWriter pw = new PrintWriter (sw);  
	        ex.printStackTrace(pw); 
	        
	        return sw.toString();
		}
	}

	public String checaIncluiBarraNoFinalDoCaminho(String caminho){
		if ( !String.valueOf(caminho.trim().charAt(caminho.trim().length() - 1)).equals(System.getProperty("file.separator")) )
			caminho = caminho.trim() + System.getProperty("file.separator");
		
		return caminho;
	}
	
	
	public static <T> T resolvePrimeiroItemNulo(List<T> lista){
		
		if ( lista != null && lista.size() > 0 )
			return lista.get(0);
		else
			return null;		
	}
	
	public static int resolveStringInteiro(String texto){
		
		try {
			return Integer.parseInt(texto);
		} catch (Exception e){
			return 0;
		}
	}
	
	public static BigDecimal bigDecimalNulo(BigDecimal valor){
		if ( valor != null )
			return valor;
		else
			return new BigDecimal(0);
	}
		
	public static BigInteger bigIntegerNulo(BigInteger bi){
		
		if ( bi != null )
			return bi;
		else return new BigInteger("0");
	}
	
	public static String bigDecimalToStringMoeda(BigDecimal valor){
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator(',');
		dfs.setGroupingSeparator('.');
		
		if ( valor != null )			
			return new DecimalFormat("#,##0.00######", dfs).format(valor);
		else
			return new DecimalFormat("#,##0.00######", dfs).format(0F);
	}
	
	public static BigDecimal stringTobigDecimalMoeda(String valor){
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator(',');
		dfs.setGroupingSeparator('.');
		
		if ( valor != null && !valor.trim().isEmpty() )			
			return new BigDecimal(valor.replace(".", "").replace(",", "."));
		else
			return new BigDecimal("0");

	}
	
	public static String stringNulo(String valor){
		if ( valor == null)
			return "";
		else
			return valor;
	}
	
	public static String dateToString(Date data){
		if ( data == null )
			return "";
		else
			return new SimpleDateFormat("dd/MM/yyyy").format(data);
	}
	
	public static Date stringToDate(String data, boolean podeTrazerException) throws Exception{
		if ( data == null )
			return null;
		else
			try {
				return new SimpleDateFormat("dd/MM/yyyy").parse(data);
			} catch (Exception e) {
				if ( podeTrazerException)
					throw e;
				else
					return null;
			}
	}
	
	public static String dateTimeToString(Date data){
		if ( data == null )
			return "";
		else
			return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(data);
	}
	
	public static Date stringToDateTime(String data, boolean podeTrazerException) throws Exception {
		if ( data == null )
			return null;
		else
			try {
				return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(data);
			} catch (Exception e) {
				if ( podeTrazerException)
					throw e;
				else
					return null;
			}
	}
	
	public static boolean cpfValido(String valor){
		try {
			String aux_valorSemDV = valor.replace("-", "").replace(".", "");
			aux_valorSemDV = aux_valorSemDV.substring(0, aux_valorSemDV.length() - 2);
			
			String aux_valor = valor.replace("-", "").replace(".", "");
			
			if ( aux_valor.length() != 11)
				return false;
			
			int primeiroDigito = 99;
			int segundoDigito = 99;
			
			int soma = 0;
			int pos = 0;		
			for (int i = 10; i > 1; i--) {			
				soma = soma + ( Integer.valueOf(aux_valorSemDV.substring(pos, pos + 1)) * i);
				pos = pos + 1;
			}
			
			primeiroDigito = ( (soma % 11) < 2 ? 0 : (11 - (soma % 11)) );
			aux_valorSemDV = aux_valorSemDV+String.valueOf(primeiroDigito);
			
			soma = 0;
			pos = 0;
			for (int i = 11; i > 1; i--) {			
				soma = soma + ( Integer.valueOf(aux_valorSemDV.substring(pos, pos + 1)) * i);
				pos = pos + 1;
			}
			
			segundoDigito = ( (soma % 11) < 2 ? 0 : (11 - (soma % 11)) );

			aux_valorSemDV = valor.replace("-", "").replace(".", "");
			if ( aux_valorSemDV.substring(9, 11).equals(String.valueOf(primeiroDigito) + String.valueOf(segundoDigito)) )
				return true;
			else
				return false;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public static boolean cnpjValido(String valor){
		try {
			String aux_valorSemDV = valor.replace("-", "").replace(".", "").replace("/", "");
			aux_valorSemDV = aux_valorSemDV.substring(0, aux_valorSemDV.length() - 2);
			
			String aux_valor = valor.replace("-", "").replace(".", "").replace("/", "");
			
			
			int primeiroDigito = 99;
			int segundoDigito = 99;
			
			int soma = 0;
			int pos = 0;
			int i = 5;
			while ( pos < aux_valorSemDV.length()) {						
				soma = soma + ( Integer.valueOf(aux_valorSemDV.substring(pos, pos + 1)) * i);
				pos = pos + 1;
				
				i = i - 1;
				
				if ( i < 2)
					i = 9;
			}

			primeiroDigito = ( (soma % 11) < 2 ? 0 : (11 - (soma % 11)) );
			aux_valorSemDV = aux_valorSemDV+String.valueOf(primeiroDigito);
			
			soma = 0;
			pos = 0;
			i = 6;
			while ( pos < aux_valorSemDV.length()) {						
				soma = soma + ( Integer.valueOf(aux_valorSemDV.substring(pos, pos + 1)) * i);
				pos = pos + 1;
				
				i = i - 1;
				
				if ( i < 2)
					i = 9;
			}
			
			segundoDigito = ( (soma % 11) < 2 ? 0 : (11 - (soma % 11)) );

			if ( aux_valor.substring(aux_valorSemDV.length() - 1, aux_valor.length()).equals(String.valueOf(primeiroDigito) + String.valueOf(segundoDigito)) )
				return true;
			else
				return false;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public static Object getHttpParam(Map<String, Object> lista, String parametro) throws ParametroJsonNaoEncontradoException{
		if ( lista.containsKey(parametro) == false )
			throw new ParametroJsonNaoEncontradoException(parametro);
		else
			return lista.get(parametro);
	}
}
