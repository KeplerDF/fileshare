package com.fileshare;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Component
public class FileStorageService{

    private static final String STORAGE_DIRECTORY = "C:\\upload";

    public void saveFile(MultipartFile filetosave) throws IOException {
        if (filetosave == null){
            throw new NullPointerException("filetosave is null");
        }
        var targetfile = new File(STORAGE_DIRECTORY + File.separator + filetosave.getOriginalFilename());
        if(!Objects.equals(targetfile.getParent(), STORAGE_DIRECTORY)){
            throw new SecurityException("Unsupported file name");
        }
        Files.copy(filetosave.getInputStream(), targetfile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    public File[] getAllFiles() throws IOException {
        var targetfiles = new File(STORAGE_DIRECTORY + File.separator);
        File[] list = targetfiles.listFiles();
        return list;
    }

    public File getDownloadFile(String filename) throws FileNotFoundException {
        if( filename == null){
            throw new NullPointerException("fileName is null");
        }
        var filetodownload = new File( STORAGE_DIRECTORY + File.separator + filename);
        if(!Objects.equals(filetodownload.getParent(), STORAGE_DIRECTORY)){
            throw new SecurityException("Unsupported file name");
        }
        if(!filetodownload.exists()){
            throw new FileNotFoundException("File" + filename + " does not exist");
        }
        return filetodownload;
    }

    @Bean
    public FileStorageService getFileStorageService() {
        return new FileStorageService();
    }

}
