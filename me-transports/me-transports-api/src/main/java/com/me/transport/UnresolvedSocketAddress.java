
package com.me.transport;

import com.me.common.util.Requires;

/**
 * Unresolved address.
 * <p>
 * jupiter
 * org.jupiter.transport
 *
 * @author jiachun.fjc
 */
public class UnresolvedSocketAddress implements UnresolvedAddress {

    private final String host;
    private final int port;

    public UnresolvedSocketAddress(String host, int port) {
        Requires.requireNotNull(host, "host can't be null");
        Requires.requireTrue(port > 0 && port < 0xFFFF, "port out of range:" + port);

        this.host = host;
        this.port = port;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public String getPath() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UnresolvedSocketAddress that = (UnresolvedSocketAddress) o;

        return port == that.port && host.equals(that.host);
    }

    @Override
    public int hashCode() {
        int result = host.hashCode();
        result = 31 * result + port;
        return result;
    }

    @Override
    public String toString() {
        return host + ':' + port;
    }
}
