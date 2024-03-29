package br.com.dvsn.controllers;

import br.com.dvsn.helpers.StringHelper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController extends BaseController {

    @GetMapping(value = "/hello", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity hello(@RequestParam(required = false) String nome) {

        try {

            StringBuilder sb = new StringBuilder();
            sb.append("<html><body style='font-size:30px'><div id='div'>");

            if (StringHelper.isNullOrEmpty(nome))
                sb.append("Informe o parâmetro 'nome' na url.");
            else
                sb.append("Olá ").append(nome);

            sb.append("</div></body></html>");

            return ResponseEntity.ok(sb.toString());

        } catch(Exception ex) {
            return internalServerError(ex);
        }
    }
}
