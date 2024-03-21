package org.example.service;

import org.example.Main;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * service class used to execute command line arguments
 */
@Service
public class CMDService {
    /**
     * run a cmd command and convert the entire output to a string
     * @param cmd
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public String runCommandReturnOutput(String cmd, File dir) throws IOException, InterruptedException {
        Main.logger.info("Running command "+cmd+" in directory "+dir.getName());
        Process process = Runtime.getRuntime().exec(cmd.split(" "), new String[]{""}, dir);
        process.waitFor();
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(process.getInputStream()));
        String result = "";
        String outputLine = "";
        do{
            outputLine = reader.readLine();
            if(outputLine!=null)
                result += outputLine + "\n";
        }while(outputLine!=null);
        process.waitFor();
        Main.logger.info("Command "+cmd +" in directory "+dir.getName()+ " produced result: "+
                result);
        return result;
    }

}
