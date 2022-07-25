package com.devjola.fashionblog.pagination_criteria;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Getter
@Setter
public class CategoryListPages {
    private int pageNumber = 0;
    private int pageSize = 2;
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sortBy = "name";

}
