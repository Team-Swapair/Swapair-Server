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
public class PostDetailParams {
    private Long postId;

    @JsonIgnore
    private User user;

    private String postTitle;

    private String postContent;

    private String wantImage;

    private String haveImage;

    private Boolean isClosed;

    private Boolean isChecked;

    private LocalDateTime createdAt;

    private LocalDateTime updateAt;

    private List<GoodsParams> wantGoodsList = new ArrayList<>();

    private List<GoodsParams> haveGoodsList = new ArrayList<>();
}
