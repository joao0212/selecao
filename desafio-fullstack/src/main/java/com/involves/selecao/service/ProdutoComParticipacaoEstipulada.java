package com.involves.selecao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.involves.selecao.alerta.Alerta;
import com.involves.selecao.alerta.Pesquisa;
import com.involves.selecao.alerta.Resposta;
import com.involves.selecao.gateway.AlertaGateway;

@Service
public class ProdutoComParticipacaoEstipulada {

	@Autowired
	private AlertaGateway gateway;

	public void verificar(Resposta resposta, Pesquisa pesquisa) {
		int participacaoColetada = Integer.parseInt(resposta.getResposta());
		int participacaoEstipulada = Integer.parseInt(pesquisa.getParticipacao_estipulada());
		Alerta alerta = new Alerta();
		int margem = participacaoEstipulada - Integer.parseInt(resposta.getResposta());
		alerta.setPontoDeVenda(pesquisa.getPonto_de_venda());
		alerta.setFlTipo(2);
		alerta.setMargem(margem);
		alerta.setProduto(pesquisa.getProduto());
		alerta.setCategoria(pesquisa.getCategoria());
		if(participacaoColetada < participacaoEstipulada) {
			alerta.setDescricao("Participação inferior ao estipulado!");
		}else {
			alerta.setDescricao("Participação acima do estipulado");
		}
		gateway.salvar(alerta);
	}
}
