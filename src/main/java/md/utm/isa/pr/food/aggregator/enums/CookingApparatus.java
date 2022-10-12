package md.utm.isa.pr.food.aggregator.enums;

public enum CookingApparatus {
    oven, stove;

    public static String getName(CookingApparatus cookingApparatus) {
        switch (cookingApparatus) {
            case oven:
                return "oven";
            case stove:
                return "stove";
            default:
                return null;
        }
    }

    public static CookingApparatus getByName(String string) {
        switch (string) {
            case "oven":
                return oven;
            case "stove":
                return stove;
            default:
                return null;
        }
    }
}
