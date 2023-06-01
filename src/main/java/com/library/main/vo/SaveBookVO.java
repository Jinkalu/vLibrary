package com.library.main.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveBookVO {
    private String title;
    private String author;
    private String category;
    private String releaseDate;
    private Long price;
}
