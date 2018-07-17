package br.com.odontoprev.portal.corretor.dao;

import br.com.odontoprev.portal.corretor.model.TbodDeviceToken;
import br.com.odontoprev.portal.corretor.model.TbodNotificationTemplate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenDAO extends CrudRepository<TbodDeviceToken, String> {

	@Query(value=" SELECT * FROM "
			+ " (SELECT * FROM TBOD_DEVICE_TOKEN TOKEN WHERE TOKEN.CD_LOGIN = :cdLogin ORDER BY TOKEN.DT_ATUALIZACAO DESC ) "
			+ " WHERE ROWNUM = 1 "
			, nativeQuery=true)

	public TbodDeviceToken findbyCdlogin(@Param("cdLogin") Long cdLogin);
	
}
