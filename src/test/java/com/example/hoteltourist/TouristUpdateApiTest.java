package com.example.hoteltourist;

import com.example.hoteltourist.entity.Tourist;
import com.example.hoteltourist.repository.TouristRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "tourist.api.token=test-private-token")
@AutoConfigureMockMvc
class TouristUpdateApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TouristRepository touristRepository;

    @BeforeEach
    void resetSampleTourist() {
        Tourist sample = touristRepository.findById(1L).orElseThrow();
        sample.setName("Aarav Sharma");
        sample.setDestination("Jaipur");
        sample.setContactNumber("+919876543210");
        sample.setEmail("aarav@example.com");
        touristRepository.save(sample);
    }

    @Test
    void updatesTouristWhenTokenIsValid() throws Exception {
        mockMvc.perform(put("/api/tourist/1")
                        .header("X-Request-Token", "test-private-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Aarav Sharma",
                                  "destination": "Udaipur",
                                  "contactNumber": "+919876543211",
                                  "email": "aarav.updated@example.com"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {"message":"Tourist updated successfully"}
                        """));

        Tourist saved = touristRepository.findById(1L).orElseThrow();
        assertThat(saved.getDestination()).isEqualTo("Udaipur");
        assertThat(saved.getContactNumber()).isEqualTo("+919876543211");
        assertThat(saved.getEmail()).isEqualTo("aarav.updated@example.com");
    }

    @Test
    void rejectsMissingOrInvalidTokenWithoutUpdatingTourist() throws Exception {
        mockMvc.perform(put("/api/tourist/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Changed Name",
                                  "destination": "Changed Destination",
                                  "contactNumber": "+919876543211",
                                  "email": "changed@example.com"
                                }
                                """))
                .andExpect(status().isUnauthorized())
                .andExpect(content().json("""
                        {"message":"Invalid or missing request token"}
                        """));

        Tourist unchanged = touristRepository.findById(1L).orElseThrow();
        assertThat(unchanged.getDestination()).isEqualTo("Jaipur");
    }

    @Test
    void returnsNotFoundForUnknownTourist() throws Exception {
        mockMvc.perform(put("/api/tourist/999")
                        .header("X-Request-Token", "test-private-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Tourist",
                                  "destination": "Delhi",
                                  "contactNumber": "+919876543211",
                                  "email": "tourist@example.com"
                                }
                                """))
                .andExpect(status().isNotFound())
                .andExpect(content().json("""
                        {"message":"Tourist not found"}
                        """));
    }
}
