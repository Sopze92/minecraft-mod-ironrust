package com.sopze.mc.ironrust.mixin;

import com.sopze.mc.ironrust.WipeUtil.ThreadTrackerUtil;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ChunkTaskDispatcher;
import net.minecraft.util.thread.TaskScheduler;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.storage.SerializableChunkData;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.file.Path;
import java.util.concurrent.Executor;

import static com.sopze.mc.ironrust.WipeUtil.ZONE_NAMES;

public class WatchersMixin {

  @Mixin(LevelStorageSource.class)
  public static class LevelStorageSourceMixin {

    @Inject( method= "readLevelDataTagRaw(Ljava/nio/file/Path;)Lnet/minecraft/nbt/CompoundTag;", at= @At( value= "HEAD" ))
    private static void h_readLevelDataTagFixed_h(Path path, CallbackInfoReturnable<CompoundTag> cir){ ThreadTrackerUtil.updateLevelStorage(path); }

    @Inject( method= "readLevelDataTagRaw(Ljava/nio/file/Path;)Lnet/minecraft/nbt/CompoundTag;", at= @At( value= "RETURN" ))
    private static void r_readLevelDataTagFixed_r(Path path, CallbackInfoReturnable<CompoundTag> cir){ cir.getReturnValue(); ThreadTrackerUtil.updateLevelStorage(null); }
  }

  @Mixin(SerializableChunkData.class)
  public static class SerializableChunkDataMixin {

    @ModifyVariable( method = "parse", ordinal=0, at = @At( value = "STORE", ordinal=0 ))
    private static ChunkPos h_parse_v(ChunkPos var){ ThreadTrackerUtil.updateThreadChunk(var); return var; }

    @Inject( method = "parse", at = @At( value= "RETURN" ))
    private static void h_parse_r(CallbackInfoReturnable<SerializableChunkData> cir) { ThreadTrackerUtil.updateThreadChunk(null); }
  }

  @Mixin(Util.class)
  public static class UtilMixin {

    @Inject( method= "runNamed", at= @At( "HEAD" ))
    private static void h_runNamed_h(Runnable runnable, String name, CallbackInfo ci){
      if(!ZONE_NAMES.contains(name)) ZONE_NAMES.add(name);

      //Thread t= Thread.currentThread();
      //System.out.printf("thread %d:%s started zone: %s%n", t.threadId(), t.getName(), name);
      ThreadTrackerUtil.updateThreadZone(name);
    }
  }

  @Mixin(ChunkTaskDispatcher.class)
  public static class ChunkTaskDispatcherMixin {

    @Inject( method= "<init>", at= @At( value = "HEAD", shift = At.Shift.BY, by=(2) ))
    private static void h_ctor_h(TaskScheduler<Runnable> executor, Executor dispatcher, CallbackInfo ci){
      //Thread t= Thread.currentThread();
      //System.out.printf("thread %d:%s started task: %s%n", t.threadId(), t.getName(), executor.getName());
      ThreadTrackerUtil.updateThreadTask(executor.name());
    }
  }
}