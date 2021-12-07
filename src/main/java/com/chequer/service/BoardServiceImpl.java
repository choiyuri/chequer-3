package com.chequer.service;

import com.chequer.domain.board.*;
import com.chequer.exception.BaseException;
import com.chequer.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    @Transactional
    public BoardResponseDto save(BoardSaveRequestDto requestDto) {
        Board board = boardRepository.save(requestDto.toEntity());
        return new BoardResponseDto(board);
    }

    @Override
    @Transactional
    public BoardResponseDto update(Long id, BoardUpdateRequestDto requestDto, String username) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new BaseException(ErrorCode.BOARD_NOT_FOUND));

        if (!board.getAuthor().equals(username)) {
            throw new BaseException(ErrorCode.USER_FORBIDDEN);
        }

        board.update(requestDto.getTitle(), requestDto.getContent());
        return new BoardResponseDto(board);
    }

    @Override
    @Transactional
    public Long delete(Long id, String username) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new BaseException(ErrorCode.BOARD_NOT_FOUND));

        if (!board.getAuthor().equals(username)) {
            throw new BaseException(ErrorCode.USER_FORBIDDEN);
        }

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
    @Transactional
    public BoardResponseDto view(Long id, String username) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new BaseException(ErrorCode.BOARD_NOT_FOUND));

        // 본인 게시글이 아닐 경우에만
        if (!board.getAuthor().equals(username)) {
            board.increaseHits();
        }

        return new BoardResponseDto(board);
    }
}
