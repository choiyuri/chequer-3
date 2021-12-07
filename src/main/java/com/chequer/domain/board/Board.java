package com.chequer.domain.board;

import com.chequer.domain.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 게시글 엔티티
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Board extends BaseTimeEntity {

    @Builder
    public Board(String title, String content, String author, int hits, Boolean deleteYn) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.hits = hits;
        this.deleteYn = deleteYn;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 제목
     */
    @Column(nullable = false)
    private String title;

    /**
     * 내용
     */
    @Column(columnDefinition = "TEXT")
    private String content;

    /**
     * 작성자
     */
    @Column(nullable = false)
    private String author;

    /**
     * 조회수
     */
    private Integer hits;

    /**
     * 삭제 여부
     */
    private Boolean deleteYn = Boolean.FALSE;

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void delete(Long id) {
        this.deleteYn = Boolean.TRUE;
    }

    public void increaseHits() {
        this.hits = getHits() + 1;
    }
}


