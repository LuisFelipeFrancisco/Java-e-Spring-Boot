package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/medicos")
public class MedicosController {

    @Autowired
    private MedicoRepository repo;

    @PostMapping("/post")
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados){
        repo.save(new Medico(dados));
    }

    @GetMapping("/get")
    public Page<DadosListagemMedico> listar(@PageableDefault(size = 10, page = 0, sort = {"id"}) Pageable page){
        return repo.findAllByAtivoTrue(page).map(DadosListagemMedico::new);
    }

    @PutMapping("/update")
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizarMedico dados){
        var medico = repo.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public void excluir(@PathVariable Long id){
        repo.deleteById(id);
    }

    @DeleteMapping("/inactivate/{id}")
    @Transactional
    public void inativar(@PathVariable Long id){
        var medico = repo.getReferenceById(id);
        medico.inativar();
    }

}
