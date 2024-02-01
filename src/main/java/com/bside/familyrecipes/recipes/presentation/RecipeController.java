package com.bside.familyrecipes.recipes.presentation;

import java.awt.print.Pageable;
import java.util.Map;

import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bside.familyrecipes.common.dto.response.ResponseDto;
import com.bside.familyrecipes.recipes.dto.request.RecipeCreateRequest;
import com.bside.familyrecipes.recipes.dto.response.RecipeDetailResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/recipes")
@Tag(name = "RecipeController", description = "Recipes API")
public class RecipeController {

    @GetMapping
    @Operation(description = "레시피 리스트를 조회한다")
    public String getRecipeList(@PageableDefault Pageable pageable) {
        return "success";
    }

    @GetMapping("/{recipeId}")
    @Operation(description = "레시피 상세 정보를 조회한다")
    public ResponseEntity<RecipeDetailResponse> getRecipeDetail(@PathVariable Long recipeId) {
        return ResponseDto.ok(null);
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(description = "레시피를 등록한다")
    @RequestBody(content = @Content(
        schema=@Schema(implementation = RecipeCreateRequest.class)
    ))
    public ResponseEntity<Long> saveRecipe(@RequestPart RecipeCreateRequest recipeCreateRequest,
        @RequestPart("file") Map<String, MultipartFile> multipartFileMap) {
        return ResponseDto.ok(null);
    }

    @PutMapping("/{recipeId}")
    @Operation(description = "레시피를 수정한다")
    public String updateRecipe(@PathVariable Long recipeId) {
        return "success";
    }

    @DeleteMapping("/{recipeId}")
    @Operation(description = "레시피를 삭제한다")
    public String deleteRecipe(@PathVariable Long recipeId) {
        return "success";
    }
}
