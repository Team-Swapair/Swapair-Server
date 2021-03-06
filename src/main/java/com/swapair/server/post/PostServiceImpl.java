package com.swapair.server.post;

import com.swapair.server.category.CategoryRepository;
import com.swapair.server.goods.GoodsParams;
import com.swapair.server.goods.GoodsRepository;
import com.swapair.server.post.have.HaveGoods;
import com.swapair.server.post.have.HaveGoodsRepository;
import com.swapair.server.post.params.PostDetailParams;
import com.swapair.server.post.params.PostSearchParams;
import com.swapair.server.post.want.WantGoods;
import com.swapair.server.post.want.WantGoodsRepository;
import com.swapair.server.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public void createPost(Post post) {

        postRepository.save(post);

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

        return params;
    }

    @Override
    public List<Post> getMyPosts(Long userId) {
        return null;
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
