package be.howest.ti.mars.webserver;

import io.vertx.ext.web.RoutingContext;

public interface MarsOpenApiBridgeInterface {
    Object getUsers(RoutingContext ctx);

    Object getSeeds(RoutingContext ctx);

    Object getPlants(RoutingContext ctx);

    Object getHistory(RoutingContext ctx);

    Object getSubscriptions(RoutingContext ctx);

    Object getTools(RoutingContext ctx);

    Object getBasket(RoutingContext ctx);

    Object getLocation(RoutingContext ctx);

    Object getFavorites(RoutingContext ctx);

    Boolean buyProduct(RoutingContext ctx);

    Boolean setSubscription(RoutingContext ctx);

    Boolean editUser(RoutingContext ctx);

    Boolean createUser(RoutingContext ctx);

    Object getCropTypes(RoutingContext ctx);


}
