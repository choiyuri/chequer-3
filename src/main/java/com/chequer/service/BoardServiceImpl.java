package com.chequer.service;

import com.chequer.domain.board.Board;
import com.chequer.domain.board.BoardRepository;
import com.chequer.domain.board.BoardSaveRequestDto;
import com.chequer.domain.board.BoardUpdateRequestDto;
import com.chequer.exception.BaseException;
import com.chequer.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    @Transactional
    public Board save(BoardSaveRequestDto requestDto) {
        return boardRepository.save(requestDto.toEntity());
    }

    @Override
    @Transactional
    public Board update(Long id, BoardUpdateRequestDto requestDto, Long userId) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new BaseException(ErrorCode.E2001));

        if (!board.getAuthor().equals(userId)) {
            throw new BaseException(ErrorCode.E1003);
        }

        board.update(requestDto.getTitle(), requestDto.getContent());
        return board;
    }

    @Override
    @Transactional
    public void delete(Long id, Long userId) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new BaseException(ErrorCode.E2001));

        if (!board.getAuthor().equals(userId)) {
            throw new BaseException(ErrorCode.E1003);
        }

        board.delete(id);
    }

    @Override
    public Page<Board> list(Pageable pageable) {

        return boardRepository.findByDeleteYn(Boolean.FALSE, pageable);
    }

    @Override
    @Transactional
    public Board view(Long id, Long userId) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new BaseException(ErrorCode.E2001));

        // 본인 게시글이 아닐 경우에만
        if (!board.getAuthor().equals(userId)) {
            board.increaseHits();
        }

        return board;
    }
}
