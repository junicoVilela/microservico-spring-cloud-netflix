package br.com.microservices.financeiro.config;

import br.com.microservices.financeiro.data.vo.ProdutoVO;
import br.com.microservices.financeiro.entity.Produto;
import br.com.microservices.financeiro.repository.ProdutoRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ProdutoReceiveMessage {

    private final ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoReceiveMessage(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @RabbitListener(queues = {"${catalogo.rabbitmq.queue}"})
    public void receive(@Payload ProdutoVO produtoVO) {
        produtoRepository.save(Produto.create(produtoVO));
    }
}
