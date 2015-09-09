/* zet evacuation tool copyright (c) 2007-15 zet evacuation team
 *
 * This program is free software; you can redistribute it and/or
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */package gui.propertysheet.abs;

import com.thoughtworks.xstream.converters.Converter;
import gui.propertysheet.BasicProperty;
import gui.propertysheet.types.GeneralPropertyConverter;
import java.util.function.Supplier;

/**
 *
 * @author Jan-Philipp Kappmeier
 * @param <P>
 * @param <T>
 */
public class DefaultConverterFactory<P extends BasicProperty<T>, T> extends AbstractConverterFactory<P> {
    private final Supplier<P> sup;
    private final Class<T> valueType;
    
    public DefaultConverterFactory(String name, Supplier<P> sup, Class<P> propertyType, Class<T> valueType) {
        super(name, propertyType);
        this.sup = sup;
        this.valueType = valueType;
    }
    
    @Override
    public Converter getConverter() {
        return new GeneralPropertyConverter<>(sup, getName(), super.getPropertyType(), valueType);
    }
}
