package com.swapair.server.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select p.postId from Post p where p.user.userId = :userId")
    List<Long> findIdsByUser_UserId(@Param("userId") Long userId);

    @Query("select p from Post p where p.postId in (:postIds) and p.postCategory.categoryId in (:categories)")
    List<Post> findByIdsAndPostCategory(@Param("postIds")List<Long> postIds, @Param("categories") List<Long> categories);

    @Query("select p from Post p where p.postId in (:postIds)")
    List<Post> findInIds(@Param("postIds")List<Long> postIds);

}
