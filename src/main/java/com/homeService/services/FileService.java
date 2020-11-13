package com.homeService.services;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

@Component
public class FileService {
    public ArrayList<String> save(MultipartFile[] files) {
        ArrayList<String> result = new ArrayList<>();
        String str = "error";
        for (MultipartFile f : files) {
            str = "Файл [ " + f.getOriginalFilename() + " ] успешно загружен на сервер.";
            if (!f.isEmpty()) {
                try {
                    String name = Long.toString(System.nanoTime());
                    System.out.println(name);
                    System.out.println(f.getContentType());
                    File downFile = new File("images/" + f.getOriginalFilename());
                    byte[] bytes = f.getBytes();
                    BufferedOutputStream stream =
                            new BufferedOutputStream(new FileOutputStream(downFile));
                    stream.write(bytes);
                    stream.close();
                } catch (Exception e) {
                    result.add(e.getMessage());
                }
            } else {
                result.add("Файл [" + f.getOriginalFilename() + "] пустой.");
            }
        }
        return result;
    }
}
