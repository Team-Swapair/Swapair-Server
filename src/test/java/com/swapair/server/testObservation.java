package com.swapair.server;

import com.swapair.server.category.Category;
import com.swapair.server.category.CategoryRepository;
import com.swapair.server.goods.GoodsRepository;
import com.swapair.server.post.Filter;
import com.swapair.server.post.Post;
import com.swapair.server.post.PostRepository;
import com.swapair.server.post.PostService;
import com.swapair.server.post.have.HaveGoodsRepository;
import com.swapair.server.post.params.PostDetailParams;
import com.swapair.server.post.params.PostSearchParams;
import com.swapair.server.post.params.PostWriteParams;
import com.swapair.server.post.params.PostWriteParams2;
import com.swapair.server.user.User;
import com.swapair.server.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class testObservation {

    @Autowired
    PostService postService;

    @Autowired
    HaveGoodsRepository haveGoodsRepository;

    @Autowired
    GoodsRepository goodsRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void searchPost(){

        PostDetailParams p = postService.getPost(1L);
        System.out.println(p);

    }

    @Test
    void searchHave(){
        List<Long> goodsIds = new ArrayList<>();
        goodsIds.add(1L);
        goodsIds.add(2L);
        goodsIds.add(6L);
        List<Long> postIds = haveGoodsRepository.findByGoodsIds(goodsIds);

        System.out.println(postIds);
    }

    @Test
    void searchGoodsByName(){
        List<Long> goodsId = goodsRepository.findByGoodsNameContaining("LP");
        System.out.println(goodsId);
    }

    @Test
    void searchPostByCategoryAndId() {
        List<Long> postIds = new ArrayList<>();
        postIds.add(1L);
        postIds.add(2L);

        List<Long> categoryIds = new ArrayList<>();
        categoryIds.add(1L);

        List<Post> postList = postRepository.findByIdsAndPostCategory(postIds, categoryIds);

        for (Post p : postList) {
            System.out.println(p.getPostId());
        }
    }

    @Test
    void searchPostWithFilter(){
        List<PostSearchParams> params = postService.searchPosts(1L, "LP", Filter.BOTH);

        System.out.println(params.size());
        for (PostSearchParams p : params) {
            System.out.println(p.getPostId());
        }
    }

    @Test
    void savePost(){
        List<Long> goodsIds = new ArrayList<>();

        goodsIds.add(1L);
        goodsIds.add(2L);

        PostWriteParams2 params =
                new PostWriteParams2().builder()
                        .postTitle("제목입니다.").postContent("내용입니다")
                        .postCategory(1L)
                        .haveImage("https://cdn-icons-png.flaticon.com/512/5650/5650378.png")
                        .wantImage("https://cdn-icons-png.flaticon.com/512/5650/5650380.png")
                        .haveGoodsList(goodsIds)
                        .wantGoodsList(goodsIds)
                        .build();

        postService.createPost(params);

//        Assertions.assertThat(postService.getPost(1)).isNotNull();


    }

    @Test
    void TestCategory(){
        Category c = categoryRepository.getCategoryByCategoryId(1L);
        System.out.println(c.getCategoryName());

    }


}
