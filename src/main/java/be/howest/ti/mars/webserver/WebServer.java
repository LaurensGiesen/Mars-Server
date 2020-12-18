package be.howest.ti.mars.webserver;

import be.howest.ti.mars.logic.controller.MarsController;
import be.howest.ti.mars.logic.data.MarsRepository;
import be.howest.ti.mars.logic.exceptions.ConfigException;
import be.howest.ti.mars.logic.unit.Config;
import io.vertx.config.ConfigRetriever;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.api.contract.openapi3.OpenAPI3RouterFactory;
import io.vertx.ext.web.api.validation.ValidationException;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.FaviconHandler;
import io.vertx.ext.web.handler.LoggerFormat;
import io.vertx.ext.web.handler.LoggerHandler;
import org.apache.commons.lang3.StringUtils;
import java.sql.SQLException;
import java.util.Base64;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;


public class WebServer extends AbstractVerticle {
    private static final Logger LOGGER = Logger.getLogger(WebServer.class.getName());
    private static final Integer DB_WEB_CONSOLE_FALLBACK = 9000;
    private static final String OPEN_API_SPEC = "openapi-group-14.yaml";
    private final MarsOpenApiBridge bridge;

    public WebServer(MarsOpenApiBridge bridge) {
        this.bridge = bridge;
    }

    public WebServer() {
        this(new MarsOpenApiBridge());
    }

    @Override
    public void start(Promise<Void> promise) {
        ConfigRetriever.create(vertx).getConfig(ar -> {
            if (ar.failed()) {
                LOGGER.warning("Config not available");
            } else {
                JsonObject properties = ar.result();
                JsonObject dbProperties = properties.getJsonObject("db");
                configureDatabase(dbProperties);
                int port = properties.getJsonObject("http").getInteger("port");
                LOGGER.info(String.format("Starting web server on port %s ", port));
                configureOpenApiServer(promise, OPEN_API_SPEC, port);

                MarsController marsController = new MarsController();

                String prefix = "data:image/png;base64,";
                String type = "plant";

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
        });
    }

    @Override
    public void stop() {
        MarsRepository.getInstance().cleanUp();
    }

    private void configureDatabase(JsonObject dbProps) {
        try {
            MarsRepository.configure(dbProps.getString("url"),
                    dbProps.getString("username"),
                    dbProps.getString("password"),
                    dbProps.getInteger("webconsole.port", DB_WEB_CONSOLE_FALLBACK));
            LOGGER.info("Database webconsole started on port: " + dbProps.getInteger("webconsole.port"));
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE,"DB web console is unavailable", ex);
        }
        try {
            createDatabase();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Failed To Load Database");
            throw new ConfigException(ex.getMessage());
        }
    }

    private void createDatabase() throws SQLException {
        Config.getInstance().executeScript("databaseStructure.sql");
        Config.getInstance().executeScript("populateDatabase.sql");
    }


    private void configureOpenApiServer(Promise<Void> promise, String apiSpecification, int port) {
        LOGGER.info(() -> String.format("Starting webserver with spec %s", apiSpecification));
        OpenAPI3RouterFactory.create(vertx,
                apiSpecification,
                ar -> {
                    if (ar.succeeded()) {
                        LOGGER.info("Successfully loaded API specification");
                        vertx.createHttpServer()
                                .requestHandler(createRequestHandler(ar.result()))
                                .listen(port, x -> listen(promise, x));
                    } else {
                        LOGGER.log(Level.SEVERE," Failed to load API specification", ar.cause());
                        LOGGER.info("Shutting down");
                        vertx.close();
                    }
                });
    }

    private void listen(Promise<Void> promise, AsyncResult<HttpServer> result) {
        if (result.succeeded()) {
            LOGGER.info(() -> String.format("Listening at port %d", result.result().actualPort()));
            promise.complete();
        } else {
            LOGGER.log(Level.SEVERE, "Web server failed to start listening", result.cause());
            LOGGER.info("Shutting down the crucial martian application!");
            vertx.close();
        }
    }

    private Router createRequestHandler(OpenAPI3RouterFactory factory) {
        // Log all incoming requests
        factory.addGlobalHandler(LoggerHandler.create(LoggerFormat.TINY));

        // all expected responses are JSON
        factory.addGlobalHandler(ctx -> {
            ctx.response().putHeader("Content-Type", "application/json");
            ctx.next();
        });

        // Allow Cross-Origin Resource Sharing (CORS) from all clients
        factory.addGlobalHandler(createCorsHandler());

        // Add all route handlers
        addRoutes(factory);

        // Build the router
        Router router = factory.getRouter();
        router.route("/favicon.ico").handler(FaviconHandler.create());

        // Install general error handlers
        installGeneralErrorHandlers(router);

        return router;
    }

