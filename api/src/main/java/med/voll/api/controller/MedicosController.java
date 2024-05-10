package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/medicos")
public class MedicosController {

    @Autowired
    private MedicoRepository repo;

    @PostMapping()
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder) {
        var medico = new Medico(dados);
        repo.save(medico);
        var URI = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(URI).body(new DataMedicos(medico)); // 201 Created
    }

    @GetMapping()
    public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size = 10, page = 0, sort = {"id"}) Pageable pages) {
        var page = repo.findAllByAtivoTrue(pages).map(DadosListagemMedico::new);
        return ResponseEntity.ok(page); // Retorna o objeto listado 200 OK
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var medico = repo.getReferenceById(id);
        return ResponseEntity.ok(new DataMedicos(medico)); // 200 OK
    }

    @PutMapping()
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizarMedico dados) {
        var medico = repo.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DataMedicos(medico)); // 200 OK
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id) {
        repo.deleteById(id); // 200 OK

    }

    @DeleteMapping("/inactivate/{id}")
    @Transactional
    public ResponseEntity inativar(@PathVariable Long id) {
        var medico = repo.getReferenceById(id);
        medico.inativar();
        return ResponseEntity.noContent().build(); // 204 No Content
    }

}
