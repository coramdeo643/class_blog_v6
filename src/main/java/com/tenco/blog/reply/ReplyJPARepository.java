package com.tenco.blog.reply;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyJPARepository extends JpaRepository<Reply, Long> {
	//CRUD 기능 추가
	//Save(Reply) 댓글 저장
	//findById(Long id) : id 로 댓글 조회
	//deleteById(Long id) : id로 댓글 삭제
	//findAll() : 모든 댓글 조회
	//update? : dirty checking 사용(reply 객체 조회 <- 상태값 변경시, 트랜잭션 종료 및 커밋)

	// 추후 필요기능은 JPQL 등 추가하자
}
