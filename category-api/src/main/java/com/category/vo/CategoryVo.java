package com.category.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;


@NoArgsConstructor
@Getter
@Setter
public class CategoryVo {

    @Schema(nullable = false, example = "steve")
    @NotNull
    private String categoryName;

    @Schema(nullable = true, example = "1")
    private Long parentId;
}
