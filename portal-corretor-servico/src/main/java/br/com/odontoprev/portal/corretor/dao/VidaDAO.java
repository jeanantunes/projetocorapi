package br.com.odontoprev.portal.corretor.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.odontoprev.portal.corretor.model.TbodVida;

@Repository
public interface VidaDAO extends CrudRepository<TbodVida, Long>{

    @Query(value = "select "
    	    +"t1.* "
    	    //+"--, ROWNUM row_num " 
    	    +"from ( "
    	        +"select " 
    	        +"ROWNUM rn, " //0
    	        +" ve.CD_EMPRESA " //1
    	        +",ve.CD_VENDA " //2
    	        +",vv.CD_VENDA_VIDA " //3
    	        +",ve.CD_PLANO " //4
    	        +",vi.CD_VIDA " //5
    	        +",vi.CD_TITULAR " //6
    	        +",vi.NOME " //7
    	        +",vi.CPF " //8
    	        +",vi.DATA_NASCIMENTO " //9
    	        +",vi.SEXO " //10
    	        +",vi.NOME_MAE " //11
    	        +",en.CEP " //12
    	        +",pl.TITULO " //13
    	        +"from TBOD_VIDA vi " 
    	        +"inner join TBOD_VENDA_VIDA vv on vv.CD_VIDA = vi.CD_VIDA "
    	        +"inner join TBOD_VENDA ve on ve.CD_VENDA = vv.CD_VENDA "
    	        //+"--inner join TBOD_EMPRESA em on em.CD_EMPRESA = ve.CD_EMPRESA "
    	        +"inner join TBOD_PLANO pl on pl.CD_PLANO = ve.CD_PLANO "
    	        +"left outer join TBOD_ENDERECO en on en.CD_ENDERECO = vi.CD_ENDERECO "
    	        +"where " 
    	        +"vi.CD_TITULAR is null "
    	        +"and "
    	        //+"--em.CD_EMPRESA = 2391 "
    	        //+"--ve.CD_EMPRESA = 2391 "
    	        //+"ve.CD_EMPRESA = 1260 --53 vidas 33 titulares 1 venda 1760 "
    	        //+"--ve.CD_EMPRESA = 1659 --13 vidas 8 titulares 2 vendas 2368,2369 "
    	        +"ve.CD_EMPRESA = :cdEmpresa "
    	        +"order by " 
    	        //+"--ve.CD_VENDA "
    	        //+"--, "
    	        +"vi.CD_VIDA "
    	    +") t1 "
    	    //+"--query interna limita o fim diretamente usando ROWNUM " 
    	    +"where " 
    	    //+"--ROWNUM <= 3 "
    	    //+"rn between 1 and 54 "
    	    +"rn between :primeiroReg and :ultimoReg "
    	    , nativeQuery = true)
    public List<Object[]> findVidasTitularByCdEmpresaPrimeiroRegUltimoReg(
    		@Param("cdEmpresa") Long cdEmpresa, 
    		@Param("primeiroReg") Long primeiroReg, 
    		@Param("ultimoReg") Long ultimoReg);
    
    @Query(value = "select "
    		+"count(vi.CD_VIDA) count_CD_VIDA " //5
    		+"from TBOD_VIDA vi " 
    		+"inner join TBOD_VENDA_VIDA vv on vv.CD_VIDA = vi.CD_VIDA "
    		+"inner join TBOD_VENDA ve on ve.CD_VENDA = vv.CD_VENDA "
    		//+"--inner join TBOD_EMPRESA em on em.CD_EMPRESA = ve.CD_EMPRESA "
    		+"inner join TBOD_PLANO pl on pl.CD_PLANO = ve.CD_PLANO "
    		+"left outer join TBOD_ENDERECO en on en.CD_ENDERECO = vi.CD_ENDERECO "
    		+"where " 
    		+"vi.CD_TITULAR is null "
    		+"and "
    		//+"--em.CD_EMPRESA = 2391 "
    		//+"--ve.CD_EMPRESA = 2391 "
    		//+"ve.CD_EMPRESA = 1260 --53 vidas 33 titulares 1 venda 1760 "
    		//+"--ve.CD_EMPRESA = 1659 --13 vidas 8 titulares 2 vendas 2368,2369 "
    		+"ve.CD_EMPRESA = :cdEmpresa "
    		+"order by " 
    		//+"--ve.CD_VENDA "
    		//+"--, "
    		+"vi.CD_VIDA "
    		, nativeQuery = true)
    public Long countVidasTitularByCdEmpresa(@Param("cdEmpresa") Long cdEmpresa); //201807271455 - COR-475

	public List<TbodVida> findByCdTitular(Long cdVida); //201807262000 - COR-475

}
