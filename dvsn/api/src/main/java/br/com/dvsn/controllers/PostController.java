package br.com.dvsn.controllers;

import br.com.dvsn.dtos.PostDto;
import br.com.dvsn.entities.Post;
import br.com.dvsn.entities.Usuario;
import br.com.dvsn.helpers.StringHelper;
import br.com.dvsn.repository.ComentarioRepository;
import br.com.dvsn.repository.PostRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;

@RestController
public class PostController extends BaseController {

    @Autowired
    public PostRepository repository;

    @Autowired
    public ComentarioRepository comentarioRepository;

    @GetMapping("/post")
    public ResponseEntity buscarPosts(@RequestParam(required = false) String filtro, HttpServletRequest request) {

        try {
            var list = new ArrayList<PostDto>();

            var idUsuarioLogado = obterUsuarioLogado(request).getId();

            for (var post : repository.buscar(filtro)) {
                var postDto = new PostDto(post);
                postDto.setOwner(postDto.getUsuario().getId() == idUsuarioLogado);

                for (var comentario : postDto.getComentarios())
                    comentario.setOwner(comentario.getUsuarioId() == idUsuarioLogado);

                list.add(postDto);
            }

            return ResponseEntity.ok(list);
        } catch(Exception ex) {
            return internalServerError(ex);
        }
    }

    @PostMapping("/post")
    public ResponseEntity createPost(HttpServletRequest request, @RequestPart String text, @RequestPart(required = false) MultipartFile image) {

        try {

            var post = new Post();
            post.setTexto(text);

            post.setVisibilidade("P");
            post.setCriadoEm(new Date());
            var usuario = new Usuario();
            usuario.setId(obterUsuarioLogado(request).getId());
            post.setUsuario(usuario);

            if (image != null) {
                var filename = StringHelper.createFilenameFromMultipartFile(image);
                var path = StringHelper.createUploadFilePath(filename);

                try {
                    try (OutputStream os = Files.newOutputStream(Paths.get(path))) {
                        os.write(image.getBytes());
                    }
                    post.setFoto(filename);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

            repository.add(post);

            return ResponseEntity.ok().build();
        } catch(Exception ex) {
            return internalServerError(ex);
        }
    }

    @DeleteMapping("post/{id}")
    public ResponseEntity removerPost(@PathVariable long id) {

        try {
            comentarioRepository.deleteByPostId(id);
            repository.deletarPorId(id);
            return ResponseEntity.ok().build();
        }catch(Exception ex) {
            return internalServerError(ex);
        }
    }
}