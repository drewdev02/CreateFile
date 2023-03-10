package Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class Element {
    private long id;
    private String name;
    private String model;
    private String description;
    private String img_url;
    private double price;

    public Element() {

    }

    public Element(long id, String name, String model, String description, String img_url, double price) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.description = description;
        this.img_url = img_url;
        this.price = price;
    }


}
