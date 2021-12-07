package com.chequer.service;

import com.chequer.domain.board.BoardResponseDto;
import com.chequer.domain.board.BoardSaveRequestDto;
import com.chequer.domain.board.BoardUpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardService {

    Long save(BoardSaveRequestDto requestDto);

    Long update(Long id, BoardUpdateRequestDto requestDto);

    Long delete(Long id);

    Page<BoardResponseDto> list(Pageable pageable);

    BoardResponseDto view(Long id);
}
