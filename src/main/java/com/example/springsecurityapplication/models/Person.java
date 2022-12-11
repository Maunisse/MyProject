package com.example.springsecurityapplication.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.ConnectionBuilder;
import java.util.List;

@Entity
@Table(name = "Person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Логин не может быть пустым")
    @Size(min = 5, max = 100, message = "Логин должен быть от 5 до 100 символов")
    @Column(name = "login")
    private String login;

    @NotEmpty(message = "Пароль не может быть пустым")
    @Column(name = "password")
    @Pattern(regexp = "(?=^.{8,100}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$",message = "Пароль должен содержать не менее 8 символов, хотя бы одну цифру, специальный символ, букву в верхнем и нижнем регистрах")
    private String password;

    @NotEmpty(message = "Фамилия не может быть пустой")
    @Column(name="lastname")
    @Size(min=2,max=30,message = "Фамилия должны быть в диапазоне от 2 до 30 символов")
    private String lastname;

    @NotEmpty(message = "Имя не может быть пустым")
    @Column(name = "firstname")
    @Size(min=2,max=30,message = "Имя должно быть в диапазоне от 2 до 30 символов")
    private String firstname;

    @Column(name = "patronymic")
    private String patronymic;

    @NotEmpty(message = "Емайл не может быть пустым")
    @Column(name = "email")
    @Email(message = "Неверный имейл")
    private String email;

    @NotEmpty(message = "Номер телефона не может быть пустым")
    @Column(name = "phonenumber")
    @Pattern(regexp="^((\\+7|7|8)+([0-9]){10})$", message="Номер телефона должен быть в формате +7/7/89000000000")
    private String phonenumber;

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    @ManyToOne(optional = false)
    private Role role_role;

    public Role getRole_role() {
        return role_role;
    }

    public void setRole_role(Role role_role) {
        this.role_role = role_role;
    }


    @ManyToMany()
    @JoinTable(name = "product_cart", joinColumns = @JoinColumn(name = "person_id"), inverseJoinColumns=@JoinColumn(name = "product_id"))
    private List<Product> product;

    @OneToMany(mappedBy = "person")
    private List<Order> orderList;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
