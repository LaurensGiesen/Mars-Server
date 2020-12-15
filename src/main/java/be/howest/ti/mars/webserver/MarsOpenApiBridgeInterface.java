package be.howest.ti.mars.webserver;

import be.howest.ti.mars.logic.domain.CropTypes;
import io.vertx.ext.web.RoutingContext;

import java.util.List;

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

    List<CropTypes> getCropTypes(RoutingContext ctx);


}
