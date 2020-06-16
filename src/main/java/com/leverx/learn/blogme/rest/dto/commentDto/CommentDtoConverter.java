package com.leverx.learn.blogme.rest.dto.commentDto;

import com.leverx.learn.blogme.entity.Comment;
import com.leverx.learn.blogme.service.UserService;
import org.springframework.stereotype.Component;

/**
 * Allows to convert {@link Comment} into {@link CommentDto} and vice versa.
 *
 * @author Viktar on 12.06.2020
 */
@Component
public class CommentDtoConverter {

    private final UserService userService;

    public CommentDtoConverter(UserService userService) {
        this.userService = userService;
    }

    public Comment convertToEntity(CommentDto commentDto){
        Comment comment = new Comment();
        comment.setText(commentDto.getText());
        comment.setAuthor(userService.getUserById(commentDto.getAuthorId()));
        comment.setCreatedAt(commentDto.getCreatedAt());
        return comment;
    }

    public CommentDto convertToDto(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setText(comment.getText());
        commentDto.setAuthorId(comment.getAuthor().getId());
        commentDto.setCreatedAt(comment.getCreatedAt());

        return commentDto;
    }
}
