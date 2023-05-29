package jpa.jpatest.mapper;

import jpa.jpatest.dto.PostRequest;
import jpa.jpatest.model.Post;
import jpa.jpatest.model.SubReddit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "createdDate", expression = "java(LocalDateTime.now())")
    @Mapping(target = "description", source = "postRequest.description")
    Post map(PostRequest postRequest, SubReddit subReddit, User user);

}
