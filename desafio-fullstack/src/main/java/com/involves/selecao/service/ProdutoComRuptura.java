package com.involves.selecao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.involves.selecao.alerta.Alerta;
import com.involves.selecao.alerta.Pesquisa;
import com.involves.selecao.alerta.Resposta;
import com.involves.selecao.gateway.AlertaGateway;

@Service
public class ProdutoComRuptura {

	@Autowired
	private AlertaGateway gateway;

	public void verificar(Resposta resposta, Pesquisa pesquisa) {
		if(resposta.getResposta().equals("Produto ausente na gondola")){
			Alerta alerta = new Alerta();
			alerta.setPontoDeVenda(pesquisa.getPonto_de_venda());
			alerta.setDescricao("Ruptura detectada!");
			alerta.setProduto(pesquisa.getProduto());
			alerta.setFlTipo(1);
			gateway.salvar(alerta);
		}
	}
}
