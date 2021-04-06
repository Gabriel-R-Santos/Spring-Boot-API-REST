package br.com.alura.forum.controller;

import java.net.URI;
import java.util.Optional;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.DetalhesDoTopicoDto;
import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.controller.form.AtualizacaoTopicoForm;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

	@Autowired
	private TopicoRepository topicoRepository;

	@Autowired
	private CursoRepository cursoRepository;

	@GetMapping
	public Page<TopicoDto> lista(@RequestParam(required = false) String nomeCurso, @RequestParam int pagina,
			@RequestParam int qtd, @RequestParam(required = false) String ordenacao) {

		Pageable paginacao;
		if (ordenacao == null) {
			System.out.println("null:"+ordenacao);
			paginacao = PageRequest.of(pagina, qtd);
		}
		else {	
			System.out.println("valor:"+ordenacao);
			paginacao = PageRequest.of(pagina, qtd, Direction.DESC, ordenacao);
		}

		if (nomeCurso == null) {
			Page<Topico> topicos = topicoRepository.findAll(paginacao);
			return TopicoDto.converter(topicos);
		} else {
			Page<Topico> topicos = topicoRepository.findByCurso_Nome(nomeCurso, paginacao);
			return TopicoDto.converter(topicos);
		}
		// Topico topico = new Topico("Duvida", "Duvidas com Spring", new
		// Curso("Spring", "Programacao"));
		// return TopicoDto.converter(Arrays.asList(topico, topico, topico));
	}

	@GetMapping("/{id}")
	public ResponseEntity<DetalhesDoTopicoDto> detalhar(@PathVariable("id") Long codigo) {
		Optional<Topico> topico = topicoRepository.findById(codigo);
		if (topico.isPresent()) {
			return ResponseEntity.ok(new DetalhesDoTopicoDto(topico.get()));
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@Transactional
	public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm topicoForm,
			UriComponentsBuilder uriBuilder) {
		Topico topico = topicoForm.converter(cursoRepository);
		topicoRepository.save(topico);
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDto(topico));
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<TopicoDto> atualizar(@PathVariable("id") Long codigo,
			@RequestBody @Valid AtualizacaoTopicoForm topicoForm) {
		Optional<Topico> optional = topicoRepository.findById(codigo);
		if (optional.isPresent()) {
			Topico topico = topicoForm.atualizar(codigo, topicoRepository);
			return ResponseEntity.ok(new TopicoDto(topico));
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable("id") Long codigo) {
		Optional<Topico> optional = topicoRepository.findById(codigo);
		if (optional.isPresent()) {
			topicoRepository.deleteById(codigo);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();

	}

}
