package TypewiseAlert;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TypewiseAlertTest {

    @Test
    public void testInferBreachTooLow() {
        assertEquals(TypewiseAlert.BreachType.TOO_LOW, TypewiseAlert.inferBreach(10, 20, 30));
    }

    @Test
    public void testInferBreachTooHigh() {
        assertEquals(TypewiseAlert.BreachType.TOO_HIGH, TypewiseAlert.inferBreach(40, 20, 30));
    }

    @Test
    public void testInferBreachNormal() {
        assertEquals(TypewiseAlert.BreachType.NORMAL, TypewiseAlert.inferBreach(25, 20, 30));
    }

    @Test
    public void testClassifyTemperatureBreachPassiveCooling() {
        assertEquals(TypewiseAlert.BreachType.NORMAL, 
                     TypewiseAlert.classifyTemperatureBreach(TypewiseAlert.CoolingType.PASSIVE_COOLING, 25));
        assertEquals(TypewiseAlert.BreachType.TOO_HIGH, 
                     TypewiseAlert.classifyTemperatureBreach(TypewiseAlert.CoolingType.PASSIVE_COOLING, 40));
    }

    @Test
    public void testClassifyTemperatureBreachHiActiveCooling() {
        assertEquals(TypewiseAlert.BreachType.TOO_LOW, 
                     TypewiseAlert.classifyTemperatureBreach(TypewiseAlert.CoolingType.HI_ACTIVE_COOLING, -5));
        assertEquals(TypewiseAlert.BreachType.NORMAL, 
                     TypewiseAlert.classifyTemperatureBreach(TypewiseAlert.CoolingType.HI_ACTIVE_COOLING, 25));
    }

    @Test
    public void testSendToController() {
        TypewiseAlert.sendToController(TypewiseAlert.BreachType.TOO_HIGH);
    }

    @Test
    public void testSendToEmail() {
        TypewiseAlert.sendToEmail(TypewiseAlert.BreachType.TOO_LOW);
    }

    @Test
    public void testCheckAndAlertToController() {
        TypewiseAlert.BatteryCharacter batteryChar = new TypewiseAlert.BatteryCharacter();
        batteryChar.coolingType = TypewiseAlert.CoolingType.PASSIVE_COOLING;
        TypewiseAlert.checkAndAlert(TypewiseAlert.AlertTarget.TO_CONTROLLER, batteryChar, 40);
    }

    @Test
    public void testCheckAndAlertToEmail() {
        TypewiseAlert.BatteryCharacter batteryChar = new TypewiseAlert.BatteryCharacter();
        batteryChar.coolingType = TypewiseAlert.CoolingType.HI_ACTIVE_COOLING;
        TypewiseAlert.checkAndAlert(TypewiseAlert.AlertTarget.TO_EMAIL, batteryChar, 50);
    }
}

