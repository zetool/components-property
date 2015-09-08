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
 */
package org.zetool.components.property;

import gui.propertysheet.GenericProperty;
import gui.propertysheet.abs.ConverterFactory;
import gui.propertysheet.abs.DefaultConverterFactory;
import gui.propertysheet.abs.ManualConverterFactory;
import gui.propertysheet.types.BooleanProperty;
import gui.propertysheet.types.ColorProperty;
import gui.propertysheet.types.ColorPropertyConverter;
import gui.propertysheet.types.DoubleProperty;
import gui.propertysheet.types.EnumConverter;
import gui.propertysheet.types.EnumProperty;
import gui.propertysheet.types.IntegerProperty;
import gui.propertysheet.types.IntegerRangeProperty;
import gui.propertysheet.types.IntegerRangePropertyConverter;
import gui.propertysheet.types.StringListProperty;
import gui.propertysheet.types.StringListPropertyConverter;
import gui.propertysheet.types.StringProperty;
import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class PropertyConverterLibrary implements Iterable<ConverterFactory<? extends GenericProperty>> {
    private final Map<String, ConverterFactory<? extends GenericProperty>> converterMap = new HashMap<>();
    
    public static final ConverterFactory<BooleanProperty> BOOL_CONVERTER_FACTORY
            = new DefaultConverterFactory<>("boolNode", () -> new BooleanProperty(), BooleanProperty.class, Boolean.class);
    public static final ConverterFactory<IntegerProperty> INT_CONVERTER_FACTORY
            = new DefaultConverterFactory<>("intNode", () -> new IntegerProperty(), IntegerProperty.class, Integer.class);
    public static final ConverterFactory<DoubleProperty> DOUBLE_CONVERTER_FACTORY
            = new DefaultConverterFactory<>("doubleNode", () -> new DoubleProperty(), DoubleProperty.class, Double.class);
    public static final ConverterFactory<StringProperty> STRING_CONVERTER_FACTORY
            = new DefaultConverterFactory<>("stringNode", () -> new StringProperty(), StringProperty.class, String.class);
    public static final ConverterFactory<ColorProperty> COLOR_CONVERTER_FACTORY
            = new DefaultConverterFactory<>(ColorPropertyConverter.NODE_NAME, () -> new ColorProperty(), ColorProperty.class, Color.class);
    public static final ConverterFactory<EnumProperty> ENUM_CONVERTER_FACTORY
            = new ManualConverterFactory<>(new EnumConverter(), EnumProperty.class);
    public static final ConverterFactory<IntegerRangeProperty> INT_RANGE_CONVERTER_FACTORY
            = new ManualConverterFactory<>(new IntegerRangePropertyConverter(), IntegerRangeProperty.class);
    public static final ConverterFactory<StringListProperty> STRING_LIST_CONVERTER_FACTORY
            = new ManualConverterFactory<>(new StringListPropertyConverter(), StringListProperty.class);
    public static final ConverterFactory<ColorProperty> COLOR_CONVERTER_FACTORY_ALTERNATIVE
            = new ManualConverterFactory<>(new ColorPropertyConverter(), ColorProperty.class);

    public static final PropertyConverterLibrary createDefaultConverters() {
        PropertyConverterLibrary converters = new PropertyConverterLibrary();
        converters.registerPropertyConverterFactory(BOOL_CONVERTER_FACTORY);
        converters.registerPropertyConverterFactory(INT_CONVERTER_FACTORY);
        converters.registerPropertyConverterFactory(DOUBLE_CONVERTER_FACTORY);
        converters.registerPropertyConverterFactory(STRING_CONVERTER_FACTORY);
        converters.registerPropertyConverterFactory(COLOR_CONVERTER_FACTORY_ALTERNATIVE);
        converters.registerPropertyConverterFactory(ENUM_CONVERTER_FACTORY);
        converters.registerPropertyConverterFactory(INT_RANGE_CONVERTER_FACTORY);
        converters.registerPropertyConverterFactory(STRING_LIST_CONVERTER_FACTORY);
        return converters;
    }
    
    public final void registerPropertyConverterFactory(ConverterFactory<? extends GenericProperty> factory) {
        converterMap.put(factory.getName(), factory);
    }

    @Override
    public Iterator<ConverterFactory<? extends GenericProperty>> iterator() {
        return converterMap.values().iterator();
    }

    public ConverterFactory<GenericProperty> getFactoryFor(String nodeName) {
        return (ConverterFactory<GenericProperty>) converterMap.get(nodeName);
    }
    
    public boolean canConvert(Class propertyClass) {
        return converterMap.values().stream().anyMatch(factory -> factory.getPropertyType().equals(propertyClass));
    }
    
}
