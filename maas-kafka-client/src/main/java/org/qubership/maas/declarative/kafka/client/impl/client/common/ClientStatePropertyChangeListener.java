package org.qubership.maas.declarative.kafka.client.impl.client.common;


import org.qubership.maas.declarative.kafka.client.api.MaasKafkaClientState;
import org.qubership.maas.declarative.kafka.client.api.MaasKafkaClientStateChangeListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ClientStatePropertyChangeListener implements PropertyChangeListener {

    private final MaasKafkaClientStateChangeListener clientStateChangeListener;

    public ClientStatePropertyChangeListener(MaasKafkaClientStateChangeListener clientStateChangeListener) {
        this.clientStateChangeListener = clientStateChangeListener;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        clientStateChangeListener.onChange((MaasKafkaClientState) evt.getOldValue(),(MaasKafkaClientState) evt.getNewValue());
    }
}
