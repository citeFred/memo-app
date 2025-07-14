package com.metaverse.memo.service;

import com.metaverse.memo.domain.Memo;
import com.metaverse.memo.dto.MemoRequestDto;
import com.metaverse.memo.dto.MemoResponseDto;
import com.metaverse.memo.repository.MemoRepository;
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
        List<MemoResponseDto> responseList = memoRepository.findAll();
        return responseList;
    }

    public Long updateMemo(Long id, MemoRequestDto memoRequestDto) {
        Memo memo = memoRepository.findById(id);
        if(memo != null) {
            Long updatedId = memoRepository.update(id, memoRequestDto);
            return updatedId;
        } else {
            throw new IllegalArgumentException("해당 ID의 메모는 존재하지 않습니다.");
        }
    }

    public Long deleteMemo(Long id) {
        Memo memo = memoRepository.findById(id);

        if(memo != null) {
            Long deletedId = memoRepository.delete(id);
            return deletedId;
        } else {
            throw new IllegalArgumentException("해당 ID의 메모는 존재하지 않습니다.");
        }
    }
}
