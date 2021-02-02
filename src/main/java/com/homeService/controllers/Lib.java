package com.homeService.controllers;

import javax.servlet.http.HttpServletRequest;

public class Lib {

    /**
     * Обратная ссылка
     *
     * Возвращает ссылку на страницу с которой был произведён запрос
     */
    public String ref(HttpServletRequest request) {
        String referer = "/";
        if (request != null) {
            String temp = request.getHeader("Referer");
            if (temp != null &&
                    !temp.isEmpty() &&
                    !temp.equals("/login") &&
                    !temp.equals("/registration"))
            {referer = temp;}
        }
        return referer;
    }
}
