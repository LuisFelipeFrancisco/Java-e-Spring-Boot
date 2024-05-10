package med.voll.api.medico;

import med.voll.api.endereco.Endereco;

public record DataMedicos(Long Id, String nome, String email, String telefone, String crm, Especialidade especialidade, Endereco endereco) {

    public DataMedicos (Medico medico){
        this(
                medico.getId(),
                medico.getNome(),
                medico.getEmail(),
                medico.getTelefone(),
                medico.getCrm(),
                medico.getEspecialidade(),
                medico.getEndereco()
        );
    }


}
