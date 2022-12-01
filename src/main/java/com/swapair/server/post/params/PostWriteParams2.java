package com.swapair.server.post.params;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swapair.server.goods.GoodsParams;
import com.swapair.server.user.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostWriteParams2 {
    private Long postId;

    private Long userId;

    private String postTitle;

    private String postContent;

    private String wantImage;

    private String haveImage;

    private Long postCategory;

    private List<Long> wantGoodsList = new ArrayList<>();

    private List<Long> haveGoodsList = new ArrayList<>();
}

