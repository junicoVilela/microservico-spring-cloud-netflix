package br.com.microservices.catalogo.message;

import br.com.microservices.catalogo.data.vo.ProdutoVO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProdutoSendMessage {

    @Value("${catalogo.rabbitmq.exchange}")
    private String exchange;

    @Value("${catalogo.rabbitmq.routingkey}")
    private String routingkey;

    public final RabbitTemplate rabbitTemplate;


    public ProdutoSendMessage(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(ProdutoVO produtoVO) {
        rabbitTemplate.convertAndSend(exchange, routingkey, produtoVO);
    }
}
