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
package gui.propertysheet;

import ds.PropertyContainer;
import gui.propertysheet.abs.PropertyValue;

/**
 *
 * @param <T> 
 * @author Jan-Philipp Kappmeier
 */
public class BasicProperty<T> extends GenericProperty implements PropertyValue<T> {

    private static final long serialVersionUID = 1L;
    public BasicProperty( ) {
        super();
    }

    public BasicProperty( String name, String displayName ) {
        super();
        setName( name );
        setDisplayName( displayName );
    }

    public static void reloadFromPropertyContainer(GenericProperty pe) {
        pe.setValue( PropertyContainer.getGlobal().get( pe.getName() ) );
    }

    /**
     * Returns the value for the property.
     * @return the value for the property
     */
    @Override
    @SuppressWarnings("unchecked")
    public T getValue() {
        return (T)super.getValue();
    }

    public void setPropertyValue( T defaultValue ) {
        setValue( defaultValue );
    }
}
