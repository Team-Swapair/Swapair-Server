package com.swapair.server.goods;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class GoodsService {
    private final GoodsRepository goodsRepository;

    public List<GoodsParams> getAllGoods(){
        List<Goods> goods = goodsRepository.findAll();
        List<GoodsParams> params = new ArrayList<>();

        for (Goods g : goods) {
            GoodsParams p = GoodsParams.builder()
                    .goodsId(g.getGoodsId())
                    .goodsName(g.getGoodsName())
                    .build();
            params.add(p);
        }
        return params;
    }
}