    private void addRoutes(OpenAPI3RouterFactory factory) {
        addRouteWithCtxFunction(factory, "getMessage", bridge::getMessage);
        addRouteWithCtxFunction(factory, "createUser", bridge::createUser);
        addRouteWithCtxFunction(factory, "addProduct", bridge::addProduct);
        addRouteWithCtxFunction(factory, "getSeeds", bridge::getSeeds);
        addRouteWithCtxFunction(factory, "getPlants", bridge::getPlants);
        addRouteWithCtxFunction(factory, "addProductToFavorite", bridge::addProductToFavorite);
        addRouteWithCtxFunction(factory, "getFavorites", bridge::getFavorites);
        addRouteWithCtxFunction(factory, "addProductToBasket", bridge::addProductToBasket);
        addRouteWithCtxFunction(factory, "getBasket", bridge::getBasket);
        addRouteWithCtxFunction(factory, "removeProductFromFavorite", bridge::removeProductFromFavorite);
        addRouteWithCtxFunction(factory, "removeProductFromBasket", bridge::removeProductFromBasket);
        addRouteWithCtxFunction(factory, "removeProduct", bridge::removeProduct);
        addRouteWithCtxFunction(factory, "getLocations", bridge::getCropTypes);
        addRouteWithCtxFunction(factory, "getCropsWhereNameIsLike", bridge::getCropsWhereNameIsLike);
        addRouteWithCtxFunction(factory, "getCropByLocation", bridge::getCropByLocation);
        addRouteWithCtxFunction(factory, "updateUser", bridge::updateUser);
        addRouteWithCtxFunction(factory, "getUser", bridge::getUser);
        addRouteWithCtxFunction(factory, "updateSubscription", bridge::updateSubscription);
        addRouteWithCtxFunction(factory, "getCrops", bridge::getCrops);
    }

    private void addRouteWithCtxFunction(OpenAPI3RouterFactory factory, String operationId,
                                            Function<RoutingContext, Object> bridgeFunction) {
        factory.addHandlerByOperationId(operationId,
                ctx -> handleResult(bridgeFunction.apply(ctx), ctx));
    }

    private void handleResult(Object result, RoutingContext ctx) {
        ctx.response().setStatusCode(200).end(Json.encodePrettily(result));
    }

    private void installGeneralErrorHandlers(Router router) {
        router.errorHandler(400, this::onBadRequest)
            .errorHandler(401, this::onUnAuthorised)
            .errorHandler(403, this::onForbidden)
            .errorHandler(404, this::onNotFound)
            .errorHandler(409, this::onConflict)
            .errorHandler(500, this::onInternalServerError);

        router.route().handler(ctx -> ctx.fail(404, new RuntimeException()));
    }


    private CorsHandler createCorsHandler() {
        return CorsHandler.create(".*.")
                .allowedHeader("Access-Control-Allow-Headers: Authorization")
                .allowedHeader("x-requested-with")
                .allowedHeader("Access-Control-Allow-Credentials")
                .allowedHeader("Access-Control-Allow-Origin")
                .allowedHeader("origin")
                .allowedHeader("Content-Type")
                .allowedHeader("accept")
                .allowCredentials(true)
                .allowedMethod(HttpMethod.GET)
                .allowedMethod(HttpMethod.POST)
                .allowedMethod(HttpMethod.DELETE)
                .allowedMethod(HttpMethod.PUT)
                .allowedMethod(HttpMethod.OPTIONS);
    }

    private void onBadRequest(RoutingContext ctx) {
        ValidationException ex = (ValidationException) ctx.failure();
        String cause = String.format("%s in %s", ex.getMessage(), ex.parameterName());
        LOGGER.info(() -> String.format("onBadRequest %s: %s", ctx.request().absoluteURI(), cause));
        replyWithFailure(ctx, 400, "Bad Request", cause);
    }

    private void onNotFound(RoutingContext ctx) {
        LOGGER.info(() -> String.format("onNotFound at %s", ctx.request().absoluteURI()));
        replyWithFailure(ctx, 404, "Not Found", ctx.failure().getMessage());
    }

    private void onUnAuthorised(RoutingContext ctx) {
        replyWithFailure(ctx, 401, "Unauthorised", null);
    }

    private void onForbidden(RoutingContext ctx) {
        replyWithFailure(ctx, 403, "Forbidden", null);
    }

    private void onConflict(RoutingContext ctx) {
        LOGGER.info(() -> String.format("onConflict at %s", ctx.request().absoluteURI()));
        replyWithFailure(ctx, 409, "Conflict", ctx.failure().getMessage());

    }

    private void onInternalServerError(RoutingContext ctx) {
        LOGGER.log(Level.SEVERE, () -> String.format("onInternalServerError at %s", ctx.request().absoluteURI()));
        replyWithFailure(ctx, 500, "Internal Server Error", ctx.failure().getMessage());
    }

    private void replyWithFailure(RoutingContext ctx, int statusCode, String message, String cause) {
        JsonObject reply = context2json(ctx).put("status", statusCode).put("message", message);

        if (cause != null) {
            reply.put("cause", cause);
        }

        ctx.response().setStatusCode(statusCode).end(Json.encodePrettily(reply));
    }

    private JsonObject context2json(RoutingContext ctx) {
        JsonObject result = new JsonObject()
                .put("method", ctx.request().method())
                .put("url", ctx.request().absoluteURI());

        if (StringUtils.isNotEmpty(ctx.getBodyAsString()))
            result.put("body", ctx.getBodyAsString());

        return result;
    }
}