/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.propertysheet;

import com.l2fprod.common.propertysheet.DefaultProperty;
import gui.propertysheet.abs.PropertyElement;
import org.zetool.common.localization.CommonLocalization;
import org.zetool.common.localization.Localization;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class GenericProperty extends DefaultProperty implements PropertyElement {

    boolean useAsLocString = false;
    private Localization loc = CommonLocalization.LOC;

    @Override
    public boolean isUsedAsLocString() {
        return useAsLocString;
    }

    @Override
    public void useAsLocString( boolean useAsLocString ) {
        this.useAsLocString = useAsLocString;
    }

    @Override
    public String getDisplayNameTag() {
        return super.getDisplayName();
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
    public String getShortDescription() {
        return isUsedAsLocString() ? loc.getString( super.getShortDescription() ) : super.getShortDescription();
    }

    public String getShortDescriptionTag() {
        return super.getShortDescription();
    }

    /**
     * Sets the description for the property. Note that you cannot change the
     * description if it shall be used as a tag for localized string. In that
     * case, you can only change the tag.
     * @param text the description
     */
    @Override
    public void setShortDescription( String text ) {
        super.setShortDescription( text );
    }

}
