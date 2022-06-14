package com.swapair.server.post.params;

import com.swapair.server.goods.GoodsParams;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostProfileParams {

    private Long postId;

    private String postTitle;

    private String postCategory;
}
