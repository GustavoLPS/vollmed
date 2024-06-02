package med.voll.api.controllers;

import med.voll.api.enums.EspecialidadeEnum;
import med.voll.api.records.ConsultaRecord;
import med.voll.api.records.ConsultaReturnRecord;
import med.voll.api.services.AgendaDeConsultasService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<ConsultaRecord> consultaRecordJson;

    @Autowired
    private JacksonTester<ConsultaReturnRecord> consultaReturnJson;

    @MockBean
    private AgendaDeConsultasService agendaDeConsultasService;

    @Test
    @DisplayName("Deveria devolver codigo http 400 quando informações estão inválidas")
    @WithMockUser
    void agendar_cenario1() throws Exception {
        var response = mvc.perform(post("/consultas"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver codigo http 200 quando informações estão válidas")
    @WithMockUser
    void agendar_cenario2() throws Exception {
        var data = LocalDateTime.now().plusHours(1);
        var especialidade = EspecialidadeEnum.CARDIOLOGIA;
        var consultaReturnRecord = new ConsultaReturnRecord(2l, 5l, data, especialidade);

        when(agendaDeConsultasService.agendar(any())).thenReturn(consultaReturnRecord);

        var response = mvc
                .perform(post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(consultaRecordJson.write(
                                new ConsultaRecord(2l, 5l, data, especialidade)
                        ).getJson())
                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonEsperado = consultaReturnJson.write(
                consultaReturnRecord
        ).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }
}