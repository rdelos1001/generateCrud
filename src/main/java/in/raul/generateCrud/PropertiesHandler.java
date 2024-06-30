package in.raul.generateCrud;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class PropertiesHandler {

	public static HashMap<String,String> args = null;
	
	public static void config(String args[]) {
		
		PropertiesHandler.args = new LinkedHashMap<>();
		
		for (int i = 0; i < args.length; i++) {
			
			String[] arr = args[i].split("=");
			if(arr.length > 0)
				PropertiesHandler.args.put(arr[0] , arr[1]);
			
		}
		
		if(!PropertiesHandler.args.containsKey("--name")) {
			PropertiesHandler.args.put("--name", AppProperties.getName());
		}
		
		if(!PropertiesHandler.args.containsKey("--packageName")) {
			PropertiesHandler.args.put("--packageName", AppProperties.getPackage());
		}
		
	}
}
