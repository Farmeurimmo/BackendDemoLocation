package fr.farmeurimmo.backenddemolocation;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
        "logging.level.org.springframework=DEBUG"
})
class BackendAppTests {

    @Test
    void contextLoads() {
        System.out.println("No tests");
    }

}
