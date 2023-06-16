package br.com.dvsn.controllers;

import br.com.dvsn.helpers.StringHelper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
public class DownloadController extends BaseController {

    @GetMapping("/image/{fileName:.+}")
    public ResponseEntity downloadFileFromLocal(@PathVariable String fileName) {
        try {
            var path = StringHelper.createUploadFilePath(fileName);

            final byte[] pdfBytes = Files.readAllBytes(Paths.get(path));

            var contentType = URLConnection.guessContentTypeFromName(path);

            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(contentType));
            headers.setCacheControl("no-cache");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception ex) {
            return internalServerError(ex);
        }
    }
}
