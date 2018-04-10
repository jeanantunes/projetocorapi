package br.com.odontoprev.portal.corretor.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.odontoprev.portal.corretor.model.TbodTokenResetSenha;

@Repository
public interface TokenEsqueciSenhaDAO extends CrudRepository<TbodTokenResetSenha, Long> {
		
	@Query("SELECT reset FROM TbodTokenResetSenha reset WHERE reset.token = :token  ")
	TbodTokenResetSenha findToken(@Param("token") String token);
	
}
