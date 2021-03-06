package br.com.odontoprev.portal.corretor.dao;

import br.com.odontoprev.portal.corretor.model.TbodLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginDAO extends JpaRepository<TbodLogin, Long> {

    @Query("select login from TbodLogin login join login.tbodCorretoras corretora where corretora.cnpj  =:cnpj ")
    TbodLogin findByTbodCorretoras(@Param("cnpj") String cnpj);
    
}