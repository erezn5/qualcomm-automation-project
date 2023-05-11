package org.qualcomm.automation.framework.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FileUtil {

    private static final Gson GSON = new GsonBuilder().create();

    private FileUtil() { }

    public static Properties createPropertiesFromResource(Class clazz , String relativePath) {
        try(InputStream ips = clazz.getClassLoader().getResourceAsStream(relativePath)){
            Properties properties = new Properties();
            properties.load(ips);
            return properties;
        }catch (IOException e){
            System.err.println("Failed to convert resource'" + relativePath + "'stream to properties, cause: " + e.getMessage());
            return null;
        }
    }

    public static void createFolder(File folder , boolean recursive){
        if(folder.exists() && folder.isDirectory()){
            System.out.println(folder.getName() + " directory already exist");
        }else if((recursive ? folder.mkdirs() : folder.mkdir())){
            System.out.println(folder.getName() + " directory created successfully");
        }else{
            System.out.println("failed to create '" + folder.getName() + "' directory");
        }
    }

}

