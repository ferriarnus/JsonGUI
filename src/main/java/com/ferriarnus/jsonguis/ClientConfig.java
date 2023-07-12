package com.ferriarnus.jsonguis;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.BooleanValue CONTAINERNAMES = BUILDER
            .comment("Prints the container names in chat")
            .define("Print Container Names", false);

    static final ForgeConfigSpec SPEC = BUILDER.build();
}
