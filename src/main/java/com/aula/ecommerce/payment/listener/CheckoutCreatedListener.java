package com.aula.ecommerce.payment.listener;

import com.aula.ecommerce.checkout.event.CheckoutCreatedEvent;
import com.aula.ecommerce.payment.event.PaymentCreatedEvent;
import com.aula.ecommerce.payment.streaming.CheckoutProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CheckoutCreatedListener {

    private final CheckoutProcessor checkoutProcessor;

    @StreamListener(CheckoutProcessor.INPUT)
    public void handler(CheckoutCreatedEvent event){
            //Rotina de processamento de pagamento em gateway
            //Salva dados
            //Enviar o evento do pagamento realizado

        final PaymentCreatedEvent paymentCreatedEvent =  PaymentCreatedEvent
                .newBuilder()
                .setCheckoutCode(event.getCheckoutCode())
                .setCheckoutStatus(event.getStatus())
                .setPaymentCode(UUID.randomUUID().toString())
                .build();

        checkoutProcessor.output().send(MessageBuilder.withPayload(paymentCreatedEvent).build());
    }
}
