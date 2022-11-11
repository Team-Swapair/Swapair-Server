package com.swapair.server.post.want;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swapair.server.category.Category;
import com.swapair.server.goods.Goods;
import com.swapair.server.post.Post;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="wantGoods")
public class WantGoods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wantId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goodsId")
    private Goods goods;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    private Post post;
}
