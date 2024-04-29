package africashop.be.controllers;

import africashop.be.entities.Category;
import africashop.be.services.Member.CategoriesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/member")
public class MemberControllers {

    private final CategoriesService categoriesService;

    public MemberControllers(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories(){
        List<Category> categories = categoriesService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
}
