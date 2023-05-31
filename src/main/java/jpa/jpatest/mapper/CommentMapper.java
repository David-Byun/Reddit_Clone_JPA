package jpa.jpatest.mapper;

import jpa.jpatest.dto.CommentsDto;
import jpa.jpatest.model.Comment;
import jpa.jpatest.model.Member;
import jpa.jpatest.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    /*
        @Mapping(target="id", ignore = ture)는 Comment 객체의 id 속성을 무시하도록 설정
        commentsDto 객체의 'text' 속성을 Comment 객체의 text 속성에 매핑하고
        'id' 속성은 무시하도록 지정
        ignore=true 매핑시 특정속성을 무시하면 해당 속성은 매핑대상에서 제외
        auto-generated 이기 때문
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "text", source = "commentsDto.text")
    /*
        mapstruct import가 들어가지 않으므로 expression에 full name 작성해줘야함
     */
    @Mapping(target = "createdDate", expression = "java(LocalDateTime.now())")
    @Mapping(target = "post", source = "post")
    Comment map(CommentsDto commentsDto, Post post, Member member);

    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "username", expression = "java(comment.getMember().getUsername()")
    CommentsDto mapToDto(Comment comment);


}
