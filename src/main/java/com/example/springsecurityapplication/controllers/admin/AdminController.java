package com.example.springsecurityapplication.controllers.admin;

import com.example.springsecurityapplication.models.Image;
import com.example.springsecurityapplication.models.Order;
import com.example.springsecurityapplication.models.Person;
import com.example.springsecurityapplication.models.Product;
import com.example.springsecurityapplication.repositories.*;
import com.example.springsecurityapplication.services.ImageService;
import com.example.springsecurityapplication.services.OrderService;
import com.example.springsecurityapplication.services.PersonService;
import com.example.springsecurityapplication.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
//@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
public class AdminController {
    @Value("${upload.path}")
    private String uploadPath;

    private final ProductService productService;
    private final CategoryRepository categoryRepository;
    private  final PersonRepository personRepository;
    private  final PersonService personService;
    private  final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private  final ImageService imageService;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final StatusRepository statusRepository;

    @Autowired
    public AdminController(ProductService productService, CategoryRepository categoryRepository, PersonRepository personRepository, PersonService personService, RoleRepository roleRepository, PasswordEncoder passwordEncoder, ImageService imageService, ProductRepository productRepository, OrderRepository orderRepository, OrderService orderService, StatusRepository statusRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
        this.personRepository = personRepository;
        this.personService = personService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.imageService = imageService;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.orderService = orderService;
        this.statusRepository = statusRepository;
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("")
    public String admin(Model model){
        model.addAttribute("products",productService.getAllProduct());
        return "admin/admin";
    }

    @GetMapping("/product/add")
    public String addProduct(Model model){
        model.addAttribute("product",new Product());
        model.addAttribute("category", categoryRepository.findAll());
        return "product/addProduct";
    }

    @PostMapping("/orderList/edit/{id}")
    public String editStatus(Model model, Order order, @PathVariable("id") int id){
        model.addAttribute("status", statusRepository.findAll());
        orderService.updateOrder(id,order);
 //       orderRepository.save(order.setStatus(id));
//        person.setRole_role(roleRepository.getReferenceById(3)
//               order.setStatus(statusRepository.getReferenceById(4).getId());
        return "admin/orderList";
    }


    //метод добавления в бд через сервис-->репозиторий
    @PostMapping("/product/add")
    public String addProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, @RequestParam("file_one") MultipartFile file_one, @RequestParam("file_two") MultipartFile file_two, @RequestParam("file_three") MultipartFile file_three, @RequestParam("file_fore") MultipartFile file_fore, @RequestParam("file_five") MultipartFile file_five) throws IOException {
        if (bindingResult.hasErrors()){
            return "product/addProduct";
        }
        if (file_one !=null){
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." +file_one.getOriginalFilename();
            file_one.transferTo(new File(uploadPath + "/" +resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }
        if (file_two !=null){
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." +file_two.getOriginalFilename();
            file_two.transferTo(new File(uploadPath + "/" +resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }
        if (file_three !=null){
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." +file_three.getOriginalFilename();
            file_three.transferTo(new File(uploadPath + "/" +resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }
        if (file_fore !=null){
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." +file_fore.getOriginalFilename();
            file_fore.transferTo(new File(uploadPath + "/" +resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }
        if (file_five !=null){
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." +file_five.getOriginalFilename();
            file_five.transferTo(new File(uploadPath + "/" +resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }
        productService.saveProduct(product);
        return "redirect:/admin";
    }
    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id){
        productService.deleteProduct(id);
        return "redirect:/admin";
    }

    @GetMapping("/product/delete/img/{id}")
    public String deleteImage(@PathVariable("id") int id){
        imageService.deleteImage(id);
        return "redirect:/admin";
    }

    @GetMapping("/product/edit/{id}")
    public String editProduct(Model model, @PathVariable("id") int id){
        model.addAttribute("product",productService.getProductId(id));
        model.addAttribute("category", categoryRepository.findAll());
        model.addAttribute("image", imageService.getImageId(id));
        return "product/editProduct";
    }

    @PostMapping("/product/edit/{id}")
    public String editProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()){
            return "product/editProduct";
        }
        productService.updateProduct(id,product);
        return "redirect:/admin";
    }

    @PostMapping("/product/edit/img/{id}")
    public String editImage(@ModelAttribute("product") @Valid Product product, @ModelAttribute("image") Image image,@RequestParam("file_one") MultipartFile file_one, @RequestParam("file_two") MultipartFile file_two, @RequestParam("file_three") MultipartFile file_three, @RequestParam("file_fore") MultipartFile file_fore, @RequestParam("file_five") MultipartFile file_five, BindingResult bindingResult, @PathVariable("id") int id) throws IOException {
        if (bindingResult.hasErrors()) {
            return "product/editProduct";
        }
        if (file_one != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_one.getOriginalFilename();
            file_one.transferTo(new File(uploadPath + "/" + resultFileName));
            Image new_image = new Image();
//            new_image.getProduct();
            new_image.setFileName(resultFileName);
            product.updateImageToProduct(new_image);
        }

        imageService.updateImage(id, image);
        return "redirect:/admin";
    }

    @GetMapping("/userList")
    public String userList(Model model){
        model.addAttribute("users", personRepository.findAll());
        return "admin/userList";
    }

    @GetMapping("/userList/edit/{id}")
    public String userList(Model model, @PathVariable("id") int id){
        model.addAttribute("person",personService.getPersonId(id));
        model.addAttribute("role_role", roleRepository.findAll());
        return "admin/editList";
    }

    @PostMapping("/userList/edit/{id}")
    public String editRole( @ModelAttribute("person") @Valid Person person, @RequestParam(value = "new_role", required = false, defaultValue = "") String new_role, BindingResult bindingResult, @PathVariable("id") int id)

    {
        if (bindingResult.hasErrors()){
            return "redirect:/admin/userList";
        }

        if (new_role.equals("ROLE_USER")){
            person.setRole_role(roleRepository.getReferenceById(2));
        } else {
            person.setRole_role(roleRepository.getReferenceById(3));
        }

        person.setPassword(passwordEncoder.encode(person.getPassword()));
        personService.updateRole(id, person);
        return "redirect:/admin";
    }

    //сортировка товаров
    @PostMapping("/search")
    public String productSearch(@RequestParam(value = "contact", required = false, defaultValue = "") String contact, Model model) {

            if (contact.equals("furniture")) {
                model.addAttribute("search_product", productRepository.findByCategory(1));
            } else if (contact.equals("appliances")) {
                model.addAttribute("search_product", productRepository.findByCategory(2));
            } else if (contact.equals("clothes")) {
                model.addAttribute("search_product", productRepository.findByCategory(3));
            }
        model.addAttribute("products",productService.getAllProduct());
        return "/admin/admin";
    }
    @GetMapping("/orderList")
    public String ordersUser(Model model){
        model.addAttribute("orders", orderRepository.findAll());
        model.addAttribute("status", statusRepository.findAll());
        return "/admin/orderList";
    }

    @PostMapping("/orderList/search")
    public String numberSearch(@RequestParam("search") String search, Model model){
        model.addAttribute("search_number", orderRepository.findByNumber(search));
        model.addAttribute("value_search",search);
        model.addAttribute("orders", orderRepository.findAll());
        return "admin/orderList";
    }

//    @PostMapping("/orderList/reStatus")
//    public String statusReload()

}
