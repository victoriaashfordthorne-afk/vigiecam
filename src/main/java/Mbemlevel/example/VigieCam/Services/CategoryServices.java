package Mbemlevel.example.VigieCam.Services;

import Mbemlevel.example.VigieCam.Dto.CategoryRequestDto;
import Mbemlevel.example.VigieCam.Dto.CategoryResponseDto;
import Mbemlevel.example.VigieCam.Mapper.CategoryMapper;
import Mbemlevel.example.VigieCam.Model.Category;
import Mbemlevel.example.VigieCam.Repository.CategoryRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServices {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServices(CategoryRepository categoryRepository,CategoryMapper categoryMapper){
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;

    }

    public CategoryResponseDto createCategory(
            CategoryRequestDto request
    ) {

        if (categoryRepository.existsByName(request.getName())) {
            throw new RuntimeException(
                    "Category already exists"
            );
        }

        Category category =
                categoryMapper.toEntity(request);

        Category savedCategory =
                categoryRepository.save(category);

        return categoryMapper.toResponseDto(savedCategory);
    }

    public List<CategoryResponseDto> findAllCategories() {

        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toResponseDto)
                .toList();
    }
    public CategoryResponseDto getCategoryById(Long id) {

        Category category =
                categoryRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Category not found with id: " + id
                                )
                        );

        return categoryMapper.toResponseDto(category);
    }

    public void deleteCategory(Long id) {

        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException(
                    "Category not found with id: " + id
            );
        }

        categoryRepository.deleteById(id);
    }
    public Category getCategoryEntityById(Long id) {

        return categoryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Category not found with id: " + id
                        )
                );
    }

}
