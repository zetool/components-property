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

import com.l2fprod.common.propertysheet.DefaultProperty;
import ds.PropertyContainer;
import gui.propertysheet.abs.PropertyElement;
import gui.propertysheet.abs.PropertyValue;
import org.zetool.common.localization.CommonLocalization;
import org.zetool.common.localization.Localization;

/**
 *
 * @param <T> 
 * @author Jan-Philipp Kappmeier
 */
public class BasicProperty<T> extends DefaultProperty implements PropertyValue<T>, PropertyElement {
    private static final long serialVersionUID = 1L;
    boolean useAsLocString = false;
    private Localization loc = CommonLocalization.LOC;
    public BasicProperty( ) {
        super();
    }

    public BasicProperty( String name, String displayName ) {
        super();
        setName( name );
        setDisplayName( displayName );
    }

    public void store() {
        try {
            if( getName() != null && PropertyContainer.getGlobal().isDefined( getName() ) )
                PropertyContainer.getGlobal().set( getName(), getValue() );
            else
                System.out.println( "NOT DEFINED: " + getName() );
            
        } catch( Exception e ) {
            System.out.println( "ERROR STORING THIS" );
        }
    }

    public void reloadFromPropertyContainer() {
        setPropertyValue( (T)PropertyContainer.getGlobal().get( getName() ) );
    }

    /**
     * Returns the detailed description for the property.
     * @return the detailed description for the property
     */
    @Override
    public String getDisplayName() {
        return isUsedAsLocString() ? loc.getString( super.getDisplayName() ) : super.getDisplayName();
    }

    @Override
    public String getDisplayNameTag() {
        return super.getDisplayName();
    }

    /**
     * Returns the value for the property.
     * @return the value for the property
     */
    @Override
    public T getValue() {
        return (T)super.getValue();
    }

    @Override
    public String getShortDescription() {
        return isUsedAsLocString() ? loc.getString( super.getShortDescription() ) : super.getShortDescription();
    }

    @Override
    public String getShortDescriptionTag() {
        return super.getShortDescription();
    }

    /**
     * Sets the description for the property. Note that you cannot change the
     * description if it shall be used as a tag for localized string. In that
     * case, you can only change the tag.
     * @param text the description
     */
    
    public void setShortDescription( String text ) {
        super.setShortDescription( text );
    }

    /**
     * Returns the name of the property. That is the name by which it can be
     * accessed in the {@link PropertyContainer}.
     * @return the name of the property
     */
//    @Override
//    public String getPropertyName() {
//        return property;
//    }

    /**
     * Sets the name of the property. That is the name by which it can be accessed
     * using the {@link PropertyContainer}.
     * @param property the name of the property
     * @return  
     */
//    @Override
//    public void setPropertyName( String property ) {
//        this.property = property;
//    }

    @Override
    public boolean isUsedAsLocString() {
        return useAsLocString;
    }

    @Override
    public void useAsLocString( boolean useAsLocString ) {
        this.useAsLocString = useAsLocString;
    }

//    @Override
//    public String getNameTag() {
//        return getName();
//    }    

    @Override
    public void setPropertyValue( T defaultValue ) {
        setValue( defaultValue );
    }

}
