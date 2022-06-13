package com.swapair.server.post.mapping;

import com.swapair.server.post.have.HaveGoods;
import com.swapair.server.post.want.WantGoods;

import java.util.List;

public interface PostSearchMapping {
    Long getPostId();
    String getPostTitle();
    String getWantImage();
    String getHaveImage();

    List<WantGoods> getWantGoodsList();
    List<HaveGoods> getHaveGoodsList();


}

