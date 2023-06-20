package br.com.dvsn.controllers;

import br.com.dvsn.dtos.UsuarioDto;
import br.com.dvsn.dtos.UsuarioLogadoDto;
import br.com.dvsn.enums.TipoAutenticacao;
import br.com.dvsn.helpers.Constantes;
import br.com.dvsn.helpers.CookieHelper;
import br.com.dvsn.helpers.JwtHelper;
import br.com.dvsn.helpers.StringHelper;
import br.com.dvsn.security.SecurityRuntimeConfig;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController extends BaseController {

    @PostMapping("criarConta")
    public ResponseEntity criarConta(@RequestBody UsuarioDto usuarioDto) {
        try {

            if (!StringHelper.isEmail(usuarioDto.getEmail()))
                return badRequest("Email inválido.");

            if (StringHelper.isNullOrEmpty(usuarioDto.getNome())) {
                return badRequest("Nome inválido.");
            }

            if (StringHelper.isNullOrEmpty(usuarioDto.getSobrenome())) {
                return badRequest("Sobrenome inválido.");
            }

            if (usuarioRepository.buscarPorEmail(usuarioDto.getEmail()) != null) {
                return badRequest("Email já cadastrado.");
            }

            try {
                usuarioRepository.adicionar(usuarioDto);
            } catch (Exception ex) {
                return new ResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return ResponseEntity.ok().build();
        }catch(Exception ex) {
            return internalServerError(ex);
        }
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody UsuarioDto usuarioDto, HttpServletResponse response) {

        if(StringHelper.isNullOrEmpty(usuarioDto.getSenha()) || StringHelper.isNullOrEmpty(usuarioDto.getSenha()))
            return new ResponseEntity("Informe email e senha.", HttpStatus.UNAUTHORIZED);

        try {

            var usuario = usuarioRepository.login(usuarioDto);

            if (usuario != null) {

                var usuarioLogadoDto = new UsuarioLogadoDto(usuario);

                var tipoAutenticacao = SecurityRuntimeConfig.getInstance().getTipoAutenticacao();

                if(tipoAutenticacao == TipoAutenticacao.CookieBase64) {
                    String jsonData = StringHelper.toJson(usuarioLogadoDto);
                    String cookieValue = StringHelper.toBase64(jsonData);
                    CookieHelper.AddCookie(response, Constantes.AUTH_COOKIE_NAME, cookieValue);
                } else if(tipoAutenticacao == TipoAutenticacao.Jwt) {
                    var token = JwtHelper.criarToken(usuario);
                    usuarioLogadoDto.setToken(token);
                }

                return new ResponseEntity(usuarioLogadoDto, HttpStatus.OK);
            }
            return new ResponseEntity("Email ou senha inválidos", HttpStatus.UNAUTHORIZED);
        }catch (Exception ex) {
            return internalServerError(ex);
        }
    }

    @GetMapping("logout")
    public ResponseEntity logout(HttpServletResponse response) {
        try {
            CookieHelper.clearCookie(response, Constantes.AUTH_COOKIE_NAME);
            return new ResponseEntity(HttpStatus.OK);
        } catch(Exception ex) {
            return internalServerError(ex);
        }
    }
}
