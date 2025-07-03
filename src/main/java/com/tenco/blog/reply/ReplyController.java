package com.tenco.blog.reply;

import ch.qos.logback.core.model.Model;
import com.tenco.blog.user.User;
import com.tenco.blog.utils.Define;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ReplyController {
	private static final Logger log = LoggerFactory.getLogger(ReplyController.class);
	private final ReplyService replyService;

	// 댓글 저장 기능 요청
	@PostMapping("/reply/save")
	public String save(ReplyRequest.SaveDTO saveDTO, HttpSession session) {
		// 1. 인증검사(Interceptor 처리, config-WebMvcConfig)
		// 2. 유효성검사
		saveDTO.validate();
		User sessionUser = (User) session.getAttribute(Define.SESSION_USER);
		// 댓글 저장
		replyService.save(saveDTO, sessionUser);
		log.info("saved");
		return "redirect:/board/" + saveDTO.getBoardId();
		// 로그인 > 댓글작성 > 댓글등록 > controller.save > service.save > JpaRepository.save
	}

	@PostMapping("/reply/{id}/delete")
	public String delete(@PathVariable(name = "id") Long replyId,
						 @RequestParam(name = "boardId") Long boardId,
						 HttpSession session) {
		User sessionUser = (User) session.getAttribute(Define.SESSION_USER);
		replyService.deleteById(replyId, sessionUser);
		return "redirect:/board/" + boardId;
	}
}
// 댓글 작성 조건
/*
1. 로그인이 되어 있어야
2. 로그인이 안되면 다른 디자인
3. 로그인한 유저가 작성한 댓글은 본인이 삭제가능해야한다
 */