package com.involves.selecao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.involves.selecao.alerta.Alerta;
import com.involves.selecao.alerta.Pesquisa;
import com.involves.selecao.alerta.Resposta;
import com.involves.selecao.gateway.AlertaGateway;

@Service
public class ProdutoComPreco {

	@Autowired
	private AlertaGateway gateway;

	public void verificar(Resposta resposta, Pesquisa pesquisa) {
		int precoColetado = Integer.parseInt(resposta.getResposta());
		int precoEstipulado = Integer.parseInt(pesquisa.getPreco_estipulado());
		Alerta alerta = new Alerta();
		int margem = precoEstipulado - Integer.parseInt(resposta.getResposta());
		alerta.setMargem(margem);
		alerta.setProduto(pesquisa.getProduto());
		alerta.setPontoDeVenda(pesquisa.getPonto_de_venda());
		if(precoColetado < precoEstipulado){
			alerta.setDescricao("Preço abaixo do estipulado!");
			alerta.setFlTipo(3);
		}else {
			alerta.setDescricao("Preço acima do estipulado!");
			alerta.setFlTipo(2);
		}
		gateway.salvar(alerta);
	}
}
