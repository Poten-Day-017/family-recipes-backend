package com.bside.familyrecipes.recipes.presentation;

import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bside.familyrecipes.common.dto.response.ResponseDto;
import com.bside.familyrecipes.recipes.dto.request.RecipeCreateRequest;
import com.bside.familyrecipes.recipes.dto.response.RecipeCategoryResponse;
import com.bside.familyrecipes.recipes.dto.response.RecipeDetailResponse;
import com.bside.familyrecipes.recipes.dto.response.RecipeListResponse;
import com.bside.familyrecipes.recipes.facade.RecipeFacade;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/recipes")
@Tag(name = "RecipeController", description = "레시피 API")
public class RecipeController {

    private static final Integer DEFAULT_PAGE_SIZE = 10;
    private final RecipeFacade recipeFacade;

    @GetMapping
    @Operation(summary = "레시피 리스트를 조회한다")
    public ResponseEntity<RecipeListResponse> findRecipeList(
        @RequestParam(required = false, defaultValue = "1") Integer page
    ) {
        Pageable pageable = PageRequest.of(page - 1, DEFAULT_PAGE_SIZE, Sort.by("createdAt").descending());
        return ResponseDto.ok(recipeFacade.findRecipeList(1L, pageable));
    }

    @GetMapping("/{recipeId}")
    @Operation(summary = "레시피 상세 정보를 조회한다")
    public ResponseEntity<RecipeDetailResponse> getRecipeDetail(@PathVariable Long recipeId) {
        return ResponseDto.ok(recipeFacade.getRecipeDetail(1L, recipeId));
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "레시피를 등록한다")
    @RequestBody(content = @Content(
        schema=@Schema(implementation = RecipeCreateRequest.class)
    ))
    public ResponseEntity<Long> saveRecipe(@RequestPart RecipeCreateRequest recipeCreateRequest,
        @RequestPart("file") Map<String, MultipartFile> multipartFileMap) {
        return ResponseDto.ok(recipeFacade.saveRecipe(1L, recipeCreateRequest, multipartFileMap));
    }

    @GetMapping("/category")
    @Operation(summary = "레시피 카테고리를 조회한다")
    public ResponseEntity<RecipeCategoryResponse> findRecipeCategory() {
        return ResponseDto.ok(recipeFacade.findCategoryList());
    }


    // @PutMapping("/{recipeId}")
    // @Operation(description = "레시피를 수정한다")
    // public String updateRecipe(@PathVariable Long recipeId) {
    //     return "success";
    // }


    // @DeleteMapping("/{recipeId}")
    // @Operation(description = "레시피를 삭제한다")
    // public String deleteRecipe(@PathVariable Long recipeId) {
    //     return "success";
    // }
}
