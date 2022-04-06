package com.swapair.server.goods;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swapair.server.category.Category;
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
    @JoinColumn(name = "goodsId", insertable = false, updatable=false)
    private Goods goods;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId", insertable = false, updatable=false)
    private Post post;
}
