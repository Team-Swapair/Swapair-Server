package com.swapair.server.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select c.categoryId from Category c where c.parent.categoryId = :targetId")
    List<Long> findTargetChildIds(@Param("targetId") Long targetId);

    Category getCategoryByCategoryId(@Param("categoryId") Long categoryId);

}
