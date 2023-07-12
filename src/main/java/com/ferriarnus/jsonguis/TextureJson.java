package com.ferriarnus.jsonguis;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

public class TextureJson extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new Gson();
    private Map<Pair<ResourceLocation, ResourceLocation>, ResourceLocation> map = new HashMap<>();

    public TextureJson() {
        super(GSON, "textureoverwrites");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> p_10793_, ResourceManager p_10794_, ProfilerFiller p_10795_) {
        Map<Pair<ResourceLocation, ResourceLocation>, ResourceLocation> map = Maps.newHashMap();

        for(Map.Entry<ResourceLocation, JsonElement> entry : p_10793_.entrySet()) {
            try {
                JsonObject jsonObject = entry.getValue().getAsJsonObject();
                ResourceLocation menuRL = new ResourceLocation(jsonObject.get("menu").getAsString());
                ResourceLocation oldTexture = new ResourceLocation(jsonObject.get("oldtexture").getAsString());
                ResourceLocation newTexture = new ResourceLocation(jsonObject.get("newtexture").getAsString());
                map.put(new ImmutablePair<>(menuRL, oldTexture), newTexture);
            } catch (Exception e) {

            }
        }
        this.map = map;
    }

    public ResourceLocation changeTextureIfNeeded(ResourceLocation menuType, ResourceLocation oldTexture) {
        Pair<ResourceLocation, ResourceLocation> key = new ImmutablePair<>(menuType, oldTexture);
        if (map.containsKey(key)) {
            return map.get(key);
        }
        return oldTexture;
    }
}
