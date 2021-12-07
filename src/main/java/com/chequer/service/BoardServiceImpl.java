package com.chequer.service;

import com.chequer.domain.board.*;
import com.chequer.exception.BaseException;
import com.chequer.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

// TODO : 로그 연동 필요
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    @Transactional
    public Long save(BoardSaveRequestDto requestDto) {
        return boardRepository.save(requestDto.toEntity()).getId();
    }

    @Override
    @Transactional
    public Long update(Long id, BoardUpdateRequestDto requestDto) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new BaseException(ErrorCode.BOARD_NOT_FOUND));
        board.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    @Override
    @Transactional
    public Long delete(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new BaseException(ErrorCode.BOARD_NOT_FOUND));
        board.delete(id);
        return id;
    }

    @Override
    public Page<BoardResponseDto> list(Pageable pageable) {

        Page<Board> boardPage = boardRepository.findByDeleteYn(Boolean.FALSE, pageable);

        List<BoardResponseDto> responseDtoList = boardPage.stream()
                .map(BoardResponseDto::new)
                .collect(Collectors.toList());

        return new PageImpl<>(responseDtoList, pageable, boardPage.getTotalElements());
    }

    @Override
    public BoardResponseDto view(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new BaseException(ErrorCode.BOARD_NOT_FOUND));

        // TODO : 본인글이 아닌지 검증 후
        board.increaseHits();

        return new BoardResponseDto(board);
    }
}
