// jTDS JDBC Driver for Microsoft SQL Server and Sybase
// Copyright (C) 2004 The jTDS Project
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
//
package net.sourceforge.jtds.jdbc;

/**
 * This class encapsulates the MS SQL2000 UniqueIdentifer data type.
 *
 * @author Mike Hutchinson.
 * @version $Id: UniqueIdentifier.java,v 1.4 2005-04-28 14:29:30 alin_sinpalean Exp $
 */
public class UniqueIdentifier {
    private final byte[] bytes;

    /**
     * Construct a unique identifier with the supplied byte array.
     */
    public UniqueIdentifier(byte[] id) {
        bytes = id;
    }

    /**
     * Retrieve the unique identifier as a byte array.
     *
     * @return The unique identifier as a <code>byte[]</code>.
     */
    public byte[] getBytes() {
        return bytes.clone();
    }

    /**
     * Retrieve the unique identifier as a formatted string.
     * <p>Format is NNNNNNNN-NNNN-NNNN-NNNN-NNNNNNNNNNNN.
     *
     * @return The uniqueidentifier as a <code>String</code>.
     */
    public String toString() {
        byte[] tmp = bytes;

        if (bytes.length == 16) {
            // Need to swap some bytes for correct text version
            tmp = new byte[bytes.length];
            System.arraycopy(bytes, 0, tmp, 0, bytes.length);
            tmp[0] = bytes[3];
            tmp[1] = bytes[2];
            tmp[2] = bytes[1];
            tmp[3] = bytes[0];
            tmp[4] = bytes[5];
            tmp[5] = bytes[4];
            tmp[6] = bytes[7];
            tmp[7] = bytes[6];
        }

        byte bb[] = new byte[1];

        StringBuilder buf = new StringBuilder(36);

        for (int i = 0; i < bytes.length; i++) {
            bb[0] = tmp[i];
            buf.append(Support.toHex(bb));

            if (i == 3 || i == 5 || i == 7 || i == 9) {
                buf.append('-');
            }
        }

        return buf.toString();
    }
}
