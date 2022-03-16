
package com.me.transport;

import java.io.File;

/**
 * Unresolved address.
 *
 * jupiter
 * org.jupiter.transport
 *
 * @author jiachun.fjc
 */
public class UnresolvedDomainAddress implements UnresolvedAddress {

    private final String socketPath;

    public UnresolvedDomainAddress(String socketPath) {
        if (socketPath == null) {
            throw new NullPointerException("socketPath");
        }
        this.socketPath = socketPath;
    }

    public UnresolvedDomainAddress(File file) {
        this(file.getPath());
    }

    @Override
    public String getHost() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getPort() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getPath() {
        return socketPath;
    }

    @Override
    public boolean equals(Object o) {
        return this == o || o instanceof UnresolvedDomainAddress && ((UnresolvedDomainAddress) o).socketPath.equals(socketPath);

    }

    @Override
    public int hashCode() {
        return socketPath.hashCode();
    }

    @Override
    public String toString() {
        return getPath();
    }
}
