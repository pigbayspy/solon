package org.noear.solon.boot.websocket.netty;

import io.netty.channel.ChannelHandlerContext;
import org.noear.solon.core.SignalType;
import org.noear.solon.core.message.Session;
import org.noear.solon.socketd.SessionManager;

import java.util.Collection;
import java.util.Collections;

public class _SessionManagerImpl extends SessionManager {
    @Override
    protected SignalType signalType() {
        return SignalType.WEBSOCKET;
    }

    @Override
    public Session getSession(Object conn) {
        if (conn instanceof ChannelHandlerContext) {
            return _SocketServerSession.get((ChannelHandlerContext) conn);
        } else {
            throw new IllegalArgumentException("This conn requires a java_websocket WebSocket type");
        }
    }

    @Override
    public Collection<Session> getOpenSessions() {
        return Collections.unmodifiableCollection(_SocketServerSession.sessions.values());
    }

    @Override
    public void removeSession(Object conn) {
        if (conn instanceof ChannelHandlerContext) {
            _SocketServerSession.remove((ChannelHandlerContext) conn);
        } else {
            throw new IllegalArgumentException("This conn requires a java_websocket WebSocket type");
        }
    }
}
