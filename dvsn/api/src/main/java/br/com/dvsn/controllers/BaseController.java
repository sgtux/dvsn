package br.com.dvsn.controllers;

import br.com.dvsn.dtos.ResponseErrorDto;
import br.com.dvsn.dtos.UsuarioLogadoDto;
import br.com.dvsn.enums.TipoAutenticacao;
import br.com.dvsn.helpers.*;
import br.com.dvsn.repository.SessaoRepository;
import br.com.dvsn.repository.UsuarioRepository;
import br.com.dvsn.security.SecurityRuntimeConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController {

    @Autowired
    protected UsuarioRepository usuarioRepository;

    @Autowired
    protected SessaoRepository sessaoRepository;

    protected UsuarioLogadoDto obterUsuarioLogado(HttpServletRequest request) {

        var tipoAutenticacao = SecurityRuntimeConfig.getInstance().getTipoAutenticacao();
        if(tipoAutenticacao == TipoAutenticacao.Jwt) {
            var usuario = JwtHelper.verificarToken(request);
            return new UsuarioLogadoDto(usuario);
        }

        if(tipoAutenticacao == TipoAutenticacao.CookieBase64) {
            var cookie = CookieHelper.getCookieValue(request, Constantes.AUTH_COOKIE_NAME);

            if (cookie != null) {
                try {
                    var userJson = StringHelper.fromBase64(cookie);
                    var usuarioLogado = StringHelper.fromJson(userJson, UsuarioLogadoDto.class);

                    var usuarioDb = usuarioRepository.buscarPorId(usuarioLogado.getId());

                    if (usuarioDb != null)
                        usuarioLogado.setFoto(usuarioDb.getFoto());

                    return usuarioLogado;
                } catch (JsonProcessingException ex) {
                    System.err.println(ex);
                }
            }
        }

        if(tipoAutenticacao == TipoAutenticacao.TokenOpaco) {
            var sessao = TokenOpacoHelper.verificarSessao(request, sessaoRepository);
            var usuario = usuarioRepository.buscarPorId(sessao.getUsuarioId());
            return new UsuarioLogadoDto(usuario);
        }

        return null;
    }

    public boolean isAdmin(HttpServletRequest request) {
        var usuario = obterUsuarioLogado(request);
        return usuario.getFuncao() == 1;
    }

    protected ResponseEntity<ResponseErrorDto> forbidden() {
        return new ResponseEntity("Acesso proibido.", HttpStatus.FORBIDDEN);
    }

    protected ResponseEntity<ResponseErrorDto> badRequest(String erro) {
        return new ResponseEntity(new ResponseErrorDto(erro), HttpStatus.BAD_REQUEST);
    }

    protected ResponseEntity<ResponseErrorDto> internalServerError(Exception exception) {
        System.err.println(exception);
        return new ResponseEntity(new ResponseErrorDto("Erro interno."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}