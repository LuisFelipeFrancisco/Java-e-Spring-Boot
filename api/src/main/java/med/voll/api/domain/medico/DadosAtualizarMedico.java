package med.voll.api.domain.medico;

import med.voll.api.domain.endereco.DadosEndereco;

public record DadosAtualizarMedico(
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco) {
}
