package org.example.service;

import org.example.exception.FileException;
import org.springframework.stereotype.Service;

import javax.annotation.processing.FilerException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class FileManagerService {
    public void writeBytesToZip(String directoryName, byte[] data) throws FileException{
        File outputFile = new File(directoryName+".zip");
        try {
            outputFile.createNewFile();
            try(FileOutputStream outputStream = new FileOutputStream(outputFile)){
                outputStream.write(data);
            } catch (IOException e) {
                e.printStackTrace();
                throw new FileException("issue writing data to zipfile.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileException("issue creating zipfile.");
        }
    }
    public void unzipFile(String name) throws FileException {
        try {
            String fileZip = "./" + name+".zip";
            File destDir = new File("./"+name);
            destDir.mkdir();
            byte[] buffer = new byte[1024];
            ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                File newFile = newFile(destDir, zipEntry);
                if (zipEntry.isDirectory()) {
                    if (!newFile.isDirectory() && !newFile.mkdirs()) {
                        throw new IOException("Failed to create directory " + newFile);
                    }
                } else {
                    // fix for Windows-created archives
                    File parent = newFile.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                        throw new IOException("Failed to create directory " + parent);
                    }

                    // write file content
                    FileOutputStream fos = new FileOutputStream(newFile);
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    fos.close();
                }
                zipEntry = zis.getNextEntry();
            }

            zis.closeEntry();
            zis.close();
        }catch (IOException e){
            e.printStackTrace();
            throw new FileException("issue while unzipping");
        }
    }
    /**
     * unpacks subdirectories of the zip file
     */
    public File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }
        return destFile;
    }
    public void tearDown(String name){
        File dir = new File(name);
        deleteDirectory(dir);
        File zip = new File(name+".zip");
        zip.delete();
    }
    public void deleteDirectory(File dir){
        File[] allContents = dir.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
    }
}
