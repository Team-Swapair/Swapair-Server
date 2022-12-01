package com.swapair.server.goods;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, Long> {
    @Query("SELECT g.goodsId from Goods g where g.goodsName like %:goodsName%")
    List<Long> findByGoodsNameContaining(@Param("goodsName") String goodsName);

    @Query("SELECT g.goodsId from Goods g")
    List<Long> findAllIds();

    Goods findByGoodsId(@Param("goodsId") Long goodsId);
}
