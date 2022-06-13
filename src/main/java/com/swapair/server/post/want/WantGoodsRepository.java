package com.swapair.server.post.want;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WantGoodsRepository extends JpaRepository<WantGoods, Long> {
    @Query(value = "SELECT distinct w.post.postId FROM WantGoods w WHERE w.goods.goodsId IN (:goodsIds) ")
    List<Long> findByGoodsIds(@Param("goodsIds") List<Long> goodsIds);

}
