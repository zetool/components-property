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
import gui.propertysheet.abs.PropertyElement;
import java.io.IOException;
import java.util.Objects;
import org.zetool.common.localization.CommonLocalization;
import org.zetool.common.localization.Localization;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
@SuppressWarnings("serial")
public class GenericProperty extends DefaultProperty implements PropertyElement {

    private boolean useAsLocString = false;
    protected Localization loc = CommonLocalization.LOC;

    public GenericProperty() {
        super();
        setDisplayName("");
        super.setShortDescription("");
    }

    @Override
    public boolean isUsedAsLocString() {
        return useAsLocString;
    }

    @Override
    public void useAsLocString(boolean useAsLocString) {
        this.useAsLocString = useAsLocString;
    }

    public void setLoc(Localization loc) {
        this.loc = loc;
    }

    @Override
    public String getDisplayNameTag() {
        return super.getDisplayName();
    }

    /**
     * Returns the name of the property for displaying in components. If localization is active the displayed name is
     * the localized text according to the tag ({@link #getDisplayNameTag()}.
     *
     * @return the detailed description for the property
     */
    @Override
    public String getDisplayName() {
        return isUsedAsLocString() ? loc.getString(super.getDisplayName()) : super.getDisplayName();
    }

    /**
     * Returns the detailed description for the property. If localization is active the description is the localized
     * text according to the tag ({@link #getShortDescriptionTag()}.
     *
     * @return the detailed description for the property
     */
    @Override
    public String getShortDescription() {
        return isUsedAsLocString() ? loc.getString(super.getShortDescription()) : super.getShortDescription();
    }

    public String getShortDescriptionTag() {
        return super.getShortDescription();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return equalsMainProperties((GenericProperty) obj);
    }
    
    /**
     * Checks if two {@code GenericProperty} objects main properties are equal.
     * @param other
     * @return 
     */
    protected final boolean equalsMainProperties(GenericProperty other) {
        return Objects.equals(this.getName(), other.getName());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.getName());
        return hash;
    }

    /** Prohibits serialization. */
    private synchronized void writeObject(java.io.ObjectOutputStream s) throws IOException {
        throw new UnsupportedOperationException("Serialization of " + this.getClass() + " is not supported");
    }
    
    /** Prohibits serialization. */
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException("Deserialization of " + this.getClass() + " is not supported");
    }
}
