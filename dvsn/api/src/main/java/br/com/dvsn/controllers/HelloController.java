package br.com.dvsn.controllers;

import br.com.dvsn.helpers.StringHelper;
import br.com.dvsn.security.SecurityRuntimeConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class HelloController extends BaseController {

    @GetMapping(value = "/hello", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity hello(HttpServletRequest request, @RequestParam(required = false) String nome) {

        if (StringHelper.isNullOrEmpty(nome)){
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/hello?nome=" + obterUsuarioLogado(request).getNome());
            return new ResponseEntity<String>(headers, HttpStatus.FOUND);
        }

        try {

            StringBuilder sb = new StringBuilder();
            sb.append("<html><body style='font-size:30px'><div id='div'>");

            if(SecurityRuntimeConfig.getInstance().isXssPreventionEnabled())
                sb.append("Olá ").append(StringHelper.sanitizarHtml(nome));
            else
                sb.append("Olá ").append(nome);

            var cssInjection = "<script>\n" +
                    "if (location.hash.slice(1)) {\n" +
                    "document.getElementById('div').style.cssText = 'color: ' + location.hash.slice(1);\n" +
                    "}\n" +
                    "</script>";

            sb.append(cssInjection);

            sb.append("</div></body></html>");

            return ResponseEntity.ok(sb.toString());

        } catch(Exception ex) {
            return internalServerError(ex);
        }
    }
}
