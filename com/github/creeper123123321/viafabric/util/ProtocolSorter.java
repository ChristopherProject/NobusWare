// 
// Decompiled by Procyon v0.5.36
// 

package com.github.creeper123123321.viafabric.util;

import us.myles.ViaVersion.api.protocol.ProtocolRegistry;
import java.util.Optional;
import us.myles.ViaVersion.api.protocol.ProtocolVersion;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.ArrayList;
import java.util.List;

public class ProtocolSorter
{
    private static List<Protocols> protocols;
    
    public static void init() {
        ProtocolSorter.protocols = new ArrayList<Protocols>();
        Protocols.getProtocols().stream().filter(t -> !t.getName().equalsIgnoreCase("UNKNOWN")).forEach(t -> ProtocolSorter.protocols.add(t));
        ProtocolSorter.protocols.sort((v1, v2) -> v2.getId() - v1.getId());
        ProtocolSorter.protocols.removeIf(t -> !isSupported(t.getId()));
    }
    
    public static int getIndexOfProtocol(final int protocol) {
        return ProtocolSorter.protocols.stream().map(Protocols::getId).collect(Collectors.toList()).indexOf(protocol);
    }
    
    public static int getProtocolFromIndex(final int index) {
        return ProtocolSorter.protocols.stream().map(Protocols::getId).collect(Collectors.toList()).get(index);
    }
    public static String getProtocolName(final int protocol) {
        final Optional<ProtocolVersion> opt = ProtocolVersion.getProtocols().stream().filter(t -> t.getId() == protocol).findFirst();
        return opt.map((Function<? super ProtocolVersion, ? extends String>)ProtocolVersion::getName).orElse(null);
    }
    
    public static boolean isSupported(final int protocol) {
        return ProtocolRegistry.getProtocolPath(ProtocolRegistry.SERVER_PROTOCOL, protocol) != null || ProtocolRegistry.SERVER_PROTOCOL == protocol;
    }
    
    public static List<Protocols> getProtocols() {
        return ProtocolSorter.protocols;
    }
}
