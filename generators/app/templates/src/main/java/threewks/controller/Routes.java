package threewks.controller;

import com.threewks.thundr.route.Router;
import com.threewks.thundr.route.staticResource.StaticResource;

public class Routes {
	public static void addRoutes(Router router) {
		router.get("/**", Controller.class, "index");
	}
}
