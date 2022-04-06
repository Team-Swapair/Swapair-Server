package com.swapair.server.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swapair.server.post.Post;
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
@Table(name="category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(nullable = false)
    private String categoryName;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId", insertable = false, updatable=false)
    private Category parent;

    @JsonIgnore
    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Category> children = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "postCategory", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();
}
