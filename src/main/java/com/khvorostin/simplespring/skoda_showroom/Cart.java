package com.khvorostin.simplespring.skoda_showroom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class Cart {

    /**
     * Продукты в корзине
     */
    @Autowired
    private ProductRepository productRepository;

    /**
     * Счетчики товаров в корзине
     */
    private final Map<Long, Integer> productsCounter = new HashMap<>();

    /**
     * Добавление товара в корзину по идентификатору товарной позиции.
     *
     * @param id Идентификатор товарной позиции
     */
    public void addProduct(Long id) {

        // проверка на наличие товарной позиции
        if (productRepository.getProduct(id) == null) {
            return;
        }

        // если товар с заданным идентификатором отсутствует в корзине, добавляем его во внутреннее хранилище,
        // иначе - инкрементим счетчик
        if (!productsCounter.containsKey(id)) {
            productsCounter.put(id, 1);
        } else {
            productsCounter.replace(id, productsCounter.get(id) + 1);
        }

        System.out.println("\nВы добавили в корзину " + productRepository.getProduct(id).getTitle());
    }

    /**
     * Убирает из корзины один товар по идентификатору товарной позиции.
     *
     * @param id Идентификатор товарной позиции
     * @return Товар
     */
    public Product removeProduct(Long id) {

        // если в корзине нет нужной позиции, возвращаем пустоту
        if (!productsCounter.containsKey(id)) {
            return null;
        }

        // если в корзине 1 шт. указанного товара, то удаляем элемент внутреннего хранилища,
        // иначе - декрементим счетчик
        if (productsCounter.get(id) == 1) {
            productsCounter.remove(id);
        } else {
            productsCounter.replace(id, productsCounter.get(id) - 1);
        }

        System.out.println("\nВы убрали из корзины " + productRepository.getProduct(id).getTitle());

        // возвращаем товар из репозитория товаров
        return productRepository.getProduct(id);
    }

    public void showProductsInCart() {
        if (productsCounter.isEmpty()) {
            System.out.println("\nВаша корзина пуста");
        } else {
            System.out.println("\nСейчас у вас в корзине такие товары:");
            for (var entry : productsCounter.entrySet()) {
                Long id = entry.getKey();
                System.out.println(productRepository.getProduct(id).getTitle() + " (ид " + id + "), " + entry.getValue() + " шт.");
            }
        }
    }
}
