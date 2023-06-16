package br.com.dvsn.controllers;

import br.com.dvsn.dtos.HashDto;
import br.com.dvsn.dtos.SecurityRuntimeConfigDto;
import br.com.dvsn.entities.RainbowTableHash;
import br.com.dvsn.repository.RainbowTableRepository;
import br.com.dvsn.security.SecurityRuntimeConfig;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController extends BaseController{

    @Autowired
    private RainbowTableRepository rainbowTableRepository;

    @PostMapping("rainbowtable")
    public ResponseEntity rainbowtable(HttpServletRequest request, @RequestBody HashDto hashDto) {

        if(!isAdmin(request))
            return forbidden();

        RainbowTableHash rainbowTableHash = null;

        var tipo = hashDto.getTipo();

        if(tipo == 1)
            rainbowTableHash = rainbowTableRepository.findByMd5(hashDto.getHash());
        else if(tipo == 2)
            rainbowTableHash = rainbowTableRepository.findBySha1(hashDto.getHash());

        return ResponseEntity.ok(rainbowTableHash);
    }

    @PutMapping("security-config")
    public ResponseEntity updateSecurityRuntimeConfig(HttpServletRequest request, @RequestBody SecurityRuntimeConfigDto config) {

        if(!isAdmin(request))
            return forbidden();

        SecurityRuntimeConfig.getInstance().update(config);

        return ResponseEntity.ok().build();
    }

    @GetMapping("security-config")
    public ResponseEntity updateSecurityRuntimeConfig(HttpServletRequest request) {

        var config = new SecurityRuntimeConfigDto(SecurityRuntimeConfig.getInstance());

        return ResponseEntity.ok(config);
    }

    @DeleteMapping("security-config")
    public ResponseEntity resetSecurityRuntimeConfig(HttpServletRequest request) {

        if(!isAdmin(request))
            return forbidden();

        SecurityRuntimeConfig.reset();

        var config = new SecurityRuntimeConfigDto(SecurityRuntimeConfig.getInstance());

        return ResponseEntity.ok(config);
    }
}