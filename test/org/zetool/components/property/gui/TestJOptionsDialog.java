package org.zetool.components.property.gui;

import gui.propertysheet.JOptionsDialog;
import gui.propertysheet.PropertyTreeModel;
import gui.propertysheet.PropertyTreeNode;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import static org.zetool.components.property.SerializationMatchers.notDeserializable;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class TestJOptionsDialog {

    @Test(expected = NotSerializableException.class)
    public void testSerializationFails() throws IOException {
        JOptionsDialog dialog = new JOptionsDialog(new PropertyTreeModel(new PropertyTreeNode("test")), false);
        ObjectOutputStream out = new ObjectOutputStream(new ByteArrayOutputStream());
        out.writeObject(dialog);
    }
    
    @Test
    public void testDeserializationNotSupported() {
        assertThat(JOptionsDialog.class, is(notDeserializable()));
    }
}
