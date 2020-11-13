package com.homeService.controllers.common;

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
            String str = ConfigWriteReader.parse(conf).get("pathDirImage");
            System.err.println(str);
            if (str == null) str = "images/";
            return str;
        } catch (Exception e) {
            System.err.println("Не удалось считать файл конфигурации: resources/application.properties");
        }
        return "images/";
    }

    @GetMapping("/resource")
    public @ResponseBody byte[] getFile(@RequestParam("path") String path) throws IOException {
        return get("src/main/resources/" + path);
    }

    @GetMapping("/resource/{name}/style.css")
    public @ResponseBody byte[] getStyle(@PathVariable("name") String name) throws IOException {
        return get("src/main/resources/templates/" + name + "/css/styles.css");
    }

    @GetMapping("/resource/com/{dir}/{name}.{exe}")
    public @ResponseBody byte[] getCommons2(
            @PathVariable("dir") String dir,
            @PathVariable("name") String name,
            @PathVariable("exe") String exe
    ) throws IOException {
        return get("src/main/resources/commons/" + dir + '/' + name + '.' + exe);
    }

    @GetMapping("/favicon.ico")
    public @ResponseBody byte[] getFavicon() throws IOException {
        return get("src/main/resources/commons/img/favicon.ico");
    }

    @GetMapping("/resource/img/{name}")
    public @ResponseBody byte[] getImage(@PathVariable("name") String name) throws IOException {
        return get(PATH_IMAGE + name);
    }

    private byte[] get(String path) throws IOException {
        InputStream in = new FileInputStream(new File(path));
        return IOUtils.readAllBytes(in);
    }
}
