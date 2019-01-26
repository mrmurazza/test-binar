package request;

import java.util.Optional;

public class ProductRequest {
    private String name;
    private Long price;
    private String imageurl;

    private ProductRequest(String name, Long price, String imageurl){
        this.name = name;
        this.price = price;
        this.imageurl = imageurl;
    }

    public static ProductRequest initFromJsonRequest(JsonRequest request){
        String name = request.getOptionalObject("name", String.class).orElse(null);
        Long price = request.getOptionalObject("price", Long.class).orElse(null);
        String imageurl = request.getOptionalObject("imageurl", String.class).orElse(null);
        
        return new ProductRequest(name, price, imageurl);
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public Optional<Long> getPrice() {
        return Optional.ofNullable(price);
    }

    public Optional<String> getImageurl() {
        return Optional.ofNullable(imageurl);
    }
}
