package in.raul.generateCrud;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.MissingFormatArgumentException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import in.raul.generateCrud.templates.FileTemplates;
import jakarta.annotation.PreDestroy;

@Service
public class Script {
	
	Logger logger = LogManager.getLogger(Script.class);
	
	@PreDestroy
	public void generate() {
		logger.info("Ejecutando script");
		
		HashMap<String, String> args = PropertiesHandler.args;
		
		if( !args.containsKey("--name") || !args.containsKey("--packageName") ) {
			logger.fatal("Faltan las flags --name o --packageName");
			throw new MissingFormatArgumentException("Faltan las flags --name o --packageName");
		}
		
		String name = args.get("--name");
		name = name.substring(0, 1).toUpperCase().concat(name.substring(1));

		String packageName = args.get("--packageName");

		FileTemplates ft = new FileTemplates(name, packageName);
		
		String basePath = "";
		
		if(AppProperties.isDev()) {
			basePath += "C:/Users/raul/Desktop/generateScriptDest/";
		} else {
			basePath += "./";
		}
		
		String packageFolder = packageName.replace('.', '/');
		if(!packageFolder.endsWith("/")) {
			packageFolder = packageFolder.concat("/");
		}
		
		basePath = basePath.concat(packageFolder);
        //Controller
		String contenido = ft.getControllerTemplate();
		String path = basePath + "controllers/";
		String fileName = path +name+"Controller.java";
		createFile(fileName, contenido);
	
        //Dto
		contenido = ft.getDtoTemplate();
		path= basePath + "dto/";
		fileName = path +name+"DTO.java";
		createFile(fileName, contenido);
        
	    //Dao
  		contenido = ft.getDaoTemplate();
  		path= basePath + "models/dao/";
  		fileName = path +"I"+name+"Dao.java";
  		createFile(fileName, contenido);
	    
  	    
  	    //Service
  		contenido = ft.getServiceTemplate();
  		path= basePath + "models/service/";
  		fileName = path+"I" +name+".java";
  		createFile(fileName, contenido);

  	    
  	    //Implement
  		contenido = ft.getImplementTemplate();
  		path= basePath + "models/implement/";
  		fileName = path+"I" +name+"Impl.java";
  	    createFile(fileName, contenido);
  	    
  	    //Entity
  		contenido = ft.getEntityTemplate();
  		path= basePath + "models/entity/";
  		fileName = path +name+".java";
  		createFile(fileName, contenido);
	}
	public boolean createFile(String path, String content) {
		
		File directorio = new File(path.substring(0,  path.lastIndexOf('/') ));
		if (!directorio.exists()) {
		    boolean check = directorio.mkdirs();
		    if(check) {
		    	logger.info("Directorio creado correctamente");
		    } else {
		    	logger.info("No se ha creado el directorio" );
		    }
		}
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
	  	       
  	        writer.write(content);
  	        logger.info("Archivo creado exitosamente.");
  	        return true;
        } catch (IOException e) {
  	          logger.error("Error al crear el archivo: " + e.getMessage());
  	          return false;
  	    }
		
	}
}
