package com.involves.selecao.service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.involves.selecao.alerta.Alerta;
import com.involves.selecao.alerta.Pesquisa;
import com.involves.selecao.alerta.Resposta;
import com.involves.selecao.gateway.AlertaGateway;

@Service
public class ProcessadorAlertas {

	@Autowired
	private ProdutoComRuptura produtoComRuptura;

	@Autowired
	private ProdutoComPreco produtoComPreco;

	@Autowired
	private ProdutoComParticipacaoEstipulada produtoComParticipacaoEstipulada;

	@Autowired
	private AlertaGateway gateway;

	public List<Alerta> buscarTodos() {
		return gateway.buscarTodos();
	}

	public void processa() throws IOException {
		StringBuffer content = this.lerUrl();
		Gson gson = new Gson();
		Pesquisa[] ps =  gson.fromJson(content.toString(), Pesquisa[].class);
		for (int i = 0; i < ps.length; i++){
			for (int j = 0; j < ps[i].getRespostas().size(); j++){
				Resposta resposta = ps[i].getRespostas().get(j);
				if (resposta.getPergunta().equals("Qual a situação do produto?")) {
					produtoComRuptura.verificar(resposta, ps[i]);
				}
				if(resposta.getPergunta().equals("Qual o preço do produto?")) {
					produtoComPreco.verificar(resposta, ps[i]);
				}
				if(resposta.getPergunta().equals("%Share")) {
					produtoComParticipacaoEstipulada.verificar(resposta, ps[i]);
				}
			} 
		}
	}

	public StringBuffer lerUrl() throws IOException {
		URL url = new URL("https://selecao-involves.agilepromoter.com/pesquisas");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");

		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream(), "UTF-8"));
		String inputLine;
		StringBuffer content = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			content.append(inputLine);
		}
		in.close();
		return content;
	}
}

