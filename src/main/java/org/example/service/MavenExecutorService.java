package org.example.service;

import org.example.dto.Program;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class MavenExecutorService implements ProgramExecutor{

    CMDService cmdService;
    @Autowired
    public MavenExecutorService(CMDService cmdService){
        this.cmdService = cmdService;
    }
    @Override
    public Program conductTests(File directory) {
        try{
            String out = cmdService.runCommandReturnOutput("mvn test", directory);
            return new Program(0, 0, "", "", out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
