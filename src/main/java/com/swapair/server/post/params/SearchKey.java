package com.swapair.server.post.params;

import com.swapair.server.post.Filter;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchKey {
    Filter filter = Filter.BOTH;
    String keyword;
    Long categoryId;
}
