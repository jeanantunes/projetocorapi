package br.com.odontoprev.portal.corretor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.odontoprev.portal.corretor.model.Login;

public interface ILoginRepository extends JpaRepository<Login, Long> {

}
