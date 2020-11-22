package com.homeService.controllers.common;

import com.homeService.lib.ConfigWriteReader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.IOUtils;

import java.io.*;
import java.util.ArrayList;

public class FileController {
    private static final String PATH_IMAGE = init();
    private static String init() {
        try {
            File conf = new File("src/main/resources/application.properties");
            String str = ConfigWriteReader.parse(conf).get("pathDirImage");
            System.err.println(str);
            if (str == null) str = "images/";
            return str;
        } catch (Exception e) {
            System.err.println("Не удалось считать файл конфигурации: resources/application.properties");
        }
        return "images/";
    }

    private byte[] get(String path) throws IOException {
        InputStream in = new FileInputStream(new File(path));
        return IOUtils.readAllBytes(in);
    }
}
