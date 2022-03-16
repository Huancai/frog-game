
package com.me.transport;

/**
 * Unresolved address.
 *
 * jupiter
 * org.jupiter.transport
 *
 * @author jiachun.fjc
 */
public interface UnresolvedAddress {

    String getHost();

    int getPort();

    /**
     * For unix domain socket.
     */
    String getPath();
}
