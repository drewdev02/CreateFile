package Entity;

import com.google.gson.Gson;
import com.thedeanda.lorem.LoremIpsum;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

        public Element(long id,
                       String name,
                       String model,
                       String description,
                       String img_url,
                       double price
        ) {
            this.id = id;
            this.name = name;
            this.model = model;
            this.description = description;
            this.img_url = img_url;
            this.price = price;
        }


    }

    public static @NotNull String toJson(@NotNull Element element) {
        var gson = new Gson();
        return "{\n" +
                gson.toJson("id") + " : " + gson.toJson(element.id) + ",\n" +
                gson.toJson("name") + " : " + gson.toJson(element.name) + ",\n" +
                gson.toJson("model") + " : " + gson.toJson(element.model) + ",\n" +
                gson.toJson("description") + " : " + gson.toJson(element.description) + ",\n" +
                gson.toJson("img_url") + " : " + gson.toJson(element.img_url) + ",\n" +
                gson.toJson("price") + " : " + gson.toJson(element.price) +
                "\n}";
    }

    /*public static String toJsonString(@NotNull Object object) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Field[] fields = object.getClass().getDeclaredFields();
        JsonObject jsonObject = new JsonObject();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                jsonObject.addProperty(field.getName(), gson.toJson(field.get(object)));
            } catch (IllegalAccessException e) {
                // handle exception
            }
        }
        return gson.toJson(jsonObject);
    }*/


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

    public static void CreateFile(
            String path,
            @NotNull String name,
            @NotNull List<Element> elements

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
        var path = "../../";
        var name = "elements";
        var elements = new ArrayList();
        createElement(elements, 50);
        File.CreateFile(path, name, elements);


    }

}
