package org.example.controller;

import org.example.dto.Program;
import org.example.exception.FileException;
import org.example.exception.TestTypeException;
import org.example.service.TestHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GraderController {
    TestHandlerService testHandlerService;
    @Autowired
    public GraderController(TestHandlerService testHandlerService){
        this.testHandlerService = testHandlerService;
    }
    @PostMapping("program")
    public ResponseEntity<Program> getTestResults(@RequestBody Program incoming){
        try{
            Program program = testHandlerService.handleTest(incoming);
            return new ResponseEntity<>(program, HttpStatus.OK);
        } catch (TestTypeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (FileException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
