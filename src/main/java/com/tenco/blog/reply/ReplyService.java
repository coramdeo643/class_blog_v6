package com.tenco.blog.reply;

import com.tenco.blog._core.errors.exception.Exception404;
import com.tenco.blog.board.Board;
import com.tenco.blog.board.BoardJpaRepository;
import com.tenco.blog.user.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor // final keyword member init
@Service // IoC 대상
public class ReplyService {

	private static final Logger log = LoggerFactory.getLogger(ReplyService.class);
	// DI 처리
	private final ReplyJPARepository replyJPARepository;
	private final BoardJpaRepository boardJpaRepository;

	// 댓글저장기능
	// 서비스계층, Repository 계층에 메서드 이름(같이, 다르게 정의)
	@Transactional
	public void save(ReplyRequest.SaveDTO saveDTO, User sessionUser) {
		log.info("댓글 저장 서비스 처리 시작 - 게시글 id {}, 작성자 {}, ",
				saveDTO.getBoardId(), sessionUser.getUsername());
		// 2. 댓글이 달릴 게시글 존재여부 확인
		Board board = boardJpaRepository.findById(saveDTO.getBoardId())
				.orElseThrow(() -> new Exception404("Not found board"));
		// 3. 준영속 상태이다
		Reply reply = saveDTO.toEntity(sessionUser, board);
		// 4. 저장 : 정방향 insert 처리
		replyJPARepository.save(reply);
	}
	// 댓글삭제기능
	public void deleteById(Long id) {
		replyJPARepository.deleteById(id);
	}
	// 댓글 목록 조회
	public List<Reply> findAll() {
		List<Reply> replies = replyJPARepository.findAll();
		log.info("{}", replies.size());
		return replies;
	}
}
