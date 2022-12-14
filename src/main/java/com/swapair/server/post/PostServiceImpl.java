package com.swapair.server.post;

import com.swapair.server.category.CategoryRepository;
import com.swapair.server.goods.Goods;
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
                .build();

        Long id = postRepository.save(post1).getPostId();
        List<Long> havaGoodsList = post.getHaveGoodsList();

        for (Long l : havaGoodsList) {
            HaveGoods hg = HaveGoods.builder().goods(goodsRepository.findById(l).orElseThrow(IllegalAccessError::new))
                    .post(post1).build();
            haveGoodsRepository.save(hg);

            LocalDateTime referenceDate = goodsRepository.findById(l).orElseThrow(IllegalAccessError::new).getStorageReferenceDate();
            int dateDifference = referenceDate.compareTo(LocalDateTime.now());

            if (dateDifference == 0) {
                calculateGoodsPrice1(l);
            } else if (dateDifference == 1) {
                calculateGoodsPrice2(l);
            } else if (dateDifference == 2) {
                calculateGoodsPrice3(l);
            }
        }


        List<Long> wantGoodsList = post.getWantGoodsList();
        for (Long l : wantGoodsList) {
            WantGoods wg = WantGoods.builder().goods(goodsRepository.findById(l).orElseThrow(IllegalAccessError::new))
                    .post(post1).build();
            wantGoodsRepository.save(wg);

            LocalDateTime referenceDate = goodsRepository.findById(l).orElseThrow(IllegalAccessError::new).getStorageReferenceDate();
            int dateDifference = referenceDate.compareTo(LocalDateTime.now());

            if (dateDifference == 0) {
                calculateGoodsPrice1(l);
            } else if (dateDifference == 1) {
                calculateGoodsPrice2(l);
            } else if (dateDifference == 2) {
                calculateGoodsPrice3(l);

                List<Long> haveGoods = haveGoodsRepository.findByGoodsAndPost_IsDoubted(goodsRepository.findById(l).orElseThrow(IllegalAccessError::new), false);
                List<Long> wantGoods = wantGoodsRepository.findByGoodsAndPost_IsDoubted(goodsRepository.findById(l).orElseThrow(IllegalAccessError::new), false);

                if (wantGoods.size() / haveGoods.size() >= 2) {
                    // wantGoods의 게시글의 조회수 체크
                    int views1 = goodsRepository.findById(l).orElseThrow(IllegalAccessError::new).getViews1();
                    int views3 = goodsRepository.findById(l).orElseThrow(IllegalAccessError::new).getViews3();

                    if (views3 <= 1.5 * views1) {
                        // 해당 기간의 wantGoods를 가진 포스트들 isDoubted 처리
                        for (Long wantGoodsId : wantGoods) {
                            Long postId = wantGoodsRepository.findById(wantGoodsId).orElseThrow(IllegalAccessError::new).getPost().getPostId();
                            LocalDateTime createdAt = wantGoodsRepository.findById(wantGoodsId).orElseThrow(IllegalAccessError::new).getPost().getCreatedAt();

                            if (createdAt.isAfter(referenceDate) && createdAt.isBefore(referenceDate.plusDays(3))) {
                                postRepository.findById(postId).orElseThrow(IllegalAccessError::new).setIsDoubted(true);
                            }
                        }
                    }
                }
            } else {
                Long price = goodsRepository.findById(l).orElseThrow(IllegalAccessError::new).getGoodsPrice();
                Long price2 = goodsRepository.findById(l).orElseThrow(IllegalAccessError::new).getGoodsPrice2();
                Long price3 = goodsRepository.findById(l).orElseThrow(IllegalAccessError::new).getGoodsPrice3();

                goodsRepository.findById(l).orElseThrow(IllegalAccessError::new).setGoodsPrice1(price2);
                goodsRepository.findById(l).orElseThrow(IllegalAccessError::new).setGoodsPrice2(price3);
                goodsRepository.findById(l).orElseThrow(IllegalAccessError::new).setGoodsPrice(price);

                goodsRepository.findById(l).orElseThrow(IllegalAccessError::new).setStorageReferenceDate(referenceDate.plusDays(1));
            }
        }
        return id;
    }

    public void calculateGoodsPrice1(Long goodsId) {
        List<Long> haveGoods = haveGoodsRepository.findByGoodsAndPost_IsDoubted(goodsRepository.findById(goodsId).orElseThrow(IllegalAccessError::new), false);
        List<Long> wantGoods = wantGoodsRepository.findByGoodsAndPost_IsDoubted(goodsRepository.findById(goodsId).orElseThrow(IllegalAccessError::new), false);

        Long price = goodsRepository.findById(goodsId).orElseThrow(IllegalAccessError::new).getGoodsPrice1();

        goodsRepository.findById(goodsId).orElseThrow(IllegalAccessError::new).setGoodsPrice1(price * setWantGoodsSize(wantGoods) / setHaveGoodsSize(haveGoods));
    }

    public void calculateGoodsPrice2(Long goodsId) {
        List<Long> haveGoods = haveGoodsRepository.findByGoodsAndPost_IsDoubted(goodsRepository.findById(goodsId).orElseThrow(IllegalAccessError::new), false);
        List<Long> wantGoods = wantGoodsRepository.findByGoodsAndPost_IsDoubted(goodsRepository.findById(goodsId).orElseThrow(IllegalAccessError::new), false);

        Long price = goodsRepository.findById(goodsId).orElseThrow(IllegalAccessError::new).getGoodsPrice2();

        goodsRepository.findById(goodsId).orElseThrow(IllegalAccessError::new).setGoodsPrice2(price * setWantGoodsSize(wantGoods) / setHaveGoodsSize(haveGoods));
    }

    public void calculateGoodsPrice3(Long goodsId) {
        List<Long> haveGoods = haveGoodsRepository.findByGoodsAndPost_IsDoubted(goodsRepository.findById(goodsId).orElseThrow(IllegalAccessError::new), false);
        List<Long> wantGoods = wantGoodsRepository.findByGoodsAndPost_IsDoubted(goodsRepository.findById(goodsId).orElseThrow(IllegalAccessError::new), false);

        Long price = goodsRepository.findById(goodsId).orElseThrow(IllegalAccessError::new).getGoodsPrice3();

        goodsRepository.findById(goodsId).orElseThrow(IllegalAccessError::new).setGoodsPrice3(price * setWantGoodsSize(wantGoods) / setHaveGoodsSize(haveGoods));
    }

    private int setHaveGoodsSize(List<Long> haveGoods) {
        if (haveGoods.size() == 0) {
            return 1;
        } else {
            return haveGoods.size();
        }
    }

    private int setWantGoodsSize(List<Long> wantGoods) {
        if (wantGoods.size() == 0) {
            return 1;
        } else {
            return wantGoods.size();
        }
    }


    @Override
    public List<PostSearchParams> getAllPosts() {
        List<Post> postList = postRepository.findAll();
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
            for (HaveGoods hg : p.getHaveGoodsList()) {
                GoodsParams goodsParams = new GoodsParams();
                goodsParams.setGoodsId(hg.getGoods().getGoodsId());
                goodsParams.setGoodsName(hg.getGoods().getGoodsName());
                haveParamList.add(goodsParams);
            }
            for (WantGoods wg : p.getWantGoodsList()) {
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
        for (HaveGoods hg : p.getHaveGoodsList()) {
            addGoodsView(haveParamList, hg.getGoods());
        }
        for (WantGoods wg : p.getWantGoodsList()) {
            addGoodsView(haveParamList, wg.getGoods());
        }
        params.setHaveGoodsList(haveParamList);
        params.setWantGoodsList(wantGoodsList);

        return params;
    }

    private void addGoodsView(List<GoodsParams> haveParamList, Goods goods) {
        Long goodsId = goods.getGoodsId();

        GoodsParams goodsParams = new GoodsParams();
        goodsParams.setGoodsId(goodsId);
        goodsParams.setGoodsName(goods.getGoodsName());
        haveParamList.add(goodsParams);

        LocalDateTime referenceDate = goodsRepository.findById(goodsId).orElseThrow(IllegalAccessError::new).getStorageReferenceDate();
        int dateDifference = referenceDate.compareTo(LocalDateTime.now());

        if (dateDifference == 0) {
            goodsRepository.findById(goodsId).orElseThrow(IllegalAccessError::new).setViews1(goodsRepository.findById(goodsId).orElseThrow(IllegalAccessError::new).getViews1() + 1);
        } else if (dateDifference == 1) {
            goodsRepository.findById(goodsId).orElseThrow(IllegalAccessError::new).setViews2(goodsRepository.findById(goodsId).orElseThrow(IllegalAccessError::new).getViews2() + 1);
        } else if (dateDifference == 2) {
            goodsRepository.findById(goodsId).orElseThrow(IllegalAccessError::new).setViews3(goodsRepository.findById(goodsId).orElseThrow(IllegalAccessError::new).getViews3() + 1);
        } else {
            int views2 = goodsRepository.findById(goodsId).orElseThrow(IllegalAccessError::new).getViews2();
            int views3 = goodsRepository.findById(goodsId).orElseThrow(IllegalAccessError::new).getViews3();

            goodsRepository.findById(goodsId).orElseThrow(IllegalAccessError::new).setViews1(views2);
            goodsRepository.findById(goodsId).orElseThrow(IllegalAccessError::new).setViews2(views3);
            goodsRepository.findById(goodsId).orElseThrow(IllegalAccessError::new).setViews3(0);

            goodsRepository.findById(goodsId).orElseThrow(IllegalAccessError::new).setStorageReferenceDate(referenceDate.plusDays(1));
        }
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
