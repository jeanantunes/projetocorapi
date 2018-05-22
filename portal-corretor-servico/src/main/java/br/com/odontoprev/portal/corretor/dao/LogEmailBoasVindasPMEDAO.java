package br.com.odontoprev.portal.corretor.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.odontoprev.portal.corretor.model.TbodLogEmailBoasVindasPME;

//201805221239 - esert - COR-225 - Serviço - LOG Envio e-mail de Boas Vindas PME
@Repository
public interface LogEmailBoasVindasPMEDAO extends JpaRepository<TbodLogEmailBoasVindasPME, Long> {
    
}