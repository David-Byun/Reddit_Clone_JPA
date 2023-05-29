package jpa.jpatest.service;

import jpa.jpatest.dto.SubredditDto;
import jpa.jpatest.exception.SpringRedditException;
import jpa.jpatest.mapper.SubredditMapper;
import jpa.jpatest.model.SubReddit;
import jpa.jpatest.repository.SubredditRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubredditService {

    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
        SubReddit subReddit = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
        subredditDto.setId(subReddit.getId());
        return subredditDto;
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll().stream().map(subredditMapper::mapSubredditToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public SubredditDto getSubreddit(Long id) {
        SubReddit subReddit = subredditRepository.findById(id).orElseThrow(() -> new SpringRedditException("No subreddit found with"));
        return subredditMapper.mapSubredditToDto(subReddit);
    }
}
