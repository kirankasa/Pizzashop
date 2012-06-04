package com.fss.roo.pizzashop.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@Entity
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class PizzaOrder {

    @NotNull
    @Size(min = 2)
    private String name;

    @Size(max = 30)
    private String address;

    private Float total;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date deliveryDate;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Pizza> pizzas = new HashSet<Pizza>();

	public String getName() {
        return this.name;
    }

	public void setName(String name) {
        this.name = name;
    }

	public String getAddress() {
        return this.address;
    }

	public void setAddress(String address) {
        this.address = address;
    }

	public Float getTotal() {
        return this.total;
    }

	public void setTotal(Float total) {
        this.total = total;
    }

	public Date getDeliveryDate() {
        return this.deliveryDate;
    }

	public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

	public Set<Pizza> getPizzas() {
        return this.pizzas;
    }

	public void setPizzas(Set<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new PizzaOrder().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countPizzaOrders() {
        return entityManager().createQuery("SELECT COUNT(o) FROM PizzaOrder o", Long.class).getSingleResult();
    }

	public static List<PizzaOrder> findAllPizzaOrders() {
        return entityManager().createQuery("SELECT o FROM PizzaOrder o", PizzaOrder.class).getResultList();
    }

	public static PizzaOrder findPizzaOrder(Long id) {
        if (id == null) return null;
        return entityManager().find(PizzaOrder.class, id);
    }

	public static List<PizzaOrder> findPizzaOrderEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM PizzaOrder o", PizzaOrder.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	@Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }

	@Transactional
    public void remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            PizzaOrder attached = PizzaOrder.findPizzaOrder(this.id);
            this.entityManager.remove(attached);
        }
    }

	@Transactional
    public void flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }

	@Transactional
    public void clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }

	@Transactional
    public PizzaOrder merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        PizzaOrder merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

	@Version
    @Column(name = "version")
    private Integer version;

	public Long getId() {
        return this.id;
    }

	public void setId(Long id) {
        this.id = id;
    }

	public Integer getVersion() {
        return this.version;
    }

	public void setVersion(Integer version) {
        this.version = version;
    }
}
