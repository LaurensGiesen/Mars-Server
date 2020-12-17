package be.howest.ti.mars.logic.controller;

import be.howest.ti.mars.logic.data.MarsRepository;
import be.howest.ti.mars.logic.domain.*;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MarsControllerTest {


    MarsController marsController;
    @BeforeAll
    void setup() throws SQLException {
        MarsRepository.configure("jdbc:h2:~/mars-db", "group-14", "t3sfe1k3nUe", 9000);
    }

    @BeforeEach
    void init() throws IOException, SQLException {
        createDatabase();
        marsController = new MarsController();
    }

    private void createDatabase() throws IOException, SQLException {
        executeScript("src/test/recourses/databaseTest.sql");
    }

    private void executeScript(String fileName) throws IOException, SQLException {
        String createDbSql = readFile(fileName);
        try (
                Connection con = MarsRepository.getConnection();
                PreparedStatement stmt = con.prepareStatement(createDbSql)
        ) {
            stmt.executeUpdate();
        }
    }

    private String readFile(String fileName) throws IOException {
        Path file = Path.of(fileName);
        return Files.readString(file);
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
        User user = new User(2,"Alice", "Foo", "Alice@Foo.com", LocalDate.of(2000,1,1),new Subscription(SubscriptionType.PREMIUM), new Address("Foo", 1,"Test"));
        marsController.createUser("Alice", "Foo", "Alice@Foo.com", LocalDate.of(2000,1,1),new Subscription(SubscriptionType.PREMIUM), 1 );
        assertEquals(user, marsController.getUserById(2));
    }

    @Test
    void getPlantsTest(){
        List<Product> products = new LinkedList<>();
        Product product = new Product(1,"Apple",2,marsController.getUserById(1),LocalDate.of(2052, 8, 20),5, "",ProductType.PLANT);
        products.add(product);
        products.add(new Product(1,"Carrot",2,marsController.getUserById(1),LocalDate.of(2052, 5, 11),15, "",ProductType.PLANT));
        products.add(new Product(1,"Banana",2,marsController.getUserById(1),LocalDate.of(2052, 7, 9),8, "",ProductType.PLANT));
        products.add(new Product(1,"Grapes",2,marsController.getUserById(1),LocalDate.of(2052, 7, 6),12, "",ProductType.PLANT));
        products.add(new Product(1,"Strawberry",2,marsController.getUserById(1),LocalDate.of(2052, 2, 6),12, "",ProductType.PLANT));
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
        Product product = new Product(1, "Foo", 5.0, marsController.getUserById(1), LocalDate.of(2050, 1,1), 4, "data:image/png;base64,image", ProductType.PLANT);
        boolean check = marsController.createProduct("Foo", 5.0, 1, "01-01-2020", 4,"data:image/png;base64,imag", "plant");
        assertTrue(check);
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
        Product product = new Product(1,"Apple",2,null,null,-1, null,ProductType.PLANT);
        marsController.addProductToFavorite(1,1,"plant", 3);
        assertEquals(1, marsController.getFavorites(1).size());
        assertEquals(product, marsController.getFavorites(1).get(0));
    }

    @Test
    void getBasketOfUserTest(){
        Product product = new Product(1,"Apple",2,null,null,-1, null,ProductType.PLANT);
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
        User sys = new User(1,"Kurt", "Sys", "Kurt.Sys@hotmail.com", LocalDate.of(2030, 5, 20), new Subscription(SubscriptionType.PREMIUM),new Address(1, "The Moon", 404, "1337"));
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
