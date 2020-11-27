// 
// Decompiled by Procyon v0.5.36
// 

package com.github.creeper123123321.viafabric.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Collections;
import java.util.Collection;
import java.util.ArrayList;
import com.google.common.base.Preconditions;
import java.util.List;
import java.util.Map;

public class Protocols
{
    private static final Map<Integer, Protocols> versions;
    private static final List<Protocols> versionList;
    public static final Protocols v1_2_5;
    public static final Protocols v1_4_6;
    public static final Protocols v1_5_1;
    public static final Protocols v1_5_2;
    public static final Protocols v_1_6_1;
    public static final Protocols v_1_6_2;
    public static final Protocols v_1_6_3;
    public static final Protocols v_1_6_4;
    public static final Protocols v1_7_1;
    public static final Protocols v1_7;
    public static final Protocols v1_8;
    public static final Protocols v1_9;
    public static final Protocols v1_9_1;
    public static final Protocols v1_9_2;
    public static final Protocols v1_9_3;
    public static final Protocols v1_10;
    public static final Protocols v1_11;
    public static final Protocols v1_11_1;
    public static final Protocols v1_12;
    public static final Protocols v1_12_1;
    public static final Protocols v1_12_2;
    public static final Protocols v1_13;
    public static final Protocols v1_13_1;
    public static final Protocols v1_13_2;
    public static final Protocols v1_14;
    public static final Protocols v1_14_1;
    public static final Protocols v1_14_2;
    public static final Protocols v1_14_3;
    public static final Protocols v1_14_4;
    public static final Protocols v1_15;
    public static final Protocols v1_15_1;
    public static final Protocols v1_15_2;
    public static final Protocols v1_16;
    public static final Protocols v1_16_1;
    public static final Protocols v1_16_2;
    public static final Protocols v1_16_3;
    public static final Protocols unknown;
    private final int id;
    private final String name;
    
    public Protocols(final int id, final String name) {
        this.id = id;
        this.name = name;
    }
    
    public static void register(final Protocols protocol) {
        Preconditions.checkNotNull(protocol);
        Protocols.versions.put(protocol.getId(), protocol);
        Protocols.versionList.add(protocol);
    }
    
    public static boolean isRegistered(final int id) {
        return Protocols.versions.containsKey(id);
    }
    
    public static Protocols getProtocol(final int id) {
        final Protocols Protocols = com.github.creeper123123321.viafabric.util.Protocols.versions.get(id);
        return (Protocols != null) ? Protocols : new Protocols(id, "Unknown (" + id + ")");
    }
    
    public static int getIndex(final Protocols version) {
        return Protocols.versionList.indexOf(version);
    }
    
    public static List<Protocols> getProtocols() {
        return Collections.unmodifiableList((List<? extends Protocols>)new ArrayList<Protocols>(Protocols.versions.values()));
    }
    
    public static Protocols getClosest(final String protocol) {
        for (final Protocols version : Protocols.versions.values()) {
            if (version.getName().equals(protocol)) {
                return version;
            }
            if (version.getName().equals(protocol + "")) {
                return version;
            }
            final String[] var2;
            final String[] parts = var2 = version.getName().split("-");
            for (int var3 = parts.length, var4 = 0; var4 < var3; ++var4) {
                final String part = var2[var4];
                if (part.equalsIgnoreCase(protocol)) {
                    return version;
                }
                if (part.equals(protocol + "")) {
                    return version;
                }
            }
        }
        return null;
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o != null && this.getClass() == o.getClass()) {
            final Protocols that = (Protocols)o;
            return this.id == that.id;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return this.id;
    }
    
    @Override
    public String toString() {
        return String.format("%s(%d)", this.getName(), this.getId());
    }
    
    static {
        versions = new HashMap<Integer, Protocols>();
        versionList = new ArrayList<Protocols>();
        register(v1_2_5 = new Protocols(29, "1.2.5"));
        register(v1_4_6 = new Protocols(51, "1.4.6"));
        register(v1_5_1 = new Protocols(60, "1.5.1"));
        register(v1_5_2 = new Protocols(61, "1.5.2"));
        register(v_1_6_1 = new Protocols(73, "1.6.1"));
        register(v_1_6_2 = new Protocols(74, "1.6.2"));
        register(v_1_6_3 = new Protocols(77, "1.6.3"));
        register(v_1_6_4 = new Protocols(78, "1.6.4"));
        register(v1_7_1 = new Protocols(4, "1.7-1.7.5"));
        register(v1_7 = new Protocols(5, "1.7.6-1.7.10"));
        register(v1_8 = new Protocols(47, "1.8"));
        register(v1_9 = new Protocols(107, "1.9"));
        register(v1_9_1 = new Protocols(108, "1.9.1"));
        register(v1_9_2 = new Protocols(109, "1.9.2"));
        register(v1_9_3 = new Protocols(110, "1.9.3/4"));
        register(v1_10 = new Protocols(210, "1.10"));
        register(v1_11 = new Protocols(315, "1.11"));
        register(v1_11_1 = new Protocols(316, "1.11.1"));
        register(v1_12 = new Protocols(335, "1.12"));
        register(v1_12_1 = new Protocols(338, "1.12.1"));
        register(v1_12_2 = new Protocols(340, "1.12.2"));
        register(v1_13 = new Protocols(393, "1.13"));
        register(v1_13_1 = new Protocols(401, "1.13.1"));
        register(v1_13_2 = new Protocols(404, "1.13.2"));
        register(v1_14 = new Protocols(477, "1.14"));
        register(v1_14_1 = new Protocols(480, "1.14.1"));
        register(v1_14_2 = new Protocols(485, "1.14.2"));
        register(v1_14_3 = new Protocols(490, "1.14.3"));
        register(v1_14_4 = new Protocols(498, "1.14.4"));
        register(v1_15 = new Protocols(573, "1.15"));
        register(v1_15_1 = new Protocols(575, "1.15.1"));
        register(v1_15_2 = new Protocols(578, "1.15.2"));
        register(v1_16 = new Protocols(735, "1.16"));
        register(v1_16_1 = new Protocols(736, "1.16.1"));
        register(v1_16_2 = new Protocols(751, "1.16.2"));
        register(v1_16_3 = new Protocols(753, "1.16.3"));
        register(unknown = new Protocols(-1, "UNKNOWN"));
    }
}
