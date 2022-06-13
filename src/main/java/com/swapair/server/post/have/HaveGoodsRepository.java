package com.swapair.server.post.have;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HaveGoodsRepository extends JpaRepository<HaveGoods, Long> {

    @Query(value = "SELECT distinct h.post.postId FROM HaveGoods h WHERE h.goods.goodsId IN (:goodsIds) ")
    List<Long> findByGoodsIds(@Param("goodsIds")List<Long> goodsIds);
}
