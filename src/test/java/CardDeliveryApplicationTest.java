import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryApplicationTest {

    @Test
    void happyPathTest() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[data-test-id = 'city'] input").setValue("Новосибирск");
        $("[data-test-id = 'date'] input").setValue("01.05.2023");
    }
}
