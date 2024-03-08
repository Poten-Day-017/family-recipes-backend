package com.bside.familyrecipes.recipes.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RecipeConstants {

    public static final Integer DEFAULT_PAGE_SIZE = 10;

    public static final String CREATE_RECIPE_DESCRIPTION = """
        curl --location '{{서버도메인}}/api/v1/recipes'
        --form 'recipeCreateRequest="{
          \\\\"title\\\\": \\\\"어머니의 김치찌개\\\\",
          \\\\"origin\\\\": \\\\"어머니\\\\",
          \\\\"content\\\\": \\\\"가족의 레시피를 간단하게 1줄로 소개해보세요.\\\\",
          \\\\"category\\\\": \\\\"CTGR_001\\\\",
          \\\\"capacity\\\\": 2,
          \\\\"isOpen\\\\": true,
          \\\\"ingredientList\\\\": [
            {
              \\\\"order\\\\": 1,
              \\\\"name\\\\": \\\\"채끝살\\\\",
              \\\\"amount\\\\": \\\\"300g\\\\"
            }
          ],
          \\\\"secretIngredientList\\\\": [
            {
              \\\\"order\\\\": 1,
              \\\\"name\\\\": \\\\"채끝살\\\\",
              \\\\"amount\\\\": \\\\"300g\\\\"
            }
          ],
          \\\\"procedureList\\\\": [
            {
              \\\\"order\\\\": 1,
              \\\\"description\\\\": \\\\"쌀은 씻어 30분간 불린다. 쪽파는 송송 썰어 둔다.\\\\"
            }
          ]
        }";type=application/json'
        --form '파일명=@"파일 실제 경로"'
                    
        [파일명]
        cookingImage (요리 이미지)
        cookingVideo (요리 영상)
        procedureImage1, 2, 3 (요리 순서 이미지1,2,3 - orderNo와 동일하게)
        """;


}
