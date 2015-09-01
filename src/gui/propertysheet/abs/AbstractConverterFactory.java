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

import gui.propertysheet.BasicProperty;

/**
 *
 * @author kapman
 * @param <P>
 * @param <T>
 */
public abstract class AbstractConverterFactory<P extends BasicProperty<T>, T> implements ConverterFactory<P, T> {

    private final Class<P> propertyType;
    //private final Class<T> type;
    private final String name;

    public AbstractConverterFactory(String name, Class<P> propertyType) {
        this.propertyType = propertyType;
        //this.type = type;
        this.name = name;
    }

    @Override
    public abstract AbstractPropertyConverter<P, T> getConverter();

    @Override
    public String getName() {
        return name;
    }

//    public Class<T> getType() {
//        return type;
//    }

    @Override
    public Class<P> getPropertyType() {
        return propertyType;
    }
}
