package com.hang.book.springboot.service.posts;

import com.hang.book.springboot.domain.posts.Posts;
import com.hang.book.springboot.domain.posts.PostsRepository;
import com.hang.book.springboot.web.dto.PostsListResponseDto;
import com.hang.book.springboot.web.dto.PostsResponseDto;
import com.hang.book.springboot.web.dto.PostsSaveRequestDto;
import com.hang.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor // final 이 선언된 모든 필드를 인자값으로 하는 생성자를 대신 생성
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id : " + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id : " + id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc(){
        // postsRepository 결과로 넘어온 Posts 의 Stream 을 map을 통해
        // PostsListResponseDto 변환 -> List 로 반환하는 메소드
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new) // == .map(posts -> new PostsListResponseDto(posts))
                .collect(Collectors.toList());
    }

    public void delete(Long id){
        Posts posts = postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id : "+id));
        postsRepository.delete(posts);  // JpaRepository 에서 이미 delete메소드 지원.
    }

}
