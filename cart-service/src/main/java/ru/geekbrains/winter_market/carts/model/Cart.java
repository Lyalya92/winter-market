package ru.geekbrains.winter_market.carts.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.winter_market.api.ProductDto;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class Cart {

    private List<CartItem> products;
    private BigDecimal totalPrice;

    public Cart(List<CartItem> productList) {
        this.products = productList;
    }

    public void add(ProductDto productDto) {
       for (CartItem item : products) {
           if (item.getProductId().equals(productDto.getId())) {
                 item.changeQuantity(1);
                 recalculate();
                 return;
           }
       }
        CartItem newItem = new CartItem(productDto.getId(), productDto.getTitle(), 1,
                                                productDto.getPrice(), productDto.getPrice());
        products.add(newItem);
        recalculate();
    }

    public void add (CartItem cartItem) {
        products.add(cartItem);
        recalculate();
    }

    public void clear () {
        products.clear();
        totalPrice = BigDecimal.ZERO;
    }

    private  void recalculate () {
        totalPrice = BigDecimal.ZERO;
        products.forEach(p -> totalPrice = totalPrice.add(p.getPriceTotal()));
    }

    public void delete(Long id) {
        products.removeIf(p -> p.getProductId().equals(id));
        recalculate();
    }

    public void changeProductQuantity(Long id, Integer delta) {
        for (CartItem p: products) {
            if (p.getProductId().equals(id)) {
                p.changeQuantity(delta);
                if (p.getQuantity() <= 0) {
                      products.remove(p);
                }
                recalculate();
                return;
            }
        }
    }
}
