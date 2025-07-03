package com.tenco.blog.board;


import com.tenco.blog.reply.Reply;
import com.tenco.blog.user.User;
import com.tenco.blog.utils.MyDateUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
// 기본 생성자 - JPA에서 엔티티는 기본 생성자가 필요
@Data
@Table(name = "board_tb")
@Entity
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;
	private String content;
	// V2에서 사용했던 방식
	// private String username;
	// V3 에서 Board 엔티티는 User 엔티티와 연관관계가 성립이 된다

	// 다대일
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id") // 외래키 컬러명 명시
	private User user;

	@CreationTimestamp
	private Timestamp createdAt;

	// 테이블에 필드 만들지마~
	// (현재 로그인한 유저와 게시글 작성자 여부를 판단한다)
	@Transient
	private boolean isBoardOwner;

	// 게시글에 소유자를 직접 확인하는 기능을 만들자
	public boolean isOwner(Long checkUserId) {
		return this.user.getId().equals(checkUserId);
	}

	public String getTime() {
		return MyDateUtil.timestampFormat(createdAt);
	}

	/*
	  Board와 Reply를 양방향 매핑 설계해보자
	  하나의 게시글(one)에는 여러개의 댓글(many)을 가질수있다

	  테이블 기준으로 고민을 해본다면 게시글 테이블과 댓글 테이블 관계를 형성할때,
	  FK는 누가 들고있어야하나? Reply,
	  연관관계의 주인은 Reply(FK)
	  mappedBy = FK 주인이 아닌 Entity에 설정해야한다
	  cascade = CascadeType.REMOVE
	   = 영속성 전이 : 게시글 삭제 시, 관련된 모든 댓글도 자동 삭제 처리한다
		= 데이터 무결성 보장하기 위해서

	 */
	@OrderBy("id desc") // 정렬 옵션 설정 (내림차순)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "board", cascade = CascadeType.REMOVE)
	List<Reply> replies = new ArrayList<>(); // List 선언과 동시에 초기화

}
