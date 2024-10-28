package com.thinhle.controller;

import com.thinhle.entity.Category;
import com.thinhle.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @GetMapping
    public String listCategories(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize, Model model) {

        List<Category> categories = categoryService.getCategoriesByPage(page, pageSize);
        model.addAttribute("listCategory", categories);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", (int) Math.ceil((double) categoryService.countCategories() / pageSize));
        return "category-list";  // Đường dẫn tới view danh sách danh mục
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("category", new Category());
        return "category-add";  // Đường dẫn tới view thêm danh mục
    }

    @PostMapping("/add")
    public String addCategory(@ModelAttribute("category") Category category) {
        categoryService.saveCategory(category);
        return "redirect:/category";  // Redirect về danh sách danh mục
    }

    @GetMapping("/edit")
    public String showEditForm(@RequestParam("id") int id, Model model) {
        Category category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        return "category-edit";  // Đường dẫn tới view sửa danh mục
    }



    @PostMapping("/update")
    public String updateCategory(@ModelAttribute("category") Category category) {
        // Kiểm tra ID có hợp lệ không
            Category existingCategory = categoryService.getCategoryById((int) category.getCategoryId());
            if (existingCategory != null) {
                categoryService.updateCategory(category);  // Cập nhật
            }
        return "redirect:/category";  // Redirect về danh sách danh mục
    }


    @GetMapping("/delete")
    public String deleteCategory(@RequestParam("id") int id) throws Exception {
        categoryService.deleteCategoryById(id);
        return "redirect:/category";  // Redirect về danh sách danh mục
    }

    @GetMapping("/search")
    public String searchCategories(@RequestParam("keyword") String keyword, Model model) {
        List<Category> categories = categoryService.searchCategoriesByName(keyword);
        model.addAttribute("listCategory", categories);
        return "category-list";  // Đường dẫn tới view danh sách danh mục
    }
}
