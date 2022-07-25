package com.devjola.fashionblog.pagination_criteria;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;


@Getter @Setter
@Component
public class PostListPages {
    private int pageNumber = 0;
    private int pageSize = 4;
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sortBy = "id";
}
