package com.khvorostin.simplespring;

import com.khvorostin.simplespring.config.ApplicationConfiguration;
import com.khvorostin.simplespring.skoda_showroom.Cart;
import com.khvorostin.simplespring.skoda_showroom.ProductRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);

        // текущая корзина
        Cart cart = context.getBean(Cart.class);
        ProductRepository productRepository = context.getBean(ProductRepository.class);

        System.out.println("Добро пожаловать в шоу-рум Skoda");
        System.out.println("Для просмотра возможных действий введите /help");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Введите команду: ");
            switch (scanner.nextLine()) {
                case "/help" -> {
                    System.out.println("\nВозможные действия:");
                    System.out.println("/help - список возможных действий");
                    System.out.println("/cars - список доступных моделей");
                    System.out.println("/cart - показать текущую корзину");
                    System.out.println("/add - добавить в корзину товар");
                    System.out.println("/remove - убрать из корзины товар");
                    System.out.println("/newcart - создать новую корзину");
                    System.out.println("/exit - покинуть шоурум");
                }
                case "/cars" -> productRepository.showAvailableProducts();
                case "/cart" -> cart.showProductsInCart();
                case "/add" -> {
                    productRepository.showAvailableProducts();
                    while (true) {
                        System.out.print("\nВведите идентификатор товара, чтобы добавить его в корзину: ");
                        Long id = (long) scanner.nextInt();
                        if (productRepository.getProduct(id) != null) {
                            cart.addProduct(id);
                            break;
                        }
                    }
                }
                case "/remove" -> {
                    cart.showProductsInCart();
                    while (true) {
                        System.out.print("\nВведите идентификатор товара, чтобы убрать его из корзины: ");
                        Long id = (long) scanner.nextInt();
                        if (cart.removeProduct(id) != null) {
                            break;
                        }
                    }
                }
                case "/newcart" -> {
                    cart = context.getBean(Cart.class);
                    System.out.println("\nВы создали новую корзину");
                }
                case "/exit" -> {
                    System.out.println("\nДо свидания. Будем рады видеть вас снова");
                    return;
                }
            }
        }
    }
}
