package com.chequer.service;

import com.chequer.domain.board.BoardResponseDto;
import com.chequer.domain.board.BoardSaveRequestDto;
import com.chequer.domain.board.BoardUpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardService {

    BoardResponseDto save(BoardSaveRequestDto requestDto);

    BoardResponseDto update(Long id, BoardUpdateRequestDto requestDto, Long userId);

    void delete(Long id, Long userId);

    Page<BoardResponseDto> list(Pageable pageable);

    BoardResponseDto view(Long id, Long userId);
}
