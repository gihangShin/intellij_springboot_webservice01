package com.hang.book.springboot.web;

import com.hang.book.springboot.service.posts.PostsService;
import com.hang.book.springboot.web.dto.PostsSaveRequestDto;
import com.hang.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor    // final 이 선언된 모든 필드를 인자값으로 하는 생성자를 대신 생성 (권장 방식 ★★★★★)
@RestController
public class PostApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")   // insert 는 Post 사용
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")   // update 는 PUT 사용
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto){
        return postsService.update(id,requestDto);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id){
        postsService.delete(id);
        return id;
    }

}
