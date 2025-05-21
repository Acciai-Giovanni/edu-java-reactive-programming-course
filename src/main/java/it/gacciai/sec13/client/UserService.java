package it.gacciai.sec13.client;

import reactor.util.context.Context;

import java.util.Map;
import java.util.function.Function;

public class UserService {

    public static final Map<String,String> USER_CATEGORY = Map.of(
            "sam", "standard",
            "mike", "prime"
    );

    static Function<Context, Context> userCategoryContext() {
        return context -> context.getOrEmpty("user")
                    .filter(USER_CATEGORY::containsKey)
                    .map(USER_CATEGORY::get)
                    .map(category -> context.put("category", category))
                    .orElse(Context.empty());
    }

}
