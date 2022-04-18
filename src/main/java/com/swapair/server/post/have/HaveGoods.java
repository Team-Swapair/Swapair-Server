package com.swapair.server.post.have;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name="haveGoods")
public class HaveGoods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long haveId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goodsId", insertable = false, updatable=false)
    private Goods goods;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId", insertable = false, updatable=false)
    private Post post;

}
