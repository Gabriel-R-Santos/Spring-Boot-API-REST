package br.com.alura.forum.repository;

import java.util.List;

//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
//import org.springframework.data.repository.query.Param;

import br.com.alura.forum.modelo.Topico;

public interface TopicoRepository  extends JpaRepositoryImplementation<Topico, Long>{

	List<Topico> findByCurso_Nome(String nomeCurso);
	
	//@Query("SELECT t FROM Topico t where t.curso.nome = :nomeCurso")
	//List<Topico> carregarPorNomeDoCurso(@Param("nomeCurso") String nomeCurso);

}
