package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigUtils {

    public String getPropertyValue(String propertyKey) throws IOException {
        Properties prop = readPropertiesFile("src/main/resources/config/Executionconfig.properties");
        System.out.println(propertyKey + ": " + prop.getProperty(propertyKey));
        return prop.getProperty(propertyKey);
    }

    public static Properties readPropertiesFile(String fileName) throws IOException {
        FileInputStream fis = null;
        Properties prop = null;
        try {
            fis = new FileInputStream(fileName);
            prop = new Properties();
            prop.load(fis);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            fis.close();
        }
        return prop;
    }
}
