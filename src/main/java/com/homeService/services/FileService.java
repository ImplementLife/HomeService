package com.homeService.services;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class FileService {
    public ArrayList<String> save(MultipartFile[] files) throws IOException {
        ArrayList<String> result = new ArrayList<>();
        for (MultipartFile f : files) result.add(save(f));
        return result;
    }
    public String save(MultipartFile f) throws IOException {
        String name;
        if (!f.isEmpty()) {
            try {
                name = getNewName(f.getOriginalFilename());
                File downFile = new File("src/main/webapp/images/" + name);
                byte[] bytes = f.getBytes();
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(downFile));
                stream.write(bytes);
                stream.close();
            } catch (IOException e) {
                throw new IOException("Ошибка чтения / записи файла" + f.getOriginalFilename());
            }
        } else {
            throw new IOException("Файл: " + f.getOriginalFilename() + " пустой");
        }
        return name;
    }

    private int num = 0;
    private String getNewName(String name) throws IOException {
        File file = new File(name);
        if (file.exists() && !file.isDirectory()) {
            num++;
            String tempName = file.getName();
            String exe;
            if (tempName.lastIndexOf(".") != -1 && tempName.lastIndexOf(".") != 0) {
                exe = tempName.substring(tempName.lastIndexOf(".")+1);
            } else {
                throw new IOException("Ошибка в расширении файла");
            }
            tempName = tempName.substring(0, tempName.lastIndexOf(".")) + '(' + num + ")." + exe;
            return getNewName(tempName);
        } else {
            num = 0;
            return name;
        }
    }
}
