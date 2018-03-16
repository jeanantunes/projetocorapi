package br.com.odontoprev.portal.corretor.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.odontoprev.portal.corretor.model.TbodTokenAceite;

@Repository
public interface TokenAceiteDAO extends CrudRepository<TbodTokenAceite, Long> {
	
	@Query("SELECT token FROM TbodTokenAceite token WHERE token.id.cdVenda = :cd_Venda AND token.id.cdToken = :cd_token and token.emailEnvio = :emailEnvio ")
	TbodTokenAceite findTokenPorVendaToken(@Param("cd_Venda") Long cd_Venda ,@Param("cd_token") String cd_token, @Param("emailEnvio") String emailEnvio);		
}
