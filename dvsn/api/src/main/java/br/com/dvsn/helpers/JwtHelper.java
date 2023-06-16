package br.com.dvsn.helpers;

import br.com.dvsn.entities.Usuario;
import br.com.dvsn.security.SecurityRuntimeConfig;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Calendar;
import java.util.Date;

public class JwtHelper {

    private static String SECRET = "ba1f2511fc30423bdbb183fe33f3dd0f";

    private static String PREFIXO_TOKEN = "Bearer ";

    public static String criarToken(Usuario usuario) {
        var date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, SecurityRuntimeConfig.getInstance().getSessionMinutes());
        String token = JWT.create()
                .withClaim("id", usuario.getId())
                .withClaim("nome", usuario.getNome())
                .withClaim("sobrenome", usuario.getSobrenome())
                .withClaim("funcao", usuario.getFuncao())
                .withClaim("foto", usuario.getFoto())
                .withExpiresAt(cal.getTime())
                .sign(Algorithm.HMAC512(SECRET.getBytes()));

        return token;
    }

    public static Usuario verificarToken(HttpServletRequest request) {

        var header = request.getHeader("Authorization");

        if(StringHelper.isNullOrEmpty(header))
            return null;

        var token = header.replace(PREFIXO_TOKEN, "");

        var decoded = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                .build()
                .verify(token.replace(PREFIXO_TOKEN, ""));

        Usuario usuario = null;

        if(decoded != null) {
            usuario = new Usuario();
            usuario.setId(decoded.getClaim("id").asLong());
            usuario.setNome(decoded.getClaim("nome").asString());
            usuario.setSobrenome(decoded.getClaim("sobrenome").asString());
            usuario.setFuncao(decoded.getClaim("funcao").asInt());
            usuario.setFoto(decoded.getClaim("foto").asString());
        }

        return usuario;
    }
}