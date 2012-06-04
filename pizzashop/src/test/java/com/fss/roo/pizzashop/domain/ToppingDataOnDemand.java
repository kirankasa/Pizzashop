package com.fss.roo.pizzashop.domain;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.dod.RooDataOnDemand;
import org.springframework.stereotype.Component;

@Configurable
@Component
@RooDataOnDemand(entity = Topping.class)
public class ToppingDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<Topping> data;

	public Topping getNewTransientTopping(int index) {
        Topping obj = new Topping();
        setName(obj, index);
        return obj;
    }

	public void setName(Topping obj, int index) {
        String name = "name_" + index;
        obj.setName(name);
    }

	public Topping getSpecificTopping(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Topping obj = data.get(index);
        Long id = obj.getId();
        return Topping.findTopping(id);
    }

	public Topping getRandomTopping() {
        init();
        Topping obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return Topping.findTopping(id);
    }

	public boolean modifyTopping(Topping obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = Topping.findToppingEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Topping' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Topping>();
        for (int i = 0; i < 10; i++) {
            Topping obj = getNewTransientTopping(i);
            try {
                obj.persist();
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            obj.flush();
            data.add(obj);
        }
    }
}
