package jpa.jpatest.mapper;

import jpa.jpatest.dto.PostRequest;
import jpa.jpatest.dto.PostResponse;
import jpa.jpatest.model.Member;
import jpa.jpatest.model.Post;
import jpa.jpatest.model.SubReddit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.userdetails.User;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "id", source = "postId")
    //target이 같으면 삭제가능
//    @Mapping(target = "postName", source = "postName")
//    @Mapping(target = "description", source = "description")
//    @Mapping(target = "url", source = "url")
    @Mapping(target = "subredditName", source = "subReddit.name")
    @Mapping(target = "userName", source = "member.username")
    PostResponse mapToDto(Post post);

}
