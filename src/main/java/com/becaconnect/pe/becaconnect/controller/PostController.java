package com.becaconnect.pe.becaconnect.controller;

import com.becaconnect.pe.becaconnect.model.Comment;
import com.becaconnect.pe.becaconnect.model.Post;
import com.becaconnect.pe.becaconnect.model.User;
import com.becaconnect.pe.becaconnect.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    // 1. Ver todos los posts (público)
    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    // 2. Ver un post por ID (público)
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Optional<Post> postOptional = postService.getPostById(id);

        if (postOptional.isPresent()) {
            Post post = postOptional.get();

            // Incrementar las vistas
            post.setViewCount(post.getViewCount() + 1);

            // Guardar el cambio en la base de datos
            postService.updatePost(post);

            return ResponseEntity.ok(post);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    // 3. Crear un post (solo admin)
    @PostMapping("/create")
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            Post createdPost = postService.createPost(post);
            return ResponseEntity.ok(createdPost);
        } else {
            return ResponseEntity.status(403).body(null);  // Solo admins pueden crear posts
        }
    }

    // 4. Actualizar un post (solo admin)
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post postDetails) {
        Optional<Post> postOptional = postService.getPostById(id);

        if (postOptional.isPresent()) {
            Post postToUpdate = postOptional.get();
            postToUpdate.setTitle(postDetails.getTitle());
            postToUpdate.setContent(postDetails.getContent());
            postToUpdate.setTags(postDetails.getTags());
            postToUpdate.setImg(postDetails.getImg());
            postToUpdate.setViewCount(postDetails.getViewCount());

            Post updatedPost = postService.updatePost(postToUpdate);
            return ResponseEntity.ok(updatedPost);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. Eliminar un post (solo admin)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        Optional<Post> postOptional = postService.getPostById(id);

        if (postOptional.isPresent()) {
            postService.deletePost(id);
            return ResponseEntity.noContent().build();  // Devolver 204 No Content
        } else {
            return ResponseEntity.notFound().build();  // El post no existe
        }
    }


    // Agregar un comentario a un post
    @PostMapping("/{id}/comments")
    public ResponseEntity<Comment> addComment(@PathVariable Long id, @RequestBody Comment comment) {
        try {
            // Delegar la creación del comentario al servicio
            Comment savedComment = postService.addComment(id, comment);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    // Obtener los comentarios de un post
    @GetMapping("/{id}/comments")
    public ResponseEntity<List<Comment>> getComments(@PathVariable Long id) {
        List<Comment> comments = postService.getCommentsByPostId(id);
        comments.forEach(Comment::getUsername); // Asegurarse de obtener username en la respuesta
        return ResponseEntity.ok(comments);
    }


    // 6. Incrementar los likes de un post
    @PutMapping("/{id}/like")
    public ResponseEntity<Post> likePost(@PathVariable Long id) {
        Optional<Post> postOptional = postService.getPostById(id);

        if (postOptional.isPresent()) {
            Post postToLike = postOptional.get();
            postToLike.setLikeCount(postToLike.getLikeCount() + 1);  // Incrementa el contador de likes

            Post updatedPost = postService.updatePost(postToLike);
            return ResponseEntity.ok(updatedPost);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}