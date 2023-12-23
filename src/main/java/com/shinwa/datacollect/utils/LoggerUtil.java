package com.shinwa.datacollect.utils;

import java.io.IOException;
import java.util.logging.*;

public class LoggerUtil {

    public static Logger getLogger(String className){
        Logger logger = Logger.getLogger(className);
        try {
            Handler fileHandler=  new FileHandler("log.txt");
            logger.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            logger.setLevel(Level.INFO);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logger;
    }
}
