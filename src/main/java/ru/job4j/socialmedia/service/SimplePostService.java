package ru.job4j.socialmedia.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.job4j.socialmedia.dto.PostDto;
import ru.job4j.socialmedia.mapper.FileMapper;
import ru.job4j.socialmedia.mapper.PostMapper;
import ru.job4j.socialmedia.model.Post;
import ru.job4j.socialmedia.model.User;
import ru.job4j.socialmedia.repository.FileRepository;
import ru.job4j.socialmedia.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Validated
@Service
@AllArgsConstructor
public class SimplePostService implements PostService {

    private final PostRepository repository;
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;
    private final PostMapper postMapper;

    @Override
    public Post save(Post post) {
        return repository.save(post);
    }

    @Override
    public Optional<Post> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        return repository.deletePostById(id) > 0L;
    }

    @Override
    public List<Post> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Post> findByUserId(Long id) {
        return repository.findByUserId(id);
    }

    @Override
    public List<Post> findByToday(LocalDateTime today) {
        return repository.findByCreatedGreaterThanEqual(today);
    }

    @Override
    public Page<Post> findByOrderByCreated(Pageable pageable) {
        return repository.findByOrderByCreated(pageable);
    }

    @Override
    public boolean updateTitleAndText(String title, String text, Long id) {
        return repository.updateTitleAndText(title, text, id) > 0;
    }

    @Override
    public boolean deleteFileByPostId(Long id) {
        return repository.deleteFileByPostId(id) > 0;
    }

    @Override
    public Page<Post> findAllSubscriberPostsByOrderDescWithPagination(Long id, Pageable pageable) {
        return repository.findAllSubscriberPostsByOrderDescWithPagination(id, pageable);
    }

    @Transactional
    @Override
    public Post create(@Valid PostDto post) {
        var newPost = postMapper.getPostFromPostDto(post);
        repository.save(newPost);
        var fileList = fileMapper.getFileListFromFileListDto(post);
        fileList.forEach(file -> file.setPost(newPost));
        fileList.forEach(fileRepository::save);
        return newPost;
    }

    @Transactional
    @Override
    public boolean deletePostByUser(User user, Post post) {
        boolean rsl = false;
        if (repository.findByUserId(user.getId()).contains(post)) {
            var id = post.getId();
            repository.deleteFileByPostId(id);
            repository.deletePostById(id);
            rsl = true;
        }
        return rsl;
    }

    @Override
    public boolean update(Post post) {
        return repository.updateTitleAndText(post.getTitle(), post.getText(), post.getId()) > 0L;
    }

    @Override
    public boolean updateFromDto(@Valid PostDto post) {
        var newPost = postMapper.getPostFromPostDto(post);
        return repository.updateTitleAndText(newPost.getTitle(), newPost.getText(), newPost.getId()) > 0L;
    }

    @Transactional
    @Override
    public boolean updatePostByUser(User user, Post post) {
        boolean rsl = false;
        if (repository.findByUserId(user.getId()).contains(post)) {
            repository.updateTitleAndText(post.getTitle(), post.getText(), post.getId());
            rsl = true;
        }
        return rsl;
    }
}
