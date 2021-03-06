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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package gui.propertysheet.abs;

import gui.propertysheet.BasicProperty;

/**
 *
 * @param <P> The property type.
 * @param <T> The value stored by the property.
 * @author Jan-Philipp Kappmeier
 */
public class ManualConverterFactory<P extends BasicProperty<T>, T> extends AbstractConverterFactory<P> {
    private final AbstractPropertyConverter<P> converter;

    public ManualConverterFactory(AbstractPropertyConverter<P> converter, Class<P> propertyType) {
        super(converter.getNodeName(), propertyType);
        this.converter = converter;
    }
    
    @Override
    public AbstractPropertyConverter<P> getConverter() {
        return converter;
    }
}
