package br.com.dvsn.helpers;

public class Constantes {
    public static final String AUTH_COOKIE_NAME = "dvsm_cookie";

    private static final String PREFIX_URL_IMAGE = "http://localhost:8080/image/";

    public static final String getPrefixUrlImage() {
        var path = System.getenv("PREFIX_URL_IMAGE");
        return path == null || path.isEmpty() ? PREFIX_URL_IMAGE : path;
    }
}
