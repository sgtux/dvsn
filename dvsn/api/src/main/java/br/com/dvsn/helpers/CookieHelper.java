package br.com.dvsn.helpers;

import br.com.dvsn.security.SecurityRuntimeConfig;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public final class CookieHelper {

    public static void AddCookie(HttpServletResponse response, String chave, String valor) {

        var config = SecurityRuntimeConfig.getInstance();

        var cookie = new Cookie(chave, valor);
        cookie.setMaxAge(config.getSessionMinutes() * 60);
        cookie.setPath("/");
        cookie.setHttpOnly(config.isCookieHttpOnly());
        cookie.setSecure(config.isCookieSecure());

        if(!StringHelper.isNullOrEmpty(config.getCookieDomain()))
            cookie.setDomain(config.getCookieDomain());

        response.addCookie(cookie);
    }

    public static Cookie getCookie(HttpServletRequest request, String chave){

        var cookies = request.getCookies();

        if(cookies == null)
            return null;

        for (var c : cookies) {
            if(c.getName().equals(chave))
                return c;
        }
        return null;
    }

    public static void clearCookie(HttpServletResponse response, String chave) {
        var cookie = new Cookie(chave, "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    public static String getCookieValue(HttpServletRequest request, String chave) {
        var cookie = getCookie(request, chave);
        return cookie == null ? null : cookie.getValue();
    }
}
