package br.com.alura.forum.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
//import org.springframework.data.repository.query.Param;

import br.com.alura.forum.modelo.Topico;

public interface TopicoRepository  extends JpaRepositoryImplementation<Topico, Long>{

	Page<Topico> findByCurso_Nome(String nomeCurso, Pageable paginacao);
	
	//@Query("SELECT t FROM Topico t where t.curso.nome = :nomeCurso")
	//List<Topico> carregarPorNomeDoCurso(@Param("nomeCurso") String nomeCurso);

}
