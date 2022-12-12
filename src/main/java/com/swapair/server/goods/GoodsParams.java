package com.swapair.server.goods;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GoodsParams {
    private Long goodsId;
    private String goodsName;
    private Long price;
}
