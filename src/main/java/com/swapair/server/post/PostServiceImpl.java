package com.swapair.server.post;

import com.swapair.server.category.CategoryRepository;
import com.swapair.server.goods.GoodsParams;
import com.swapair.server.goods.GoodsRepository;
import com.swapair.server.post.have.HaveGoods;
import com.swapair.server.post.have.HaveGoodsRepository;
import com.swapair.server.post.params.PostDetailParams;
import com.swapair.server.post.params.PostSearchParams;
import com.swapair.server.post.params.PostWriteParams2;
import com.swapair.server.post.want.WantGoods;
import com.swapair.server.post.want.WantGoodsRepository;
import com.swapair.server.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{
    private final PostRepository postRepository;
    private final GoodsRepository goodsRepository;
    private final HaveGoodsRepository haveGoodsRepository;
    private final WantGoodsRepository wantGoodsRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Override
    public void createExcelPost(Post post) {

        postRepository.save(post);

    }
    @Override
    public Long createPost(PostWriteParams2 post) {

        Post post1 = Post.builder()
                        .postTitle(post.getPostTitle())
                        .postContent(post.getPostContent())
                        .postCategory(categoryRepository.findById(post.getPostCategory()).orElseThrow(IllegalAccessError::new))
                        .wantImage(post.getWantImage())
                        .haveImage(post.getHaveImage())
                        .user(userRepository.findByUserId(post.getUserId()))
                        .createdAt(LocalDateTime.now())
                        .isChecked(true)
                        .isClosed(false)
                .isDoubted(false)
                        .build();
        Long id = postRepository.save(post1).getPostId();
        List<Long> havaGoodsList = post.getHaveGoodsList();
        for (Long l : havaGoodsList) {
            HaveGoods hg = HaveGoods.builder().goods(goodsRepository.findById(l).orElseThrow(IllegalAccessError::new))
                    .post(post1).build();
            haveGoodsRepository.save(hg);
        }
        List<Long> wantGoodsList = post.getWantGoodsList();
        for (Long l : wantGoodsList) {
            WantGoods wg = WantGoods.builder().goods(goodsRepository.findById(l).orElseThrow(IllegalAccessError::new))
                    .post(post1).build();
            wantGoodsRepository.save(wg);
        }
        return id;

    }

    @Override
    public List<PostSearchParams> getAllPosts() {
        List<Post> postList= postRepository.findAll();
        List<PostSearchParams> postSearchParamsList = new ArrayList<>();



        for(Post p : postList){
            PostSearchParams params = PostSearchParams.builder()
                    .postId(p.getPostId())
                    .postTitle(p.getPostTitle())
                    .wantImage(p.getWantImage())
                    .haveImage(p.getHaveImage())
                    .build();

            List<GoodsParams> haveParamList = new ArrayList<>();
            List<GoodsParams> wantGoodsList = new ArrayList<>();
            for(HaveGoods hg : p.getHaveGoodsList()){
                GoodsParams goodsParams = new GoodsParams();
                goodsParams.setGoodsId(hg.getGoods().getGoodsId());
                goodsParams.setGoodsName(hg.getGoods().getGoodsName());
                haveParamList.add(goodsParams);
            }
            for(WantGoods wg : p.getWantGoodsList()){
                GoodsParams goodsParams = new GoodsParams();
                goodsParams.setGoodsId(wg.getGoods().getGoodsId());
                goodsParams.setGoodsName(wg.getGoods().getGoodsName());
                wantGoodsList.add(goodsParams);
            }
            params.setHaveGoodsList(haveParamList);
            params.setWantGoodsList(wantGoodsList);

            postSearchParamsList.add(params);
        }
        return postSearchParamsList;
    }

    @Override
    public PostDetailParams getPost(Long postId) {
        Post p = postRepository.findById(postId).orElseThrow(IllegalAccessError::new);
        PostDetailParams params = PostDetailParams.builder()
                .postId(p.getPostId())
                .user(p.getUser())
                .postTitle(p.getPostTitle())
                .postContent(p.getPostContent())
                .wantImage(p.getWantImage())
                .haveImage(p.getHaveImage())
                .isClosed(p.getIsClosed())
                .isChecked(p.getIsChecked())
                .createdAt(p.getCreatedAt())
                .updateAt(p.getUpdateAt())
                .isDoubted(p.getIsDoubted())
                .build();

        List<GoodsParams> haveParamList = new ArrayList<>();
        List<GoodsParams> wantGoodsList = new ArrayList<>();
        for(HaveGoods hg : p.getHaveGoodsList()){
            GoodsParams goodsParams = new GoodsParams();
            goodsParams.setGoodsId(hg.getGoods().getGoodsId());
            goodsParams.setGoodsName(hg.getGoods().getGoodsName());
            goodsParams.setPrice(hg.getGoods().getGoodsPrice3());
            haveParamList.add(goodsParams);
        }
        for(WantGoods wg : p.getWantGoodsList()){
            GoodsParams goodsParams = new GoodsParams();
            goodsParams.setGoodsId(wg.getGoods().getGoodsId());
            goodsParams.setGoodsName(wg.getGoods().getGoodsName());
            goodsParams.setPrice(wg.getGoods().getGoodsPrice3());
            wantGoodsList.add(goodsParams);
        }
        params.setHaveGoodsList(haveParamList);
        params.setWantGoodsList(wantGoodsList);

        return params;
    }

    @Override
    public List<String> getAllImages() {
        return postRepository.findImages();

    }


    @Override
    public List<PostSearchParams> searchPosts(Long categoryId, String searchKey, Filter filter) {

        List<Long> goodsIds = new ArrayList<>();
        if (searchKey != null) {
            goodsIds = goodsRepository.findByGoodsNameContaining(searchKey);
        } else {
            goodsIds = goodsRepository.findAllIds();
        }
//        System.out.println("goodsId length is "+ goodsIds.size());

        List<Long> postIds = new ArrayList<>();
        if(filter.equals(Filter.HAVE)){
            postIds = haveGoodsRepository.findByGoodsIds(goodsIds);
        }else if(filter.equals(Filter.WANT)){
            postIds = wantGoodsRepository.findByGoodsIds(goodsIds);
        } else if (filter.equals(Filter.BOTH)) {
            postIds = haveGoodsRepository.findByGoodsIds(goodsIds);
            List<Long> wantIds = wantGoodsRepository.findByGoodsIds(goodsIds);

            for (Long l : wantIds) {
                if (!postIds.contains(l)) {
                    postIds.add(l);
                }
            }

        }

//        System.out.println("postId length is "+ postIds.size());

        List<Long> categoryIds = new ArrayList<>();
        List<Post> postList = new ArrayList<>();
        if(categoryId != null && !categoryId.equals(0)){
            categoryIds = categoryRepository.findTargetChildIds(categoryId);
            categoryIds.add(categoryId);
            postList = postRepository.findByIdsAndPostCategory(postIds, categoryIds);
        }else{
            postList = postRepository.findInIds(postIds);
        }




        List<PostSearchParams> postSearchParamsList = new ArrayList<>();

        for (Post p : postList) {
            PostSearchParams params = PostSearchParams.builder()
                    .postId(p.getPostId())
                    .postTitle(p.getPostTitle())
                    .wantImage(p.getWantImage())
                    .haveImage(p.getHaveImage())
                    .build();

            List<GoodsParams> haveParamList = new ArrayList<>();
            List<GoodsParams> wantGoodsList = new ArrayList<>();
            for(HaveGoods hg : p.getHaveGoodsList()){
                GoodsParams goodsParams = new GoodsParams();
                goodsParams.setGoodsId(hg.getGoods().getGoodsId());
                goodsParams.setGoodsName(hg.getGoods().getGoodsName());
                haveParamList.add(goodsParams);
            }
            for(WantGoods wg : p.getWantGoodsList()){
                GoodsParams goodsParams = new GoodsParams();
                goodsParams.setGoodsId(wg.getGoods().getGoodsId());
                goodsParams.setGoodsName(wg.getGoods().getGoodsName());
                wantGoodsList.add(goodsParams);
            }
            params.setHaveGoodsList(haveParamList);
            params.setWantGoodsList(wantGoodsList);

            postSearchParamsList.add(params);
        }


        return postSearchParamsList;
    }
}
