package br.com.s2.mercadorias.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;

import br.com.s2.mercadorias.annotation.PropriedadeAnnotation;
import br.com.s2.mercadorias.exception.S2Exception;
import br.com.s2.mercadorias.helper.Helper;
import br.com.s2.mercadorias.pojo.Propriedades;

public class PropriedadesDao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	public void inserir(Propriedades pro) throws Exception{
    	
		HashMap<String, String> lista = converterPropriedadeToProperty(pro);
		
		Properties prop = new Properties();
    		
		for (Entry<String, String> p : lista.entrySet()) {
				prop.setProperty(p.getKey(), Helper.stringNulo(p.getValue()));
		}
 
		//save properties to project root folder
		prop.store(new FileOutputStream(getArquivo()), null);
	}
	
	private HashMap<String, String> converterPropriedadeToProperty(Propriedades pro) throws Exception{
		HashMap<String, String> h = new HashMap<String, String>();
				
		for (Field field : pro.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			
			if ( field.isAnnotationPresent(PropriedadeAnnotation.class)){
				h.put(field.getName(), (String) field.get(pro));
			}
		}

		return h;
	}
	
	public Propriedades ler() throws Exception{
    	
		Properties prop = new Properties();

		prop.load(new FileInputStream(getArquivo()));
		
		Propriedades propriedade = converterPropertyToPropriedade(prop);
		
		return propriedade;

	}
	
	private Propriedades converterPropertyToPropriedade(Properties lista) throws Exception{
		Propriedades pro = new Propriedades();
		
		for (Field field : pro.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			
			if ( field.isAnnotationPresent(PropriedadeAnnotation.class)){
				field.set(pro, lista.get(field.getName()));
			}
		}		
		
		return pro;
	}
	

	private File getArquivo() throws Exception{
		File raiz_install = new File(this.getClass().getClassLoader().getResource(".").getPath()+"").getParentFile().getParentFile();
		File raiz_projeto = new File(this.getClass().getClassLoader().getResource("/").getPath()).getParentFile().getParentFile();
		
		if ( !raiz_install.canRead() || !raiz_install.canWrite() )
			throw new S2Exception("É preciso de permissões de leitura e gravação para a pasta " + raiz_install.getAbsolutePath());
		
		File projeto_conf = new File(raiz_install.getAbsolutePath() + System.getProperty("file.separator") + "s2_conf" + System.getProperty("file.separator") + raiz_projeto.getName().toLowerCase());
		if ( !projeto_conf.exists() )
			projeto_conf.mkdirs();			
		
		File cfg = new File(projeto_conf.getPath() + System.getProperty("file.separator") + "s2.cfg");
		
		if (!cfg.exists())
			new Properties().store(new FileOutputStream(cfg), null);
		
		return cfg;
	}

}
