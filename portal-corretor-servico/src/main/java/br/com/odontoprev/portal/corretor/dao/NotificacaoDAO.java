package br.com.odontoprev.portal.corretor.dao;

import br.com.odontoprev.portal.corretor.model.TbodForcaVenda;
import br.com.odontoprev.portal.corretor.model.TbodMensagemPadrao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//public interface NotificacaoDAO extends CrudRepository<TbodForcaVenda, String> {
public interface NotificacaoDAO extends CrudRepository<TbodMensagemPadrao, String> {

	@Query(value=" SELECT NOTIFICATION.* "
			+ " FROM TBOD_NOTIFICATION_TEMPLATE NOTIFICATION "
			+ " WHERE NOTIFICATION.TIPO = :tipo "
			, nativeQuery=true)

	public TbodMensagemPadrao findbyTipo(@Param("tipo") String tipo);
	
}
