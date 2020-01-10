package de.telran.blog.service;

import de.telran.blog.dto.PostDto;
import de.telran.blog.entity.PostEntity;
import de.telran.blog.repository.AuthorRepository;
import de.telran.blog.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final AuthorRepository authorRepository;

    public PostService(PostRepository postRepository, AuthorRepository authorRepository) {
        this.postRepository = postRepository;
        this.authorRepository = authorRepository;
    }

    public Long createPost(PostDto postDto) {
        PostEntity postEntity = new PostEntity();
        postEntity.setTitle(postDto.getTitle());
        postEntity.setBody(postDto.getBody());
        postEntity.setDate(postDto.getDate());
        postEntity.setAuthorEntity(authorRepository.getOne(1L)); // TODO just for cresting first post
        return postRepository.save(postEntity).getId();
    }

    public List<PostDto> getAllPosts() {
        List<PostEntity> result = new ArrayList<>();
        postRepository.findAll().forEach(result::add);
        return result
                .stream()
                .map(PostDto::new)
                .collect(Collectors.toList());
    }

    public PostDto getPost(Long id) {
        return new PostDto(postRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException("Post with id=" + id + "not found")
                ));
    }
}
