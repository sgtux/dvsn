package br.com.dvsn.auth.filters;

import br.com.dvsn.auth.UsuarioDetails;
import br.com.dvsn.enums.TipoAutenticacao;
import br.com.dvsn.helpers.JwtHelper;
import br.com.dvsn.helpers.TokenOpacoHelper;
import br.com.dvsn.repository.SessaoRepository;
import br.com.dvsn.repository.UsuarioRepository;
import br.com.dvsn.security.SecurityRuntimeConfig;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class TokenOpacoAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private SessaoRepository sessaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        if (SecurityRuntimeConfig.getInstance().getTipoAutenticacao() != TipoAutenticacao.TokenOpaco) {
            filterChain.doFilter(request, response);
            return;
        }

        var sessao = TokenOpacoHelper.verificarSessao(request, sessaoRepository);

        if (sessao == null) {
            filterChain.doFilter(request, response);
            return;
        }

        var usuario = usuarioRepository.buscarPorId(sessao.getUsuarioId());

        var authentication = new UsernamePasswordAuthenticationToken(new UsuarioDetails(usuario), usuario.getEmail(), new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}