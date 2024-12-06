package com.becaconnect.pe.becaconnect.service;

import com.becaconnect.pe.becaconnect.model.Comment;
import com.becaconnect.pe.becaconnect.model.Post;
import com.becaconnect.pe.becaconnect.model.User;
import com.becaconnect.pe.becaconnect.repository.CommentRepository;
import com.becaconnect.pe.becaconnect.repository.PostRepository;
import com.becaconnect.pe.becaconnect.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @PostConstruct
    public void initDefaultPost() {
        if (postRepository.count() == 0) {
            Post defaultPost = new Post();
            defaultPost.setTitle("Primer Post de Prueba");
            defaultPost.setContent("Este es el contenido del primer post creado automáticamente.");
            defaultPost.setPostedBy("admin");
            defaultPost.setImg("https://via.placeholder.com/150");
            defaultPost.setTags(List.of("prueba", "inicial"));
            defaultPost.setLikeCount(0);
            defaultPost.setViewCount(0);
            defaultPost.setCommentCount(0);
            defaultPost.setDate(new Date());

            postRepository.save(defaultPost);
            System.out.println("Post inicial creado.");
        } else {
            System.out.println("Ya existen posts en la base de datos.");
        }
    }

    public Post createPost(Post post) {

        post.setLikeCount(0);
        post.setViewCount(0);
        post.setCommentCount(0);
        post.setDate(new Date());

        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    public Post updatePost(Post post) {
        return postRepository.save(post);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public Comment addComment(Long postId, Comment comment) {
        // Buscar el post al que se agregará el comentario
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("Post not found"));

        // Obtener el usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (authentication != null && authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser"))
                ? authentication.getName()
                : null;

        if (username != null) {
            // Si el usuario está autenticado, buscarlo en la base de datos
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            comment.setUser(user); // Asociar el usuario autenticado al comentario
        }

        // Asociar comentario al post
        comment.setPost(post);
        comment.setCreatedAt(LocalDateTime.now());

        // Incrementar el contador de comentarios en el post
        post.setCommentCount(post.getCommentCount() + 1);

        // Guardar el comentario
        Comment savedComment = commentRepository.save(comment);

        // Actualizar el post
        postRepository.save(post);

        return savedComment;
    }

    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

}
