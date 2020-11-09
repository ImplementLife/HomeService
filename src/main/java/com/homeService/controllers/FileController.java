package com.homeService.controllers;

import com.homeService.lib.ConfigWriteReader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.IOUtils;

import java.io.*;
import java.util.ArrayList;

@Controller
public class FileController {
    private static final String PATH_IMAGE = init();
    private static String init() {
        try {
            File conf = new File("src/main/resources/application.properties");
            return  ConfigWriteReader.parse(conf).get("pathDirImage");
        } catch (IOException e) {
            System.out.println("Не удалось считать файл конфигурации: resources/application.properties");
        }
        return "images/";
    }


    @GetMapping("/resource/style/{name}")
    public @ResponseBody byte[] getStyle(@PathVariable("name") String name) throws IOException {
        return get("src/main/resources/web/page/" + name + "/css/styles.css");
    }
    @GetMapping("/resource/img/{name}")
    public @ResponseBody byte[] getImage(@PathVariable("name") String name) throws IOException {
        return get(PATH_IMAGE + name);
    }

    @GetMapping("/resource/{dir}/{name}")
    public @ResponseBody byte[] getCommons(
            @PathVariable("dir") String dir,
            @PathVariable("name") String name) throws IOException
    { return get("src/main/resources/web/commons/" + dir + '/' + name); }

    private byte[] get(String path) throws IOException {
        InputStream in = new FileInputStream(new File(path));
        return IOUtils.readAllBytes(in);
    }

    @PostMapping("/resource")
    public @ResponseBody String upload(@RequestParam("file") MultipartFile[] file) {
        ArrayList<String> result = new ArrayList<>();
        String str = "error";
        for (MultipartFile f : file) {
            str = "Файл [ " + f.getOriginalFilename() + " ] успешно загружен на сервер.";
            if (!f.isEmpty()) {
                try {
                    File downFile = new File("download/" + f.getOriginalFilename());
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
        return str;
    }
}
