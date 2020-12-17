package be.howest.ti.mars.webserver;

import be.howest.ti.mars.logic.domain.CropTypes;
import io.vertx.ext.web.RoutingContext;

import java.util.List;

public interface MarsOpenApiBridgeInterface {
    Object getSeeds(RoutingContext ctx);

    Object getPlants(RoutingContext ctx);

    Object getBasket(RoutingContext ctx);

    Object getFavorites(RoutingContext ctx);

    Boolean createUser(RoutingContext ctx);

    List<CropTypes> getCropTypes(RoutingContext ctx);

}
