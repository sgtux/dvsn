package br.com.dvsn.controllers;

import br.com.dvsn.dtos.UsuarioDto;
import br.com.dvsn.dtos.UsuarioExibicaoDto;
import br.com.dvsn.helpers.StringHelper;
import br.com.dvsn.repository.PostRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class UsuarioController extends BaseController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("usuario")
    public ResponseEntity obterDadosUsuarioLogado(HttpServletRequest request) {
        try {
            var usuario = obterUsuarioLogado(request);

            if (usuario == null)
                return badRequest("Usuário não encontrado.");

            return ResponseEntity.ok(usuario);
        }catch(Exception ex) {
            return internalServerError(ex);
        }
    }

    @GetMapping("usuario/{id}")
    public ResponseEntity obterDadosUsuario(@PathVariable long id) {
        try {
            var usuario = usuarioRepository.buscarPorId(id);

            if (usuario == null)
                return badRequest("Usuário não encontrado.");

            return ResponseEntity.ok(new UsuarioDto(usuario));
        }catch(Exception ex) {
            return internalServerError(ex);
        }
    }

    @PutMapping("usuario/{id}")
    public ResponseEntity update(@PathVariable long id, @RequestPart(required = false) String nome, @RequestPart(required = false) String sobrenome, @RequestPart(required = false) MultipartFile imagem) {
        try {
            if (StringHelper.isNullOrEmpty(nome)) {
                return badRequest("Nome inválido.");
            }

            if (StringHelper.isNullOrEmpty(sobrenome)) {
                return badRequest("Sobrenome inválido.");
            }

            var usuario = new UsuarioDto(id, nome, sobrenome);
            usuario.setAtualizadoEm(new Date());

            if (imagem != null) {
                var filename = StringHelper.createFilenameFromMultipartFile(imagem);
                var path = StringHelper.createUploadFilePath(filename);
                try (OutputStream os = Files.newOutputStream(Paths.get(path))) {
                    os.write(imagem.getBytes());
                }
                usuario.setFoto(filename);
            }
            usuarioRepository.atualizar(usuario);
            return ResponseEntity.ok().build();
        }catch(Exception ex) {
            return internalServerError(ex);
        }
    }

    @GetMapping("usuarios")
    public ResponseEntity obterUsuarios(@RequestParam String filtro) {

        try {

            List<UsuarioExibicaoDto> list = new ArrayList<>();

            for (var item : usuarioRepository.buscar(filtro)) {
                var dto = new UsuarioExibicaoDto(item);
                var quantidatePostsUsuario = postRepository.quantidadePostPorUsuario(item.getId());
                dto.setQuantidadePosts(quantidatePostsUsuario);
                list.add(dto);
            }

            return new ResponseEntity(list, HttpStatus.OK);
        } catch(Exception ex) {
            return internalServerError(ex);
        }
    }
}