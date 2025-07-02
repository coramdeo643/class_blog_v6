package com.tenco.blog.reply;

import ch.qos.logback.core.model.Model;
import com.tenco.blog.user.User;
import com.tenco.blog.utils.Define;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ReplyController {
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
		return "redirect:/board/" + saveDTO.getBoardId();
		// 로그인 > 댓글작성 > 댓글등록 > controller.save > service.save > JpaRepository.save
	}

	// 댓글 전체 조회 기능요청
	@GetMapping("/board/detail")
	public String replyList(Model model) {
		List<Reply> replies = replyService.findAll();
//		model.addAttribute("replyList", replyService.findAll());
		return "board/detail";
	}
}