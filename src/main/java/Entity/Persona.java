package Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Persona {
    private long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String address;
    private String city;
    private String country;
    private String phone;
    private String img_url;

    public Persona() {
    }

    public Persona(long id, String name, String surname, String email, String password, String address, String city, String country, String phone, String img_url) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.address = address;
        this.city = city;
        this.country = country;
        this.phone = phone;
        this.img_url = img_url;
    }
}
