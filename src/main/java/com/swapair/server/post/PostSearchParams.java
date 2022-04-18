package com.swapair.server.post;

import com.swapair.server.goods.GoodsParams;
import com.swapair.server.post.have.HaveGoods;
import com.swapair.server.post.want.WantGoods;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostSearchParams {

    private Long postId;

    private String postTitle;

    private String wantImage;

    private String haveImage;

    private List<GoodsParams> wantGoodsList = new ArrayList<>();

    private List<GoodsParams> haveGoodsList = new ArrayList<>();
}
