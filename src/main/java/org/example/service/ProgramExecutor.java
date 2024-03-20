package org.example.service;

import org.example.dto.Program;

import java.io.File;

public interface ProgramExecutor {

    public Program conductTests(File directory);
}
