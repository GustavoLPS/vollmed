package med.voll.api.repository;

import med.voll.api.enums.EspecialidadeEnum;
import med.voll.api.models.Consulta;
import med.voll.api.models.Medico;
import med.voll.api.models.Paciente;
import med.voll.api.records.EnderecoRecord;
import med.voll.api.records.MedicoRecord;
import med.voll.api.records.PacienteRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {
    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Deveria devolver null quando unico medico cadastrado não esta disponível da nada")
    void escolherMedicoAleatorioLivreNaDataCenario1() {
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);
        var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", EspecialidadeEnum.CARDIOLOGIA);
        var paciente = cadastrarPaciente("Paciente", "paciente@voll.med", "00000000000");
        cadastrarConsulta(medico, paciente, proximaSegundaAs10);

        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(EspecialidadeEnum.CARDIOLOGIA, proximaSegundaAs10);
        assertThat(medicoLivre).isNull();
    }

    @Test
    @DisplayName("Deveria devolver medico quando ele estiver disponível da nada")
    void escolherMedicoAleatorioLivreNaDataCenario2() {
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);
        var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", EspecialidadeEnum.CARDIOLOGIA);

        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(EspecialidadeEnum.CARDIOLOGIA, proximaSegundaAs10);
        assertThat(medicoLivre).isEqualTo(medico);
    }

    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime date) {
        entityManager.persist(new Consulta(null, medico, paciente, date));
    }

    private Medico cadastrarMedico(String nome, String email, String crm, EspecialidadeEnum especialidade) {
        var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
        entityManager.persist(medico);
        return medico;
    }

    private Paciente cadastrarPaciente(String nome, String email, String cpf) {
        var paciente = new Paciente(dadosPaciente(nome, email, cpf));
        entityManager.persist(paciente);
        return paciente;
    }

    private MedicoRecord dadosMedico(String nome, String email, String crm, EspecialidadeEnum especialidade) {
        return new MedicoRecord(
                nome,
                email,
                "61999999999",
                crm,
                especialidade,
                dadosEndereco()
        );
    }

    private PacienteRecord dadosPaciente(String nome, String email, String cpf) {
        return new PacienteRecord(
                nome,
                email,
                "61999999999",
                cpf,
                dadosEndereco()
        );
    }

    private EnderecoRecord dadosEndereco() {
        return new EnderecoRecord(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                null,
                null
        );
    }
}
