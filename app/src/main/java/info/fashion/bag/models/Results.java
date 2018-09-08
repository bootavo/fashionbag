package info.fashion.bag.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Results {

    @Expose
    @SerializedName("productos")
    private List<Product> products;

    @Expose
    @SerializedName("promociones")
    private List<Promotion> promotions;

    @Expose
    @SerializedName("shopping_car")
    private List<ShoppingCar> shopping_car;

    @Expose
    @SerializedName("shopping_car_items")
    private List<ShoppingCarItem> shopping_car_items;

    @Expose
    @SerializedName("user")
    private User user;

    @Expose
    @SerializedName("producto")
    private Product product;

    @Expose
    @SerializedName("promocion")
    private Promotion promotion;

    @Expose
    @SerializedName("orders")
    private List<Order> orders;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ShoppingCar> getShopping_car() {
        return shopping_car;
    }

    public void setShopping_car(List<ShoppingCar> shopping_car) {
        this.shopping_car = shopping_car;
    }

    public List<ShoppingCarItem> getShopping_car_items() {
        return shopping_car_items;
    }

    public void setShopping_car_items(List<ShoppingCarItem> shopping_car_items) {
        this.shopping_car_items = shopping_car_items;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    @Override
    public String toString() {
        return "Results{" +
                "products=" + products +
                ", promotions=" + promotions +
                ", shopping_car=" + shopping_car +
                ", shopping_car_items=" + shopping_car_items +
                ", user=" + user +
                ", product=" + product +
                ", promotion=" + promotion +
                ", orders=" + orders +
                '}';
    }
}
