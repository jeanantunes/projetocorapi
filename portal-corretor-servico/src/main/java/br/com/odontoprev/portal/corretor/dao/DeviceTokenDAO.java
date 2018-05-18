package br.com.odontoprev.portal.corretor.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.odontoprev.portal.corretor.model.TbodDeviceToken;

public interface DeviceTokenDAO extends JpaRepository<TbodDeviceToken, Long> {

	public List<TbodDeviceToken> findByTokenAndLoginTbodForcaVendasCdForcaVenda(String token, Long cdForcaVenda);
}
