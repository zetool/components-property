/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zetool.components.property.converter;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import gui.propertysheet.GenericProperty;
import java.io.StringWriter;
import java.util.Arrays;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class ConverterTestHelper {
    
    public static String convertProperty(Converter conv, GenericProperty cp) {
        DataConverter dc = new DataConverter();
        XStream xstream = new XStream();
        xstream.registerConverter(conv);
        xstream.registerConverter(dc);
        xstream.alias("converterTest", DataHolder.class);

        DataHolder dh = new DataHolder(cp);
        StringWriter sw = new StringWriter();
        xstream.toXML(dh, sw);
        
        String[] lines = sw.toString().split("\n");
        return lines[1].trim();
    }


    static class DataHolder {
        GenericProperty c;
        public DataHolder(GenericProperty c) {
            this.c = c;
        }
    }

    static class DataConverter implements Converter {

        @Override
        public boolean canConvert(Class clazz) {
            return clazz.equals(DataHolder.class);
        }

        @Override
        public void marshal(Object value, HierarchicalStreamWriter writer,
                MarshallingContext context) {
            DataHolder person = (DataHolder) value;
            context.convertAnother(person.c);
        }

        @Override
        public Object unmarshal(HierarchicalStreamReader reader,
                UnmarshallingContext context) {
            DataHolder person = new DataHolder(null);
            return person;
        }

    }
}
