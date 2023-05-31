package jpa.jpatest.service;

import jpa.jpatest.dto.PostRequest;
import jpa.jpatest.dto.PostResponse;
import jpa.jpatest.exception.PostNotFoundException;
import jpa.jpatest.exception.SubredditNotFoundException;
import jpa.jpatest.mapper.PostMapper;
import jpa.jpatest.model.Member;
import jpa.jpatest.model.Post;
import jpa.jpatest.model.SubReddit;
import jpa.jpatest.repository.PostRepository;
import jpa.jpatest.repository.SubredditRepository;
import jpa.jpatest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    private final SubredditRepository subredditRepository;
    private final AuthService authService;
    private final PostMapper postMapper;


    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostBySubreddit(Long subredditId) {
        SubReddit subReddit = subredditRepository.findById(subredditId)
                .orElseThrow(() -> new SubredditNotFoundException(subredditId.toString()));
        List<Post> posts = postRepository.findAllBySubReddit(subReddit);
        return posts.stream().map(postMapper::mapToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username) {
        Member member = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return postRepository.findByMember(member)
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

}
