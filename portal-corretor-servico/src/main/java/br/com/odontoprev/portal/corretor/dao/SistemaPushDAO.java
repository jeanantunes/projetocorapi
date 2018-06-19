package br.com.odontoprev.portal.corretor.dao;

import br.com.odontoprev.portal.corretor.model.TbodSistemaPush;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SistemaPushDAO extends CrudRepository<TbodSistemaPush, Long> {

    @Query(value=" SELECT NOTIFICATION.* "
            + " FROM TBOD_SISTEMA_PUSH NOTIFICATION "
            + " WHERE NOTIFICATION.NM_SISTEMA = :nmsistema"
            , nativeQuery=true)

    public TbodSistemaPush findbyNmSistema(@Param("nmsistema") String nmsistema);
}
