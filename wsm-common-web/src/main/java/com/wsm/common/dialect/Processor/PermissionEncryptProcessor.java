package com.wsm.common.dialect.Processor;

import com.wsm.common.util.AjaxJson;
import com.wsm.sso.feign.consumer.SsoPermissionFeignConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.processor.element.AbstractMarkupSubstitutionElementProcessor;
import org.thymeleaf.spring4.context.SpringWebContext;

import java.util.ArrayList;
import java.util.List;

@Component
public class PermissionEncryptProcessor extends AbstractMarkupSubstitutionElementProcessor {

    @Autowired
    public PermissionEncryptProcessor() {
        super("permission");
    }

    @Override
    protected List<Node> getMarkupSubstitutes(Arguments arguments, Element element) {

        //获取标签name属性的值
        String value = element.getAttributeValue("value");

        ApplicationContext appCtx = ((SpringWebContext)arguments.getContext()).getApplicationContext();
        SsoPermissionFeignConsumer client = appCtx.getBean(SsoPermissionFeignConsumer.class);
        AjaxJson hasPermission = client.hasPermission(value);
        String display = "";
        if (!(boolean)hasPermission.getData()){
            display = "display:none";
        }

        List<Node> nodes = new ArrayList<>();

        /*Element container = new Element("div");
        container.setAttribute("style", display);
        Text text = new Text("");
        container.addChild(text);
        container.setChildren(element.getChildren());*/
        List<Element> elements = element.getElementChildren();
        if (elements != null && elements.size() > 0){
            for (Element el : elements){
                el.setAttribute("style", display);
            }
        }
        nodes.addAll(element.getChildren());
        return nodes;
    }

    @Override
    public int getPrecedence() {
        return 1000;
    }
}
