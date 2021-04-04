package br.com.alura.forum.repository;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import br.com.alura.forum.modelo.Curso;

public interface CursoRepository extends JpaRepositoryImplementation<Curso, Long>{

	Curso findByNome(String nome);  
	
}
