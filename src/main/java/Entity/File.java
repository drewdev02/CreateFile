package Entity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.thedeanda.lorem.LoremIpsum;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class File {
    @Getter
    @Setter
    static class Element {
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

    public static @NotNull String toJson(@NotNull Object object) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject jsonObject = new JsonObject();
        for (Field field : object.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                jsonObject.add(field.getName(), gson.toJsonTree(field.get(object)));
            } catch (IllegalAccessException err) {
                System.out.println(err);
            }
        }
        return gson.toJson(jsonObject);
    }


    public static @NotNull Element randowElement() {
        var lorem = LoremIpsum.getInstance();
        var random = new Random();

        var id = random.nextLong() % 1000;
        var name = lorem.getName();
        var model = lorem.getName();
        var description = lorem.getWords(100);
        var url = lorem.getUrl();
        var price = (random.nextDouble() % 1000) * 100;


        return new Element(id, name, model, description, url, price);
    }

    public static void createElement(List<Element> elements, int limitCant) {
        for (var i = 0; i < limitCant; i++) {
            elements.add(randowElement());
        }
    }

    public static void CreateFile(@NotNull String name, @NotNull List<Element> elements

    ) {
        try {
            var fw = new FileWriter(name.concat(".txt"), false);
            var bw = new BufferedWriter(fw);
            var file = new PrintWriter(bw);
            var i = elements.size() - 1;
            for (var element : elements) {
                if (elements.indexOf(element) == i) {
                    file.println(File.toJson(element));
                } else {
                    file.println(File.toJson(element) + ",");
                }

            }
            file.close();
        } catch (IOException err) {
            System.out.println(err);
        }
    }

    public static void main(String[] args) {
        var name = "elements";
        var elements = new ArrayList();
        createElement(elements, 2);
        File.CreateFile(name, elements);


    }

}
