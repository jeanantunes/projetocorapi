package br.com.odontoprev.portal.corretor.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.odontoprev.portal.corretor.model.Login;
import br.com.odontoprev.portal.corretor.repository.ILoginRepository;

@RestController
@RequestMapping("/login")
public class LoginService {
	
	@Autowired
	private ILoginRepository loginRepository;
	
	@PostMapping
	public Login salvar(@RequestBody @Valid Login login) {
		return loginRepository.save(login);
	}
	
	@GetMapping()
	public List<Login> lista() {
		return loginRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public Login findOne(@PathVariable Long id) {
		return loginRepository.findOne(id);
	}
	
	@PostMapping("/{id}")
	public Login editar(@RequestBody @Valid Login login) {
		return loginRepository.save(login);
	}
	
	@DeleteMapping("/{id}")
	public String apagar(@PathVariable Long id) {
		Login login = loginRepository.findOne(id);
		
		loginRepository.delete(login.getId());
	
		return "redirect:login/novo";
	}
}
