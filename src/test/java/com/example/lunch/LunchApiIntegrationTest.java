package com.example.lunch;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class LunchApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createsAndReadsMenus() throws Exception {
        mockMvc.perform(post("/menus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "menuDate": "2026-06-24",
                                  "menuName": "김치볶음밥",
                                  "category": "주식",
                                  "cafeteria": "본관식당"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.menuDate").value("2026-06-24"))
                .andExpect(jsonPath("$.menuName").value("김치볶음밥"))
                .andExpect(jsonPath("$.category").value("주식"))
                .andExpect(jsonPath("$.cafeteria").value("본관식당"));

        mockMvc.perform(post("/menus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "menuDate": "2026-06-25",
                                  "menuName": "된장국",
                                  "category": "국",
                                  "cafeteria": "본관식당"
                                }
                                """))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/menus"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        mockMvc.perform(get("/menus").param("date", "2026-06-24"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].menuName").value("김치볶음밥"));
    }

    @Test
    void exposesOpenApiDocs() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.openapi").exists())
                .andExpect(jsonPath("$.info.title").value("Lunch Menu API"));
    }
}
