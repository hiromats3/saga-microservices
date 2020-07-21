package org.hiromats3.saga.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hiromats3.saga.model.Ticket;
import org.hiromats3.saga.model.TicketState;

@ApplicationScoped
public class TicketService {

    @Inject
    EntityManager em;

    @Transactional
    public void create(Ticket ticket) {
        ticket.setState(TicketState.TICKET_BOOKED_PENDING);
        em.persist(ticket);
    }

    public List<Ticket> findAll() {
        return em.createQuery("SELECT t FROM Ticket t", Ticket.class)
                .getResultList();
                // .getResultList()
                // .toArray(new Ticket[0]);
    }

    public Ticket getSingle(Integer id) {
        return em.find(Ticket.class, id);
    }

    public Ticket getByLraId(String lraId) {
        return em.createQuery("SELECT t FROM Ticket t WHERE t.lraId = :lraId", Ticket.class)
                .setParameter("lraId", lraId)
                .getSingleResult();
    }

    public Ticket getByOrderId(String orderId) {
        return em.createQuery("SELECT t FROM Ticket t WHERE t.orderId = :orderId", Ticket.class)
                .setParameter("orderId", orderId)
                .getSingleResult();
    }

    @Transactional
    public void update(Integer id, Ticket ticket) {
        Ticket entity = this.getSingle(id);
        // if(entity == null)をチェックしたほうが良い
        entity.setAccountId(ticket.getAccountId());
        entity.setName(ticket.getName());
        entity.setNumberOfPersons(ticket.getNumberOfPersons());
        entity.setOrderId(ticket.getOrderId());
        entity.setState(ticket.getState());
        entity.setTotalCost(ticket.getTotalCost());
        em.merge(entity);
    }

    @Transactional
    public void delete(Integer id) {
        em.remove(this.getSingle(id));
    }
}