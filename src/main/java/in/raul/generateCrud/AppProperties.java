package in.raul.generateCrud;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Configuration
public class AppProperties {

    @Value("${name}")
    private String name;

    @Value("${packageName}")
    private String packageName;
    
    @Value("${dev}")
    private boolean dev;
    
    private static String nameStatic;
    private static String packageStatic;
    private static boolean devStatic;
    
    @PostConstruct
    public void init() {
    	nameStatic = name;
    	packageStatic = packageName;
    	devStatic = dev;
    }

    public static String getName() {
        return nameStatic;
    }
    
    public static String getPackage() {
    	return packageStatic;
    }
    
    public static boolean isDev() {
    	return devStatic;
    }
}