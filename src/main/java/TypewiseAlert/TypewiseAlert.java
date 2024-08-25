package TypewiseAlert;

public class TypewiseAlert {
    public enum BreachType {
        NORMAL, TOO_LOW, TOO_HIGH
    }

    public enum CoolingType {
        PASSIVE_COOLING, HI_ACTIVE_COOLING, MED_ACTIVE_COOLING
    }

    public enum AlertTarget {
        TO_CONTROLLER, TO_EMAIL
    }

    public static BreachType inferBreach(double value, double lowerLimit, double upperLimit) {
        if (value < lowerLimit) {
            return BreachType.TOO_LOW;
        } else if (value > upperLimit) {
            return BreachType.TOO_HIGH;
        }
        return BreachType.NORMAL;
    }

    public static BreachType classifyTemperatureBreach(CoolingType coolingType, double temperatureInC) {
        int[] limits = getLimitsForCoolingType(coolingType);
        return inferBreach(temperatureInC, limits[0], limits[1]);
    }

    private static int[] getLimitsForCoolingType(CoolingType coolingType) {
        if (coolingType == CoolingType.PASSIVE_COOLING) return getLimitsForPassiveCooling();
        if (coolingType == CoolingType.HI_ACTIVE_COOLING) return getLimitsForHiActiveCooling();
        if (coolingType == CoolingType.MED_ACTIVE_COOLING) return getLimitsForMedActiveCooling();
        throw new IllegalArgumentException("Invalid Cooling Type");
    }

    private static int[] getLimitsForPassiveCooling() { return new int[]{0, 35}; }
    private static int[] getLimitsForHiActiveCooling() { return new int[]{0, 45}; }
    private static int[] getLimitsForMedActiveCooling() { return new int[]{0, 40}; }

    public static void checkAndAlert(AlertTarget alertTarget, BatteryCharacter batteryChar, double temperatureInC) {
        BreachType breachType = classifyTemperatureBreach(batteryChar.coolingType, temperatureInC);
        sendAlert(alertTarget, breachType);
    }

    private static void sendAlert(AlertTarget alertTarget, BreachType breachType) {
        if (alertTarget == AlertTarget.TO_CONTROLLER) sendToController(breachType);
        if (alertTarget == AlertTarget.TO_EMAIL) sendToEmail(breachType);
    }

    private static void sendToController(BreachType breachType) {
        int header = 0xfeed;
        System.out.printf("%x : %s\n", header, breachType);
    }

    private static void sendToEmail(BreachType breachType) {
        String recipient = "a.b@c.com";
        if (breachType != BreachType.NORMAL) {
            System.out.printf("To: %s\n", recipient);
            String message = (breachType == BreachType.TOO_LOW) ? "too low" : "too high";
            System.out.printf("Hi, the temperature is %s\n", message);
        }
    }

    public static class BatteryCharacter {
        public CoolingType coolingType;
        public String brand;
    }
}
