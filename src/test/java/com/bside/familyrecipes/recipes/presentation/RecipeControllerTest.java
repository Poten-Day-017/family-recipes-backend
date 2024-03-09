package com.bside.familyrecipes.recipes.presentation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
            .andExpect(jsonPath("result").value("success"))
            .andExpect(jsonPath("data").value(1L))
            .andDo(MockMvcResultHandlers.print());
    }
}