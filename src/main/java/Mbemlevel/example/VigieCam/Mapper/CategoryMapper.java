package Mbemlevel.example.VigieCam.Mapper;

import Mbemlevel.example.VigieCam.Dto.CategoryRequestDto;
import Mbemlevel.example.VigieCam.Dto.CategoryResponseDto;
import Mbemlevel.example.VigieCam.Model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryRequestDto dto){
        if (dto == null){
            return null;
        }
        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        return category;
    }

    public CategoryResponseDto toResponseDto(Category category){
        if(category == null){
            return null;
        }
        CategoryResponseDto dto = new CategoryResponseDto();

        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        return dto;
    }
}
