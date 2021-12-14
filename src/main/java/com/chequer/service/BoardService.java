package com.chequer.service;

import com.chequer.domain.board.Board;
import com.chequer.domain.board.BoardSaveRequestDto;
import com.chequer.domain.board.BoardUpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardService {

    Board save(BoardSaveRequestDto requestDto);

    Board update(Long id, BoardUpdateRequestDto requestDto, Long userId);

    void delete(Long id, Long userId);

    Page<Board> list(Pageable pageable);

    Board view(Long id, Long userId);
}
