
package gui.propertysheet.types;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import gui.propertysheet.BasicProperty;
import gui.propertysheet.abs.AbstractPropertyConverter;
import java.util.function.Supplier;

/**
 *
 * @author Jan-Philipp Kappmeier
 * @param <P>
 * @param <T>
 */
public class GeneralPropertyConverter<P extends BasicProperty<T>, T> extends AbstractPropertyConverter<P, T> {

    public GeneralPropertyConverter(Supplier<P> propGenerator, String name, Class<T> type) {
        this.propGenerator = propGenerator;
        this.name = name;
        this.type = type;
    }
    
    Supplier<P> propGenerator;
    String name;
    Class<T> type;
    
    @Override
    public String getNodeName() {
        return name;
    }

    @Override
    public void createNewProp() {
        prop = propGenerator.get();
    }

    @Override
    public void writeValue(MarshallingContext context) {
        context.convertAnother(prop.getValue());
    }

    @Override
    public void readValue(UnmarshallingContext context) {
        T bool = (T) context.convertAnother(prop, type);
        prop.setValue(bool);
    }

    @Override
    public boolean canConvert(Class type) {
        return type.equals(type);
    }
}
