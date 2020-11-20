package net.minecraft.client.resources;

import java.util.UUID;
import net.minecraft.util.ResourceLocation;

public class DefaultPlayerSkin
{
    private static final ResourceLocation field_177337_a = new ResourceLocation("nobita/b.png");
    private static final ResourceLocation field_177336_b = new ResourceLocation("nobita/a.png");
    private static final String __OBFID = "CL_00002396";

    public static ResourceLocation func_177335_a()
    {
        return field_177337_a;
    }

    public static ResourceLocation func_177334_a(UUID p_177334_0_)
    {
        return func_177333_c(null) ? field_177336_b : field_177337_a;
    }

    public static String func_177332_b(UUID p_177332_0_)
    {
        return func_177333_c(null) ? "slim" : "default";
    }

    private static boolean func_177333_c(UUID p_177333_0_)
    {
    	//(p_177333_0_.hashCode() & 1) ==1
        return false;
    }
}
