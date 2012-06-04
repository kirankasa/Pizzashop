package com.fss.roo.pizzashop.web;

import com.fss.roo.pizzashop.domain.Pizza;
import com.fss.roo.pizzashop.domain.PizzaOrder;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

@RequestMapping("/pizzaorders")
@Controller
@RooWebScaffold(path = "pizzaorders", formBackingObject = PizzaOrder.class)
public class PizzaOrderController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid PizzaOrder pizzaOrder, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, pizzaOrder);
            return "pizzaorders/create";
        }
        uiModel.asMap().clear();
        pizzaOrder.persist();
        return "redirect:/pizzaorders/" + encodeUrlPathSegment(pizzaOrder.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new PizzaOrder());
        return "pizzaorders/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("pizzaorder", PizzaOrder.findPizzaOrder(id));
        uiModel.addAttribute("itemId", id);
        return "pizzaorders/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("pizzaorders", PizzaOrder.findPizzaOrderEntries(firstResult, sizeNo));
            float nrOfPages = (float) PizzaOrder.countPizzaOrders() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("pizzaorders", PizzaOrder.findAllPizzaOrders());
        }
        addDateTimeFormatPatterns(uiModel);
        return "pizzaorders/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid PizzaOrder pizzaOrder, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, pizzaOrder);
            return "pizzaorders/update";
        }
        uiModel.asMap().clear();
        pizzaOrder.merge();
        return "redirect:/pizzaorders/" + encodeUrlPathSegment(pizzaOrder.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, PizzaOrder.findPizzaOrder(id));
        return "pizzaorders/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        PizzaOrder pizzaOrder = PizzaOrder.findPizzaOrder(id);
        pizzaOrder.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/pizzaorders";
    }

	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("pizzaOrder_deliverydate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }

	void populateEditForm(Model uiModel, PizzaOrder pizzaOrder) {
        uiModel.addAttribute("pizzaOrder", pizzaOrder);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("pizzas", Pizza.findAllPizzas());
    }

	String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
}
