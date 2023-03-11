package Entity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.thedeanda.lorem.LoremIpsum;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class File {
    /**
     * Converts an object to a JSON formatted string using the Gson library.
     *
     * @param object the object to convert to JSON format
     * @return the JSON formatted string representation of the object
     * @throws NullPointerException if the input object is null
     * @author https://github.com/drewdev02
     */
    public static @NotNull String toJson(@NotNull Object object) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject jsonObject = new JsonObject();
        Arrays.stream(object.getClass().getDeclaredFields())
                .peek(field -> field.setAccessible(true))
                .forEach(field -> {
                    try {
                        jsonObject.add(field.getName(), gson.toJsonTree(field.get(object)));
                    } catch (IllegalAccessException err) {
                        System.out.println(err);
                    }
                });
        return gson.toJson(jsonObject);
    }


    /**
     * Generates a random instance of a class using reflection and assigns random values to its fields.
     *
     * @param clazz the class of the object to be instantiated
     * @return a new instance of the given class with randomized field values
     * @throws IllegalAccessException if the class or its nullary constructor is not accessible
     * @throws InstantiationException if the class is an interface, an array class, an abstract class, or lacks a nullary constructor
     * @author https://github.com/drewdev02
     */
    public static <T> @NotNull T randomElement(@NotNull Class<T> clazz) throws IllegalAccessException, InstantiationException {
        var lorem = LoremIpsum.getInstance();
        var random = new Random();

        var obj = clazz.newInstance();
        var fields = clazz.getDeclaredFields();
        Arrays.stream(fields)
                .peek(field -> field.setAccessible(true))
                .forEach(field -> {
                    var type = field.getType();
                    try {
                        if (type.equals(int.class) || type.equals(Integer.class)) {
                            field.setInt(obj, random.nextInt(1000));
                        } else if (type.equals(long.class) || type.equals(Long.class)) {
                            field.setLong(obj, random.nextLong() % 1000);
                        } else if (type.equals(double.class) || type.equals(Double.class)) {
                            field.setDouble(obj, (random.nextDouble() % 1000) * 100);
                        } else if (type.equals(String.class)) {
                            var fieldName = field.getName();
                            if (fieldName.equalsIgnoreCase("name") || fieldName.equalsIgnoreCase("model")) {
                                field.set(obj, lorem.getName());
                            } else if (fieldName.equalsIgnoreCase("description")) {
                                field.set(obj, lorem.getWords(100));
                            } else if (fieldName.equalsIgnoreCase("img_url")) {
                                field.set(obj, lorem.getUrl());
                            } else {
                                field.set(obj, lorem.getWords(1));
                            }
                        } else {
                            // handle other types as needed
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
        return obj;
    }

    /**
     * Generates a random element for an object, modifying its fields with random values based on their data type.
     *
     * @param object the object to modify with random values.
     * @return the modified object with random values.
     * @throws IllegalAccessException if the access to the object's fields is not allowed.
     * @author https://github.com/drewdev02
     */
    @Contract("_ -> param1")
    public static @NotNull Object randomElement(@NotNull Object object) throws IllegalAccessException {
        var lorem = LoremIpsum.getInstance();
        var random = new Random();

        Arrays.stream(object.getClass().getDeclaredFields())
                .peek(field -> field.setAccessible(true))
                .forEach(field -> {
                    try {
                        var type = field.getType();
                        if (type.equals(int.class) || type.equals(Integer.class)) {
                            field.set(object, random.nextInt(1000));
                        } else if (type.equals(long.class) || type.equals(Long.class)) {
                            field.set(object, random.nextLong() % 1000);
                        } else if (type.equals(double.class) || type.equals(Double.class)) {
                            field.set(object, (random.nextDouble() % 1000) * 100);
                        } else if (type.equals(String.class)) {
                            var fieldName = field.getName();
                            if (fieldName.equalsIgnoreCase("name") || fieldName.equalsIgnoreCase("model")) {
                                field.set(object, lorem.getName());
                            } else if (fieldName.equalsIgnoreCase("description")) {
                                field.set(object, lorem.getWords(100));
                            } else if (fieldName.equalsIgnoreCase("img_url")) {
                                field.set(object, lorem.getUrl());
                            } else {
                                field.set(object, lorem.getWords(1));
                            }
                        } else {
                            // handle other types as needed
                        }
                    } catch (IllegalAccessException err) {
                        System.out.println(err);
                    }
                });
        return object;
    }


    /**
     * Generates a specified number of random objects of the given class and adds them to a list.
     *
     * @param limitCant The number of objects to generate.
     * @param clazz     The class of the objects to be generated.
     * @throws IllegalAccessException If the class or its nullary constructor is not accessible.
     * @throws InstantiationException If an object cannot be instantiated due to an error in the creation process.
     * @author https://github.com/drewdev02
     */
    public static void createElement(List<Object> objects, int limitCant, Class<?> clazz) throws IllegalAccessException, InstantiationException {
        for (var i = 0; i < limitCant; i++) {
            objects.add(randomElement(clazz));
        }
    }

    /**
     * Creates a text file with the given name and writes the JSON representation of the objects in the given list.
     * Each object is written in a new line, separated by commas except for the last one.
     *
     * @param name    the name of the file to create (without the ".txt" extension)
     * @param objects the list of objects to write in the file
     * @author https://github.com/drewdev02
     */
    public static void createFile(@NotNull String name, @NotNull List<Object> objects) {
        try (var fw = new FileWriter(name.concat(".txt"), false);
             var bw = new BufferedWriter(fw);
             var file = new PrintWriter(bw)) {

            IntStream.range(0, objects.size())
                    .mapToObj(i -> {
                        try {
                            return File.toJson(objects.get(i)) + (i == objects.size() - 1 ? "" : ",");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .forEach(file::println);

        } catch (IOException e) {
            System.out.println(e);
        }
    }


    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        var name = "elements";
        var elements = new ArrayList();
        createElement(elements, 7, Element.class);
        File.createFile(name, elements);

        var name1 = "persons";
        var persons = new ArrayList();
        createElement(persons, 7, Persona.class);
        File.createFile(name1, persons);


    }

}
