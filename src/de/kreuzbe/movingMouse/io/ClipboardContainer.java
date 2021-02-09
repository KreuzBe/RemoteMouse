package de.kreuzbe.movingMouse.io;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

public class ClipboardContainer implements Transferable, Serializable {

    private final HashMap<DataFlavor, Serializable> dataStorage = new HashMap<>();

    public void put(DataFlavor flavor, Serializable o) {
        dataStorage.put(flavor, o);
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return dataStorage.keySet().toArray(new DataFlavor[0]);
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return dataStorage.containsKey(flavor);
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        return dataStorage.get(flavor);
    }
}
