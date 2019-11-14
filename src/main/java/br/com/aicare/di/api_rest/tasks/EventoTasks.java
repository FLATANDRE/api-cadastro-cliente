/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.tasks;

import br.com.aicare.di.api_rest.repository.evento.EventoRepository;
import br.com.aicare.di.api_rest.repository.evento.TipoEventoRepository;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author Paulo Collares
 */
@Component
@EnableAsync
public class EventoTasks {

    @Autowired
    EventoRepository eventoRepository;

    @Async
    @Scheduled(fixedDelay = 600000, initialDelay = 10000)//A cada 10 minutos, inicial 10 segundos
    public void removerEventosAntigos() {

        long diff = 1000 * 60 * 60 * 24; //24hs
        Date expiryDate = new Date(System.currentTimeMillis() - diff);

        eventoRepository.deleteByDataHoraBefore(expiryDate);

//         @TODO: remover eventos por tipo
//        List<TipoEvento> tiposEvento = tipoEventoRepository.findAll();
//
//        for (TipoEvento tipoEvento : tiposEvento) {
//            System.out.println(tipoEvento.getNome() + " " + tipoEvento.getCodigo() + " " + tipoEvento.getQuantidadeMaximaPorDispositivo());
//        }
    }

}
