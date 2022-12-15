package com.swapair.server.goods;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swapair.server.post.have.HaveGoods;
import com.swapair.server.post.want.WantGoods;
import lombok.*;
import org.apache.tomcat.jni.Local;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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

    @Column(nullable = false)
    private Long goodsPrice;

    @Column(nullable = false)
    private Long goodsPrice1;

    @Column(nullable = false)
    private Long goodsPrice2;

    @Column(nullable = false)
    private Long goodsPrice3;

    @Column(nullable = false)
    private int views1;

    @Column(nullable = false)
    private int views2;

    @Column(nullable = false)
    private int views3;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime storageReferenceDate;
}
