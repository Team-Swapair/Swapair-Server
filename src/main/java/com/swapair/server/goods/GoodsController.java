package com.swapair.server.goods;

import com.swapair.server.post.params.PostSearchParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Api(tags = {"3.1 물건"})
@RestController
@RequestMapping(value = "/v1")
@RequiredArgsConstructor
public class GoodsController {

    private final GoodsService goodsService;

    @ApiOperation(value = "모든 물건 조회", notes = "모든 물건을 조회한다")
    @GetMapping(value = "goods")
    public List<GoodsParams> getAllGoods(){ return goodsService.getAllGoods(); }
}
