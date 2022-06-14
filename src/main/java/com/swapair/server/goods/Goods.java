package com.swapair.server.goods;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swapair.server.post.have.HaveGoods;
import com.swapair.server.post.want.WantGoods;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor

@Table(name="goods")
public class Goods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long goodsId;

    @Column
    private String goodsName;

    @JsonIgnore
    @OneToMany(mappedBy = "goods", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<WantGoods> wantGoodsList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "goods", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<HaveGoods> haveGoodsList = new ArrayList<>();
}
