package dh.backend.maxisoriano.ClinicaMVC.controller;

import dh.backend.maxisoriano.ClinicaMVC.service.IOdontologoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureMockMvc
class OdontologoControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private IOdontologoService odontologoService;

    @Test
    @DisplayName("Testear la obtencion de un odontologo")
    void obtenerOdontologo() throws Exception {
        mockMvc.perform(get("/odontologo/5")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(("$.nombre")).value("Celeste"))
                .andExpect(jsonPath(("$.apellido")).value("Severich"))
                .andExpect(jsonPath(("$.matricula")).value( "7894"));
    }

    @Test
    @DisplayName("Testear que un odontologo no exista")
    void noObtenerOdontologo() throws Exception {
        mockMvc.perform(get("/odontologo/120")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Testear que se agrego un odontologo a la base de datos")
    void registrarOdontologo() throws Exception {
        String odontologo = "{\"nombre\":\"Juan\",\"apellido\":\"Perez\",\"matricula\":\"4789\"}";

        mockMvc.perform(post("/odontologo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(odontologo))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath(("$.nombre")).value("Juan"))
                .andExpect(jsonPath(("$.apellido")).value("Perez"))
                .andExpect(jsonPath(("$.matricula")).value( "4789"));

    }

    @Test
    @DisplayName("Testear la actualización de un odontólogo")
    void actualizarOdontologo() throws Exception {
        // Preparar datos de prueba
        String nuevoNombre = "Juan Carlos";
        String nuevoApellido = "Gómez";
        String nuevaMatricula = "6789";

        String odontologoActualizado = "{\"nombre\":\"" + nuevoNombre + "\",\"apellido\":\"" + nuevoApellido + "\",\"matricula\":\"" + nuevaMatricula + "\"}";

        mockMvc.perform(put("/odontologo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(odontologoActualizado))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(("$.nombre")).value(nuevoNombre))
                .andExpect(jsonPath(("$.apellido")).value(nuevoApellido))
                .andExpect(jsonPath(("$.matricula")).value(nuevaMatricula));
    }

    @Test
    @DisplayName("Testear la eliminación de un odontólogo")
    void eliminarOdontologo() throws Exception {
        // Realizar la eliminación del odontólogo con ID 5 (por ejemplo)
        mockMvc.perform(delete("/odontologo/{id}", 2))
                .andDo(print())
                .andExpect(status().isOk());
    }

}