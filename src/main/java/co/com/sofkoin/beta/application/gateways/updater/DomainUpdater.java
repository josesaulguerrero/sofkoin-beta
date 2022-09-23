package co.com.sofkoin.beta.application.gateways.updater;

import co.com.sofka.domain.generic.DomainEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public abstract class DomainUpdater {
  protected Set<Consumer<? super DomainEvent>> listeners = new HashSet<>();

  protected void addUpdater(Consumer<? extends DomainEvent> changeEvent) {
    this.listeners.add((Consumer<? super DomainEvent>) changeEvent);
  }

  public final void applyEvent(DomainEvent domainEvent) {
    this.listeners.forEach(consumer -> {
      try {
        consumer.accept(domainEvent);
      } catch (ClassCastException ignored) {}
    });
  }
}
