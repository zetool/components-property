
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
    private final Class<T> valueType;
    private final Supplier<P> propGenerator;
    private final String name;
    private final Class<P> type;

    public GeneralPropertyConverter(Supplier<P> propGenerator, String name, Class<P> type, Class<T> valueType) {
        this.propGenerator = propGenerator;
        this.name = name;
        this.type = type;
        this.valueType = valueType;
    }
    
    
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
        T bool = (T) context.convertAnother(prop, valueType);
        prop.setValue(bool);
    }

    @Override
    public boolean canConvert(Class type) {
        return this.type.equals(type);
    }
}
