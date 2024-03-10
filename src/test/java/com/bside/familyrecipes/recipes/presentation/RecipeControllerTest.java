package com.bside.familyrecipes.recipes.presentation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import com.bside.familyrecipes.recipes.dto.request.RecipeCreateRequest;
import com.bside.familyrecipes.recipes.facade.RecipeFacade;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(RecipeController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc(addFilters = false)
class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeFacade recipeFacade;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("레시피 등록 요청을 성공한다.")
    void recipeCreateRequestTest() throws Exception {
        //given
        var request = new RecipeCreateRequest("레시피 제목", "레시피 주인", "레시피 소개", "001", 1, null, true, null, null, null);
        var requestJson = new MockMultipartFile("recipeCreateRequest", "", APPLICATION_JSON_VALUE,
            objectMapper.writeValueAsBytes(request));
        when(recipeFacade.createRecipe(any(), any(), any()))
            .thenReturn(1L);

        //when
        var actions = mockMvc.perform(multipart("/api/v1/recipes")
            .file(requestJson));

        //then
        actions
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("result").value("success"))
            .andExpect(jsonPath("data").value(1L))
            .andDo(print());
    }

    @ParameterizedTest
    @DisplayName("레시피 등록 요청 validation")
    @MethodSource("provideRecipeCreateRequest")
    void recipeCreateRequestValidationTest(String request, String result) throws Exception {
        
        //given
        var requestJson = new MockMultipartFile("recipeCreateRequest", "", APPLICATION_JSON_VALUE, request.getBytes());
        
        //when
        var actions = mockMvc.perform(multipart("/api/v1/recipes")
            .file(requestJson));
        
        //then
        actions
            .andExpect(status().is4xxClientError())
            .andExpect(jsonPath("result").value("fail"))
            .andExpect(jsonPath("message").value(result))
            .andDo(print());
    }

    private static Stream<Arguments> provideRecipeCreateRequest() {
        return Stream.of(
            Arguments.of("""
                  {
                  "title": "",
                  "origin": "어머니",
                  "content": "가족의 레시피를 간단하게 1줄로 소개해보세요.",
                  "category": "001",
                  "capacity": 2,
                  "isOpen": true,
                  "ingredientList": [{"order": 1,"name": "채끝살","amount": "300g"}],
                  "secretIngredientList": [{"order": 1,"name": "채끝살","amount": "300g"}],
                  "procedureList": [{"order": 1,"description": "쌀은 씻어 30분간 불린다. 쪽파는 송송 썰어 둔다."}]}
                """ , "레시피 제목을 작성해주세요."),
            Arguments.of("""
                  {
                  "title": "제",
                  "origin": "어머니",
                  "content": "가족의 레시피를 간단하게 1줄로 소개해보세요.",
                  "category": "001",
                  "capacity": 2,
                  "isOpen": true,
                  "ingredientList": [{"order": 1,"name": "채끝살","amount": "300g"}],
                  "secretIngredientList": [{"order": 1,"name": "채끝살","amount": "300g"}],
                  "procedureList": [{"order": 1,"description": "쌀은 씻어 30분간 불린다. 쪽파는 송송 썰어 둔다."}]}
                """ , "레시피 제목을 최소 2자 최대 30자 이내로 작성해주세요."),
            Arguments.of("""
                  {
                  "title": "제목",
                  "origin": "",
                  "content": "가족의 레시피를 간단하게 1줄로 소개해보세요.",
                  "category": "001",
                  "capacity": 2,
                  "isOpen": true,
                  "ingredientList": [{"order": 1,"name": "채끝살","amount": "300g"}],
                  "secretIngredientList": [{"order": 1,"name": "채끝살","amount": "300g"}],
                  "procedureList": [{"order": 1,"description": "쌀은 씻어 30분간 불린다. 쪽파는 송송 썰어 둔다."}]}
                """ , "가족 레시피를 만든 사람을 적어주세요.")
        );
    }
}
