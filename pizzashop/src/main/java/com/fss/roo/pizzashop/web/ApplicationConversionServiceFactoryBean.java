package com.fss.roo.pizzashop.web;

import com.fss.roo.pizzashop.domain.Base;
import com.fss.roo.pizzashop.domain.Pizza;
import com.fss.roo.pizzashop.domain.PizzaOrder;
import com.fss.roo.pizzashop.domain.Topping;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.roo.addon.web.mvc.controller.converter.RooConversionService;

@Configurable
/**
 * A central place to register application converters and formatters. 
 */
@RooConversionService
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
	}

	public Converter<Base, String> getBaseToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.fss.roo.pizzashop.domain.Base, java.lang.String>() {
            public String convert(Base base) {
                return new StringBuilder().append(base.getName()).toString();
            }
        };
    }

	public Converter<Long, Base> getIdToBaseConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.fss.roo.pizzashop.domain.Base>() {
            public com.fss.roo.pizzashop.domain.Base convert(java.lang.Long id) {
                return Base.findBase(id);
            }
        };
    }

	public Converter<String, Base> getStringToBaseConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.fss.roo.pizzashop.domain.Base>() {
            public com.fss.roo.pizzashop.domain.Base convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Base.class);
            }
        };
    }

	public Converter<Pizza, String> getPizzaToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.fss.roo.pizzashop.domain.Pizza, java.lang.String>() {
            public String convert(Pizza pizza) {
                return new StringBuilder().append(pizza.getName()).append(' ').append(pizza.getPrice()).toString();
            }
        };
    }

	public Converter<Long, Pizza> getIdToPizzaConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.fss.roo.pizzashop.domain.Pizza>() {
            public com.fss.roo.pizzashop.domain.Pizza convert(java.lang.Long id) {
                return Pizza.findPizza(id);
            }
        };
    }

	public Converter<String, Pizza> getStringToPizzaConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.fss.roo.pizzashop.domain.Pizza>() {
            public com.fss.roo.pizzashop.domain.Pizza convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Pizza.class);
            }
        };
    }

	public Converter<PizzaOrder, String> getPizzaOrderToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.fss.roo.pizzashop.domain.PizzaOrder, java.lang.String>() {
            public String convert(PizzaOrder pizzaOrder) {
                return new StringBuilder().append(pizzaOrder.getName()).append(' ').append(pizzaOrder.getAddress()).append(' ').append(pizzaOrder.getTotal()).append(' ').append(pizzaOrder.getDeliveryDate()).toString();
            }
        };
    }

	public Converter<Long, PizzaOrder> getIdToPizzaOrderConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.fss.roo.pizzashop.domain.PizzaOrder>() {
            public com.fss.roo.pizzashop.domain.PizzaOrder convert(java.lang.Long id) {
                return PizzaOrder.findPizzaOrder(id);
            }
        };
    }

	public Converter<String, PizzaOrder> getStringToPizzaOrderConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.fss.roo.pizzashop.domain.PizzaOrder>() {
            public com.fss.roo.pizzashop.domain.PizzaOrder convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), PizzaOrder.class);
            }
        };
    }

	public Converter<Topping, String> getToppingToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.fss.roo.pizzashop.domain.Topping, java.lang.String>() {
            public String convert(Topping topping) {
                return new StringBuilder().append(topping.getName()).toString();
            }
        };
    }

	public Converter<Long, Topping> getIdToToppingConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.fss.roo.pizzashop.domain.Topping>() {
            public com.fss.roo.pizzashop.domain.Topping convert(java.lang.Long id) {
                return Topping.findTopping(id);
            }
        };
    }

	public Converter<String, Topping> getStringToToppingConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.fss.roo.pizzashop.domain.Topping>() {
            public com.fss.roo.pizzashop.domain.Topping convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Topping.class);
            }
        };
    }

	public void installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getBaseToStringConverter());
        registry.addConverter(getIdToBaseConverter());
        registry.addConverter(getStringToBaseConverter());
        registry.addConverter(getPizzaToStringConverter());
        registry.addConverter(getIdToPizzaConverter());
        registry.addConverter(getStringToPizzaConverter());
        registry.addConverter(getPizzaOrderToStringConverter());
        registry.addConverter(getIdToPizzaOrderConverter());
        registry.addConverter(getStringToPizzaOrderConverter());
        registry.addConverter(getToppingToStringConverter());
        registry.addConverter(getIdToToppingConverter());
        registry.addConverter(getStringToToppingConverter());
    }

	public void afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
}
