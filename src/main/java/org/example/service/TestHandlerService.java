package org.example.service;

import org.example.dto.Program;
import org.example.exception.FileException;
import org.example.exception.TestTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class TestHandlerService {
    ProgramExecutor programExecutor;
    ApplicationContext applicationContext;
    FileManagerService fileManagerService;
    BlobService blobService;
    @Autowired
    public TestHandlerService(ApplicationContext applicationContext, FileManagerService fileManagerService, BlobService blobService){
        this.applicationContext = applicationContext;
        this.fileManagerService = fileManagerService;
        this.blobService = blobService;
    }
    public Program handleTest(Program program) throws TestTypeException, FileException {
        determineTestType(program.getType());
        ByteArrayResource bytes = blobService.getSavedFromAzure(program.getId());
        fileManagerService.writeBytesToZip(program.getId(), bytes.getByteArray());
        fileManagerService.unzipFile(program.getId());
        File programDirectory = new File(program.getId());
        Program testReport = programExecutor.conductTests(programDirectory);
        fileManagerService.tearDown(program.getId());
        return null;
    }
    public void determineTestType(String type) throws TestTypeException {

        if(type.equals("maven")){
            programExecutor = applicationContext.getBean(MavenExecutorService.class);
        }else{
            throw new TestTypeException("test type not found");
        }
    }
}
