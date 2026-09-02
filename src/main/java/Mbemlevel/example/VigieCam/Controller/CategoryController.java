package Mbemlevel.example.VigieCam.Controller;

import Mbemlevel.example.VigieCam.Dto.CategoryRequestDto;
import Mbemlevel.example.VigieCam.Dto.CategoryResponseDto;
import Mbemlevel.example.VigieCam.Model.Category;
import Mbemlevel.example.VigieCam.Services.CategoryServices;
import Mbemlevel.example.VigieCam.Services.IncidentServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(
        name = "Categories",
        description = "Endpoints for managing incident categories"
)
public class CategoryController {

    private final CategoryServices categoryServices;
  public CategoryController(CategoryServices categoryServices){
      this.categoryServices = categoryServices;
    }

    @Operation(
            summary = "Get all categories",
            description = "Returns all categories available for incident reporting."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Categories successfully retrieved"
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No categories found"
            )
    })
    @GetMapping("categ")
    public ResponseEntity<List<CategoryResponseDto>>findAllCategory(){
      List<CategoryResponseDto> category = categoryServices.findAllCategories();
      return ResponseEntity.ok(category);
    }

    @Operation(
            summary = "Get category by ID",
            description = "Returns a specific incident category using its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Category successfully retrieved"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Category not found"
            )
    })
    @GetMapping("{id}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable Long id){
      return ResponseEntity.ok(categoryServices.getCategoryById(id));
    }
    @Operation(
            summary = "Create a category",
            description = "Creates a new category for classifying incidents."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Category successfully created"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid category data"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Category already exists"
            )
    })
    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory(
            @Valid @RequestBody CategoryRequestDto request
    ) {

        CategoryResponseDto category =
                categoryServices.createCategory(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(category);
    }




    @Operation(
            summary = "Delete a category",
            description = "Deletes an existing incident category."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Category successfully deleted"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Category not found"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable Long id
    ) {

        categoryServices.deleteCategory(id);

        return ResponseEntity.noContent().build();
    }
}

