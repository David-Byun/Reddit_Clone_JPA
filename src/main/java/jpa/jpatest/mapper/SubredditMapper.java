package jpa.jpatest.mapper;

import jpa.jpatest.dto.SubredditDto;
import jpa.jpatest.model.Post;
import jpa.jpatest.model.SubReddit;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubredditMapper {

    SubredditMapper INSTANCE = Mappers.getMapper(SubredditMapper.class);

    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subReddit.getPosts()))")
    SubredditDto mapSubredditToDto(SubReddit subReddit);

    default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }

    /*
        post 만들 경우 posts를 세팅해야 하기 때문에 ignore
     */
    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    SubReddit mapDtoToSubreddit(SubredditDto subredditDto);

}
