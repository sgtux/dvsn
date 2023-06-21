package br.com.dvsn.helpers;

import br.com.dvsn.entities.Sessao;
import br.com.dvsn.entities.Usuario;
import br.com.dvsn.repository.SessaoRepository;
import br.com.dvsn.security.SecurityRuntimeConfig;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class TokenOpacoHelper {

    private static String PREFIXO_TOKEN = "Bearer ";

    public static Sessao criarSessao(Usuario usuario) {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, SecurityRuntimeConfig.getInstance().getSessionMinutes());
        var sessao = new Sessao();
        sessao.setToken(UUID.randomUUID().toString().replaceAll("-", ""));
        sessao.setExpiraEm(cal.getTime());
        sessao.setAtivo(true);
        sessao.setUsuarioId(usuario.getId());

        return sessao;
    }

    public static Sessao verificarSessao(HttpServletRequest request, SessaoRepository sessaoRepository) {

        var header = request.getHeader("Authorization");

        if(StringHelper.isNullOrEmpty(header))
            return null;

        var token = header.replace(PREFIXO_TOKEN, "");

        if(StringHelper.isNullOrEmpty(token))
            return null;

        var sessao = sessaoRepository.findByToken(token);

        if(!sessao.isAtivo() || sessao.getExpiraEm().compareTo(new Date()) < 0)
            return null;

        return sessao;
    }
}