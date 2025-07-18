package com.metaverse.memo.service;

import com.metaverse.memo.domain.Memo;
import com.metaverse.memo.dto.MemoRequestDto;
import com.metaverse.memo.dto.MemoResponseDto;
import com.metaverse.memo.repository.MemoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemoService {
    private final MemoRepository memoRepository;

    public MemoService(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }

    public MemoResponseDto createMemo(MemoRequestDto memoRequestDto) {
        Memo memo = new Memo(memoRequestDto);
        Memo savedMemo = memoRepository.save(memo);
        MemoResponseDto memoResponseDto = new MemoResponseDto(savedMemo);
        return memoResponseDto;
    }

    public List<MemoResponseDto> getMemos() {
        // 기본 조회 메서드 호출
//        List<MemoResponseDto> responseList = memoRepository.findAll().stream().map(MemoResponseDto::new).toList();
        // QueryMethod 사용 정렬 방식(OrderBy절) 추가
        List<MemoResponseDto> responseList = memoRepository.findAllByOrderByCreatedAtDesc().stream().map(MemoResponseDto::new).toList();
        return responseList;
    }

    public List<MemoResponseDto> searchMemosByKeyword(String keyword) {
        List<MemoResponseDto> responseList = memoRepository.findAllByContentsContainingIgnoreCase(keyword).stream().map(MemoResponseDto::new).toList();
        return responseList;
    }

    @Transactional
    public Long updateMemo(Long id, MemoRequestDto memoRequestDto) {
        Memo memo = findMemo(id);
        memo.update(memoRequestDto);
        return id;
    }

    public Long deleteMemo(Long id) {
        Memo memo = findMemo(id);
        memoRepository.delete(memo);
        return id;
    }

    private Memo findMemo(Long id) {
        return memoRepository.findById(id).orElseThrow(() ->
            new IllegalArgumentException("해당 메모는 존재하지 않습니다.")
        );
    }
}
