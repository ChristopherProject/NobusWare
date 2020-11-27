// 
// Decompiled by Procyon v0.5.36
// 

package com.github.creeper123123321.viafabric.util;

import java.util.stream.Stream;
import java.util.Arrays;
import java.util.function.Function;
import us.myles.ViaVersion.api.protocol.ProtocolVersion;
import java.util.function.Predicate;

public class VersionFormatFilter implements Predicate<String>
{
    @Override
    public boolean test(final String s) {
        try {
            Integer.parseInt(s);
            return true;
        }
        catch (NumberFormatException e) {
            try {
                Integer.parseInt(s + '0');
                return true;
            }
            catch (NumberFormatException e2) {
               return false;
            	// return ProtocolVersion.getProtocols().stream().map((Function<? super Object, ?>)ProtocolVersion.get).flatMap(str -> Stream.concat((Stream<?>)Arrays.stream(str.split("-")), (Stream<?>)Arrays.stream((T[])new String[] { str }))).anyMatch(ver -> ver.startsWith(s));
            }
        }
    }
}
