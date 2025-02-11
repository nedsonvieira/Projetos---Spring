package br.com.nedson.ScreenMatchComSpring.service;

public interface IConversaoDados {

    <T> T obterDados(String json, Class<T> classe);

}
