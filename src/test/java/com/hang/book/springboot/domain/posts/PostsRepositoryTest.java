package com.hang.book.springboot.domain.posts;


import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    // Junit 에서 단위 테스트가 끝날 때마다 수행되는 메소드를 지정
    // 보통 배포전 전체테스트를 수행할때 데이터 침범을 막기 위해 사용(중요 ★★★)
    @After
    public void cleanUp() {
        postsRepository.deleteAll();
    }

    @Test
    public void getAndSetPosts() {

        String title = "테스트 제목";
        String content = "테스트 본문";

        //save : posts 테이블에 insert(id값이 없다면)/update(id값이 있다면) 쿼리 실행
        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("abc1775@naver.com")
                .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void resistBaseTimeEntity() {
        //given

        LocalDateTime now = LocalDateTime.of(2021, 8, 4, 0, 0, 0);

        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
//        System.out.println("posts : " + posts.toString());
        System.out.println(">>>>>>>>> createDate : " + posts.getCreatedDate() + ", modifiedDate : " + posts.getModifiedDate());

        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);

    }

}
