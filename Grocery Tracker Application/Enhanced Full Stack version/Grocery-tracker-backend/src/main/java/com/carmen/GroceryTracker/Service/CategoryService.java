/**
 * Grocery Tracker - CategoryService
 * Version: 1.0
 * Developer: Carmen Mosquera
 * Description: This class converts the Category entity to CategoryDTO.
 * and provides service to the category entity by implementing
 * methods that help the controller communicate with the repository to send
 * data to the frontend.
 */
package com.carmen.GroceryTracker.Service;

import com.carmen.GroceryTracker.Model.Category;
import com.carmen.GroceryTracker.Model.DTOs.CategoryDTO;
import com.carmen.GroceryTracker.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    //get all categories
    public List<CategoryDTO> getAllCategories(){
        List<Category> categories = (List<Category>) categoryRepository.findAll();

        return categories.stream()
                .map(this::convertToCategoryDTO)
                .collect(Collectors.toList());
    }

    //this method converts the category entity to categoryDTO
    private CategoryDTO convertToCategoryDTO(Category category) {
        return new CategoryDTO(category.getId(), category.getName(), category.getProducts());
    }

    //getter for the below private method
    public CategoryDTO getConvertToCategoryDTO(Category category){
        return convertToCategoryDTO(category);
    }

}
