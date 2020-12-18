package be.howest.ti.mars.logic.controller;

import be.howest.ti.mars.logic.data.MarsRepository;
import be.howest.ti.mars.logic.domain.*;
import be.howest.ti.mars.logic.exceptions.ProductException;
import be.howest.ti.mars.logic.unit.Config;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MarsControllerTest {


    String img;

    MarsController marsController;
    @BeforeAll
    void setup() throws SQLException {
        MarsRepository.configure("jdbc:h2:~/mars-db", "group-14", "t3sfe1k3nUe", 9000);
    }

    @BeforeEach
    void init() throws IOException, SQLException {
        createDatabase();
        marsController = new MarsController();
        String prefix = "data:image/png;base64,";
        String type = "plant";
        img = prefix + Base64.getEncoder().encodeToString(Config.getInstance().getFile("images/1.png"));


        String img = prefix + Base64.getEncoder().encodeToString(Config.getInstance().getFile("images/1.png"));
        marsController.createProduct("Apple", 2.0, 1, "20-08-2052",5, img, type);

        img = prefix + Base64.getEncoder().encodeToString(Config.getInstance().getFile("images/2.png"));
        marsController.createProduct("Carrot", 3.0, 1, "11-05-2052",15, img, type);

        img = prefix + Base64.getEncoder().encodeToString(Config.getInstance().getFile("images/3.png"));
        marsController.createProduct("Banana", 1.0, 1, "09-07-2052",8, img, type);

        img = prefix + Base64.getEncoder().encodeToString(Config.getInstance().getFile("images/4.png"));
        marsController.createProduct("Grapes", 3.0, 1, "05-06-2052",12, img, type);

        img = prefix + Base64.getEncoder().encodeToString(Config.getInstance().getFile("images/5.png"));
        marsController.createProduct("Strawberry", 3.0, 1, "06-02-2052",12, img, type);

    }

    private void createDatabase() throws IOException, SQLException {
        Config.getInstance().executeScript("databaseTest.sql");
    }

    @Test
    void getMessageReturnsAWelcomeMessage() {
        // Arrange
        MarsController sut = new MarsController();

        // Act
        String message = sut.getMessage();

        //Assert
        assertTrue(StringUtils.isNoneBlank(message));
    }

    @Test
    void registerTest(){
        User user = new User(2,"Alice", "Foo", "Alice@Foo.com", LocalDate.of(2000,1,1),new Subscription(SubscriptionType.PREMIUM, 100), new Address("Foo", 1,"Test"));
        List<Product> products = new LinkedList<>();
        products.add(new Product(1,"Apple",2,marsController.getUserById(1),LocalDate.of(2052, 8, 20),5, "",ProductType.PLANT));
        products.add(new Product(1,"Apple2",2,marsController.getUserById(1),LocalDate.of(2052, 8, 20),5, "",ProductType.PLANT));
        products.add(new Product(1,"Apple3",2,marsController.getUserById(1),LocalDate.of(2052, 8, 20),5, "",ProductType.PLANT));
        marsController.createFavorites(new Product(1,"Apple",2,marsController.getUserById(1),LocalDate.of(2052, 8, 20),5, "",ProductType.PLANT), new Product(1,"Apple2",2,marsController.getUserById(1),LocalDate.of(2052, 8, 20),5, "",ProductType.PLANT), new Product(1,"Apple3",2,marsController.getUserById(1),LocalDate.of(2052, 8, 20),5, "",ProductType.PLANT));
        marsController.addFavoriteToUser(1, products, 1);
        marsController.createUser("Alice", "Foo", "Alice@Foo.com", LocalDate.of(2000,1,1),SubscriptionType.PREMIUM, 1 );
        assertEquals(2,marsController.addAddress("Foo", 1, "Foo2"));
        assertEquals(user, marsController.getUserById(2));
    }

    @Test
    void getSeedByName(){
        Product product = new Product(1,"Apple",2,marsController.getUserById(1),LocalDate.of(2052, 8, 20),5, img,ProductType.SEED);
        assertEquals(product, marsController.getSeedByName("Apple"));
    }

    @Test
    void getPlantsTest(){
        List<Product> products = new LinkedList<>();
        Product product = new Product(1,"Apple",2,marsController.getUserById(1),LocalDate.of(2052, 8, 20),5, img,ProductType.PLANT);
        products.add(product);
        products.add(new Product(1,"Carrot",2,marsController.getUserById(1),LocalDate.of(2052, 5, 11),15, img,ProductType.PLANT));
        products.add(new Product(1,"Banana",2,marsController.getUserById(1),LocalDate.of(2052, 7, 9),8, img,ProductType.PLANT));
        products.add(new Product(1,"Grapes",2,marsController.getUserById(1),LocalDate.of(2052, 7, 6),12, img,ProductType.PLANT));
        products.add(new Product(1,"Strawberry",2,marsController.getUserById(1),LocalDate.of(2052, 2, 6),12, img,ProductType.PLANT));
        List<Product> products1 = marsController.getProduct(ProductType.PLANT);
        assertEquals(products, products1);
    }

    @Test
    void getSeedsTest(){
        List<Product> products = new LinkedList<>();
        Product product = new Product(1,"Apple",2,null,null,-1, null,ProductType.SEED);
        products.add(product);
        products.add(new Product(2,"Apricot",3.5,null,null,-1, null,ProductType.SEED));
        products.add(new Product(3,"Banana",1,null,null,-1, null,ProductType.SEED));
        products.add(new Product(4,"Asparagus",5,null,null,-1, null,ProductType.SEED));
        products.add(new Product(5,"Broccoli",4.5,null,null,-1, null,ProductType.SEED));
        products.add(new Product(6,"Tomato",3,null,null,-1, null,ProductType.SEED));
        List<Product> products1 = marsController.getProduct(ProductType.SEED);
        assertEquals(products, products1);
    }

    @Test
    void addProductTest(){
        Product product = new Product(1, "Foo", 5.0, marsController.getUserById(1), LocalDate.of(2050, 1,1), 4, img, ProductType.PLANT);
        boolean check = marsController.createProduct("Foo", 5.0, 1, "01-01-2020", 4,"data:image/png;base64,imag", "plant");
        assertTrue(check);
        assertThrows(ProductException.class, () -> marsController.createProduct("Foo", 5.0, 1, "01-01-2020", 4,"data:image/png;base64,imag", "FOO"));
        assertTrue(marsController.getProduct(ProductType.PLANT).contains(product) );
    }

    @Test
    void removeProductTest(){
        assertEquals(5, marsController.getProduct(ProductType.PLANT).size());
        marsController.removeProduct(1, 5, "plant");
        assertEquals(4, marsController.getProduct(ProductType.PLANT).size());
    }

    @Test
    void addProductToFavoriteTest(){

        marsController.addProductToFavorite(1,1,"plant", 3);
        assertEquals(1, marsController.getFavorites(1).size());
    }

    @Test
    void addProductToBasketTest(){
        marsController.addProductToBasket(1,1,"plant", 3);
        assertEquals(1, marsController.getBasket(1).size());
    }

    @Test
    void getFavoritesOfUserTest(){
        Product product = new Product(1,"Apple",2,null,null,-1, img,ProductType.PLANT);
        marsController.addProductToFavorite(1,1,"plant", 3);
        assertEquals(1, marsController.getFavorites(1).size());
        assertEquals(product, marsController.getFavorites(1).get(0));
    }

    @Test
    void getBasketOfUserTest(){
        Product product = new Product(1,"Apple",2,null,null,-1, img,ProductType.PLANT);
        marsController.addProductToBasket(1,1,"plant", 3);
        assertEquals(1, marsController.getBasket(1).size());
        assertEquals(product, marsController.getBasket(1).get(0));
    }

    @Test
    void removeProductFromFavoritesTest(){
        marsController.addProductToFavorite(1,1,"plant", 3);
        assertEquals(1, marsController.getFavorites(1).size());
        marsController.removeProductFromFavorite(1, 1, "plant", 3);
        assertEquals(0,marsController.getFavorites(1).size());
    }

    @Test
    void removeProductFromBasketTest(){
        marsController.addProductToBasket(1,1,"plant", 3);
        assertEquals(1, marsController.getBasket(1).size());
        marsController.removeProductFromBasket(1, 1, "plant", 3);
        assertEquals(0,marsController.getBasket(1).size());
    }

    @Test
    void getAllLocationTest(){
        List<CropTypes> cropTypes = new LinkedList<>();
        cropTypes.add(new CropTypes(5.659879, -9.6548321,"Tomato", "vegetable", 8));
        cropTypes.add(new CropTypes(4.203834, -1.8522288,"Asparagus", "vegetable", 9));
        List<CropTypes> cropTypes1 = marsController.getAllCrops(1);
        assertTrue(cropTypes1.containsAll(cropTypes));
        marsController.updateSubscription(1, 2);
        cropTypes1 = marsController.getAllCrops(1);
        assertFalse(cropTypes1.containsAll(cropTypes));
        marsController.updateSubscription(1, 1);
        cropTypes1 = marsController.getAllCrops(1);
        assertFalse(cropTypes1.containsAll(cropTypes));
    }

    @Test
    void getCropsWhereNameLike(){
        List<CropTypes> cropTypes = new LinkedList<>();
        cropTypes.add(new CropTypes(3.213108,-1.8567844,"Apple", "fruit", 6));
        cropTypes.add(new CropTypes(2.218816,-2.8472767,"Apple", "fruit", 5));
        assertEquals(cropTypes, marsController.getCropsWhereNameIsLike("pp", 1));
    }

    @Test
    void getCropByLocationTest() {
        List<CropTypes> cropTypes = marsController.getCropByLocation(3,-2, 1);
        CropTypes cropTypes1 = new CropTypes(2.218816, -2.8472767, "Apple", "fruit", 5);
        CropTypes cropTypes2 = new CropTypes(3.213108, -1.8567844, "Apple", "fruit", 6);
        assertTrue(cropTypes.contains(cropTypes1));
        assertTrue(cropTypes.contains(cropTypes2));
    }

    @Test
    void updateUserInfoTest(){
        Boolean check = marsController.updateUser("Alice", "Bob", "Alice@Bob.com", LocalDate.of(2000, 1,1), 1);
        marsController.updateAddress("Foo", 403, "12345", 1);
        assertTrue(check);
        assertEquals(marsController.getUserById(1).getFirstName(), "Alice");
        assertEquals(marsController.getUserById(1).getAddress().getStreet(), "Foo");
    }

    @Test
    void getUserTest(){
        User sys = new User(1,"Kurt", "Sys", "Kurt.Sys@hotmail.com", LocalDate.of(2030, 5, 20), new Subscription(SubscriptionType.PREMIUM, 100),new Address(1, "The Moon", 404, "1337"));
        User sys2 = marsController.getUserById(1);
        assertEquals(sys, sys2);
    }

    @Test
    void changeSubscriptionTest(){
        marsController.updateSubscription(1, 2);
        assertEquals(SubscriptionType.BASIC, marsController.getUserById(1).getSubscription().getType());
    }

    @Test
    void getCropTest(){
        List<Crop> crops = new LinkedList<>();
        crops.add(new Crop("Apple", "fruit"));
        crops.add(new Crop("Apricot", "fruit"));
        crops.add(new Crop("Banana", "fruit"));
        crops.add(new Crop("Asparagus", "vegetable"));
        crops.add(new Crop("Broccoli", "vegetable"));
        crops.add(new Crop("Tomato", "vegetable"));
        List<Crop> crops1 = marsController.getCropNames();
        assertEquals(crops, crops1);
    }
}
