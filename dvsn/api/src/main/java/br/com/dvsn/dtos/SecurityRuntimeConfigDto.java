package br.com.dvsn.dtos;

import br.com.dvsn.enums.TipoAutenticacao;
import br.com.dvsn.security.SecurityRuntimeConfig;

public class SecurityRuntimeConfigDto {
    private boolean cookieHttpOnly;

    private boolean cookieSecure;

    private String cookieDomain;

    private int sessionMinutes;

    private TipoAutenticacao tipoAutenticacao;

    private boolean xssPreventionEnabled;

    private boolean sqlInjectionPreventionEnabled;

    public SecurityRuntimeConfigDto() { }

    public SecurityRuntimeConfigDto(SecurityRuntimeConfig config) {
        cookieHttpOnly = config.isCookieHttpOnly();
        cookieSecure = config.isCookieSecure();
        cookieDomain = config.getCookieDomain();
        sessionMinutes = config.getSessionMinutes();
        tipoAutenticacao = config.getTipoAutenticacao();
        xssPreventionEnabled = config.isXssPreventionEnabled();
        sqlInjectionPreventionEnabled = config.isSqlInjectionPreventionEnabled();
    }

    public boolean isCookieHttpOnly() {
        return cookieHttpOnly;
    }

    public void setCookieHttpOnly(boolean cookieHttpOnly) {
        this.cookieHttpOnly = cookieHttpOnly;
    }

    public boolean isCookieSecure() {
        return cookieSecure;
    }

    public void setCookieSecure(boolean cookieSecure) {
        this.cookieSecure = cookieSecure;
    }

    public String getCookieDomain() {
        return cookieDomain;
    }

    public void setCookieDomain(String cookieDomain) {
        this.cookieDomain = cookieDomain;
    }

    public int getSessionMinutes() {
        return sessionMinutes;
    }

    public void setSessionMinutes(int sessionMinutes) {
        this.sessionMinutes = sessionMinutes;
    }

    public TipoAutenticacao getTipoAutenticacao() {
        return tipoAutenticacao;
    }

    public void setTipoAutenticacao(TipoAutenticacao tipoAutenticacao) {
        this.tipoAutenticacao = tipoAutenticacao;
    }

    public boolean isXssPreventionEnabled() {
        return xssPreventionEnabled;
    }

    public void setXssPreventionEnabled(boolean xssPreventionEnabled) {
        this.xssPreventionEnabled = xssPreventionEnabled;
    }

    public boolean isSqlInjectionPreventionEnabled() {
        return sqlInjectionPreventionEnabled;
    }

    public void setSqlInjectionPreventionEnabled(boolean sqlInjectionPreventionEnabled) {
        this.sqlInjectionPreventionEnabled = sqlInjectionPreventionEnabled;
    }
}