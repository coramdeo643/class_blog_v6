package com.tenco.blog.reply;

import com.tenco.blog._core.errors.exception.Exception400;
import com.tenco.blog.board.Board;
import com.tenco.blog.user.User;
import lombok.Data;

public class ReplyRequest {

	@Data
	public static class SaveDTO {
		// id, comment
		private Long boardId; // 댓글 게시글 ID
		private String comment; // reply comment

		public void validate() {
			if(comment == null || comment.trim().isEmpty()) {
				throw new RuntimeException("Please insert comment");
			}
			if(comment.length() > 500) {
				throw new Exception400("Please insert comment within 500 letters");
			}if(boardId == null) {
				throw new Exception400("Please insert boardId");
			}
		}
		/**
		 * 보통 SAFE DTO에 toEntity 메서드를 만들게 된다
		 * 멤버 변수가 없는 사
		 */
		public Reply toEntity(User sessionUser, Board board) {
			return Reply.builder()
					.comment(comment.trim())
					.user(sessionUser)
					.board(board)
					.build();
		}

	}

}
