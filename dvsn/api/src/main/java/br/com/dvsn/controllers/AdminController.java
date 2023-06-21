package br.com.dvsn.controllers;

import br.com.dvsn.dtos.HashDto;
import br.com.dvsn.dtos.SecurityRuntimeConfigDto;
import br.com.dvsn.entities.RainbowTableHash;
import br.com.dvsn.enums.TipoAutenticacao;
import br.com.dvsn.helpers.Constantes;
import br.com.dvsn.helpers.CookieHelper;
import br.com.dvsn.repository.RainbowTableRepository;
import br.com.dvsn.security.SecurityRuntimeConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController extends BaseController {

    @Autowired
    private RainbowTableRepository rainbowTableRepository;

    @PostMapping("rainbowtable")
    public ResponseEntity rainbowtable(HttpServletRequest request, @RequestBody HashDto hashDto) {

        if (!isAdmin(request))
            return forbidden();

        RainbowTableHash rainbowTableHash = null;

        var tipo = hashDto.getTipo();

        if (tipo == 1)
            rainbowTableHash = rainbowTableRepository.findByMd5(hashDto.getHash());
        else if (tipo == 2)
            rainbowTableHash = rainbowTableRepository.findBySha1(hashDto.getHash());

        return ResponseEntity.ok(rainbowTableHash);
    }

    @PutMapping("security-config")
    public ResponseEntity updateSecurityRuntimeConfig(HttpServletRequest request, HttpServletResponse response, @RequestBody SecurityRuntimeConfigDto config) {

        if (!isAdmin(request))
            return forbidden();

        if(!config.getCookieDomain().equals(SecurityRuntimeConfig.getInstance().getCookieDomain()))
            CookieHelper.clearCookie(response, Constantes.AUTH_COOKIE_NAME);

        SecurityRuntimeConfig.getInstance().update(config);

        return ResponseEntity.ok().build();
    }

    @GetMapping("security-config")
    public ResponseEntity updateSecurityRuntimeConfig(HttpServletRequest request, HttpServletResponse response) {

        var config = new SecurityRuntimeConfigDto(SecurityRuntimeConfig.getInstance());

        if (config.getTipoAutenticacao() != TipoAutenticacao.CookieBase64) {
            var cookie = CookieHelper.getCookie(request, Constantes.AUTH_COOKIE_NAME);
            if (cookie != null)
                CookieHelper.clearCookie(response, Constantes.AUTH_COOKIE_NAME);
        }


        return ResponseEntity.ok(config);
    }

    @DeleteMapping("security-config")
    public ResponseEntity resetSecurityRuntimeConfig(HttpServletRequest request) {

        if (!isAdmin(request))
            return forbidden();

        SecurityRuntimeConfig.reset();

        var config = new SecurityRuntimeConfigDto(SecurityRuntimeConfig.getInstance());

        return ResponseEntity.ok(config);
    }
}